package com.company.annotations;

public class TestClass extends ParrentClass implements ITestInterface {

    private int testFieldInt = 1;
    private String testFieldString = "test string";
    private final int TESTCONSTANT = 2;
    public int publicInt;

    private TestClass(int testFieldInt, String testFieldString) {
        this.testFieldInt = testFieldInt;
        this.testFieldString = testFieldString;
        System.out.println(testFieldString);
    }

    TestClass(String testFieldString) {
        this.testFieldString = testFieldString;
    }

    public TestClass(int testFieldInt) {
        this.testFieldInt = testFieldInt;
    }

    public TestClass() {
    }

    private void testMethod(String text) {
        System.out.println(text);
    }

    public int getTESTCONSTANT() {
        return TESTCONSTANT;
    }

    public int getTestFieldInt() {
        return testFieldInt;
    }

//    public void setTestFieldInt(int testFieldInt) {
//        this.testFieldInt = testFieldInt;
//    }

    public String getTestFieldString() {
        return testFieldString;
    }

//    public void setTestFieldString(String testFieldString) {
//        this.testFieldString = testFieldString;
//    }

    @Override
    public void method1() {
        System.out.println("method1() has been invoked");
    }

    @Override
    public void method2() {
        System.out.println("method2() has been invoked");
    }
}
