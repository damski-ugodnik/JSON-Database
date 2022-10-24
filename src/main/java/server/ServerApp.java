package server;

import client.builder.BuilderFactory;
import server.handler.ClientHandler;
import server.processor.ProcessorFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ServerApp {
    private final ServerSocket serverSocket;
    private final ProcessorFactory processorFactory;
    private final BuilderFactory builderFactory;

    public ServerApp(ProcessorFactory processorFactory, ServerSocket serverSocket, BuilderFactory builderFactory) {
        this.serverSocket = serverSocket;
        this.processorFactory = processorFactory;
        this.builderFactory = builderFactory;
    }

    public void run() {
        System.out.println("Server started!");
        ExecutorService executor = Executors.newCachedThreadPool();
        Boolean works = true;
        while (works) {
            try (Socket socket = serverSocket.accept()) {
                Future<Boolean> result = executor.submit(new ClientHandler(socket, builderFactory, processorFactory));
                works = result.get();
            } catch (IOException | ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
