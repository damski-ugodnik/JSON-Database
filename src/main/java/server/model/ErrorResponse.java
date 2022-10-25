package server.model;

public class ErrorResponse extends Response {
    private final String reason;

    public ErrorResponse(String response, String reason) {
        super(response);
        this.reason = reason;
    }
}
