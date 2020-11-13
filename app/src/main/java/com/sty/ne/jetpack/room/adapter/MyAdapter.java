package com.sty.ne.jetpack.room.adapter;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sty.ne.jetpack.room.R;
import com.sty.ne.jetpack.room.bean.Student;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @Author: tian
 * @UpdateDate: 2020/11/13 9:29 PM
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    //存放列表的数据
    private List<Student> allStudents = new ArrayList<>();

    //用户调用这个函数来设置数据源
    public void setAllStudents(List<Student> allStudents) {
        this.allStudents = allStudents;
    }

    //创建ViewHolder
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.layout_item, parent, false);
        return new MyViewHolder(itemView);
    }

    //绑定操作
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        Student student = allStudents.get(position);
        holder.tvId.setText(String.valueOf(student.getId()));
        holder.tvName.setText(student.getName());
        holder.tvAge.setText(String.valueOf(student.getAge()));

        //用户点击item的业务逻辑
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://m.youdao.com/dic?le=eng&q=" + holder.tvName.getText());
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(uri);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.allStudents == null ? 0 : this.allStudents.size();
    }

    //为了每一个item性能提升
    final static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvId;
        TextView tvName;
        TextView tvAge;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);

            tvId = itemView.findViewById(R.id.tv_id);
            tvName = itemView.findViewById(R.id.tv_name);
            tvAge = itemView.findViewById(R.id.tv_age);
        }
    }
}
