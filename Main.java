import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Main {
    public static void main(String[] args) {
          try (Scanner scanner = new Scanner(System.in)) {

            System.out.print("Enter the reminder message: ");
            String message = scanner.nextLine();

            System.out.print("Enter the initial delay (in seconds): ");
            int initialDelay = scanner.nextInt();

            System.out.print("Enter the period (in seconds): ");
            int period = scanner.nextInt();

            // Use try-with-resources to automatically close the ScheduledExecutorService
            try (ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1)) {

                ReminderScheduler reminderScheduler = new ReminderScheduler(scheduler);


                Reminder reminder = new Reminder(message);

                reminderScheduler.scheduleAtFixedRate(reminder, initialDelay, period);

                System.out.println("Reminder set with an initial delay of " + initialDelay + " seconds, repeating every " + period + " seconds.");

               try {
                    Thread.sleep(60000); // 1 minute
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } // ScheduledExecutorService shutdown

        } // Scanner close
    }
}
