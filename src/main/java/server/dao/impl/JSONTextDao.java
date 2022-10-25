package server.dao.impl;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import server.dao.ITextDao;
import server.utils.Constants;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class JSONTextDao implements ITextDao {
    private Map<String, JsonElement> objDataStorage;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public JSONTextDao() {
        init();
    }

    @Override
    public String getText(JsonElement key) {
        Gson gson = new Gson();

        if (key.isJsonPrimitive()) {
            return gson.toJson(getValue(key.getAsString()));
        }
        JsonArray keys = key.getAsJsonArray();
        JsonElement startKey = keys.remove(0);
        String startKeyStr = startKey.getAsJsonPrimitive().getAsString();
        if (keys.size() == 0) {
            return gson.toJson(getValue(startKeyStr));
        }
        JsonElement elementInStorage = gson.toJsonTree(getValue(startKeyStr));
        JsonObject jsonObject;
        if (elementInStorage != null && elementInStorage.isJsonObject()) {
            jsonObject = elementInStorage.getAsJsonObject();
        } else {
            throw new IllegalArgumentException("No such key");
        }
        return getField(jsonObject, keys.deepCopy()).getAsString();
    }

    private JsonElement getField(JsonObject object, JsonArray keys) {
        String key = keys.remove(0).getAsString();
        JsonElement internalField = object.get(key);
        if (internalField == null) {
            throw new IllegalArgumentException("No such field");
        }
        if (keys.size() == 0) {
            return internalField;
        }
        return getField(internalField.getAsJsonObject(), keys.deepCopy());
    }

    @Override
    public void setText(JsonElement key, JsonElement value) {
        Gson gson = new Gson();
        if (key.isJsonPrimitive()) {
            objDataStorage.put(key.getAsString(), value);
            save();
            return;
        }
        JsonArray keys = key.getAsJsonArray();
        JsonElement startKey = keys.remove(0);
        String startKeyStr = startKey.getAsJsonPrimitive().getAsString();
        if (keys.size() == 0) {
            objDataStorage.put(startKeyStr, value);
            save();
            return;
        }
        JsonElement elementInStorage = gson.toJsonTree(objDataStorage.get(startKeyStr));
        JsonObject jsonObject;
        if (elementInStorage != null && elementInStorage.isJsonObject()) {
            jsonObject = elementInStorage.getAsJsonObject();
        } else {
            jsonObject = new JsonObject();
            try {
                if (elementInStorage.isJsonPrimitive()) {
                    jsonObject.add(startKeyStr, elementInStorage);
                }
            } catch (NullPointerException e) {
                throw new RuntimeException("No such key");
            }
        }
        jsonObject = updateJsonObject(jsonObject, keys.deepCopy(), value).getAsJsonObject();
        objDataStorage.put(startKeyStr, jsonObject);
        save();
    }

    private JsonElement updateJsonObject(JsonObject object, JsonArray keys, JsonElement value) {
        JsonElement currentKey = keys.remove(0);
        JsonElement internalProperty = object.get(currentKey.getAsString());
        if (internalProperty == null) {
            internalProperty = new JsonObject();
        }
        if (internalProperty.isJsonPrimitive()) {
            object.add(currentKey.getAsString(), value);

        } else {
            object.add(currentKey.getAsString(), updateJsonObject(internalProperty.getAsJsonObject(),
                    keys.deepCopy(), value));
        }
        return object;
    }

    @Override
    public void deleteText(JsonElement key) {
        Gson gson = new Gson();
        if (key.isJsonPrimitive()) {
            objDataStorage.remove(key.getAsString());
            save();
            return;
        }
        JsonArray keys = key.getAsJsonArray();
        if (keys.size() == 1) {
            objDataStorage.remove(key.getAsString());
            save();
            return;
        }
        JsonElement startKey = keys.remove(0);
        String keyStr = startKey.getAsJsonPrimitive().getAsString();

        JsonElement elementInStorage = gson.toJsonTree(objDataStorage.get(keyStr));
        JsonObject jsonObject;
        if (elementInStorage != null && elementInStorage.isJsonObject()) {
            jsonObject = elementInStorage.getAsJsonObject();
        } else {
            throw new IllegalArgumentException("No such key or value is not an object");
        }
        jsonObject = deleteField(jsonObject, keys).getAsJsonObject();
        objDataStorage.put(keyStr, jsonObject);
        save();
    }

    private JsonElement deleteField(JsonObject object, JsonArray keys) {
        JsonElement key = keys.remove(0);
        if (keys.size() == 0) {
            object.remove(key.getAsString());
            return object;
        }
        JsonElement internalProperty = object.get(key.getAsString());
        if (internalProperty == null || internalProperty.isJsonPrimitive()) {
            throw new IllegalArgumentException("No such field in the object");
        }
        object.add(key.getAsString(), deleteField(internalProperty.getAsJsonObject(), keys));
        return object;
    }

    @Override
    public void save() {
        Lock writeLock = lock.writeLock();
        try {
            writeLock.lock();
            Gson gson = new Gson();
            Files.writeString(Paths.get(Constants.DB_FILENAME), gson.toJson(objDataStorage), StandardOpenOption.TRUNCATE_EXISTING);
            writeLock.unlock();
        } catch (IOException e) {
            throw new RuntimeException("Could not write data to file");
        }
    }

    private void init() {
        Lock readLock = lock.readLock();
        try {
            readLock.lock();
            Gson gson = new Gson();
            Path path = Paths.get(Constants.DB_FILENAME);
            String content = Files.readString(path);
            objDataStorage = gson.fromJson(content, new TypeToken<>() {
            }.getType());
            if (objDataStorage == null) {
                objDataStorage = new HashMap<>();
            }
            readLock.unlock();
        } catch (IOException e) {
            readLock.unlock();
            throw new RuntimeException("Could not read data from file");
        }
    }

    private JsonElement getValue(String key) {
        Gson gson = new Gson();
        JsonElement value = gson.toJsonTree(objDataStorage.get(key));
        if (value == null) {
            throw new IllegalArgumentException("No such key");
        }
        return value;
    }
}
