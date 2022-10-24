package client.builder.impl;

import client.builder.IRequestBuilder;
import client.model.Input;
import client.model.Request;
import com.google.gson.Gson;

import java.util.Set;

public class RequestBuilder implements IRequestBuilder {
    @Override
    public Set<String> getSupportedTitles() {
        return Set.of("exit");
    }

    @Override
    public Request buildRequest(Input parameters) {
        return new Request(parameters.getType());
    }

    @Override
    public Request buildRequest(String jsonRequest) {
        Gson gson = new Gson();
        return gson.fromJson(jsonRequest, Request.class);
    }
}
