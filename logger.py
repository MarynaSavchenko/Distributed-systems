import datetime
import socket
import struct

UDP_IP = "230.230.230.230"
UDP_PORT = 12345

sock = socket.socket(socket.AF_INET, # Internet
                     socket.SOCK_DGRAM, socket.IPPROTO_UDP) # UDP
sock.bind((UDP_IP, UDP_PORT))
group = socket.inet_aton(UDP_IP)
mreq = struct.pack('4sL', group, socket.INADDR_ANY)
sock.setsockopt(socket.IPPROTO_IP, socket.IP_ADD_MEMBERSHIP, mreq)

while True:
    data, addr = sock.recvfrom(1024) # buffer size is 1024 bytes
    print(str(datetime.datetime.now())[:-7] + data.decode('utf-8'))
    with open("logger.txt", 'a+') as file:
        file.write(data.decode('utf-8') + " " + str(datetime.datetime.now())[:7]+'\n')

