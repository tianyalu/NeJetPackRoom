package com.sty.ne.jetpack.room.bean;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @Author: tian
 * @UpdateDate: 2020/11/13 7:42 PM
 */
@Entity
public class Student {
    //主键，自动增长
    @PrimaryKey(autoGenerate = true)
    private int id;

    //如果写了ColumnInfo注解，则使用注解中的名称作为数据库列名
    @ColumnInfo(name = "name")
    private String name; //默认使用name

    @ColumnInfo(name = "age")
    private int age;

    public Student() {
    }

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
