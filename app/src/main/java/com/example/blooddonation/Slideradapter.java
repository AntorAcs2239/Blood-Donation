package com.example.blooddonation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Slideradapter extends RecyclerView.Adapter<Slideradapter.viewholder>{
    ArrayList<slidermodel>list;
    ViewPager2 viewPager;
    Context context;

    public Slideradapter(ArrayList<slidermodel> list, ViewPager2 viewPager,Context context) {
        this.list = list;
        this.viewPager = viewPager;
        this.context=context;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.sliderrow,parent,false);
        return new viewholder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        slidermodel slide=list.get(position);
        Glide.with(context).load(slide.getUrl()).into(holder.imageView);
        if (position==list.size()-2)
        {
            viewPager.post(runnable);
        }
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public  class viewholder extends ViewHolder {
        ImageView imageView;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.img);
        }
    }
    private Runnable runnable=new Runnable() {
        @Override
        public void run() {
            list.addAll(list);
            notifyDataSetChanged();
        }
    };
}
