package error;

public class ErrorHandler {

    public void handleErrorUser(String message) {
        System.out.println(message);
    }

    public void errorDevelopmentInfo(String message, Exception exception) {
        System.out.println(message);
        System.out.println();
        System.out.println(exception);
    }
}

