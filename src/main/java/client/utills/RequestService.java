package client.utills;

import client.builder.BuilderFactory;
import client.builder.IRequestBuilder;
import client.model.Input;
import client.model.Request;
import com.google.gson.Gson;
import server.utils.Constants;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class RequestService {
    private final BuilderFactory factory;

    public RequestService(BuilderFactory factory) {
        this.factory = factory;
    }

    public String getRequestString(Input parameters) {
        if (parameters.getRequestFilename() != null) {
            String fullFilename = Constants.CLIENT_DATA_FOLDER_PATH + '/' + parameters.getRequestFilename();
            return readRequestFromFile(fullFilename);
        }
        Gson gson = new Gson();
        IRequestBuilder builder = factory.getBuilder(parameters.getType());
        Request request = builder.buildRequest(parameters);
        return gson.toJson(request);
    }


    private static String readRequestFromFile(String filename) {
        try {
            return Files.readString(Paths.get(filename));
        } catch (IOException e) {
            throw new RuntimeException("Could not read request from file");
        }
    }
}
