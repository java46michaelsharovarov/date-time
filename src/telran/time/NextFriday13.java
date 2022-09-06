package telran.time;

import java.time.DayOfWeek;
import java.time.temporal.*;

public class NextFriday13 implements TemporalAdjuster {

	@Override
	public Temporal adjustInto(Temporal temporal) {
		final ChronoUnit MONTHS = ChronoUnit.MONTHS;
		final ChronoField DAY_OF_MONTH = ChronoField.DAY_OF_MONTH;
		final int dayOfWeek = DayOfWeek.FRIDAY.ordinal() + 1;
		final int dayOfMonth = 13;
				
		Temporal current = temporal.get(DAY_OF_MONTH) < dayOfMonth 
				? temporal.with(DAY_OF_MONTH, dayOfMonth) 
				: temporal.plus(1, MONTHS).with(DAY_OF_MONTH, dayOfMonth);
		
	    while (current.get(ChronoField.DAY_OF_WEEK) != dayOfWeek) {
	        current = current.plus(1, MONTHS);
	    }
	    return current;  	
	}

}