package client.builder.impl;

import client.builder.IRequestBuilder;
import client.model.Input;
import client.model.Request;
import client.model.SetTextRequest;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.Set;

public class SetTextRequestBuilder implements IRequestBuilder {
    @Override
    public Set<String> getSupportedTitles() {
        return Set.of("set");
    }

    @Override
    public Request buildRequest(Input parameters) {
        return new SetTextRequest(parameters.getType(), parameters.getKey(), parameters.getValue());
    }

    @Override
    public SetTextRequest buildRequest(String jsonRequest) {
        Gson gson = new Gson();
        try {
            return gson.fromJson(jsonRequest, SetTextRequest.class);
        } catch (Exception e) {
            JsonElement jsonElement = gson.fromJson(jsonRequest, JsonElement.class);
            String type = jsonElement.getAsJsonObject().get("type").getAsString();
            String key = null;
            JsonElement arr = jsonElement.getAsJsonObject().get("key");
            if (arr.isJsonArray()) {
                key = gson.toJson(arr);
            }
            String value = jsonElement.getAsJsonObject().get("value").getAsString();
            return new SetTextRequest(type, key, value);
        }
    }
}
