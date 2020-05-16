package com.eddie.stream;


import java.util.ArrayList;
import java.util.List;

/**
 * (args...) -> {
 * doSomeThing();
 * }
 */

public class LambdaTest {

    public static void main(String[] args) {
        singleDog(System.out::println);
    }

    public static void singleDog(Action listener) {
        List<Student> list = new ArrayList<>();
        list.add(new Student("Eddie", "G"));
        list.add(new Student("LIU_HG", "M"));
        for (Student student : list) {
            listener.execute(student);
        }

    }
}

@FunctionalInterface
interface Action {
    void execute(Student list);
}

class Student {
    private String name;
    private String sex;

    public Student(String name, String sex) {
        this.name = name;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", SEX='" + sex + '\'' +
                '}';
    }
}
