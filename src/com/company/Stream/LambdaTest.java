package com.company.Stream;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * (args...) -> {
 *     doSomeThing();
 * }
 */

public class LambdaTest {

    public static void main(String[] args){

        SingleDog(a -> System.out.println(a));
    }

    public static void SingleDog(Action listener) {
        List<Student> list= new ArrayList<>();
        list.add(new Student("Eddie","G"));
        list.add(new Student("LIU_HG","M"));

        Iterator<Student> iterator = list.iterator();
        while (iterator.hasNext()){
            listener.execute(iterator.next());
        }

    }
}

@FunctionalInterface
interface Action {
    void execute(Student list);
}

class Student{
    private String name;
    private String SEX;

    public Student(String name, String SEX) {
        this.name = name;
        this.SEX = SEX;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSEX() {
        return SEX;
    }

    public void setSEX(String SEX) {
        this.SEX = SEX;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", SEX='" + SEX + '\'' +
                '}';
    }
}
