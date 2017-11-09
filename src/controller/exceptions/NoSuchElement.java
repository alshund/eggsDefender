package controller.exceptions;

public class NoSuchElement extends Exception {
    private String message;

    public NoSuchElement(String message) {
        this.message = message;
    }

    public void printMessage() {
        System.out.println(message);
    }
}
