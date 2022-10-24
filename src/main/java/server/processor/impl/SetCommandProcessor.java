package server.processor.impl;

import client.model.Request;
import client.model.SetTextRequest;
import server.model.Response;
import server.processor.ICommandProcessor;
import server.service.IDictionaryService;

public class SetCommandProcessor implements ICommandProcessor {
    private final IDictionaryService dictionaryService;

    public SetCommandProcessor(IDictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @Override
    public Response execute(Request request) {
        SetTextRequest setTextRequest = (SetTextRequest) request;
        dictionaryService.setText(setTextRequest.getKey(), setTextRequest.getValue());
        return new Response("OK");
    }

    @Override
    public String getSupportedTitle() {
        return "set";
    }
}
