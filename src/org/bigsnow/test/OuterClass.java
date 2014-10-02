package org.bigsnow.test;

/**
 * Use the following JAD commands to see the synthetic methods (access$0, etc.) and fields (this$0) that javac generates.
 *
 * jad -noinner classes/org/bigsnow/test/OuterClass.class
 * jad -noinner classes/org/bigsnow/test/OuterClass\$InnerClass.class
 * @author dwinters
 *
 */

public class OuterClass {
	private Integer outerInt1 = new Integer(77);
	private String outerStr2 = "Hello!";

	private class InnerClass {
		public void printVars() {
			System.out.println(String.format("outerInt1 = %s, outerStr2 = %s", outerInt1, outerStr2));
		}
	}

	private InnerClass newInnerClass() {
		return new InnerClass();
	}

	public static void main(String[] args) {
		OuterClass oc = new OuterClass();

		InnerClass ic1 = oc.new InnerClass();
		InnerClass ic2 = oc.newInnerClass();
		ic1.printVars();
		ic2.printVars();
	}

}
