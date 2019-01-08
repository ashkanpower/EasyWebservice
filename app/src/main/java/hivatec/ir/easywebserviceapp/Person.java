package hivatec.ir.easywebserviceapp;

import java.util.ArrayList;

public class Person {

	int id;
	String name;
	ArrayList<Integer> items;
	Info info;

	public Person(int id, String name) {
		this.id = id;
		this.name = name;
		items = new ArrayList<>();
		items.add(1);
		items.add(10);
		items.add(100);
		info = new Info();
	}
}
