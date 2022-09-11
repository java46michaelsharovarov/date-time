package telran.time.application;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Locale;

public class PrintCalendar {

	private static final DayOfWeek[] VALUES_DAYS_OF_WEEK = DayOfWeek.values();
	private static final int FIRST_DAY_OF_WEEK = 0;

	public static void main(String[] args) {
		int monthOfYear[];
		try {
			monthOfYear = getMonthOfYear(args);
			printCalendar(monthOfYear[0], monthOfYear[1], monthOfYear[2]);		
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}		
	}

	private static int[] getMonthOfYear(String[] args) throws Exception {
		LocalDate currentDate = LocalDate.now();
		int [] res = {currentDate.getMonthValue(), currentDate.getYear(), FIRST_DAY_OF_WEEK};
		if(args.length > 0) {
			res[0] = getGivenMonthValue(args[0]);
			if(args.length > 1) {
				res[1] = getGivenYearValue(args[1]);
				if(args.length > 2) {
					res[2] = getGivenFirstDayOfWeekOrdinal(args[2]);
				}
			}
		}
		return res;
	}

	private static int getGivenMonthValue(String givenMonth) throws Exception {
		try {
			int givenMonthNumber = Integer.parseInt(givenMonth);
			int numberOfMonthsInYear = Month.values().length;
			if(givenMonthNumber < 1 || givenMonthNumber > numberOfMonthsInYear) {
				throw new Exception(String.format("Month %d is wrong value,"
						+ "Should be in range [1, %d]", givenMonthNumber, numberOfMonthsInYear));
			}
			return givenMonthNumber;
		} catch (NumberFormatException e) {
			throw new Exception("Month should be a number");
		}
	}

	private static int getGivenYearValue(String givenYear) throws Exception {
		try {
			int givenYearNumber = Integer.parseInt(givenYear);
			if(givenYearNumber <= 0) {
				throw new Exception("Year should be a positive number");
			}
			return givenYearNumber;
		} catch (NumberFormatException e) {
			throw new Exception("Year should be a number");
		}
	}

	private static int getGivenFirstDayOfWeekOrdinal(String givenFirstDayOfWeek) throws Exception {
		String daysOfWeek = getStringOfDaysOfWeek();
		try {
			return DayOfWeek.valueOf(givenFirstDayOfWeek.toUpperCase()).ordinal();			
		} catch (Exception e) {
			throw new Exception(String.format("Day should be from the list [%s]", daysOfWeek));
		}
	}

	private static String getStringOfDaysOfWeek() {
		StringBuilder stringBuilder = new StringBuilder();
		for(DayOfWeek day : VALUES_DAYS_OF_WEEK) {
			stringBuilder.append(day.getDisplayName(TextStyle.FULL, Locale.getDefault())); 
			if(day.getValue() != VALUES_DAYS_OF_WEEK.length) {
				stringBuilder.append(", ");
			}
		}
		return stringBuilder.toString();
	}

	private static void printCalendar(int month, int year, int firstDayOfWeek) {
		printMonthAndYear(month, year);
		printDaysOfWeek(firstDayOfWeek);
		printDates(month, year, firstDayOfWeek);
	}

	private static void printMonthAndYear(int month, int year) {
		String displayNameOfmonth = Month.of(month).getDisplayName(TextStyle.FULL, Locale.getDefault());
		System.out.printf("%s, %d\n", displayNameOfmonth, year);
	}

	private static void printDaysOfWeek(int firstDayOfWeek) {
		System.out.printf(" ");
		int lastDayOfWeek = VALUES_DAYS_OF_WEEK.length - 1;		
		int dayOfWeek = firstDayOfWeek;
		do {
			System.out.printf("%s ", 
					VALUES_DAYS_OF_WEEK[dayOfWeek].getDisplayName(TextStyle.SHORT, Locale.getDefault()));
			dayOfWeek++;
			if(dayOfWeek == VALUES_DAYS_OF_WEEK.length) {
				dayOfWeek = 0;
				lastDayOfWeek = firstDayOfWeek - 1;
			}
		} while(dayOfWeek <= lastDayOfWeek);
		System.out.println();
	}

	private static void printDates(int month, int year, int firstDayOfWeek) {
		int printPosition = getStartPrintPosition(month, year, firstDayOfWeek);
		printOffset(printPosition);
		int numberOfDaysInMonth = getNumberOfDaysInMonth(month, year);
		int numberOfDaysInWeek = VALUES_DAYS_OF_WEEK.length;
		for(int day = 1; day <= numberOfDaysInMonth; day++) {
			System.out.printf("%4d", day);
			printPosition++;
			if(printPosition == numberOfDaysInWeek) {
				printPosition = 0;
				System.out.println();
			}
		}		
	}

	private static int getStartPrintPosition(int month, int year, int firstDayOfWeek) {
		LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);
		int valueDayOfWeekOfFirstDayOfMonth = firstDayOfMonth.getDayOfWeek().getValue();
		int valueFirstDayOfWeek = VALUES_DAYS_OF_WEEK[firstDayOfWeek].getValue();
		int startPrintPosition = valueDayOfWeekOfFirstDayOfMonth - valueFirstDayOfWeek;
		if(startPrintPosition < 0) {
			return VALUES_DAYS_OF_WEEK.length + startPrintPosition;
		}
		return startPrintPosition; 
	}

	private static void printOffset(int column) {
		System.out.printf("%s", " ".repeat(column * 4));
	}

	private static int getNumberOfDaysInMonth(int month, int year) {
		return YearMonth.of(year, month).lengthOfMonth();
	}
	
}
