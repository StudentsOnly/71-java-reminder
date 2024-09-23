public class Reminder {
    private String message;

    public Reminder(String message) {
        this.message = message;
    }

    public void display() {
        System.out.println("Reminder: " + message);
    }
}
