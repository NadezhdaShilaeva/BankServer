package com.shilaeva.server;

import com.shilaeva.handlers.Handler;
import com.shilaeva.handlers.ReaderHandler;
import com.shilaeva.handlers.WriterHandler;

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
                try (Socket socket = serverSocket.accept()) {

                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                    Handler readerHandler = new ReaderHandler(in);
                    Handler writerHandler = new WriterHandler(out);
                    readerHandler.setNextHandler(mapperHandler);
                    mapperHandler.setNextHandler(writerHandler);

                    readerHandler.handleQuery(null);

                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
    }
}
