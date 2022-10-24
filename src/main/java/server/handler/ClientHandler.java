package server.handler;

import client.builder.BuilderFactory;
import client.builder.IRequestBuilder;
import client.model.Request;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import server.model.Response;
import server.processor.ICommandProcessor;
import server.processor.ProcessorFactory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.Callable;

public class ClientHandler implements Callable<Boolean> {
    private final Socket socket;
    private final BuilderFactory builderFactory;
    private final ProcessorFactory processorFactory;

    public ClientHandler(Socket socket, BuilderFactory builderFactory, ProcessorFactory processorFactory) {
        this.socket = socket;
        this.builderFactory = builderFactory;
        this.processorFactory = processorFactory;
    }

    @Override
    public Boolean call() throws Exception {
        Gson gson = new Gson();
        try (
                DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream())
        ) {
            String requestStr = inputStream.readUTF();
            Request request = gson.fromJson(requestStr, Request.class);

            if (request.getType().equals("exit")) {
                String exitResponseText = gson.toJson(new Response("OK"));
                outputStream.writeUTF(exitResponseText);
                return false;
            }
            JsonElement e;
            IRequestBuilder requestBuilder = builderFactory.getBuilder(request.getType());
            ICommandProcessor processor = processorFactory.getProcessor(request.getType());
            request = requestBuilder.buildRequest(requestStr);
            Response response = processor.execute(request);
            String responseText = gson.toJson(response);
            outputStream.writeUTF(responseText);
            return true;
        } catch (IOException e) {
            throw new Exception("Stream read/write exception");
        }
    }
}
