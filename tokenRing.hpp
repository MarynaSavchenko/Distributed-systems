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
int epoll_fd;
int multi_fd = 0;
int multi_port = 12345;
char multi_adr[] = "230.230.230.230";
std::queue <token> token_queue;

struct options{
    char *id;
    int local_port;
    char *ip;
    int port;
    int token;
}client_options;

enum token_type{
    DATA, REBUILD, REBUILD_2, EMPTY, WRONG
};


struct token{
    token_type type;
    char dest[128];
    char source[128];
    char data[1024];
};

struct rebuild_options{
    int old_port;
    int new_port;
    char old_ip[16];
    char new_ip[16];
};

void error(const char *msg);
token empty_token();
void start_running();
void *starting_running(void *);



#endif //TOKENRING_TOKENRING_H
