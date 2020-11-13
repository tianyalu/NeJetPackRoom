package com.sty.ne.jetpack.room.viewmodel;

import android.app.Application;

import com.sty.ne.jetpack.room.bean.Student;
import com.sty.ne.jetpack.room.repository.StudentRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

/**
 * @Author: tian
 * @UpdateDate: 2020/11/13 9:20 PM
 */
public class StudentViewModel extends AndroidViewModel {
    private StudentRepository studentRepository;

    public StudentViewModel(@NonNull Application application) {
        super(application);
        studentRepository = new StudentRepository(application);
    }

    //数据暴露给外界 --> Activity/Fragment
    public LiveData<List<Student>> getAllStudentLive() {
        return studentRepository.getAllStudentLive();
    }

    //插入
    public void insert(Student... students) {
        studentRepository.insert(students);
    }

    //按条件更新
    public void update(Student... students) {
        studentRepository.update(students);
    }

    //清空（全部删除）
    public void clear() {
        studentRepository.clear();
    }

    //按条件更新
    public void delete(Student... students) {
        studentRepository.delete(students);
    }
}
