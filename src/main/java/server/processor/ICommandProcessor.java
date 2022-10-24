package server.processor;

import client.model.Request;
import server.model.Response;

public interface ICommandProcessor {
    String getSupportedTitle();
    Response execute(Request request);
}
