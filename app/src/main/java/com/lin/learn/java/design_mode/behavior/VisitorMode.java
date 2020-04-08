package com.lin.learn.java.design_mode.behavior;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VisitorMode {
    public static class Student {
        public String name;
        public int score_total;
        public int score_yuwen;
        public int score_math;
        public int score_sport;
        public int score_music;

        public Student(String name) {
            this.name = name;
            this.score_yuwen = new Random().nextInt(100);
            this.score_math = new Random().nextInt(100);
            this.score_sport = new Random().nextInt(100);
            this.score_music = new Random().nextInt(100);
            this.score_total = this.score_yuwen + this.score_math +
                    this.score_sport + this.score_music;
        }

        public void accept(Visitor visitor) {
            visitor.visitor(this);
        }
    }

    public interface Visitor {
        void visitor(Student student);
    }

    /**
     * 班主任
     */
    public static class HeadmasterTeacher implements Visitor {

        @Override
        public void visitor(Student student) {
            //班主任更关心学生总成绩
            System.out.println(student.name + "总分：" + student.score_total);
        }
    }

    /**
     * 体育老师
     */
    public static class SportTeacher implements Visitor {

        @Override
        public void visitor(Student student) {
            System.out.print(student.name + "体育分：" + student.score_sport);
            if (student.score_sport > 90) {
                System.out.println("，是个好苗子");
            } else {
                System.out.println("，真是个废柴");
            }
        }
    }

    /**
     * 音乐老师
     */
    public static class MusicTeacher implements Visitor {

        @Override
        public void visitor(Student student) {
            System.out.print(student.name + "音乐分：" + student.score_music);
            if (student.score_music > 90) {
                System.out.println("，音乐天分不错");
            } else {
                System.out.println("，真是个废柴");
            }
        }
    }


    public static void test() {
        List<Student> students = new ArrayList<>();
        students.add(new Student("张三"));
        students.add(new Student("李四"));
        students.add(new Student("王五"));
        students.add(new Student("余六"));
        students.add(new Student("史七"));
        students.add(new Student("周八"));
        students.add(new Student("杨九"));

        HeadmasterTeacher headmasterTeacher = new HeadmasterTeacher();
        SportTeacher sportTeacher = new SportTeacher();
        MusicTeacher musicTeacher = new MusicTeacher();
        //每个访问者的目的明确，只关注他想关注的信息
        for (Student student : students) {
            headmasterTeacher.visitor(student);
            sportTeacher.visitor(student);
            musicTeacher.visitor(student);
        }
    }
}
