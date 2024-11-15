package main.java.com.example.task01;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class Period {
	private LocalDate startDate;
	private LocalDate endDate;
	private boolean isAltered;
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

	public Period(LocalDate startDate, LocalDate endDate, boolean isAltered) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.isAltered = isAltered;
	}

	public Period(LocalDate startDate, LocalDate endDate) {
		this(startDate, endDate, false);
	}

	public Period(Period period) {
		this(period.startDate, period.endDate, period.isAltered);
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public boolean isOverlappingWith(Period other) {
		return !this.endDate.isBefore(other.startDate) && !this.startDate.isAfter(other.endDate);
	}

	public boolean isStartingIn(Period other) {
		return this.startDate.isBefore(other.endDate);
	}

	public boolean isEndingIn(Period other) {
		return this.endDate.isAfter(other.startDate);
	}

	public boolean isContaining(Period other) {
		return !this.startDate.isBefore(other.startDate) && !this.endDate.isAfter(other.endDate);
	}

	public void markAsAltered() {
		this.isAltered = true;
	}

	public boolean isAltered() {
		return isAltered;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Period period = (Period) o;
		return startDate.equals(period.startDate) && endDate.equals(period.endDate) && isAltered == period.isAltered;
	}

	@Override
	public String toString() {
		return startDate.format(formatter) + " - " + endDate.format(formatter) + (isAltered ? "*" : "");
	}

}