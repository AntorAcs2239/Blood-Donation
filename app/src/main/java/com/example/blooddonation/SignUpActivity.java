package com.example.blooddonation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.blooddonation.databinding.ActivityMainBinding;
import com.example.blooddonation.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import io.grpc.internal.InsightBuilder;

public class SignUpActivity extends AppCompatActivity {
    ActivitySignUpBinding binding;
    String username,email,password,bg,phone,division,district,address;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();


        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(SignUpActivity.this,R.array.bloodgroup, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinner.setAdapter(adapter);
        // binding.spinner.setOnItemSelectedListener(this);
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bg=adapterView.getItemAtPosition(i).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        binding.signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bg = "O+";
                username = binding.username.getText().toString();
                phone = binding.phone.getText().toString();
                email = binding.email.getText().toString();
                password = binding.password.getText().toString();
                district = binding.district.getText().toString();
                address = binding.address.getText().toString();
                if (username.isEmpty()) {
                    binding.username.setError("User name Should be fill");
                    binding.username.requestFocus();
                }
                if (email.isEmpty()) {
                    binding.email.setError("Email Should be fill");
                    binding.email.requestFocus();
                }
                if (password.isEmpty()) {
                    binding.password.setError("Password Should be fill");
                    binding.password.requestFocus();
                }
                if (phone.isEmpty()) {
                    binding.phone.setError("Phone number Should be fill");
                    binding.phone.requestFocus();
                }
                if (district.isEmpty()) {
                    binding.district.setError("District Should be fill");
                    binding.district.requestFocus();
                }
                if (address.isEmpty()) {
                    binding.address.setError("District Should be fill");
                    binding.address.requestFocus();
                } else {
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = firebaseAuth.getCurrentUser();
                                        user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                binding.verify.setVisibility(View.VISIBLE);
                                                binding.verify.setText("Verify Your Email First");
                                                String userid = task.getResult().getUser().getUid();
                                                UsersModel usersModel = new UsersModel(username, phone, password, bg, address, district," "," ","Want to Donate",0, false, false);
                                                firebaseFirestore.collection("users")
                                                        .document(userid)
                                                        .set(usersModel)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                Toast.makeText(SignUpActivity.this, "User added", Toast.LENGTH_LONG).show();
                                                            }
                                                        }).addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Toast.makeText(SignUpActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                                                            }
                                                        });
                                                startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(SignUpActivity.this, "Please try again", Toast.LENGTH_LONG).show();
                                            }
                                        });

                                    }
                                }
                            });
                }
            }
        });
    }
}