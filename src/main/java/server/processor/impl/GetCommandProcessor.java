package server.processor.impl;

import client.model.GetDeleteRequest;
import client.model.Request;
import server.model.ErrorResponse;
import server.model.SuccessfulResponse;
import server.model.Response;
import server.processor.ICommandProcessor;
import server.service.IDictionaryService;

public class GetCommandProcessor implements ICommandProcessor {
    private final IDictionaryService dictionaryService;

    public GetCommandProcessor(IDictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @Override
    public Response execute(Request request) {
        try {
            GetDeleteRequest getRequest = (GetDeleteRequest) request;
            String value = dictionaryService.getText(getRequest.getKey());
            return new SuccessfulResponse("OK", value);
        } catch (IllegalArgumentException e) {
            return new ErrorResponse("ERROR", e.getMessage());
        }
    }

    @Override
    public String getSupportedTitle() {
        return "get";
    }

}
