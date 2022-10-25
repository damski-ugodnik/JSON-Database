package server.service.impl;

import com.google.gson.JsonElement;
import server.dao.ITextDao;
import server.service.IDictionaryService;

public class DictionaryService implements IDictionaryService {
    private final ITextDao textDao;

    public DictionaryService(ITextDao textDao) {
        this.textDao = textDao;
    }

    @Override
    public String getText(JsonElement key) {
        return textDao.getText(key);
    }

    @Override
    public void setText(JsonElement key, JsonElement text) {
        textDao.setText(key, text);
    }

    @Override
    public void deleteText(JsonElement key) {
        textDao.deleteText(key);
    }
}
