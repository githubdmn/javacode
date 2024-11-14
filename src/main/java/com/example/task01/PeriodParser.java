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

			if (!result.isEmpty() && result.getLast().isContaining(current)) {
				result.removeLast();
			}

			if (current.isOverlappingWith(next)) {
				if (next.isStartingIn(current)) {
					Period beforeOverlap = new Period(current.getStartDate(),
							next.getStartDate().minusDays(1), true);
					result.add(beforeOverlap);
					result.add(next);
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
}

/*
 * Merged and processed periods:
 * 18-03-1992 - 09-02-1994*
 * 10-02-1994 - 20-10-1995
 * 10-02-1994 - 20-10-1995
 * 01-06-2002 - 01-08-2003
 * 01-08-2003 - 25-12-2005
 * 26-12-2005 - 06-12-2006*
 * 07-12-2006 - 05-01-2014
 * 06-01-2014 - 15-07-2015*
 * 07-12-2006 - 14-06-2013*
 * 15-06-2013 - 15-07-2015
 * 
 * 18-03-1992 - 09-02-1994*
 * 10-02-1994 - 20-10-1995
 * 10-02-1994 - 20-10-1995
 * 01-06-2002 - 01-08-2003
 * 01-08-2003 - 25-12-2005
 * 26-12-2005 - 06-12-2006*
 * 07-12-2006 - 05-01-2014
 * 06-01-2014 - 15-07-2015*
 * 
 */