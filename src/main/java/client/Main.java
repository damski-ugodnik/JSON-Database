package client;

import client.builder.BuilderFactory;
import client.builder.IRequestBuilder;
import client.builder.impl.GetDeleteRequestBuilder;
import client.builder.impl.RequestBuilder;
import client.builder.impl.SetTextRequestBuilder;
import client.model.Input;
import client.utills.RequestService;
import com.beust.jcommander.JCommander;
import server.utils.Constants;

import java.io.IOException;
import java.net.Socket;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        try (Socket socket = new Socket(Constants.ADDRESS, Constants.PORT)) {
            Input input = new Input();
            JCommander.newBuilder().addObject(input).build().parse(args);
            Set<IRequestBuilder> builders = Set.of(
                    new RequestBuilder(),
                    new SetTextRequestBuilder(),
                    new GetDeleteRequestBuilder());
            BuilderFactory factory = new BuilderFactory(builders);
            RequestService requestService = new RequestService(factory);
            ClientApp app = new ClientApp(socket, requestService);
            app.run(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
