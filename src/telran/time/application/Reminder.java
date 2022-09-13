package telran.time.application;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Timer;
import java.util.TimerTask;

public class Reminder {
	
	private static final long DEFAULT_AMOUNT_OF_TIME = 3600000;  // default in 1 hour
	private static ChronoUnit unit;

	public static void main(String[] args) {
		long [] reminderArgs;
		try {
			reminderArgs = getReminderArgs(args);
			getReminder(reminderArgs[0], reminderArgs[1]);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}		
	}

	private static long[] getReminderArgs(String[] args) throws Exception {
		long [] res = new long[2];
		if(args.length > 1) {
			unit = getUnit(args);
			res[0] = getInterval(args[0]);
			res[1] = args.length > 2 ? getAmountOfTime(args[2], res[0]) : DEFAULT_AMOUNT_OF_TIME;
		} else {
			throw new Exception("mandatory arguments are not set (interval, unit)");
		}
		return res;
	}

	private static ChronoUnit getUnit(String[] args) throws Exception {
		try {
			return ChronoUnit.valueOf(args[1].toUpperCase());			
		} catch (Exception e) {
			throw new Exception("incorrect name of the time unit - " + args[1]);
		}
	}	

	private static long getInterval(String intervalStr) throws Exception {
		try {
			long interval = getValueInMilliseconds(Integer.parseInt(intervalStr));
			if(interval < 0) {
				throw new Exception("negative interval");
			}
			return interval;
		} catch (NumberFormatException e) {
			throw new Exception("interval is not a number");
		}
	}	

	private static long getAmountOfTime(String endStr, long interval) throws Exception {
		try {
			long amountOfTime = getValueInMilliseconds(Integer.parseInt(endStr));
			if(amountOfTime < 0) {
				throw new Exception("negative amount of time");
			}
			if(amountOfTime < interval) {
				throw new Exception("amount of time should be greater than the interval");
			}
			return amountOfTime;
		} catch (NumberFormatException e) {
			throw new Exception("amount of time is not a number");
		}
	}
	
	private static long getValueInMilliseconds(long value) {
		return ChronoUnit.MILLIS.equals(unit) ? value : Duration.of(value, unit).toMillis(); 
	}

	private static void getReminder(long interval, long amountOfTime) {
		Timer timer = new Timer();
		timer.schedule(
				new TimerTask() {
					long numberOfWholeTimes = amountOfTime / interval;
					@Override
			        public void run() {      
			    		System.out.println("\007\007\007");
			    		numberOfWholeTimes--;
			    		if (numberOfWholeTimes <= 0) {
			    			timer.cancel();
			    		}
			        }
			    },
				interval, interval);
	}

}
