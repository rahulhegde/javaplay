package DatePlay;

import java.math.BigDecimal;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class DatePlay {
	public int value;

	public DatePlay() {
		System.out.println("*** Date Play Constructor ***");
	}

	DatePlay(int value) {
		this.value = value;
	}

	// Given a String Date convert to util.Date using SimpleDateFormat 
	// Set to Date to Calendar instance for manipulating 
	// Get Date using Calendar instance and use SimpleDateFormat to convert to Strings (reverse)
	public void DateExample_1() {
		// Given Date in String format
		String oldDate = "2017-01-29";
		System.out.println("Date before Addition: " + oldDate);

		// Specifying date format that matches the given date
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		try {
			// Setting the date to the given date
			Date tmpDate = sdf.parse(oldDate);
			c.setTime(tmpDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// Number of Days to add
		c.add(Calendar.DAY_OF_MONTH, 7);

		// Date after adding the days to the given date
		Date tmpDate = c.getTime();
		String newDate = sdf.format(tmpDate);
		// Displaying the new Date after addition of Days
		System.out.println("Date after adding 7 days: " + newDate);
	}

	public void DateExample_2() {
		System.out.println("epoch: " + System.currentTimeMillis());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
		Date date = new Date(System.currentTimeMillis());
		System.out.println(formatter.format(date));

		// Represent LocalDateTime into different TZ dates
		// Use LocalDateTime however it does not hold TZ information
		// so feed in zone information for localdatetime and then use to compute other TZ date-times
		// In this example UTC falls on the next date
		LocalDateTime ldt = LocalDateTime.of(2012, 6, 01, 21, 30);
		ZonedDateTime ldtZoned = ldt.atZone(ZoneId.systemDefault());
		System.out.println("NYC epoch time from seconds: " + ldtZoned.toInstant().toEpochMilli() + ", "
				+ "DateTime: " + ldtZoned.toString());
		ZonedDateTime utcZoned = ldtZoned.withZoneSameInstant(ZoneId.of("UTC"));
		System.out.println("UTC epoch time from seconds: " + utcZoned.toInstant().toEpochMilli() + ", "
				+ "DateTime: " + utcZoned.toString());

		// utility function on LocalDateTime
		LocalDateTime localDateTime3 = LocalDateTime.now();
		System.out.println("increment today' date by 1 day - " + localDateTime3.plusDays(1));
		System.out.println("decrement today's date by 1 day - " + localDateTime3.plusDays(-1));

		// convert the LocalDate to util Date by providing Zone information
		ZoneId defaultZoneId = ZoneId.systemDefault();
		LocalDate localDate2 = LocalDate.of(2016, 1, 19);
		Date date2 = Date.from(localDate2.atStartOfDay(defaultZoneId).toInstant());
		System.out.println("LocalDate2 is: " + localDate2);
		System.out.println("Date2 is: " + date2);

		LocalDate date3 = localDateTime3.toLocalDate();
		System.out.println("localdate: " + date3);
		LocalDateTime localDateTime4 = LocalDateTime.of(date3, LocalTime.of(8, 35));
		System.out.println("localdate and add time 8:35: " + localDateTime4);

	}

	public void DateExample_3() {
		List<LocalDateTime> ldtList = new ArrayList<>();
		ldtList.add(LocalDateTime.now().plusDays(4));
		ldtList.add(LocalDateTime.now().plusDays(3));
		ldtList.add(LocalDateTime.now().plusDays(2));
		ldtList.add(LocalDateTime.now().plusDays(5));
		System.out.println("print localdatetime list: " + ldtList);
		System.out.println("find the minimum localdatetime: " + Collections.min(ldtList));
	}

	public void DateExample_4() {
		// Thin wrapper over util date to represent SQL Time - So all methods of date/month/.. such that not 
		// time related are deprecated.
		Time time = Time.valueOf(LocalTime.of(10, 48, 00));
		System.out.println("java sql time: " + time.toString() + ", javal localtime: " + time.toLocalTime());

		
		// Again a Thin wrapper for SQL Date
		LocalDate temp = LocalDate.now();
		java.sql.Date sqlDate = java.sql.Date.valueOf(temp);
		LocalDate sqlLocalDate = sqlDate.toLocalDate();
		java.util.Date utilDate = new java.util.Date(sqlDate.getTime());
		LocalDate utilLocalDate = utilDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		if (utilLocalDate.equals(sqlLocalDate)) {
			System.out.println("local date = util and sql are same");
			System.out.println(sqlDate.getClass().getName());
			System.out.println(utilDate.getClass().getName());
		}
	}

	public void DateExample_5() {
		LocalDateTime temp = LocalDateTime.now();
		java.util.Date utilDate1 = Date.from(temp.atZone(ZoneId.systemDefault()).toInstant());
		java.util.Date utilDate2 = java.sql.Date.valueOf(temp.toLocalDate());
		java.util.Date utilDate3 = java.util.Date.
				from(temp.toLocalDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

		System.out.println("date-time " + utilDate2.equals(utilDate3));

	}

	public void TestDatePlay() {
		// shows usage of Util Date, Calender Instance usage
		DateExample_1();
		
		// shows usage of localdate/localtime/localdatetime
		DateExample_2();
		
		// collection usage on localdate
		DateExample_3();
		
		// Java Util SQL Date/Time on LocalDate/Time
		DateExample_4();
		
		DateExample_5();
	}

}
