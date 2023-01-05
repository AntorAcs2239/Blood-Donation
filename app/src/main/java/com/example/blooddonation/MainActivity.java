package com.example.blooddonation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.blooddonation.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        FragmentTransaction transactio = getSupportFragmentManager().beginTransaction();
        transactio.replace(R.id.frame, new HOME());
        binding.homebtn.playAnimation();
        transactio.commit();
        binding.rel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.homebtn.playAnimation();
                binding.profile.pauseAnimation();
               // binding.helpline.pauseAnimation();
                binding.bloodbtn.pauseAnimation();
                binding.reques.pauseAnimation();
                FragmentTransaction transactio = getSupportFragmentManager().beginTransaction();
                transactio.replace(R.id.frame,new HOME());
                transactio.commit();
            }
        });
//        binding.rel2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                binding.helpline.playAnimation();
//                binding.profile.pauseAnimation();
//                binding.homebtn.pauseAnimation();
//                binding.bloodbtn.pauseAnimation();
//                binding.reques.pauseAnimation();
//                FragmentTransaction transactio = getSupportFragmentManager().beginTransaction();
//                transactio.replace(R.id.frame,new HelplineFrag());
//                transactio.commit();
//            }
//        });
        binding.rel3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.bloodbtn.playAnimation();
                binding.profile.pauseAnimation();
                binding.homebtn.pauseAnimation();
               // binding.helpline.pauseAnimation();
                binding.reques.pauseAnimation();
                FragmentTransaction transactio = getSupportFragmentManager().beginTransaction();
                transactio.replace(R.id.frame,new DonarlistFrag());
                transactio.commit();
            }
        });
        binding.rel4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.reques.playAnimation();
                binding.profile.pauseAnimation();
                binding.homebtn.pauseAnimation();
                //binding.helpline.pauseAnimation();
                binding.bloodbtn.pauseAnimation();
                FragmentTransaction transactio = getSupportFragmentManager().beginTransaction();
                transactio.replace(R.id.frame,new RequestFrag());
                transactio.commit();
            }
        });
        binding.rel5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.profile.playAnimation();
                binding.homebtn.pauseAnimation();
               // binding.helpline.pauseAnimation();
                binding.bloodbtn.pauseAnimation();
                binding.reques.pauseAnimation();
                FragmentTransaction transactio = getSupportFragmentManager().beginTransaction();
                transactio.replace(R.id.frame,new ProfileFrag());
                transactio.commit();
            }
        });
    }
}