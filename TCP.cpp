//
// Created by marina on 10.03.19.
//

#include <iostream>
#include <sys/types.h>
#include <sys/socket.h>
#include <cstdlib>
#include <netinet/in.h>
#include <string.h>
#include <netdb.h>
#include <zconf.h>
#include <arpa/inet.h>
#include <cstdint>
#include <cstring>
#include <ifaddrs.h>
#include "tokenRing.cpp"

using namespace std;


void init(int, char**);
void print_instructions();
void create_client();
void create_server();
void *create_client(void*);
void *create_server(void*);
void error(const char *msg);
void close_sockets();
void init_epoll();
void connect_client();
void rebuild_token_ring();
void handle_token(int fd);
void handle_data(token);
void handle_rebuild(token, sockaddr_in);
void handle_empty();
void handle_rebuild_with_adr(token);
int reconnect(token);
void disconnect_client(int fd);
void send_token(token);
token receive_token(int fd, sockaddr_in *);





int main(int argc, char **argv) {
    init(argc, argv);

    create_server();
    init_epoll();
    init_multicast();
    create_client();
    atexit(close_sockets);
    start_running();
    rebuild_token_ring();

    if (client_options.token)
        send_token(empty_token());


    struct epoll_event ev;
    while(true) {
        if (epoll_wait(epoll_fd, &ev, 1, -1) == -1)
            error("Epoll waiting error\n");
        if (ev.data.fd == sockfd_in)
            connect_client();
        else handle_token(ev.data.fd);
    }
}

void init(int argc, char **argv){
    /*
     * init options of this node
     */

    if (argc!=6){
        print_instructions();
        error("WRONG AMOUNT OF ARGS\n");
    }
    client_options.id = argv[1];
    client_options.local_port = atoi(argv[2]);
    client_options.ip = argv[3];
    client_options.port = atoi(argv[4]);
    client_options.token = atoi(argv[5]);

    if (!client_options.token==0 && !client_options.token==1){
        print_instructions();
        error("WRONG TOKEN:0/1\n");
    }
}

void create_server(){
    /*
     * creating socket, bind and listen. It will work like server.
     */
    struct sockaddr_in serv_addr;

    sockfd_in = socket(AF_INET, SOCK_STREAM, 0);
    if (sockfd_in < 0)
        error("ERROR while opening socket");
    bzero((char *) &serv_addr, sizeof(serv_addr));

    serv_addr.sin_family = AF_INET;
    serv_addr.sin_addr.s_addr = INADDR_ANY;//server address
    serv_addr.sin_port = htons(client_options.local_port);//convert from host byte order to network byte order

    if (bind(sockfd_in, (struct sockaddr *) &serv_addr,
             sizeof(serv_addr)) < 0)
        error("ERROR on binding");

    listen(sockfd_in,5);
}

void create_client() {
    if(sockfd_out != 0){
        if (shutdown(sockfd_out, SHUT_RDWR) == -1) error("Error while shuting down a socket\n");
        if (close(sockfd_out) == -1) error("Error while closing socket\n");
        sockfd_out = 0;
    }
    struct sockaddr_in serv_addr;
    sockfd_out = socket(AF_INET, SOCK_STREAM, 0);
    if (sockfd_out < 0)
        error("ERROR opening socket");
    bzero((char *) &serv_addr, sizeof(serv_addr));
    serv_addr.sin_family = AF_INET;
    serv_addr.sin_port = htons(client_options.port);
    serv_addr.sin_addr.s_addr = inet_addr(client_options.ip);
    if (connect(sockfd_out, (struct sockaddr *) &serv_addr, sizeof(serv_addr)) < 0)
        error("ERROR connecting");
}


void init_epoll(){
    if ((epoll_fd = epoll_create1(0)) == -1){
        error("Error while creating epoll\n");
    };

    struct epoll_event event;
    event.events = EPOLLIN ;
    event.data.fd = sockfd_in;
    if (epoll_ctl(epoll_fd, EPOLL_CTL_ADD, sockfd_in, &event) == -1)//add descriptor to epoll_fd
        error("Failed to add socket to epoll\n");

}

void rebuild_token_ring(){
    rebuild_options rebuild_options;

    bzero(&rebuild_options, sizeof(rebuild_options));
    rebuild_options.old_port = client_options.port;
    rebuild_options.new_port = client_options.local_port;
    strcpy(rebuild_options.old_ip, client_options.ip);

    /*
    struct sockaddr_in my_addr;
    int len = sizeof(my_addr);
    bzero(&my_addr, sizeof(my_addr));
    getsockname(sockfd_in, (struct sockaddr *) &my_addr,(socklen_t*) &len);
    inet_ntop(AF_INET, &my_addr.sin_addr, myIP, sizeof(myIP));
    strcpy(rebuild_options.new_ip, myIP);
     */

    token token = empty_token();
    token.type = REBUILD;
    memcpy(token.data, &rebuild_options, sizeof(rebuild_options));
    strcpy(token.source, client_options.id);
    strcpy(token.dest, client_options.id);
    send_token(token);

}

void handle_token(int fd){
    struct sockaddr_in addr;
    token token = receive_token(fd, &addr);
    send_multicast(client_options.id, sizeof(client_options.id));
    sleep(1);
    switch (token.type){
        case DATA:
            handle_data(token);
            break;
        case REBUILD:
            handle_rebuild(token, addr);
            break;
        case EMPTY:
            handle_empty();
            break;
        case REBUILD_2:
            handle_rebuild_with_adr(token);
            break;
        case WRONG:break;
    }

}

void handle_rebuild(token token, sockaddr_in addr){
    rebuild_options *rebuild_options = (struct rebuild_options*) token.data;
    strcpy(rebuild_options->new_ip, inet_ntoa(addr.sin_addr));
    if (!reconnect(token)){
        token.type = REBUILD_2;
        send_token(token);
    }
}

int reconnect(token token){

    rebuild_options *rebuild_options = (struct rebuild_options*) token.data;
    if (!strcmp(rebuild_options->old_ip, client_options.ip) &&
        client_options.port == rebuild_options->old_port) {
        client_options.port = rebuild_options->new_port;
        strcpy(client_options.ip, rebuild_options->new_ip);
        create_client();
        return 1;
    }
    return  0;
}

void handle_data(token token){
    if (strcmp(token.dest, client_options.id) == 0){
        cout << "Received data from " << token.source << ": "<< token.data << "\n";
        handle_empty();
    }
    else if(strcmp(token.source, client_options.id) == 0){
        cout << "Message wasn't delievered\n";
        handle_empty();
    }
     else {
        send_token(token);
    }

}

void handle_empty(){
    if (token_queue.empty()) {
        send_token(empty_token());
    }
    else {
        token token = token_queue.front();
        token_queue.pop();
        send_token(token);
    }
}

void handle_rebuild_with_adr(token token){
    if (!reconnect(token)) send_token(token);
}

void send_token(token token){
    if (write(sockfd_out, &token, sizeof(token))!= sizeof(token)){
        error("Error while sending a token\n");
    }
}

token receive_token(int fd, sockaddr_in *addr){
    int addr_len = sizeof(addr);
    token token = empty_token();
    if (read(fd, &token, sizeof(token)) != sizeof(token)) {
        disconnect_client(fd);
        token.type = WRONG;
    }
    getpeername(fd, (sockaddr*) addr, (socklen_t *) &addr_len);
    return token;
}

void disconnect_client(int fd){
    if (epoll_ctl(epoll_fd, EPOLL_CTL_DEL, fd, NULL) == -1)
        error("Error while deleting socket from epoll\n");

    if (shutdown(fd, SHUT_RDWR) == -1)
        error("Error while shutting down socket\n");

    if (close(fd) == -1)
        error("Error while closing socket\n");
}


void connect_client(){
    int newsockfd;
    newsockfd = accept(sockfd_in, NULL, NULL);
    if (newsockfd < 0)
        error("ERROR on accept");
    struct epoll_event event;
    event.events = EPOLLIN ;
    event.data.fd = newsockfd;
    if (epoll_ctl(epoll_fd, EPOLL_CTL_ADD, newsockfd, &event) == -1)//add descriptor to epoll_fd
        error("Failed to add socket to epoll\n");
}

void close_sockets()
{
    pthread_cancel(working_thread);
    close(sockfd_out);
    shutdown(sockfd_out, SHUT_RDWR);
    close(sockfd_in);
    close(epoll_fd);
    exit(1);
}