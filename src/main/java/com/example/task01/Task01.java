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
		List<Period> periodsFromFile1 = PeriodParser.parsePeriodsFromFile(file1);
		List<Period> periodsFromFile2 = PeriodParser.parsePeriodsFromFile(file2);

		PeriodParser.sortPeriods(periodsFromFile1);
		PeriodParser.sortPeriods(periodsFromFile2);

		// System.out.println("\n\nPeriods from " + file1 + ":");
		// periodsFromFile1.forEach(System.out::println);

		// System.out.println("\nPeriods from " + file2 + ":");
		// periodsFromFile2.forEach(System.out::println);

		DateLists lists1 = DateParser.splitPeriods(periodsFromFile1);
		DateLists lists2 = DateParser.splitPeriods(periodsFromFile2);

		// System.out.println(lists1.getStartingDates());
		// System.out.println(lists1.getEndingDates());

		// System.out.println(lists2.getStartingDates());
		// System.out.println(lists2.getEndingDates());

		List<Period> mergedPeriods = DateParser.adjustPeriods(lists1.getStartingDates(), lists1.getEndingDates(),
				lists2.getStartingDates(), lists2.getEndingDates());

		System.out.println(mergedPeriods.toString());
	}

}
