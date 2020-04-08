package com.lin.learn.java.design_mode.create;

/**
 * 建造者模式
 */
public class BuilderMode {

    public static class Person {
        private int age;
        private String name;

        private Person(Builder builder) {
            this.age = builder.age;
            this.name = builder.name;
        }

        public int getAge() {
            return age;
        }


        public String getName() {
            return name;
        }

        public static class Builder {
            private int age;
            private String name;

            public Builder setAge(int age) {
                this.age = age;
                return this;
            }

            public Builder setName(String name) {
                this.name = name;
                return this;
            }

            public Person build(){
                return new Person(this);
            }
        }
    }

    public static void  test() {
        Person person = new Person.Builder().setAge(10).setName("john").build();
        System.out.println(person.getName());
    }
}
