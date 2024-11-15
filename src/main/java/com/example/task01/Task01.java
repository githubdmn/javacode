package main.java.com.example.task01;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Task01 {
	public static void runFirstImplementation(String file1, String file2) {

		List<Period> periodsFromFile1 = PeriodParser.parsePeriodsFromFile(file1);
		List<Period> periodsFromFile2 = PeriodParser.parsePeriodsFromFile(file2);

		PeriodParser.sortPeriods(periodsFromFile1);
		PeriodParser.sortPeriods(periodsFromFile2);

		System.out.println("\n\nPeriods from " + file1 + ":");
		periodsFromFile1.forEach(System.out::println);

		System.out.println("\nPeriods from " + file2 + ":");
		periodsFromFile2.forEach(System.out::println);

		List<Period> mergedPeriods = PeriodParser.mergeAndProcessPeriods(periodsFromFile1, periodsFromFile2);

		// List<Period> mergedPeriods =
		// PeriodParser.mergeAndProcessPeriodsOptimised(periodsFromFile1,
		// periodsFromFile2);

		mergedPeriods = PeriodParser.removeDuplicates(mergedPeriods);

		System.out.println("\nMerged and processed periods:");
		mergedPeriods.forEach(System.out::println);
	}

	public static void runSecondImplementation(String file1, String file2) {
		List<LocalDate> startingList1 = new ArrayList<>();
		List<LocalDate> endingList1 = new ArrayList<>();

		List<LocalDate> startingList2 = new ArrayList<>();
		List<LocalDate> endingList2 = new ArrayList<>();

		DateParser.loadPeriodsFromFile(file1, startingList1, endingList1);
		DateParser.loadPeriodsFromFile(file2, startingList2, endingList2);

		Collections.sort(startingList1);
		Collections.sort(endingList1);

		Collections.sort(startingList2);
		Collections.sort(endingList2);

		System.out.println("Starting Dates: " + startingList1);
		System.out.println("Ending Dates: " + endingList1);

		System.out.println("Starting Dates: " + startingList2);
		System.out.println("Ending Dates: " + endingList2);

		List<Period> mergedPeriods = DateParser.adjustPeriods(startingList1, endingList1);

		// Output the merged periods
		System.out.println("Merged Periods:");
		for (Period period : mergedPeriods) {
			System.out.println(period);
		}
	}

}
