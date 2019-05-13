package com.company.annotations;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

public class BusinessLogic {

    // Collections for private and public fields
    ArrayList<Field> publicFields = new ArrayList<>();
    ArrayList<Field> privateFields = new ArrayList<>();
    // Collection for private and public methods
    ArrayList<Method> publicMethod = new ArrayList<>();
    ArrayList<Method> privateMethod = new ArrayList<>();
    ArrayList<Constructor> privateConstructors = new ArrayList<>();
    // Class link
    private TestClass testClass;

    public BusinessLogic() {
        this.testClass = new TestClass();
    }

    /**
     * @return class
     */
    public Class getClassObject() {

        System.out.println("CLASS NAME IS: " + testClass.getClass().getName());
        return testClass.getClass();
    }

    /**
     * @return class modifier as int
     */
    public int getClassModifiers() {

        System.out.println("MODIFIER IS: " + Modifier.toString(testClass
                .getClass().getModifiers()));
        return testClass.getClass().getModifiers();
    }

    /**
     * @return fields array
     */
    public Field[] getClassFields(Object object) {

        return object.getClass().getDeclaredFields();
    }

    /**
     * @param fields field array for sorting
     */
    public void separateFieldsByModifier(Field[] fields) {

        for (Field field : fields) {
            // get access to private fields
            field.setAccessible(true);
            // select private and public fields
            switch (Modifier.toString(field.getModifiers())) {
                case "public":
                    publicFields.add(field);
                    break;
                case "private":
                    privateFields.add(field);
                    break;
            }
        }
        System.out.println("==================");
        Stream.of(privateFields).forEach(System.out::println);
        Stream.of(publicFields).forEach(System.out::println);
        System.out.println("==================");
    }

    /**
     * @return array of all methods
     */
    public Method[] getClassMethods(Object object) {

        return object.getClass().getDeclaredMethods();
    }

    /**
     * @param methods array of methods
     */
    public void separateMethodsByModifier(Method[] methods) {

        for (Method method : methods) {
            switch (Modifier.toString(method.getModifiers())) {
                case "public":
                    publicMethod.add(method);
                    break;
                case "private":
                    privateMethod.add(method);
                    break;
            }
        }
        System.out.println("=================");
        Stream.of(privateMethod).forEach(System.out::println);
        Stream.of(publicMethod).forEach(System.out::println);
        System.out.println("=================");
    }

    /**
     * @return array with all constructors
     */
    public Constructor[] getClassConstructors(Object object) {

        System.out.println("ALL CONSTRUCTORS: ");
        for (Constructor constructor : object.getClass().getDeclaredConstructors()) {

            System.out.println(Modifier.toString(object.getClass().getModifiers()));
            if (Modifier.isPrivate(object.getClass().getModifiers())) {
                privateConstructors.add(constructor);
            }
        }
        return object.getClass().getDeclaredConstructors();
    }

    /**
     * @return superclass
     */
    public Class getClassSuperclass(Object object) {

        System.out.println("SUPERCLASS IS: ");
        return object.getClass().getSuperclass();
    }

    /**
     * @return arraylist with method of interface
     */
    public ArrayList getInterfaceMethods() {

        Class[] interfaces = testClass.getClass().getInterfaces();
        ArrayList<Method> interfaceMethods = new ArrayList<>();
        for (Class interfaceObject : interfaces) {
            interfaceMethods.addAll(Arrays.asList(interfaceObject
                    .getDeclaredMethods()));
        }
        return interfaceMethods;
    }

    /**
     * @param object    object to change
     * @param fieldName field to change
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public void changeObjectFieldByName(Object object, String fieldName) throws
            NoSuchFieldException, IllegalAccessException {

        Field field = getPrivateField(object, fieldName);
        field.set(object, 10);
        System.out.println("AFTER CHANGE:");
        System.out.println(field.getName() + " = " + field.get(object));
    }

    /**
     * @param object    object to get private field
     * @param fieldName name of field
     * @return instance of field you can handle
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private Field getPrivateField(Object object, String fieldName) throws
            NoSuchFieldException, IllegalAccessException {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        System.out.println("BEFORE CHANGE:");
        System.out.println(field.getName() + " = " + field.get(object));
        return field;
    }

    /**
     * @param className name of class
     * @return instance of called class
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public Object createClassInstanceByName(String className) throws
            ClassNotFoundException, IllegalAccessException,
            InstantiationException {

        Class invokedClass = Class.forName(className);
        System.out.println(invokedClass.getName());
        return invokedClass.newInstance();
    }

    /**
     * @param object     object of class
     * @param methodName name of method
     * @return instance of method
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public Method invokeMethodByName(Object object, String methodName) throws
            NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Method method = object.getClass().getDeclaredMethod(methodName);
        method.setAccessible(true);
        method.invoke(object);
        return method;
    }

    /**
     * Method invoke private method from ArrayList
     * with arguments
     */
    public void invokePrivateMethodWithParams() {

        Method method = privateMethod.get(0);
        method.setAccessible(true);
        try {
            method.invoke(TestClass.class.newInstance(), "method " +
                    method.getName() + " has been invoked");
        } catch (InstantiationException | IllegalAccessException |
                InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void invokePrivateConstructorWithParams() {

        Constructor constructor = null;
        try {
            constructor = TestClass.class.getDeclaredConstructor(int.class, String.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        constructor.setAccessible(true);
        try {
            TestClass testClass = (TestClass) constructor.newInstance(1,
                    "private constructor has been invoked");
            System.out.println(testClass.getTestFieldString());
        } catch (InstantiationException | IllegalAccessException |
                InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * Hijack "hello" string and change it to "bye"
     *
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public void hijackPrintln() throws NoSuchFieldException,
            IllegalAccessException {
        Field field = "hello".getClass().getDeclaredField("value");
        field.setAccessible(true);
        field.set("hello", "bue".toCharArray());
        System.out.println("hello");
    }
}
