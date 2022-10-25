package client.model;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

public class SetTextRequest extends GetDeleteRequest {
    private final JsonElement value;

    public SetTextRequest(String type, String key, String value) {
        super(type, key);
        Gson gson = new Gson();
        this.value = gson.toJsonTree(value);
    }

    public JsonElement getValue() {
        return value;
    }
}
