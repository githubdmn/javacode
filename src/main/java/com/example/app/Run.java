package main.java.com.example.app;

import main.java.com.example.utils.message.Message;
import main.java.com.example.utils.math.Add;

public class Run {
	public static void run() {
		System.out.println("Hello World from Run!");
		Message.message();
		System.out.println("1 + 2 = " + Add.add(1, 2));
	}
}
