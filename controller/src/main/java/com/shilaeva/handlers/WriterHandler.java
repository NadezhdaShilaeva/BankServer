package com.shilaeva.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shilaeva.exceptions.WriterException;
import com.shilaeva.handlers.query.Query;
import com.shilaeva.handlers.query.Response;

import java.io.BufferedWriter;
import java.io.IOException;

public class WriterHandler extends Handler {
    private final BufferedWriter writer;

    public WriterHandler(BufferedWriter writer) {
        this.writer = writer;
    }

    @Override
    public void handleQuery(Query query) {
        if (query instanceof Response<?> response) {
            writeResponse(response);
        }

        if (nextHandler != null) {
            nextHandler.handleQuery(query);
        }
    }

    private void writeResponse(Response<?> response) {
        try {
            writer.write(String.format("%s %s %s\n",
                    response.getProtocolVersion(),
                    response.getStatusCode(),
                    response.getStatusMessage()));

            for (var header: response.getHeaders().entrySet()) {
                writer.write(String.format("%s: %s\n", header.getKey(), header.getValue()));
            }

            if (response.getBody() != null) {
                String json = new ObjectMapper().writeValueAsString(response.getBody());

                writer.write(String.format("Content-Length: %d\n\n", json.getBytes().length));
                writer.write(json + "\n");
            }

            writer.flush();
        } catch (IOException e) {
            throw WriterException.errorWritingResponse(e.getMessage());
        }
    }
}
