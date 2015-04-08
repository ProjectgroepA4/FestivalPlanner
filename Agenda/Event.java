package Agenda;


import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Event implements Serializable {

	private static final long serialVersionUID = 1;

	private String eventName;
	private GregorianCalendar startDate;
	private GregorianCalendar endDate;
	private int startHour;
	private int startMinute;
	private int stopHour;
	private int stopMinute;
	private Artist artist;
	private AgendaStage stage;
	private String description;
	private int expectedPopularity;

	public Event(String eventName, int startYear, int startMonth, int startDay,
			int startHour, int startMinute, int endYear, int endMonth,
			int endDay, int endHour, int endMinute, Artist artist, AgendaStage stage,
			String description, int expectedPopularity) {
		this.eventName = eventName;
		this.startDate = new GregorianCalendar(startYear, startMonth - 1,
				startDay, startHour, startMinute);
		this.endDate = new GregorianCalendar(endYear, endMonth - 1, endDay,
				endHour, endMinute);
		this.startHour = startHour;
		this.startMinute = startMinute;
		this.stopHour = endHour;
		this.stopMinute = endMinute;
		this.stage = stage;
		this.artist = artist;
		this.description = description;
		this.expectedPopularity = expectedPopularity;
	}

	public Event(String eventName, GregorianCalendar startDate,
			GregorianCalendar endDate, Artist artist, AgendaStage stage,
			String description, int expectedPopularity) {
		this.eventName = eventName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.artist = artist;
		this.stage = stage;
		this.description = description;
		this.expectedPopularity = expectedPopularity;
	}
	
	public int getStart(){
		return (startHour * 60) + startMinute;
	}
	
	public int getStop(){
		return (stopHour * 60) + stopMinute;
	}
	
	public int getStopHour(){
		return this.stopHour;
	}

	public String getEventName() {
		return this.eventName;
	}

	public GregorianCalendar getStartDate() {
		return this.startDate;
	}

	public GregorianCalendar getEndDate() {
		return this.endDate;
	}

	public Artist getArtist() {
		return this.artist;
	}

	public String getDescription() {
		return this.description;
	}

	public AgendaStage getStage() {
		return this.stage;
	}

	public int getExpectedPopularity() {
		return this.expectedPopularity;
	}

	public void setEventName(String name) {
		this.eventName = name;
	}

	public void setStartDate(int startYear, int startMonth, int startDay,
			int startHour, int startMinute) {
		startDate.set(startYear, startMonth - 1, startDay, startHour,
				startMinute);
	}

	public void setStartDate(GregorianCalendar startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(int endYear, int endMonth, int endDay, int endHour,
			int endMinute) {
		endDate.set(endYear, endMonth - 1, endDay, endHour, endMinute);
	}

	public void setEndDate(GregorianCalendar endDate) {
		this.endDate = endDate;
	}

	public void setArtist(Artist artist) {
		this.artist.setName(artist.getName());
		this.artist.setGenre(artist.getGenre());
		this.artist.setImageIcon(artist.getImage());
		this.artist.setDescription(artist.getDescription());
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setExpectedPopularity(int popularity) {
		this.expectedPopularity = popularity;
	}

	public void setStage(AgendaStage stage) {
		this.stage = stage;
	}
	
	public int getLength()
	{
		return getEndTime() - getStartTime();
	}
	
	public int getStartTime()
	{
		DateFormat format = new SimpleDateFormat("kkmm");
		format.setLenient(false);
//		System.out.println(format.format(startDate.getTime()));
		
//		System.out.println(date.get(Calendar.HOUR_OF_DAY));
		int time = Integer.valueOf(format.format(startDate.getTime()));
		int hours = time/100;
		int minutes = time%100;
		int finaltime;
		if(minutes != 0)
		{
			finaltime = hours*60 + minutes;
		}
		else
		{
			finaltime = hours*60;
		}
		return finaltime;
	}
	
	public int getEndTime()
	{
		DateFormat format = new SimpleDateFormat("kkmm");
		format.setLenient(false);
//		System.out.println(format.format(endDate.getTime()));
		int time = Integer.valueOf(format.format(endDate.getTime()));
		int hours = time/100;
		int minutes = time%100;
		int finaltime;
		if(minutes != 0)
		{
			finaltime = hours*60 + minutes;
		}
		else
		{
			finaltime = hours*60;
		}
		
		return finaltime;
	}
	
	public void setStartTime(int hours, int minutes)
	{
//		System.out.println("Start:" + hours + ":" + minutes);
		startDate.set(Calendar.HOUR_OF_DAY, hours);
		startDate.set(Calendar.MINUTE, minutes);
//		System.out.println(startDate.getTime());
	}
	
	public void setEndTime(int hours, int minutes)
	{
//		System.out.println("End:" + hours + ":" + minutes);
		endDate.set(Calendar.HOUR_OF_DAY, hours);
		endDate.set(Calendar.MINUTE, minutes);
//		System.out.println(endDate.getTime());
	}

	public String toString() {
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		format.setLenient(false);

		return artist.getName() + " plays from "
				+ format.format(startDate.getTime()) + " to "
				+ format.format(endDate.getTime()) + ".";
	}
}
