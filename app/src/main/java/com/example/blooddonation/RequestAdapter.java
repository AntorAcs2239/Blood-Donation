package com.example.blooddonation;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.viewholder>{
    ArrayList<RequestModel>list;
    Context context;
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    public Onclickliten onclickliten;
    public interface Onclickliten
    {
        void onclick(RequestModel requestModel);
        void onclick2(RequestModel requestModel);
        Boolean longpress(RequestModel requestModel);
    }
    public void setOnclickliten(Onclickliten onclickliten) {
        this.onclickliten = onclickliten;
    }
    public RequestAdapter(ArrayList<RequestModel> list, Context context) {
        this.list = list;
        this.context = context;
    }
    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.requestrow,parent,false);
        return new viewholder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        RequestModel requestModel=list.get(position);
        holder.bloodg.setText("Blood Group: "+requestModel.getBloodGroup());
        holder.location.setText("Location: "+requestModel.getLocation()+","+requestModel.getDistrict());
        holder.time.setText("Time : "+requestModel.getTime());
        holder.date.setText("Date: "+requestModel.getDate());
        holder.problem.setText("Problem: "+requestModel.getProblem());
        holder.bloodneed.setText("Need : "+String.valueOf(requestModel.getNumBag())+" Bag");
        holder.managed.setText("Managed: "+String.valueOf(requestModel.getNumManaged())+" Bag");
        String t=(String) DateUtils.getRelativeTimeSpanString(requestModel.getCreatedAt().getSeconds()*1000);
        holder.createdat.setText("Requested At: "+t);
        if (requestModel.getUid().equals(firebaseAuth.getCurrentUser().getUid()))
        {
            holder.edit.setVisibility(View.VISIBLE);
            holder.contact.setVisibility(View.INVISIBLE);
        }
        else {
            holder.edit.setVisibility(View.INVISIBLE);
            holder.contact.setVisibility(View.VISIBLE);
        }
        holder.contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,requestModel.getContact(),Toast.LENGTH_LONG).show();
            }
        });
        holder.contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclickliten.onclick(requestModel);
            }
        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclickliten.onclick2(requestModel);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                onclickliten.longpress(requestModel);
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
        TextView bloodg,location,time,date,problem,bloodneed,managed,createdat,contact,edit;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            bloodg=itemView.findViewById(R.id.bloodg);
            location=itemView.findViewById(R.id.location);
            time=itemView.findViewById(R.id.time);
            date =itemView.findViewById(R.id.date);
            problem=itemView.findViewById(R.id.problem);
            bloodneed=itemView.findViewById(R.id.need);
            managed=itemView.findViewById(R.id.managed);
            createdat=itemView.findViewById(R.id.timeago);
            contact=itemView.findViewById(R.id.contact);
            edit=itemView.findViewById(R.id.edit);
        }
    }
    public void filter(ArrayList<RequestModel> backup) {
        list = backup;
        notifyDataSetChanged();
    }
    public void setanimation(View view,int position)
    {
        Animation animation= AnimationUtils.loadAnimation(context, R.anim.searchanim);
        view.startAnimation(animation);
    }
}
