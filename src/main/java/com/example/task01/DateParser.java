package main.java.com.example.task01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
}
