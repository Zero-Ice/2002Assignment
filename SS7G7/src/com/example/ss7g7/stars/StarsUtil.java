package com.example.ss7g7.stars;
import java.util.Calendar;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

public class StarsUtil {
	// Use Date to check date of the week
	// Use Month to check odd, even, or every week
	// Month = 1, Odd
	// Month = 2, Even
	// Month = 3, Every week
	
	public static boolean timingWillClash(LocalDateTime startTime, LocalDateTime endTime, int occuringWeek, LocalDateTime otherStartTime, LocalDateTime otherEndTime, int otherOccurringWeek)
	{
		// If neither is weekly, check if same odd/even
		if(occuringWeek != 3 && otherOccurringWeek != 3)
		{
			// Will not clash if neither are weekly and do not fall on the same odd/even week
			if(occuringWeek != otherOccurringWeek) return false;
		}
		
		// Will not clash if not on the same day
		if(startTime.getDayOfWeek() != otherStartTime.getDayOfWeek()) return false;
		
		if((startTime.equals(otherStartTime) || startTime.isAfter(otherStartTime)) && startTime.isBefore(otherEndTime)) return true;
		
		if((endTime.isAfter(otherStartTime) && (endTime.isBefore(otherEndTime) || endTime.equals(otherEndTime)))) return true;
		
		return false;
	}
}
