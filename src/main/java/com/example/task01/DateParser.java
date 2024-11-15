package main.java.com.example.task01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DateParser {

	public static void loadPeriodsFromFile(String fileName, List<LocalDate> startingList, List<LocalDate> endingList) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] dates = line.split("-");
				if (dates.length == 2) {
					LocalDate startDate = LocalDate.parse(dates[0].trim(), formatter);
					LocalDate endDate = LocalDate.parse(dates[1].trim(), formatter);
					startingList.add(startDate);
					endingList.add(endDate);
				}
			}
		} catch (IOException e) {
			System.err.println("Error reading file: " + e.getMessage());
		}
	}

	public static List<Period> adjustPeriods(List<LocalDate> startingList, List<LocalDate> endingList) {
		List<Period> mergedPeriods = new ArrayList<>();

		for (int i = 0; i < startingList.size(); i++) {
			LocalDate start = startingList.get(i);
			LocalDate end = endingList.get(i);

			// If mergedPeriods is empty, add the first period
			if (mergedPeriods.isEmpty()) {
				mergedPeriods.add(new Period(start, end));
				continue;
			}

			// Check for overlap with the last merged period
			Period lastPeriod = mergedPeriods.get(mergedPeriods.size() - 1);
			if (!start.isAfter(lastPeriod.getEndDate())) {
				// There is overlap, so adjust the end date of the last period
				lastPeriod.setEndDate(start.minusDays(1)); // Adjust previous period end date

				// Add the current period as a new period
				mergedPeriods.add(new Period(start, end));
			} else {
				// No overlap, simply add the current period
				mergedPeriods.add(new Period(start, end));
			}
		}

		return mergedPeriods;
	}
}
