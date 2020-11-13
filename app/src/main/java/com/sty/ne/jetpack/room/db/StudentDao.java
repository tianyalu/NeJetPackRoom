package com.sty.ne.jetpack.room.db;

import com.sty.ne.jetpack.room.bean.Student;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

/**
 * 数据库访问对象
 * @Author: tian
 * @UpdateDate: 2020/11/13 7:47 PM
 */
@Dao //Database Access Object
public interface StudentDao {
    //增删改查
    @Insert  //内部会自动生成代码
    void insertStudents(Student... students);

    @Update
    void updateStudents(Student... students);

    @Delete
    void deleteStudents(Student... students);

    //删除全部 没有条件
    @Query("DELETE FROM student") //内部默认转成大写
    void deleteAllStudents();

    //查询全部
    @Query("SELECT * FROM student ORDER BY ID DESC")
    //List<Student> getAllStudents();
    LiveData<List<Student>> getAllStudentLiveData();
}
