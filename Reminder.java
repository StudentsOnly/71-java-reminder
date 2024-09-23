import java.util.concurrent.TimeUnit;

public record Reminder(
  long id, String title, String description, int start, int interval, TimeUnit timeUnit
) {}
