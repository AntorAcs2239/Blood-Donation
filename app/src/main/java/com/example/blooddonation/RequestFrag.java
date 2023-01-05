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
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.example.blooddonation.databinding.FragmentProfileBinding;
import com.example.blooddonation.databinding.FragmentRequestBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RequestFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RequestFrag extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RequestFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RequestFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static RequestFrag newInstance(String param1, String param2) {
        RequestFrag fragment = new RequestFrag();
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
    FragmentRequestBinding binding;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    RequestAdapter requestAdapter;
    ArrayList<RequestModel>list;
    Animation animation,animation2,animation3;
    FloatingActionButton floatingActionButton;
    static int permission=100;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRequestBinding.inflate(inflater, container, false);

        animation = AnimationUtils.loadAnimation(getContext(), R.anim.bottomanim);
        animation2=AnimationUtils.loadAnimation(getContext(),R.anim.bottomanim);

        binding.requestsearch.setAnimation(animation);

        binding.addrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),CreateRequest.class));
            }
        });

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions((Activity) getContext(),new String[]{Manifest.permission.CALL_PHONE},permission);
        }
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        list=new ArrayList<>();
        requestAdapter=new RequestAdapter(list,getContext());
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        binding.requestlist.setAdapter(requestAdapter);
        binding.requestlist.setLayoutManager(linearLayoutManager);
        Query query=firebaseFirestore.collection("requests")
                .orderBy("createdAt", Query.Direction.DESCENDING);
        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
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

        binding.ownreq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),OwnRequestActivity.class));
            }
        });

       binding.requestsearch.addTextChangedListener(new TextWatcher() {
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
       requestAdapter.setOnclickliten(new RequestAdapter.Onclickliten() {
           @Override
           public void onclick(RequestModel requestModel) {
               Intent intent=new Intent(Intent.ACTION_DIAL);
               intent.setData(Uri.parse("tel:"+requestModel.getContact()));
               startActivity(intent);
           }
           @Override
           public void onclick2(RequestModel requestModel) {
               Intent intent=new Intent(getContext(),CreateRequest.class);
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
               return true;
           }
       });
        return binding.getRoot();
    }
    private void filterlist(String toString) {
        ArrayList<RequestModel> tem = new ArrayList<>();
        for (RequestModel obj : list) {
            if (obj.getBloodGroup()!=null&&obj.getDistrict()!=null&&obj.getDistrict()!=null&&obj.getLocation()!=null) {
                if (obj.getLocation().toLowerCase().contains(toString.toLowerCase()) || obj.getBloodGroup().toLowerCase().contains(toString.toLowerCase())||
                        obj.getDistrict().toLowerCase().contains(toString.toLowerCase())) {
                    tem.add(obj);
                }
            }
        }
        requestAdapter.filter(tem);
        requestAdapter.notifyDataSetChanged();
    }
}