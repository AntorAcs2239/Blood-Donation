package com.example.blooddonation;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.example.blooddonation.databinding.ActivityMainBinding;
import com.example.blooddonation.databinding.ActivityOwnRequestBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class OwnRequestActivity extends AppCompatActivity {
    ActivityOwnRequestBinding binding;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    ArrayList<RequestModel>list;
    RequestAdapter requestAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_own_request);
        binding = ActivityOwnRequestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        list=new ArrayList<>();
        requestAdapter=new RequestAdapter(list,this);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        binding.ownreq.setAdapter(requestAdapter);
        binding.ownreq.setLayoutManager(linearLayoutManager);


        firebaseFirestore.collection("requests")
                .whereEqualTo("uid",firebaseAuth.getCurrentUser().getUid())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        list.clear();
                        for (DocumentSnapshot snapshot:value.getDocuments())
                        {
                            RequestModel requestModel=snapshot.toObject(RequestModel.class);
                            list.add(requestModel);
                        }
                        requestAdapter.notifyDataSetChanged();
                    }
                });

        requestAdapter.setOnclickliten(new RequestAdapter.Onclickliten() {
            @Override
            public void onclick(RequestModel requestModel) {

            }
            @Override
            public void onclick2(RequestModel requestModel) {
                Intent intent=new Intent(OwnRequestActivity.this,CreateRequest.class);
                intent.putExtra("edit",1);
                intent.putExtra("bg",requestModel.getBloodGroup());
                intent.putExtra("location",requestModel.getLocation());
                intent.putExtra("district",requestModel.getDistrict());
                intent.putExtra("time",requestModel.getTime());
                intent.putExtra("date",requestModel.getDate());
                intent.putExtra("problem",requestModel.getProblem());
                intent.putExtra("phone",requestModel.getContact());
                intent.putExtra("need",requestModel.getNumBag());
                intent.putExtra("managed",requestModel.getNumManaged());
                intent.putExtra("reqid",requestModel.getReqid());
                startActivity(intent);
            }

            @Override
            public Boolean longpress(RequestModel requestModel) {
                return null;
            }
        });

    }
}