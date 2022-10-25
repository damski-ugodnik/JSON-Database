package server.utils;

import java.util.regex.Pattern;

public class Constants {
    public static final Pattern REQUEST_PATTERN = Pattern.compile("^(delete|get) (\\d+)|(set) (\\d+) (\\w+)|exit$");
    public static final String ADDRESS = "127.0.0.1";
    public static final int PORT = 37612;
    public static final Pattern CLIENT_SERVER_REQUEST_PATTERN = Pattern.compile("^(Give me a record # )(\\d+)$");
    public static final String DB_FILENAME = "/Users/mponomarenko/IdeaProjects/JSON Database/JSON Database/task/src/server/data/db.json";
    public static final String CLIENT_DATA_FOLDER_PATH = "/Users/mponomarenko/IdeaProjects/JSON Database/JSON Database/task/src/client/data";
}
