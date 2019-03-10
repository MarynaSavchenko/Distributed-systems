#include <iostream>
#include <pthread.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <cstdlib>
#include <netinet/in.h>
#include <string.h>
#include <netdb.h>
#include <zconf.h>
#include "tokenRing.cpp"

using namespace std;

void init(int, char**);
void print_instructions();
void create_client();
void create_server();
//void spawn_threads();
void error(const char *msg);
void close_sockets();


int main(int argc, char **argv) {
    init(argc, argv);
    create_server();
    create_client();
    atexit(close_sockets);

    return 0;
}

void init(int argc, char **argv){
    /*
     * init options of this node
     */

    if (argc!=6){
        print_instructions();
        error("Wrong amount of args\n");
    }
    client_options.id = argv[1];
    client_options.local_port = atoi(argv[2]);
    client_options.ip = argv[3];
    client_options.port = atoi(argv[4]);
    client_options.token = atoi(argv[5]);

    if (!client_options.token==0 && !client_options.token==1){
        print_instructions();
        error("Token can be 1 or 0\n");
    }
}

void print_instructions(){

}
/*
void spawn_threads(){

    pthread_t client_thread, server_thread;

    pthread_create(&client_thread, NULL, create_client, NULL);
    pthread_create(&server_thread, NULL, create_server, NULL);


    pthread_join(server_thread, NULL);
    pthread_join(client_thread, NULL);



}
*/
void create_client() {
    struct sockaddr_in serv_addr;
    struct hostent *server;

    char buffer[256];

    sockfd_out = socket(AF_INET, SOCK_STREAM, 0);
    if (sockfd_out < 0)
        error("ERROR opening socket");

    bzero((char *) &serv_addr, sizeof(serv_addr));
    serv_addr.sin_family = AF_INET;
    serv_addr.sin_port = htons(client_options.port);
    server = gethostbyaddr((const char *) &client_options.ip,
                           sizeof(client_options.ip), AF_INET);
    if (server == NULL) {
        error("No such host\n");
    }
    bcopy(server->h_addr, &serv_addr.sin_addr, server->h_length);

    if (connect(sockfd_out, (struct sockaddr *) &serv_addr, sizeof(serv_addr)) < 0)
        error("ERROR connecting");
}

void create_server(){
    int newsockfd;
    socklen_t clilen;
    char buffer[256];
    struct sockaddr_in serv_addr, cli_addr;
    int n;

    sockfd_in = socket(AF_INET, SOCK_STREAM, 0);
    if (sockfd_in < 0)
        error("ERROR opening socket");
    bzero((char *) &serv_addr, sizeof(serv_addr)); //zerujemy

    serv_addr.sin_family = AF_INET;
    serv_addr.sin_addr.s_addr = INADDR_ANY;//server address
    serv_addr.sin_port = htons(client_options.local_port);

    if (bind(sockfd_in, (struct sockaddr *) &serv_addr,
             sizeof(serv_addr)) < 0)
        error("ERROR on binding");

    listen(sockfd_in,5);
    clilen = sizeof(cli_addr);
    newsockfd = accept(sockfd_in,
                       (struct sockaddr *) &cli_addr,
                       &clilen);
    if (newsockfd < 0)
        error("ERROR on accept");
}





void close_sockets()
{
    close(sockfd_out);
    close(sockfd_in);
    exit(1);
}