package com.example.blooddonation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FAQadapter extends RecyclerView.Adapter<FAQadapter.viewholder>{
    Context context;
    ArrayList<Faqmodel>list;

    public FAQadapter(Context context, ArrayList<Faqmodel> list) {
        this.context = context;
        this.list = list;
    }
    public Onclickliten onclickliten;
    public interface Onclickliten
    {
        void onclick(Faqmodel faqmodel);
    }
    public void setOnclickliten(Onclickliten onclickliten) {
        this.onclickliten = onclickliten;
    }
    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.faqrow,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        Faqmodel faqmodel=list.get(position);
        holder.title.setText(faqmodel.getTitle());
        holder.description.setText(faqmodel.getDescription());
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (faqmodel.getIsvisible()==false)
                {
                    holder.description.setVisibility(View.VISIBLE);
                    faqmodel.setIsvisible(true);
                }
                else
                {
                    holder.description.setVisibility(View.GONE);
                    faqmodel.setIsvisible(false);
                }
            }
        });
        setanimation(holder.itemView,position);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView title,description;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.faqtitle);
            description=itemView.findViewById(R.id.faqdesc);
        }
    }
    public void setanimation(View view,int position)
    {
        Animation animation= AnimationUtils.loadAnimation(context, R.anim.searchanim);
        view.startAnimation(animation);
    }
}
