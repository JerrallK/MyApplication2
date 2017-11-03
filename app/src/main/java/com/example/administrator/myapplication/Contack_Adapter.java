package com.example.administrator.myapplication;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/9/8.
 */

public class Contack_Adapter extends RecyclerView.Adapter<Contack_Adapter.ViewHolder>{
    public String[] datas=null;
    public Contack_Adapter(String[] datas){
        this.datas=datas;
    }

    @Override
    public Contack_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_contack,parent,false);
        Contack_Adapter.ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView_name.setText(datas[position]);
        holder.textView_num.setText(datas[position]);

    }

    @Override
    public int getItemCount() {
        return datas.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView_name,textView_num;
        public ViewHolder(View itemView) {
            super(itemView);
            textView_name=itemView.findViewById(R.id.contack_name);
            textView_num=itemView.findViewById(R.id.contack_num);
        }
    }
}
