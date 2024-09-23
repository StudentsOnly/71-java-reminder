import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
  private static List<Reminder> reminders = new ArrayList<>();
  private static DateTimeFormatter dtf = DateTimeFormatter.ISO_DATE_TIME;
  private static ScheduledExecutorService executor;

  public static void main(String[] args) {

    try (Scanner scanner = new Scanner(System.in)) {
      executor = Executors.newScheduledThreadPool(2);

      boolean isRunning = true;
      while (isRunning) {
        menu();
        String input = scanner.nextLine().trim().toLowerCase();
        switch (input.charAt(0)) {
          case 'a' -> addReminder(scanner);
//          case 'v' -> viewAll();
//          case 'd' -> deleteReminder(scanner);
          case 'e' -> {
            System.out.println("Exiting program ...");
            isRunning = false;
          }
          default -> {}
        }
        System.out.println();
      }
      executor.shutdown();
      if(!executor.awaitTermination(5, TimeUnit.MINUTES)) {
        executor.shutdownNow();
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private static void addReminder(Scanner scanner) {
    System.out.println("""
      Enter a reminder in the following format:
      long id, String title, String description, int start, int interval, TimeUnit timeUnit
      """);
    String input = scanner.nextLine().trim();
    String[] lines = input.split(",");
    if (lines.length != 6) {
      System.out.println("Invalid number of inputs.");
      return;
    }
    for (int i = 0; i < lines.length; i++) {
      lines[i] = lines[i].trim();
      if (lines[i].isEmpty()) {
        System.out.println("Empty input field.");
        return;
      }
    }
    long id;
    String title;
    String description;
    int start;
    int interval;
    TimeUnit timeUnit;

    try {
      id = Long.parseLong(lines[0]);
      title = lines[1];
      description = lines[2];
      start = Integer.parseInt(lines[3]);
      interval = Integer.parseInt(lines[4]);
      timeUnit = getTimeUnit(lines[5]);
    } catch (NumberFormatException e) {
      System.out.println("Illegal number format.");
      return;
    }

    reminders.add(new Reminder(id, title, description, start, interval, timeUnit));
    executor.scheduleAtFixedRate(
      () -> System.out.println(title + ":\n\n" + description + "\n"), start, interval, timeUnit);
  }

  private static TimeUnit getTimeUnit(String line) {
    return switch (line.toLowerCase().charAt(0)) {
      case 'd' -> TimeUnit.DAYS;
      case 'h' -> TimeUnit.HOURS;
      case 'm' -> TimeUnit.MINUTES;
      case 's' -> TimeUnit.SECONDS;
      default -> throw new NumberFormatException("TimeUnit not found.");
    };
  }

  public static void menu() {
    String menu = """
      ================
      Reminders
      ================
      (a)dd
      (v)iew all
      (d)elete
      (e)xit
      
      Enter operation:\s""";
    System.out.print(menu);
  }
}
