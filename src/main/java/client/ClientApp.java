package client;

import client.model.Input;
import client.utills.RequestService;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientApp {
    private final Socket socket;

    private final RequestService requestService;

    public ClientApp(Socket socket, RequestService requestService) {
        this.socket = socket;
        this.requestService = requestService;
    }

    public void run(Input parameters) {
        System.out.println("Client started!");
        try (
                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
                DataInputStream inputStream = new DataInputStream(socket.getInputStream())
        ) {
            String requestString = requestService.getRequestString(parameters);
            outputStream.writeUTF(requestString);
            String result = inputStream.readUTF();
            System.out.printf("""
                    Sent: %s
                    Received: %s
                    """, requestString, result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
