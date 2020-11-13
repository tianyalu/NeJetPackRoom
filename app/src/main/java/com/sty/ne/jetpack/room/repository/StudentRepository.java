package com.sty.ne.jetpack.room.repository;

import android.content.Context;
import android.os.AsyncTask;

import com.sty.ne.jetpack.room.bean.Student;
import com.sty.ne.jetpack.room.db.StudentDao;
import com.sty.ne.jetpack.room.db.StudentDatabase;

import java.util.List;

import androidx.lifecycle.LiveData;

/**
 * 仓库：用来做数据的获取等操作
 * @Author: tian
 * @UpdateDate: 2020/11/13 9:05 PM
 */
public class StudentRepository {
    //存放数据的容器 存放data --> DB
    private LiveData<List<Student>> allStudentLive;

    //操作数据库，Dao
    private StudentDao studentDao;

    public StudentRepository(Context context) {
        StudentDatabase studentDatabase = StudentDatabase.getInstance(context);
        studentDao = studentDatabase.getStudentDao();
        this.allStudentLive = studentDao.getAllStudentLiveData();
    }

    //数据暴露给外界 --> VM
    public LiveData<List<Student>> getAllStudentLive() {
        return allStudentLive;
    }

    /**
     * 插入
     * @param students
     */
    public void insert(Student... students) {
        new InsertAsyncTask(studentDao).execute(students);
    }

    //异步线程进行数据库操作 --> insert
    static class InsertAsyncTask extends AsyncTask<Student, Void, Void> {
        private StudentDao studentDao;

        public InsertAsyncTask(StudentDao studentDao) {
            this.studentDao = studentDao;
        }

        @Override
        protected Void doInBackground(Student... students) {
            studentDao.insertStudents(students);
            return null;
        }
    }

    /**
     * 修改
     * @param students
     */
    public void update(Student... students) {
        new UpdateAsyncTask(studentDao).execute(students);
    }

    //异步线程进行数据库操作 --> update
    static class UpdateAsyncTask extends AsyncTask<Student, Void, Void> {
        private StudentDao studentDao;

        public UpdateAsyncTask(StudentDao studentDao) {
            this.studentDao = studentDao;
        }

        @Override
        protected Void doInBackground(Student... students) {
            studentDao.updateStudents(students);
            return null;
        }
    }

    /**
     * 清空
     */
    public void clear() {
        new ClearAsyncTask(studentDao).execute();
    }

    //异步线程进行数据库操作 --> clear
    static class ClearAsyncTask extends AsyncTask<Void, Void, Void> {
        private StudentDao studentDao;

        public ClearAsyncTask(StudentDao studentDao) {
            this.studentDao = studentDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            studentDao.deleteAllStudents();
            return null;
        }
    }

    /**
     * 删除（按条件）
     * @param students
     */
    public void delete(Student... students) {
        new DeleteAsyncTask(studentDao).execute(students);
    }

    //异步线程进行数据库操作 --> delete
    static class DeleteAsyncTask extends AsyncTask<Student, Void, Void> {
        private StudentDao studentDao;

        public DeleteAsyncTask(StudentDao studentDao) {
            this.studentDao = studentDao;
        }

        @Override
        protected Void doInBackground(Student... students) {
            studentDao.deleteStudents(students);
            return null;
        }
    }
}
