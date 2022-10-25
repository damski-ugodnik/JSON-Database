package server.utils;

import java.util.Scanner;

public class ConsoleReader {
    private static final Scanner scanner = new Scanner(System.in);

    public static String readText() {
        return scanner.nextLine();
    }
}
