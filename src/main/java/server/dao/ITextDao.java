package server.dao;

import com.google.gson.JsonElement;

public interface ITextDao {
    String getText(JsonElement key);
    void setText(JsonElement key, JsonElement value);
    void deleteText(JsonElement key);
    void save();
}
