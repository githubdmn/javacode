package main.java.com.example.app;

import main.java.com.example.task01.Task01;

public class App {
	public static void main(String[] args) {
		System.out.println("Hello World!");
		Task01.run("period_01.txt", "period_02.txt");
		Task01.run("period_03.txt", "period_04.txt");
		Task01.run("period_05.txt", "period_06.txt");
		Task01.run("period_07.txt", "period_08.txt");
		Task01.run("period_09.txt", "period_10.txt");
	}
}
