package com.di.fast;

import androidx.annotation.NonNull;

public class Person implements Cloneable{

    public String name;
    public int age;
    public Address address;

    @NonNull
    @Override
    public Person clone() throws CloneNotSupportedException {
        return (Person)super.clone();
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", address=" + address +
                '}';
    }
}
