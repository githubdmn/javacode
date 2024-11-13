package main.java.com.example.task01;

import java.util.List;

public class Task01 {
	public static void run(String file1, String file2) {

		List<Period> periodsFromFile1 = PeriodParser.parsePeriodsFromFile(file1);
		List<Period> periodsFromFile2 = PeriodParser.parsePeriodsFromFile(file2);

		PeriodParser.sortPeriods(periodsFromFile1);
		PeriodParser.sortPeriods(periodsFromFile2);

		System.out.println("\n\nPeriods from " + file1 + ":");
		periodsFromFile1.forEach(System.out::println);

		System.out.println("\nPeriods from " + file2 + ":");
		periodsFromFile2.forEach(System.out::println);

		List<Period> mergedPeriods = PeriodParser.mergeAndFindOverlaps(periodsFromFile1, periodsFromFile2);

		System.out.println("\nMerged and processed periods:");
		mergedPeriods.forEach(System.out::println);
	}
}
