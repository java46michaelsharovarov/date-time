package telran.time.application;

import java.util.Timer;
import java.util.TimerTask;

public class ReminderHandler {

	private long interval;
	private long amountOfTime;
	Timer timer;
	
	public ReminderHandler(long [] reminderArgs) {
		interval = reminderArgs[0];
		amountOfTime = reminderArgs[1];
		timer = new Timer();
		timer.schedule(new Beep(), interval, interval);
	}

	class Beep extends TimerTask {
		long numberOfWholeTimes = amountOfTime / interval;
        public void run() {      
    		System.out.println("\007\007\007");
    		numberOfWholeTimes--;
    		if (numberOfWholeTimes <= 0) {
    			timer.cancel();
    		}
        }
    }
}
