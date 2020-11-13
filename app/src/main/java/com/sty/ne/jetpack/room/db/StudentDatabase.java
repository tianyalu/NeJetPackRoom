package com.sty.ne.jetpack.room.db;

import android.content.Context;

import com.sty.ne.jetpack.room.bean.Student;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * @Author: tian
 * @UpdateDate: 2020/11/13 8:00 PM
 */
@Database(entities = {Student.class}, version = 1, exportSchema = false)
public abstract class StudentDatabase extends RoomDatabase {

    //create database 很耗费性能，整个项目一个实例（单例模式）
    private static volatile StudentDatabase studentDatabase;

    public static StudentDatabase getInstance(Context context) {
        if(null == studentDatabase) {
            synchronized (StudentDatabase.class) {
                if(null == studentDatabase) {
                    //默认只能在异步线程中运行，如果在主线程执行就会报错
                    studentDatabase = Room.databaseBuilder(context.getApplicationContext(),
                                StudentDatabase.class, "student_database")
                            .allowMainThreadQueries() //强制允许在main线程执行
                            .build();
                }
            }
        }
        return studentDatabase;
    }

    //注意：如果有多个Entity，就需要写多个Dao
    public abstract StudentDao getStudentDao();
}
