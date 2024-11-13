package main.java.com.example.task01;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class Period {
	private LocalDate startDate;
	private LocalDate endDate;
	private boolean isAltered;
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

	public Period(LocalDate startDate, LocalDate endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.isAltered = false;
	}

	public Period(String startDate, String endDate, boolean isAltered) {
		this.startDate = LocalDate.parse(startDate, formatter);
		this.endDate = LocalDate.parse(endDate, formatter);
		this.isAltered = isAltered;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public boolean isOverlappingWith(Period other) {
		return this.startDate.isBefore(other.endDate) && this.endDate.isAfter(other.startDate);
	}

	public boolean isStartingIn(Period other) {
		return this.startDate.isBefore(other.endDate);
	}

	public boolean isEndingIn(Period other) {
		return this.endDate.isAfter(other.startDate);
	}

	public boolean isCoveringWith(Period other) {
		return !this.startDate.isAfter(other.startDate) && !this.endDate.isBefore(other.endDate);
	}

	public void markAsAltered() {
		this.isAltered = true;
	}

	@Override
	public String toString() {
		if (isAltered) {
			return startDate.format(formatter) + " - " + endDate.format(formatter) + "*";
		} else {
			return startDate.format(formatter) + " - " + endDate.format(formatter);
		}
	}

}