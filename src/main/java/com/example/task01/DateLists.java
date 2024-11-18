package main.java.com.example.task01;

import java.time.LocalDate;
import java.util.List;

public class DateLists {
	private final List<LocalDate> startingDates;
	private final List<LocalDate> endingDates;

	public DateLists(List<LocalDate> startingDates, List<LocalDate> endingDates) {
		this.startingDates = startingDates;
		this.endingDates = endingDates;
	}

	public List<LocalDate> getStartingDates() {
		return startingDates;
	}

	public List<LocalDate> getEndingDates() {
		return endingDates;
	}
}
