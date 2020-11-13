package com.sty.ne.jetpack.room;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sty.ne.jetpack.room.adapter.MyAdapter;
import com.sty.ne.jetpack.room.bean.Student;
import com.sty.ne.jetpack.room.db.StudentDao;
import com.sty.ne.jetpack.room.db.StudentDatabase;
import com.sty.ne.jetpack.room.viewmodel.StudentViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView tvInfo;
    private Button btnInsert;
    private Button btnUpdate;
    private Button btnClear;
    private Button btnDelete;
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private StudentViewModel viewModel;

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
        recyclerView = findViewById(R.id.recycler_view);

        myAdapter = new MyAdapter();
        viewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(StudentViewModel.class);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter);

        //观察数据变化
        viewModel.getAllStudentLive().observe(this, new Observer<List<Student>>() {
            @Override
            public void onChanged(List<Student> students) {
                myAdapter.setAllStudents(students);
                myAdapter.notifyDataSetChanged();
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

                viewModel.insert(student1, student2);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student student = new Student("AAA", 1233);
                student.setId(2); //更新Id 为2的这条数据

                viewModel.update(student);
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.clear();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student student = new Student();
                student.setId(1); //删除ID为1的记录

                viewModel.delete(student);
            }
        });
    }
}