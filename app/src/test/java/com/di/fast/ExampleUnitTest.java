package com.di.fast;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testClone(){
        Person person = new Person();
        person.age = 10;
        person.name = "1";
        Address address = new Address();
        address.city = "北京";
        address.town = "海淀";
        person.address = address;

        System.out.println("init : " + person.toString());

        try {
            Person clonePerson = person.clone();
            System.out.println("after clone : " + person.toString() + ", " + clonePerson.toString());

            clonePerson.name = "2";
            System.out.println("after put clone object: " + person.toString() + ", " + clonePerson.toString());

            clonePerson.address.city = "石家庄";
            System.out.println("after put clone object address: " + person.toString() + ", " + clonePerson.toString());


        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
}