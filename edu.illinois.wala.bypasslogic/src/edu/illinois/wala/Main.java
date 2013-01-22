package edu.illinois.wala;

public class Main {
	public static void main(String[] args) {
		Foo a= new Foo();
		int result= a.someIntMethod(1, 2);
		Bar b= a.someBarMethod(new Bar(1), new Bar(2));
		System.out.println(result + b.value);
	}
}
