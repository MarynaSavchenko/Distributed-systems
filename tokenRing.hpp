//
// Created by marina on 10.03.19.
//

#ifndef TOKENRING_TOKENRING_H
#define TOKENRING_TOKENRING_H

#include <queue>

pthread_t working_thread;
int sockfd_out = 0;
int sockfd_in = 0;
struct token;
std::queue <token> token_queue;

struct options{
    char *id;
    int local_port;
    char *ip;
    int port;
    int token;
}client_options;

enum token_type{
    DATA
};


struct token{
    token_type type;
    char dest[128];
    char source[128];
    char data[1024];
};

void error(const char *msg);
token empty_token();
void start_running();
void *starting_running(void *);



#endif //TOKENRING_TOKENRING_H
