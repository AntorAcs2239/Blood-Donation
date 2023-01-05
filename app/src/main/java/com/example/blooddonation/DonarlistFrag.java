package com.example.blooddonation;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.example.blooddonation.databinding.FragmentDonarlistBinding;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Locale;
public class DonarlistFrag extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DonarlistFrag() {

    }
    public static DonarlistFrag newInstance(String param1, String param2) {
        DonarlistFrag fragment = new DonarlistFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    FragmentDonarlistBinding binding;
    FirebaseFirestore firebaseFirestore;
    ArrayList<UsersModel>list;
    UsersAdapter adapter;
    Animation animation,animation2,animation3;
    static int permission=100;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDonarlistBinding.inflate(inflater, container, false);

        animation = AnimationUtils.loadAnimation(getContext(), R.anim.searchanim);
        animation2=AnimationUtils.loadAnimation(getContext(),R.anim.animfordonarlist);
        binding.donartxt.setAnimation(animation2);
        binding.lottieAnimationView.setAnimation(animation);
        firebaseFirestore=FirebaseFirestore.getInstance();
        list=new ArrayList<>();
        adapter=new UsersAdapter(getContext(),list);
        binding.donarrec.setAdapter(adapter);
        binding.donarrec.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false));
        firebaseFirestore.collection("users")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        list.clear();
                        for (DocumentSnapshot snapshot:value.getDocuments())
                        {
                            UsersModel usersModel=snapshot.toObject(UsersModel.class);
                            list.add(usersModel);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions((Activity) getContext(),new String[]{Manifest.permission.CALL_PHONE},permission);
        }
        binding.lottieAnimationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animation3=AnimationUtils.loadAnimation(getContext(),R.anim.searchanim);
                binding.lottieAnimationView.playAnimation();
                binding.searchbar.setAnimation(animation3);
                binding.searchbar.setVisibility(View.VISIBLE);
            }
        });
        adapter.setOnclickliten(new UsersAdapter.Onclickliten() {
            @Override
            public void onclick(UsersModel usersModel) {
                Intent intent=new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+usersModel.getPhone()));
                startActivity(intent);
            }

            @Override
            public Boolean longpress(UsersModel usersModel) {
                Toast.makeText(getContext(),"Long pressed",Toast.LENGTH_LONG).show();
                return true;
            }
        });
        binding.searchbar.addTextChangedListener(new TextWatcher() {
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

        return binding.getRoot();
    }
    private void filterlist(String toString) {
        ArrayList<UsersModel> tem = new ArrayList<>();
        for (UsersModel obj : list) {
            if (obj.getBloodGroup()!=null&&obj.getDistrict()!=null&&obj.getArea()!=null) {
                if (obj.getArea().toLowerCase().contains(toString.toLowerCase()) || obj.getBloodGroup().toLowerCase().contains(toString.toLowerCase())||
                        obj.getDistrict().toLowerCase().contains(toString.toLowerCase())) {
                    tem.add(obj);
                }
            }
        }
        adapter.filter(tem);
        adapter.notifyDataSetChanged();
    }
}