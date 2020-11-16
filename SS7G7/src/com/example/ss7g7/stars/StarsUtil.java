package com.example.ss7g7.stars;
import java.util.Calendar;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

/**
 * <h1>Stars Utility(StarsUtil)</h1>
 * StarsUtil is a utility class that provides
 * utility functions that may be used anywhere
 * in the application to help develop features.
 * 
 * @author  Ong Rui Peng
 * created on 2020/10/15
 * 
 * @version %I%
 * @since 1.0
 * 
*/
public class StarsUtil {
	// Use Date to check date of the week
	// Use Month to check odd, even, or every week
	// Month = 1, Odd
	// Month = 2, Even
	// Month = 3, Every week
	
	/**
	 * This method checks for clashes of timing between two courses.
	 * 
	 * @param startTime
	 * @param endTime
	 * @param occuringWeek
	 * @param otherStartTime
	 * @param otherEndTime
	 * @param otherOccurringWeek
	 * @return
	 */
	public static boolean timingWillClash(LocalDateTime startTime, LocalDateTime endTime, int occuringWeek, LocalDateTime otherStartTime, LocalDateTime otherEndTime, int otherOccurringWeek)
	{
		if(startTime == null || endTime == null || otherStartTime == null || otherEndTime == null) {
//			System.out.println("Unable to check Null timing");
			return false;
		}
		
		// If neither is weekly, check if same odd/even
		if(occuringWeek != 3 && otherOccurringWeek != 3)
		{
			// Will not clash if neither are weekly and do not fall on the same odd/even week
			if(occuringWeek != otherOccurringWeek) return false;
		}
		
//		if(!checkTimingFormat(startTime, endTime, otherStartTime, otherEndTime)) return false;
		
		// Will not clash if not on the same day
		if(startTime.getDayOfWeek() != otherStartTime.getDayOfWeek()) return false;
		
		if((startTime.equals(otherStartTime) || startTime.isAfter(otherStartTime)) && startTime.isBefore(otherEndTime)) return true;
		
		if((endTime.isAfter(otherStartTime) && (endTime.isBefore(otherEndTime) || endTime.equals(otherEndTime)))) return true;
		
		return false;
	}
	
	/**
	 * This method checks the format of the date given
	 * 
	 * @param startTime
	 * @param endTime
	 * @param otherStartTime
	 * @param otherEndTime
	 * @return
	 */
	public static boolean checkTimingFormat(LocalDateTime startTime, LocalDateTime endTime, LocalDateTime otherStartTime, LocalDateTime otherEndTime)
	{
		if(startTime.getYear() == endTime.getYear() 
				&& startTime.getYear() == otherStartTime.getYear() 
				&& startTime.getYear() == otherEndTime.getYear()
				&& startTime.getMonth() == endTime.getMonth()
				&& startTime.getMonth() == otherStartTime.getMonth()
				&& startTime.getMonth() == otherEndTime.getMonth()
				)
		{
			return true;
		}
		
		
		return false;
	}
}
