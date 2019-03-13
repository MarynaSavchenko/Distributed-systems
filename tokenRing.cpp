//
// Created by marina on 10.03.19.
//

#include <string.h>
#include <cstdio>
#include <cstdlib>
#include <iostream>
#include <pthread.h>
#include <zconf.h>
#include <netinet/in.h>
#include <sys/epoll.h> // for epoll_create1()
#include <arpa/inet.h>
#include "tokenRing.hpp"
using namespace std;



token empty_token(){
    token token;
    bzero(&token, sizeof(token));
    token.type = EMPTY;
    return token;
}

void start_running(){
    if (pthread_create(&working_thread, NULL, starting_running, NULL )!=0)
    {
        error("Error creating a thread");
    };
}

void *starting_running(void *){
    token token;
    strcpy(token.source, client_options.id);
    token.type = DATA;
    while(true){
        cout << "Enter destination: \n";
        cin >> token.dest;
        cout << "Enter your message: \n";
        cin >> token.data;
        token_queue.push(token);
    }
}




void print_instructions(){
    cout << "First argument is text id of user\n"
    << "Second agrument is local port on which user will listen\n"
    << "Third argument is IP of next user\n"
    << "Fourth argument is port of next user, to which user will send\n"
    << "Fifth argument is 0/1 if user has token\n"
    << "Only one user is allowed in our TokenRoing\n";
}

void init_multicast(){
    if ((multi_fd = socket(AF_INET, SOCK_DGRAM, 0)) < 0){
        error("Error while creating a multi socket\n");
    }
}

void send_multicast(char* id, int len){
    sockaddr_in addr;
    addr.sin_family = AF_INET;
    addr.sin_port = htons(multi_port);
    addr.sin_addr.s_addr = inet_addr(multi_adr);
    if (sendto(multi_fd, id, len, 0,
               (sockaddr*) &addr, sizeof(addr))!= len){
    }
}


void error(const char *msg)
{
    perror(msg);
    exit(1);
}
