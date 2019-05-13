package com.company.test;

import com.company.annotations.TestClass;
import com.company.annotations.BusinessLogic;

import java.lang.reflect.InvocationTargetException;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) throws IllegalAccessException,
            InstantiationException {

        BusinessLogic businessLogic = new BusinessLogic();

        // get class of object
        businessLogic.getClassObject();

        // get class modifier
        businessLogic.getClassModifiers();

        // get class fields
        Stream.of(businessLogic.getClassFields(TestClass.class
                .newInstance())).forEach(System.out::println);

        // get class methods
        Stream.of(businessLogic.getClassMethods(TestClass.class
                .newInstance())).forEach(System.out::println);

        // get class constructors
        Stream.of(businessLogic.getClassConstructors(TestClass.class
                .newInstance())).forEach(System.out::println);

        // get class superclass
        Stream.of(businessLogic.getClassSuperclass(TestClass.class
                .newInstance())).forEach(System.out::println);

        // get interface methods
        Stream.of(businessLogic.getInterfaceMethods())
                .forEach(System.out::println);

        // get class instance
        try {
            businessLogic.createClassInstanceByName(args[0]);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // invoke method by name
        try {
            businessLogic.invokeMethodByName(TestClass.class.newInstance(), args[2]);
        } catch (NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }

        // separate fields by modifier
        businessLogic.separateFieldsByModifier(businessLogic
                .getClassFields(TestClass.class.newInstance()));

        // separate methods by modifier
        businessLogic.separateMethodsByModifier(businessLogic
                .getClassMethods(TestClass.class.newInstance()));

        // change field of object by name
        try {
            businessLogic.changeObjectFieldByName(TestClass.class.newInstance(),
                    args[1]);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        // invoke private method with params
        businessLogic.invokePrivateMethodWithParams();

        //invoke private constructor with params
        businessLogic.invokePrivateConstructorWithParams();

        // change "hello" to "bye"
        try {
            businessLogic.hijackPrintln();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
