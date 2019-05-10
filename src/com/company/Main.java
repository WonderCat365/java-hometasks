package com.company;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException,
            IllegalAccessException, InstantiationException,
            InvocationTargetException, NoSuchMethodException {

        // Collections for private and public fields
        ArrayList<Field> publicFields = new ArrayList<>();
        ArrayList<Field> privateFields = new ArrayList<>();
        // Collection for private and public methods
        ArrayList<Method> publicMethod = new ArrayList<>();
        ArrayList<Method> privateMethod = new ArrayList<>();
        // Class link
        TestClass testClass;
        final String SEPARATOR = "===============================";


        /**
         * Get class object
         */
        testClass = new TestClass();
        System.out.println("CLASS NAME IS: " + testClass.getClass().getName());

        /**
         * getModifier() is a native method returns modifier encoded as int
         * Override method toString() in Modifier class returns modifier
         * if ((mod & PUBLIC) != 0)        sb.append("public ");
         * if ((mod & PROTECTED) != 0)     sb.append("protected ");
         * if ((mod & PRIVATE) != 0)       sb.append("private ");
         * if ((mod & ABSTRACT) != 0)      sb.append("abstract ");
         * if ((mod & STATIC) != 0)        sb.append("static ");
         * if ((mod & FINAL) != 0)         sb.append("final ");
         * if ((mod & TRANSIENT) != 0)     sb.append("transient ");
         * if ((mod & VOLATILE) != 0)      sb.append("volatile ");
         * if ((mod & SYNCHRONIZED) != 0)  sb.append("synchronized ");
         * if ((mod & NATIVE) != 0)        sb.append("native ");
         * if ((mod & STRICT) != 0)        sb.append("strictfp ");
         * if ((mod & INTERFACE) != 0)     sb.append("interface ");
         */
        System.out.println("MODIFIER OF " + testClass.getClass().getName() +
                " IS: " + Modifier.toString(testClass.getClass()
                .getModifiers()));

        /**
         * getFields() allows see only public fields.
         * getDeclaredFields() allows see all fields.
         */
        System.out.println(SEPARATOR);
        System.out.println("FIELDS: ");

        Field[] fields = testClass.getClass().getDeclaredFields();
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
            System.out.println("VALUE AFTER CHANGE");
            System.out.println(field.getName() + " = " + field.get(testClass)
                    .toString());

            // change private fields
            switch (field.getType().getName()) {
                case "java.lang.String":
                    field.set(testClass, "this string was changed");
                    break;
                case "int":
                    field.set(testClass, 3);
                    break;
            }
            System.out.println("VALUE AFTER CHANGE");
            System.out.println(field.getName() + " = " + field.get(testClass)
                    .toString());
        }

        /**
         * Get methods
         */
        System.out.println(SEPARATOR);
        System.out.println("METHODS: ");

        // get all methods
        Method[] methods = testClass.getClass().getDeclaredMethods();

        for (Method method : methods) {

            Method invokedMethod = testClass.getClass().getDeclaredMethod(method.getName());

            // get access to private method
            invokedMethod.setAccessible(true);
            invokedMethod.invoke(testClass);
            System.out.println(invokedMethod);

            switch (Modifier.toString(method.getModifiers())) {
                case "public":
                    publicMethod.add(method);
                    break;
                case "private":
                    privateMethod.add(method);
                    break;
            }
        }

        /**
         * Same behavior as for fields.
         */
        System.out.println(SEPARATOR);
        System.out.println("CONSTRUCTORS: ");

        // get all constructors
        Constructor[] constructors = testClass.getClass().getDeclaredConstructors();

        for (Constructor constructor : constructors) {

            System.out.println(constructor);

            // Select private constructor
            if (Modifier.toString(constructor.getModifiers()).equals("private")) {

                System.out.println(SEPARATOR);
                System.out.println("INVOKE PRIVATE CONSTRUCTOR WITH PARAMETERS");

                Class privateConstructor = Class.forName(TestClass.class.getName());

                // create required parameters
                Class[] params = {int.class, String.class};
                Constructor cons = privateConstructor.getDeclaredConstructor(params);

                // get access to private constructor
                cons.setAccessible(true);

                // invoke private constructor
                TestClass testClass1 = (TestClass) cons.newInstance(1, "qw");

                System.out.println(testClass1.getTestFieldInt());
                System.out.println(testClass1.getTestFieldString());
            }
        }

        /**
         * get superclass
         */
        System.out.println(SEPARATOR);
        System.out.println("SUPERCLASS IS: " + testClass.getClass().getSuperclass());

        Class myClass = TestClass.class;

        /**
         * get interface methods
         */
        System.out.println(SEPARATOR);
        System.out.println("INTERFACE METHODS: ");

        // get all interfaces
        Class[] interfaces = myClass.getInterfaces();

        // get array of methods
        for (Class ArrayInterfaceMethods : interfaces) {

            // get method from array above
            for (Method interfaceMethod : ArrayInterfaceMethods.getDeclaredMethods())
            System.out.println(interfaceMethod.getName());
        }

        /**
         * Create class instance with parameters
         * Don't forget use full qualified class name in argument
         */
        System.out.println(SEPARATOR);
        System.out.println("INVOKE CLASS");
        Class invokedClass = Class.forName(args[0]);
        Object testClass1 = invokedClass.newInstance();
        System.out.println(testClass1.getClass().getName());

        Class newClass = TestClass.class;
        Constructor[] constructors1 = newClass.getConstructors();
        for (Constructor constructor1 : constructors1) {
            Class[] parameterTypes = constructor1.getParameterTypes();
            for (Class param : parameterTypes) {
                System.out.println("PARAMETER = " + param.getName());
            }
        }
    }
}
