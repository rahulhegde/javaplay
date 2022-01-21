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
import java.util.Random;

public class DatePlay implements AutoCloseable {
	public int value;
	private final static int staticValue = assignStaticValue();
	
	public static int assignStaticValue () {
		System.out.println("assign static value - ");
		return 1;
	}
	
	public DatePlay() {
		System.out.println("Date Play Constructor");
	}
	
	DatePlay(int value) {
		this.value = value;
	}
	
	public void close() {
		System.out.println("implement AutoCloseable");
	}

	public void DateExample_1( ) {
		// Given Date in String format
		String oldDate = "2017-01-29";
		System.out.println("Date {} before Addition: " + oldDate);

		// Specifying date format that matches the given date
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		try {
			// Setting the date to the given date
			c.setTime(sdf.parse(oldDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// Number of Days to add
		c.add(Calendar.DAY_OF_MONTH, 7);
		// Date after adding the days to the given date
		String newDate = sdf.format(c.getTime());
		// Displaying the new Date after addition of Days
		System.out.println("Date after Addition: " + newDate);		
	}
	
	public void DateExample_2( ) {
		System.out.println("epoch: " + System.currentTimeMillis());
		SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
		Date date = new Date(System.currentTimeMillis());
		System.out.println(formatter.format(date));
		
		LocalDateTime ldt = LocalDateTime.now();
		System.out.println(ldt.toEpochSecond(ZoneOffset.UTC));
		System.out.println(ldt.toEpochSecond(ZoneOffset.of("+02")));
		
		// convert to UTC
		//System.out.println("UTC - " + ldt.now());
		ZonedDateTime ldtZoned = ldt.atZone(ZoneId.systemDefault());
		ZonedDateTime utcZoned = ldtZoned.withZoneSameInstant(ZoneId.of("UTC"));
		System.out.println(utcZoned.toString());
	
		// convert the LocalDate to Date
		ZoneId defaultZoneId = ZoneId.systemDefault();
        LocalDate localDate2 = LocalDate.of(2016, 1, 19);
		Date date2 = Date.from(localDate2.atStartOfDay(defaultZoneId).toInstant());
        System.out.println("LocalDate2 is: " + localDate2);
        System.out.println("Date2 is: " + date2);
        
		// convert the LocalDate to Date
        LocalDateTime localDateTime3 = LocalDateTime.now();
        System.out.println("increment - " + localDateTime3.plusDays(1));
        System.out.println("increment - " + localDateTime3.plusDays(-1));

        
        LocalDate date3 = localDateTime3.toLocalDate();
        System.out.println("date3 - " + date3);
        LocalDateTime localDateTime4 = LocalDateTime.of(date3, LocalTime.of(8, 35));
        System.out.println("date4 " + localDateTime4);
        
        //Time time = new Time(1612093922);
        Time time = Time.valueOf(LocalTime.of(10, 48, 00));
        System.out.println("time in local " + time + "local time: " + time.toLocalTime() + " local date time string " + LocalTime.of(10, 48).toString());
        
        
        boolean status = true;
        status = status & true;
        System.out.println(status);
        status &= false;
        System.out.println(status);
        status &= true;
        status &= true;
        status &= true;
        System.out.println(status);


        LocalTime localTime = LocalTime.parse("08:20");
        System.out.println("local time: " + localTime.plusHours(2));
        
        
	}
	
	public void DateExample_3( ) {
		List<LocalDateTime> ldtList = new ArrayList<>();
		ldtList.add(LocalDateTime.now().plusDays(4));
		ldtList.add(LocalDateTime.now().plusDays(3));
		ldtList.add(LocalDateTime.now().plusDays(2));
		ldtList.add(LocalDateTime.now().plusDays(5		));
		System.out.println("All" + ldtList);

		System.out.println("Minimum" + Collections.min(ldtList));
		
		String str1 = new String ("new");
		String str2 = new String("new");

		Integer I1 = Integer.valueOf(1);
		Integer I2 = Integer.valueOf(1);
			
		if (I1 == I2) {
			System.out.println("equal - " + I1 + I2);
		}		
		
		BigDecimal b1 = BigDecimal.valueOf(100);
		BigDecimal b2 = BigDecimal.valueOf(100);
		
		if (b1.equals(b2)) {
			System.out.println("equal big decimal- " + b1 + b2);

		}		
	}
	
	public void DateExample_4( ) {
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
	
	public void DateExample_5( ) {
		LocalDateTime temp = LocalDateTime.now();
		java.util.Date utilDate1 = Date.from(temp.atZone(ZoneId.systemDefault()).toInstant());
		java.util.Date utilDate2 = java.sql.Date.valueOf(temp.toLocalDate());
		java.util.Date utilDate3 = java.util.Date.from(temp.toLocalDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

		System.out.println("date-time " + utilDate2.equals(utilDate3));

	}
	
	public void TestDatePlay() {

		DateExample_1();		
		DateExample_3();

		List<DatePlay> holder = new ArrayList<DatePlay>(5);
		System.out.println("holder: size: " + holder.size());
		holder.add(new DatePlay(1));
		System.out.println("holder: size: " + holder.size());
	    
		DateExample_4();
		DateExample_5();
		
	}
	
	
}
