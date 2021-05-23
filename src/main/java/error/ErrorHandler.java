package error;

public class ErrorHandler {

    public void handleErrorUser(String message) {
        System.out.println(message);
    }

    // This method is for the development process where it prints the custom message as well exception
    public void errorDevelopmentInfo(String message, Exception exception) {
        System.out.println(message);
        System.out.println();
        System.out.println(exception);
    }
}

