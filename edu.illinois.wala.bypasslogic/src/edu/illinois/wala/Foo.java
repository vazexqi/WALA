package edu.illinois.wala;

public class Foo {
	public int field1;

	public int field2;

	public int someIntMethod(int a, int b) {
		return a + b;
	}

	public Bar someBarMethod(Bar a, Bar b) {
		return new Bar(a.value + b.value);
	}
}
