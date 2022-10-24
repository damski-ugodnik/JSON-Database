package server.utils;

import java.util.regex.Matcher;

public class Validator {
    public static boolean validateRequest(String request) {
        Matcher matcher = Constants.REQUEST_PATTERN.matcher(request);
        return matcher.matches();
    }
}
