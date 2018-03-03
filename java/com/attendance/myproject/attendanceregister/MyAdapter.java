package com.attendance.myproject.attendanceregister;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by HP on 2/15/2018.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    List<StudentInformation> data= Collections.emptyList();

    public MyAdapter(Context context,List<StudentInformation> data){
        inflater=LayoutInflater.from(context);
        this.data=data;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= inflater.inflate(R.layout.cust_student_list,parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        StudentInformation std=data.get(position);
        holder.tv1.setText(std.getName());
        holder.tv2.setText(std.getRollno());
        holder.cb.isChecked();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv1,tv2;
        CheckBox cb;
        MyViewHolder(View view){
            super(view);
            tv1=(TextView)view.findViewById(R.id.textView10);
            tv2=(TextView)view.findViewById(R.id.textView11);
            cb=(CheckBox)view.findViewById(R.id.checkBoxAtt);
        }
    }
}
