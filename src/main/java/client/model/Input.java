package client.model;

import com.beust.jcommander.Parameter;

public class Input {
    @Parameter(names = {"-t"})
    private String type;
    @Parameter(names = {"-k"})
    private String key;
    @Parameter(names = {"-v"})
    private String value;
    @Parameter(names = {"-in"})
    private String requestFilename;

    public String getType() {
        return type;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public String getRequestFilename() {
        return requestFilename;
    }
}
