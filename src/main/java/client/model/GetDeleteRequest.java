package client.model;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

public class GetDeleteRequest extends Request {
    private final String key;

    public GetDeleteRequest(String type, String key) {
        super(type);
        this.key = key;
    }

    public JsonElement getKey() {
        Gson gson = new Gson();
        try {
            return gson.fromJson(key, JsonElement.class);
        } catch (Exception e) {
            return gson.toJsonTree(key);
        }
    }
}
