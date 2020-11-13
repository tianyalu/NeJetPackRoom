package com.sty.ne.jetpack.room;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sty.ne.jetpack.room.bean.Student;
import com.sty.ne.jetpack.room.db.StudentDao;
import com.sty.ne.jetpack.room.db.StudentDatabase;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView tvInfo;
    private Button btnInsert;
    private Button btnUpdate;
    private Button btnClear;
    private Button btnDelete;

    private StudentDatabase studentDatabase;
    private StudentDao studentDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        setListeners();
    }

    private void initView() {
        tvInfo = findViewById(R.id.tv_info);
        btnInsert = findViewById(R.id.btn_insert);
        btnUpdate = findViewById(R.id.btn_update);
        btnClear = findViewById(R.id.btn_clear);
        btnDelete = findViewById(R.id.btn_delete);

        studentDatabase = StudentDatabase.getInstance(this);
        studentDao = studentDatabase.getStudentDao();

        //观察数据的变化
        studentDao.getAllStudentLiveData().observe(this, new Observer<List<Student>>() {
            @Override
            public void onChanged(List<Student> students) {
                StringBuilder sb = new StringBuilder();
                for (Student student : students) {
                    sb.append(student.getId())
                            .append(":")
                            .append(student.getName())
                            .append("==")
                            .append(student.getAge())
                            .append("\n");
                }
                tvInfo.setText(sb.toString());
            }
        });
    }

    private void setListeners() {
        //插入
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student student1 = new Student("张三", 20);
                Student student2 = new Student("李四", 30);
                studentDao.insertStudents(student1, student2);

                //updateView();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student student = new Student("AAA", 1233);
                student.setId(2); //更新Id 为2的这条数据
                studentDao.updateStudents(student);

                //updateView();
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studentDao.deleteAllStudents();

                //updateView();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student student = new Student();
                student.setId(1); //删除ID为1的记录
                studentDao.deleteStudents(student);

                //updateView();
            }
        });
    }

    /**
     * 刷新UI
     * 把数据库中的内容显示到TextView
     */
//    private void updateView() {
//        List<Student> students = studentDao.getAllStudents();
//        StringBuilder sb = new StringBuilder();
//        for (Student student : students) {
//            sb.append(student.getId())
//                    .append(":")
//                    .append(student.getName())
//                    .append("==")
//                    .append(String.valueOf(student.getAge()))
//                    .append("\n");
//        }
//        tvInfo.setText(sb.toString());
//    }
}