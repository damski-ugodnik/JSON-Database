package client.model;

import com.beust.jcommander.Parameter;

import java.io.Serializable;

public class Request implements Serializable {
    private final String type;

    public Request(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
