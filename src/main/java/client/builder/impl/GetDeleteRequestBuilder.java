package client.builder.impl;

import client.builder.IRequestBuilder;
import client.model.GetDeleteRequest;
import client.model.Input;
import client.model.Request;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.Set;

public class GetDeleteRequestBuilder implements IRequestBuilder {
    @Override
    public Set<String> getSupportedTitles() {
        return Set.of("get", "delete");
    }

    @Override
    public Request buildRequest(Input parameters) {
        return new GetDeleteRequest(parameters.getType(), parameters.getKey());
    }

    @Override
    public GetDeleteRequest buildRequest(String jsonRequest) {
        Gson gson = new Gson();
        try {
            return gson.fromJson(jsonRequest, GetDeleteRequest.class);
        } catch (Exception e) {
            JsonElement jsonElement = gson.fromJson(jsonRequest, JsonElement.class);
            String type = jsonElement.getAsJsonObject().get("type").getAsString();
            String key = null;
            JsonElement arr = jsonElement.getAsJsonObject().get("key");
            if (arr.isJsonArray()) {
                key = gson.toJson(arr);
            }
            return new GetDeleteRequest(type, key);
        }
    }
}
