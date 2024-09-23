import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public record Reminder(
  long id, String title, String description,
  int start, int interval, TimeUnit timeUnit,
  Future<?> future
) {
  @Override
  public String toString() {
    return "Reminder(%d, %s, %s, %d, %d, %s)".formatted(id, title, description, start, interval, timeUnit);
  }
}
