package server.model;

public class ErrorResponse extends Response{
    private String reason;

    public ErrorResponse(String response, String reason) {
        super(response);
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }
}
