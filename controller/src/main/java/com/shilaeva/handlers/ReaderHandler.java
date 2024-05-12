package com.shilaeva.handlers;

import com.shilaeva.exceptions.ReaderException;
import com.shilaeva.handlers.query.Query;
import com.shilaeva.handlers.query.Request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ReaderHandler extends Handler {
    private final BufferedReader reader;

    public ReaderHandler(BufferedReader reader) {
        this.reader = reader;
    }

    @Override
    public void handleQuery(Query query) {
        Request request = readRequest();

        if (nextHandler != null)
            nextHandler.handleQuery(request);
    }

    private Request readRequest() {
        try {
            String line = reader.readLine();

            String method = line.split(" ")[0];
            String path = line.split(" ")[1];

            Map<String, String> headers = new HashMap<>();
            while ((line = reader.readLine()) != null && !line.isEmpty()) {
                headers.put(line.split(": ", 2)[0], line.split(": ", 2)[1]);
            }

            Request request = new Request(method, path, headers);
            System.out.println(request.getMethod());
            System.out.println(request.getPath());
            System.out.println(request.getHeaders());

            if (headers.containsKey("Content-Type") && headers.get("Content-Type").equals("application/json")) {
                int contentLength = Integer.parseInt(headers.get("Content-Length"));

                char[] jsonBody = new char[contentLength];
                reader.read(jsonBody, 0, contentLength);
                request.setBody(new String(jsonBody));

                System.out.println(request.getBody());
            }

            return request;
        } catch (IOException e) {
            throw ReaderException.errorReadingResponse(e.getMessage());
        }
    }
}
