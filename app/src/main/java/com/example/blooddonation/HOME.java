package com.example.blooddonation;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.blooddonation.databinding.FragmentHOMEBinding;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HOME#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HOME extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HOME() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HOME.
     */
    // TODO: Rename and change types and number of parameters
    public static HOME newInstance(String param1, String param2) {
        HOME fragment = new HOME();
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
    FragmentHOMEBinding binding;
    ViewPager2 viewPager;
    ArrayList<slidermodel> list;
    static final float END_SCALE = 0.7f;
    Slideradapter slideradapter;
    Animation topanimation,bottomanimation;
    FirebaseFirestore firebaseFirestore;
    ArrayList<Faqmodel>faqlist;
    FAQadapter faQadapter;
    private Handler handler=new Handler();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHOMEBinding.inflate(inflater, container, false);
        topanimation = AnimationUtils.loadAnimation(getContext(), R.anim.topanim);
        bottomanimation = AnimationUtils.loadAnimation(getContext(), R.anim.bottomanim);
        binding.introtext.setAnimation(topanimation);
        binding.introtext2.setAnimation(bottomanimation);
        firebaseFirestore=FirebaseFirestore.getInstance();
        list=new ArrayList<>();
        faqlist=new ArrayList<>();

        firebaseFirestore.collection("faq")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        faqlist.clear();
                        for (DocumentSnapshot snapshot:value.getDocuments())
                        {
                            Faqmodel faqmodel=snapshot.toObject(Faqmodel.class);
                            faqlist.add(faqmodel);
                        }
                        faQadapter.notifyDataSetChanged();
                    }
                });

        faQadapter=new FAQadapter(getContext(),faqlist);
        binding.faqrec.setAdapter(faQadapter);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        binding.faqrec.setLayoutManager(linearLayoutManager);

        slideradapter=new Slideradapter(list,binding.viewpagee,getContext());
        binding.viewpagee.setAdapter(slideradapter);
        binding.viewpagee.setClipToPadding(false);
        binding.viewpagee.setClipChildren(false);
        binding.viewpagee.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            // This method is triggered when there is any scrolling activity for the current page
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            // triggered when you select a new page
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(slider);
                handler.postDelayed(slider,3000);
            }

            // triggered when there is
            // scroll state will be changed
            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

        firebaseFirestore.collection("Sliderimage")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        list.clear();
                        for (DocumentSnapshot snapshot:value.getDocuments())
                        {
                            slidermodel slide=snapshot.toObject(slidermodel.class);
                            list.add(slide);
                        }
                        slideradapter.notifyDataSetChanged();
                    }
                });

        binding.bgop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),SearchbyBloodGroup.class);
                intent.putExtra("bg","O+");
                startActivity(intent);
            }
        });
        binding.bgon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),SearchbyBloodGroup.class);
                intent.putExtra("bg","O-");
                startActivity(intent);
            }
        });
        binding.bgap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),SearchbyBloodGroup.class);
                intent.putExtra("bg","A+");
                startActivity(intent);
            }
        });
        binding.bgan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),SearchbyBloodGroup.class);
                intent.putExtra("bg","A-");
                startActivity(intent);
            }
        });
        binding.bgabp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),SearchbyBloodGroup.class);
                intent.putExtra("bg","AB+");
                startActivity(intent);
            }
        });
        binding.bgabn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),SearchbyBloodGroup.class);
                intent.putExtra("bg","AB-");
                startActivity(intent);
            }
        });
        binding.bgbp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),SearchbyBloodGroup.class);
                intent.putExtra("bg","B+");
                startActivity(intent);
            }
        });
        binding.bgbn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),SearchbyBloodGroup.class);
                intent.putExtra("bg","B-");
                startActivity(intent);
            }
        });
        return binding.getRoot();
    }
    private  Runnable slider =new Runnable() {
        @Override
        public void run() {
            binding.viewpagee.setCurrentItem(binding.viewpagee.getCurrentItem()+1);
        }
    };
}