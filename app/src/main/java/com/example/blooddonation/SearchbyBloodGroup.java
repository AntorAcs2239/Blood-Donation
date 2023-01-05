package com.example.blooddonation;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import com.example.blooddonation.databinding.ActivitySearchbyBloodGroupBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SearchbyBloodGroup extends AppCompatActivity {
    ActivitySearchbyBloodGroupBinding binding;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    ArrayList<UsersModel>list;
    UsersAdapter usersAdapter;
    String bg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchby_blood_group);
        binding=ActivitySearchbyBloodGroupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        bg=getIntent().getStringExtra("bg");
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        list=new ArrayList<>();
        usersAdapter=new UsersAdapter(this,list);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL,false);
        binding.searchrec.setAdapter(usersAdapter);
        binding.searchrec.setLayoutManager(linearLayoutManager);
        binding.textView9.setText("Donar List(Blood Group: "+bg+")");
        firebaseFirestore.collection("users")
                .whereEqualTo("bloodGroup",bg)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        list.clear();
                        for (DocumentSnapshot snapshot:value.getDocuments())
                        {
                            UsersModel usersModel=snapshot.toObject(UsersModel.class);
                            list.add(usersModel);
                        }
                        usersAdapter.notifyDataSetChanged();
                    }
                });

        usersAdapter.setOnclickliten(new UsersAdapter.Onclickliten() {
            @Override
            public void onclick(UsersModel usersModel) {
                Intent intent=new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+usersModel.getPhone()));
                startActivity(intent);
            }
            @Override
            public Boolean longpress(UsersModel usersModel) {
                return null;
            }
        });
        binding.search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filterlist(editable.toString());
            }
        });
    }
    private void filterlist(String toString) {
        ArrayList<UsersModel> tem = new ArrayList<>();
        for (UsersModel obj : list) {
            if (obj.getDistrict()!=null&&obj.getArea()!=null) {
                if (obj.getArea().toLowerCase().contains(toString.toLowerCase()) ||
                        obj.getDistrict().toLowerCase().contains(toString.toLowerCase())) {
                    tem.add(obj);
                }
            }
        }
        usersAdapter.filter(tem);
        usersAdapter.notifyDataSetChanged();
    }
}