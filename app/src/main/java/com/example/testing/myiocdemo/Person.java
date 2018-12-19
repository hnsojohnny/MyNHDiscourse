package com.example.testing.myiocdemo;

import android.support.annotation.NonNull;

public class Person implements Comparable<Person>{
    public String name;

    public Person(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(@NonNull Person o) {
        return this.name.compareTo(o.name);
    }
}
