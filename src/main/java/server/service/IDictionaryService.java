package server.service;

import com.google.gson.JsonElement;

public interface IDictionaryService {
    String getText(JsonElement key);
    void setText(JsonElement key, JsonElement text);
    void deleteText(JsonElement key);
}
