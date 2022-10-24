package server;

import client.builder.BuilderFactory;
import client.builder.IRequestBuilder;
import client.builder.impl.GetDeleteRequestBuilder;
import client.builder.impl.RequestBuilder;
import client.builder.impl.SetTextRequestBuilder;
import server.dao.impl.JSONTextDao;
import server.processor.ICommandProcessor;
import server.processor.ProcessorFactory;
import server.processor.impl.DeleteCommandProcessor;
import server.processor.impl.GetCommandProcessor;
import server.processor.impl.SetCommandProcessor;
import server.service.IDictionaryService;
import server.service.impl.DictionaryService;
import server.utils.Constants;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        try (
                ServerSocket serverSocket = new ServerSocket(Constants.PORT, 50, InetAddress.getByName(Constants.ADDRESS))
        ) {
            IDictionaryService dictionaryService = new DictionaryService(new JSONTextDao());
            List<ICommandProcessor> processors = new ArrayList<>() {{
                add(new DeleteCommandProcessor(dictionaryService));
                add(new SetCommandProcessor(dictionaryService));
                add(new GetCommandProcessor(dictionaryService));
            }};
            ProcessorFactory processorFactory = new ProcessorFactory(processors);
            Set<IRequestBuilder> builders = Set.of(
                    new RequestBuilder(),
                    new SetTextRequestBuilder(),
                    new GetDeleteRequestBuilder());
            BuilderFactory builderFactory = new BuilderFactory(builders);
            ServerApp app = new ServerApp(processorFactory, serverSocket, builderFactory);
            app.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
