package server.model;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;

public class SuccessfulResponse extends Response {
    private JsonElement value;

    public SuccessfulResponse(String response, String value) {
        super(response);
        Gson gson = new Gson();
        try {
            this.value = gson.fromJson(value, JsonElement.class);
        } catch (JsonSyntaxException e) {
            this.value = gson.toJsonTree(value);
        }
    }
}
