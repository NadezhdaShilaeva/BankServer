package com.shilaeva.server;

import com.shilaeva.handlers.Handler;
import com.shilaeva.handlers.ReaderHandler;
import com.shilaeva.handlers.WriterHandler;

import java.io.*;
import java.net.Socket;

public class Session extends Thread {
    private final Socket socket;
    private final BufferedReader in;
    private final BufferedWriter out;
    private final Handler mapperHandler;

    public Session(Socket socket, Handler mapperHandler) throws IOException {
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        this.mapperHandler = mapperHandler;
    }

    @Override
    public void run() {
        try {
            ReaderHandler readerHandler = new ReaderHandler(in);
            Handler writerHandler = new WriterHandler(out);
            readerHandler.setNextHandler(mapperHandler);
            mapperHandler.setNextHandler(writerHandler);

            while(readerHandler.isConnectionOpen()) {
                readerHandler.handleQuery(null);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                socket.close();
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
