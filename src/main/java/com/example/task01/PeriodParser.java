package main.java.com.example.task01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
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

	public static List<Period> mergeAndProcessPeriods(List<Period> periods1, List<Period> periods2) {
		List<Period> adjustedPeriods = new ArrayList<>();

		for (Period period1 : periods1) {
			boolean hasOverlap = false;

			for (Period period2 : periods2) {
				if (period1.getEndDate().isBefore(period2.getStartDate())
						|| period1.getStartDate().isAfter(period2.getEndDate())) {
					continue;
				}

				hasOverlap = true;

				if (period1.getStartDate().isBefore(period2.getStartDate())) {
					adjustedPeriods
							.add(new Period(period1.getStartDate(), period2.getStartDate().minusDays(1), true));
				}

				adjustedPeriods.add(new Period(period2.getStartDate(), period2.getEndDate()));

				if (period1.getEndDate().isAfter(period2.getEndDate())) {
					period1 = new Period(period2.getEndDate().plusDays(1), period1.getEndDate(), true);
				} else {
					period1 = null;
					break;
				}
			}

			if (!hasOverlap && period1 != null) {
				adjustedPeriods.add(period1);
			} else if (hasOverlap && period1 != null) {
				adjustedPeriods.add(period1);
			}
		}

		for (Period period2 : periods2) {
			boolean hasOverlap = false;
			for (Period period1 : periods1) {
				if (!(period2.getEndDate().isBefore(period1.getStartDate())
						|| period2.getStartDate().isAfter(period1.getEndDate()))) {
					hasOverlap = true;
					break;
				}
			}
			if (!hasOverlap) {
				adjustedPeriods.add(period2);
			}
		}

		return adjustedPeriods;
	}

	public static List<Period> findAndSplitOverlaps(List<Period> list1, List<Period> list2) {
		List<Period> allPeriods = new ArrayList<>(list1);
		allPeriods.addAll(list2);
		allPeriods.sort(Comparator.comparing(Period::getStartDate));

		// print alll periods
		System.out.println("\n All periods:");
		for (Period period : allPeriods) {
			System.out.println(period);
		}

		List<Period> result = new ArrayList<>();
		Period current = allPeriods.get(0);

		for (int i = 1; i < allPeriods.size(); i++) {
			Period next = allPeriods.get(i);
			Period nextnext;
			if (i < allPeriods.size() - 1) {
				nextnext = allPeriods.get(i + 1);
			} else {
				nextnext = null;
			}

			if (nextnext == null) {
				break;
			}

			if (!result.isEmpty() && result.getLast().getEndDate().isAfter(current.getEndDate())) {
				result.removeLast();
				break;
			}

			if (current.isOverlappingWith(next)) {
				if (next.isStartingIn(current)) {
					Period beforeOverlap = new Period(current.getStartDate(),
							next.getStartDate().minusDays(1), true);
					result.add(beforeOverlap);
					result.add(next);
				}

				if (next.getEndDate().isAfter(nextnext.getEndDate())) {
					break;
				}

				if (next.isEndingIn(nextnext)) {
					Period afterOverlap = new Period(next.getEndDate().plusDays(1), nextnext.getEndDate(),
							true);
					result.add(afterOverlap);
				}
			} else {
				result.add(current);
			}
			current = next;
		}

		return result;
	}

	public static List<Period> removeDuplicates(List<Period> periods) {
		periods.sort(Comparator.comparing(Period::getStartDate).thenComparing(Period::getEndDate));
		for (int i = 0; i < periods.size() - 1; i++) {
			Period current = periods.get(i);
			Period next = periods.get(i + 1);
			if (current.getStartDate().equals(next.getStartDate())) {
				periods.remove(next);
				i--;
			}
		}
		return periods;
	}

	// TODO: fix this
	// https://javaconceptoftheday.com/merge-two-sorted-arrays-in-java/
	public static List<Period> mergeAndProcessPeriodsOptimised(List<Period> periods1, List<Period> periods2) {
		List<Period> adjustedPeriods = new ArrayList<>();
		int i = 0, j = 0;

		while (i < periods1.size() && j < periods2.size()) {
			Period period1 = periods1.get(i);
			Period period2 = periods2.get(j);

		}

		while (i < periods1.size()) {
			adjustedPeriods.add(periods1.get(i++));
		}
		while (j < periods2.size()) {
			adjustedPeriods.add(periods2.get(j++));
		}

		return adjustedPeriods;
	}

}
