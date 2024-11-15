package main.java.com.example.app;

import main.java.com.example.utils.message.Message;
import main.java.com.example.task01.Task01;
import main.java.com.example.utils.math.Add;

public class Run {
	public static void run() {
		System.out.println("Hello World from Run!");
		Message.message();
		System.out.println("1 + 2 = " + Add.add(1, 2));
	}

	public static void runFirst(String[] args) {
		Task01.runFirstImplementation("period_01.txt", "period_02.txt");
		Task01.runFirstImplementation("period_03.txt", "period_04.txt");
		Task01.runFirstImplementation("period_05.txt", "period_06.txt");
		Task01.runFirstImplementation("period_07.txt", "period_08.txt");
		Task01.runFirstImplementation("period_09.txt", "period_10.txt");
	}

	public static void runSecond(String[] args) {
		Task01.runSecondImplementation("period_01.txt", "period_02.txt");
	}

}
