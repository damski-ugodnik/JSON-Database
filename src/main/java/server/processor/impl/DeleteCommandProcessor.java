package server.processor.impl;

import client.model.GetDeleteRequest;
import client.model.Request;
import server.model.ErrorResponse;
import server.model.SuccessfulResponse;
import server.model.Response;
import server.processor.ICommandProcessor;
import server.service.IDictionaryService;

public class DeleteCommandProcessor implements ICommandProcessor {
    private final IDictionaryService dictionaryService;

    public DeleteCommandProcessor(IDictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @Override
    public Response execute(Request request) {
        try {
            GetDeleteRequest deleteRequest = (GetDeleteRequest) request;
            dictionaryService.deleteText(deleteRequest.getKey());
            return new Response("OK");
        } catch (IllegalArgumentException e) {
            return new ErrorResponse("ERROR", e.getMessage());
        }
    }

    @Override
    public String getSupportedTitle() {
        return "delete";
    }
}
