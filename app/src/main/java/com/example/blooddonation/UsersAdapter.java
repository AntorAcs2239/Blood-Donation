package com.example.blooddonation;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.viewholder>{
    Context context;
    ArrayList<UsersModel> list;
    public Onclickliten onclickliten;
    public interface Onclickliten
    {
        void onclick(UsersModel usersModel);
        Boolean longpress(UsersModel usersModel);
    }
    public void setOnclickliten(Onclickliten onclickliten) {
        this.onclickliten = onclickliten;
    }

    public UsersAdapter(Context context, ArrayList<UsersModel> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.donarsrows,parent,false);
        return new viewholder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        UsersModel usersModel=list.get(position);
        holder.name.setText("Name: "+usersModel.getName());
        holder.phone.setText("Contact: "+usersModel.getPhone());
        holder.location.setText("Location: "+usersModel.getArea()+", "+usersModel.getDistrict());
        holder.bloodgorup.setText("Blood Group:"+usersModel.getBloodGroup());
        if (usersModel.getBloodGroup().contains("O+"))holder.lottieAnimationView.setAnimation(R.raw.op);
        if (usersModel.getBloodGroup().contains("O-"))holder.lottieAnimationView.setAnimation(R.raw.om);
        if (usersModel.getBloodGroup().contains("A+"))holder.lottieAnimationView.setAnimation(R.raw.ap);
        if (usersModel.getBloodGroup().contains("A-"))holder.lottieAnimationView.setAnimation(R.raw.am);
        if (usersModel.getBloodGroup().contains("B+"))holder.lottieAnimationView.setAnimation(R.raw.bp);
        if (usersModel.getBloodGroup().contains("B-"))holder.lottieAnimationView.setAnimation(R.raw.bm);
        if (usersModel.getBloodGroup().contains("AB+"))holder.lottieAnimationView.setAnimation(R.raw.abp);
        if (usersModel.getBloodGroup().contains("AB-"))holder.lottieAnimationView.setAnimation(R.raw.abm);
        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclickliten.onclick(usersModel);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                onclickliten.longpress(usersModel);
                return true;
            }
        });
        setanimation(holder.itemView,position);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView name,phone,location,bloodgorup,call;
        LottieAnimationView lottieAnimationView;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.donarname);
            phone=itemView.findViewById(R.id.donarphone);
            location=itemView.findViewById(R.id.locaton);
            bloodgorup=itemView.findViewById(R.id.bloodgroup);
            lottieAnimationView=itemView.findViewById(R.id.bloodanim);
            call=itemView.findViewById(R.id.call);
        }
    }
    public void filter(ArrayList<UsersModel> backup) {
        list = backup;
        notifyDataSetChanged();
    }
    public void setanimation(View view,int position)
    {
        Animation animation= AnimationUtils.loadAnimation(context, R.anim.searchanim);
        view.startAnimation(animation);
    }
}
