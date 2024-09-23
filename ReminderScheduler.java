import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ReminderScheduler {
    private final ScheduledExecutorService scheduler;

    public ReminderScheduler(ScheduledExecutorService scheduler) {
        this.scheduler = scheduler;
    }

    public void scheduleAtFixedRate(Reminder reminder, int initialDelay, int period) {
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                reminder.display();
            }
        }, initialDelay, period, TimeUnit.SECONDS);
    }
}
