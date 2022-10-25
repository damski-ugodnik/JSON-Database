package client.builder;

import client.model.Input;
import client.model.Request;

import java.util.Set;

public interface IRequestBuilder {
    Set<String> getSupportedTitles();

    Request buildRequest(Input parameters);

    Request buildRequest(String jsonRequest);
}
