//
// Created by marina on 10.03.19.
//

#include <string.h>
#include <cstdio>
#include <cstdlib>
#include <iostream>
#include "tokenRing.hpp"
using namespace std;



token empty_token(){
    token token;
    bzero(&token, sizeof(token));
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
        cin >> token.dest;//end of line??
        cout << "Enter your message: \n";
        token_queue.push(token);
    }
}

void error(const char *msg)
{
    perror(msg);
    exit(1);
}
