package com.shilaeva.server;

import com.shilaeva.handlers.Handler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public int port;
    private final Handler mapperHandler;

    public Server(int port, Handler mappingHandler) {
        this.port = port;
        this.mapperHandler = mappingHandler;
    }

    public void start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket socket = serverSocket.accept();
                new Session(socket, mapperHandler).start();
            }
        }
    }
}
