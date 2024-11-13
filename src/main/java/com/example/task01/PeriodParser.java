package main.java.com.example.task01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

class PeriodParser {
	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy");

	public static List<Period> parsePeriodsFromFile(String filePath) {
		List<Period> periods = new ArrayList<>();

		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] dates = line.split("-");
				LocalDate startDate = LocalDate.parse(dates[0].trim(), DATE_FORMAT);
				LocalDate endDate = LocalDate.parse(dates[1].trim(), DATE_FORMAT);
				periods.add(new Period(startDate, endDate));
			}
		} catch (IOException e) {
			System.out.println("Error reading file: " + e.getMessage());
		}

		return periods;
	}

	public static void sortPeriods(List<Period> periods) {
		periods.sort(Comparator.comparing(Period::getStartDate));
	}

	public static List<Period> mergeAndProcessPeriods(List<Period> periodsFromFile1, List<Period> periodsFromFile2) {
		List<Period> mergedPeriods = new ArrayList<>();
		int i = 0, j = 0;

		while (i < periodsFromFile1.size() || j < periodsFromFile2.size()) {
			Period current;

			// Determine the current period to consider
			if (i < periodsFromFile1.size() && (j >= periodsFromFile2.size()
					|| periodsFromFile1.get(i).getStartDate().isBefore(periodsFromFile2.get(j).getStartDate()))) {
				current = periodsFromFile1.get(i++);
			} else {
				current = periodsFromFile2.get(j++);
			}

			// Handle merging or adding current period based on overlap
			if (!mergedPeriods.isEmpty()) {
				Period last = mergedPeriods.get(mergedPeriods.size() - 1);

				// Case 1: No overlap (no merge)
				if (last.getEndDate().isBefore(current.getStartDate())) {
					mergedPeriods.add(current); // No merge, separate periods
				}
				// Case 2: Complete overlap (merge into one)
				else if (last.getStartDate().isBefore(current.getStartDate())
						&& last.getEndDate().isAfter(current.getEndDate())) {
					// Last period completely overlaps current, no merge needed, just continue
				}
				// Case 3: Partial overlap (merge the periods)
				else if (last.getEndDate().isAfter(current.getStartDate())
						|| last.getStartDate().isBefore(current.getEndDate())) {
					// Partial overlap, merge the periods
					LocalDate startDate = last.getStartDate().isBefore(current.getStartDate())
							? last.getStartDate()
							: current.getStartDate();
					LocalDate endDate = last.getEndDate().isAfter(current.getEndDate()) ? last.getEndDate()
							: current.getEndDate();
					Period mergedPeriod = new Period(startDate, endDate);
					mergedPeriod.markAsAltered(); // Mark as merged/altered
					mergedPeriods.set(mergedPeriods.size() - 1, mergedPeriod); // Replace last period with merged
				}
			} else {
				mergedPeriods.add(current); // Add first period directly
			}
		}

		return mergedPeriods;
	}

	public static List<Period> mergeAndFindOverlaps(List<Period> periodsFromFile1, List<Period> periodsFromFile2) {
		List<Period> result = new ArrayList<>();
		List<Period> allPeriods = new ArrayList<>(periodsFromFile1);
		allPeriods.addAll(periodsFromFile2);
		allPeriods.sort(Comparator.comparing(Period::getStartDate));

		Iterator<Period> currentPeriod = periodsFromFile1.iterator();

		while (currentPeriod.hasNext()) {
			Period tmp = currentPeriod.next();
			System.out.println(tmp.toString() + " dasf  " + currentPeriod.toString());
		}

		// for (Period period : allPeriods) {
		// if (currentPeriod == null) {
		// currentPeriod = period;
		// } else if (period.isOverlappingWith(period)) {
		// LocalDate startDate =
		// currentPeriod.getStartDate().isBefore(period.getStartDate())
		// ? currentPeriod.getStartDate()
		// : period.getStartDate();
		// LocalDate endDate = currentPeriod.getEndDate().isAfter(period.getEndDate())
		// ? currentPeriod.getEndDate()
		// : period.getEndDate();
		// currentPeriod = new Period(startDate, endDate);
		// currentPeriod.markAsAltered();
		// } else {
		// result.add(currentPeriod);
		// currentPeriod = period;
		// }
		// }

		// if (currentPeriod != null) {
		// result.add(currentPeriod);
		// }
		return result;
	}

}