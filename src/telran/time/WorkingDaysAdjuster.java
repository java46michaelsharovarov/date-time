package telran.time;

import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.util.ArrayList;
import java.util.List;

public class WorkingDaysAdjuster implements TemporalAdjuster {

	int[] daysOff;
	int nDays;
	
	public WorkingDaysAdjuster(int[] daysOff, int nDays) {		
		this.daysOff = daysOff;
		this.nDays = nDays;
	}
	public WorkingDaysAdjuster() {
	}
	
	public int[] getDaysOff() {
		return daysOff;
	}
	
	public void setDaysOff(int[] daysOff) {
		this.daysOff = daysOff;
	}
	
	public int getnDays() { 
		return nDays;
	}
	
	public void setnDays(int nDays) {
		this.nDays = nDays;
	}
	
	@Override
	public Temporal adjustInto(Temporal temporal) {
		Temporal date = temporal;
		int i = 0;
		List<Integer> weekends = new ArrayList<Integer>();
	      for(Integer e : daysOff) {
	    	  weekends.add(e);
	      }
		while(i < nDays) {
		    i = weekends.contains(date.get(ChronoField.DAY_OF_WEEK)) ? i : ++i; 
		    date = date.plus(1, ChronoUnit.DAYS);		    
		}
		return date;
	}

}