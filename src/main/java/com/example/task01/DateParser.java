package main.java.com.example.task01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DateParser {

	public static DateLists splitPeriods(List<Period> periods) {
		List<LocalDate> startingDates = new ArrayList<>();
		List<LocalDate> endingDates = new ArrayList<>();

		if (periods != null) {
			for (Period period : periods) {
				startingDates.add(period.getStartDate());
				endingDates.add(period.getEndDate());
			}
		}

		return new DateLists(startingDates, endingDates);
	}

	public static List<Period> adjustPeriods(List<LocalDate> startingList1, List<LocalDate> endingList1,
			List<LocalDate> startingList2, List<LocalDate> endingList2) {
		List<Period> mergedPeriods = new ArrayList<>();

		int i = 0, j = 0;

		while (i < startingList1.size() || j < startingList2.size()) {
			// Determine current period
			LocalDate start1 = (i < startingList1.size()) ? startingList1.get(i) : null;
			LocalDate end1 = (i < endingList1.size()) ? endingList1.get(i) : null;
			LocalDate start2 = (j < startingList2.size()) ? startingList2.get(j) : null;
			LocalDate end2 = (j < endingList2.size()) ? endingList2.get(j) : null;

			// Case 1: Use period from list1 if it's earlier or list2 is exhausted
			if (start2 == null || (start1 != null && start1.isBefore(start2))) {
				// Adjust based on overlap with list2
				if (start2 != null && end1 != null && !end1.isBefore(start2)) {
					mergedPeriods.add(new Period(start1, start2.minusDays(1)));
					startingList1.set(i, start2); // Split the current period
				} else {
					mergedPeriods.add(new Period(start1, end1));
					i++; // Move to next period in list1
				}
			}
			// Case 2: Use period from list2 if it's earlier or list1 is exhausted
			else if (start1 == null || (start2 != null && start2.isBefore(start1))) {
				// Adjust based on overlap with list1
				if (start1 != null && end2 != null && !end2.isBefore(start1)) {
					mergedPeriods.add(new Period(start2, start1.minusDays(1)));
					startingList2.set(j, start1); // Split the current period
				} else {
					mergedPeriods.add(new Period(start2, end2));
					j++; // Move to next period in list2
				}
			}
			// Case 3: Overlapping periods, merge them
			else {
				LocalDate mergedStart = start1.isBefore(start2) ? start1 : start2;
				LocalDate mergedEnd = (end1 != null && end2 != null) ? (end1.isAfter(end2) ? end1 : end2)
						: (end1 != null ? end1 : end2);
				mergedPeriods.add(new Period(mergedStart, mergedEnd));
				i++;
				j++;
			}
		}

		return mergedPeriods;
	}
}
