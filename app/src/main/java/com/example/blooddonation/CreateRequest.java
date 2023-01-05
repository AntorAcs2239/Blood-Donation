package com.example.blooddonation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.blooddonation.databinding.ActivityCreateRequestBinding;
import com.example.blooddonation.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CreateRequest extends AppCompatActivity {
    ActivityCreateRequestBinding binding;
    String bg,location,district,time,date,problem,phone;
    String need;
    DatePickerDialog datePickerDialog;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    Boolean flag;
    int edit,numofbag,managednumofbag;
    String editlocation,editdistrict,edittime,editdate,editproblem,editbg,editphone,editid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_request);
        binding = ActivityCreateRequestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        edit=getIntent().getIntExtra("edit",0);
        numofbag=getIntent().getIntExtra("need",0);
        managednumofbag=getIntent().getIntExtra("managed",0);
        editlocation=getIntent().getStringExtra("location");
        editdistrict=getIntent().getStringExtra("district");
        edittime=getIntent().getStringExtra("time");
        editdate=getIntent().getStringExtra("date");
        editproblem=getIntent().getStringExtra("problem");
        editbg=getIntent().getStringExtra("bg");
        editphone=getIntent().getStringExtra("phone");
        editid=getIntent().getStringExtra("reqid");
        if (edit==1)
        {
            binding.bloodG.setText("Blood Group: "+editbg);
            binding.reqlocation.setText(editlocation);
            binding.reqdistrict.setText(editdistrict);
            binding.reqtime.setText(edittime);
            binding.reqdate.setText(editdate);
            binding.reqcontact.setText(editphone);
            binding.textView12.setText("Need: "+numofbag+"(Bag)");
            binding.textView10.setText("Managed: "+managednumofbag+"(Bag)");
            binding.textView10.setVisibility(View.VISIBLE);
            binding.reqmanaged.setVisibility(View.VISIBLE);
            binding.delete.setVisibility(View.VISIBLE);
            binding.reqproblem.setHint("Problem: "+editproblem);
            binding.submit.setText("Update Your Form");
            binding.textView7.setText("Edit The Request Form");
        }
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(CreateRequest.this, R.array.bloodgroup, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.bloodGroup.setAdapter(adapter);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(CreateRequest.this, R.array.numofdonate, android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.reqneed.setAdapter(adapter2);
        ArrayAdapter<CharSequence> adapter3= ArrayAdapter.createFromResource(CreateRequest.this, R.array.numofdonate, android.R.layout.simple_spinner_dropdown_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.reqmanaged.setAdapter(adapter3);

        int initialposition=binding.bloodGroup.getSelectedItemPosition();
        binding.bloodGroup.setSelection(initialposition, false);
        int initialposition2=binding.reqneed.getSelectedItemPosition();
        binding.reqneed.setSelection(initialposition2, false);
        int initialposition3=binding.reqmanaged.getSelectedItemPosition();
        binding.reqmanaged.setSelection(initialposition3, false);

        binding.bloodGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bg = adapterView.getItemAtPosition(i).toString();
                editbg=bg;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.reqneed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                need=adapterView.getItemAtPosition(i).toString();
                numofbag=Integer.parseInt(need);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.reqmanaged.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                managednumofbag=Integer.parseInt(adapterView.getItemAtPosition(i).toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                location = binding.reqlocation.getText().toString();
                district = binding.reqdistrict.getText().toString();
                time = binding.reqtime.getText().toString();
                problem = binding.reqproblem.getText().toString();
                phone = binding.reqcontact.getText().toString();
                if (edit==1)
                {
                    firebaseFirestore.collection("requests")
                            .document(editid)
                            .update("bloodGroup",editbg,"contact",phone,"date",editdate,
                            "district",district,"location",location,"problem",problem,
                            "time",edittime,"numBag",numofbag,"numManaged",managednumofbag)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(CreateRequest.this,"Edited Successfully",Toast.LENGTH_LONG).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(CreateRequest.this,e.toString(),Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                else
                {
                    DocumentReference documentReference=firebaseFirestore.collection("requests").document();
                    RequestModel requestModel = new RequestModel(bg, phone, date, district, location, problem, time, firebaseAuth.getCurrentUser().getUid(), false, Integer.parseInt(need), 0, new Timestamp(new Date()),documentReference.getId());
                    documentReference.set(requestModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(CreateRequest.this,"Request Added Successfully",Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(CreateRequest.this,"Fail to submit for:"+e.toString(),Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
        binding.reqdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        String dat = makedate2(year, month, day);
                        String d = makedate(year, month, day);
                        binding.reqdate.setText("Date: " + dat);
                        date = d;
                    }
                };
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int style = AlertDialog.THEME_HOLO_LIGHT;
                datePickerDialog = new DatePickerDialog(CreateRequest.this, style, dateSetListener, year, month, day);
                datePickerDialog.show();
            }
        });
        binding.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               AlertDialog.Builder builder=new AlertDialog.Builder(CreateRequest.this);
               builder.setTitle("Delete the Form");
               builder.setMessage("Do you want to delete the Request Form");
               builder.setIcon(R.drawable.delete2);
               builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {
                       firebaseFirestore.collection("requests")
                               .document(editid)
                               .delete()
                               .addOnSuccessListener(new OnSuccessListener<Void>() {
                                   @Override
                                   public void onSuccess(Void unused) {
                                       Toast.makeText(CreateRequest.this,"Deleted Successfully!",Toast.LENGTH_LONG).show();
                                       onBackPressed();
                                   }
                               }).addOnFailureListener(new OnFailureListener() {
                                   @Override
                                   public void onFailure(@NonNull Exception e) {
                                       Toast.makeText(CreateRequest.this,"Some Error Occur Try Again",Toast.LENGTH_LONG).show();
                                   }
                               });
                   }
               });
               builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {
                   }
               });
               builder.show();
            }
        });
    }
    public String makedate(int year,int month,int day)
    {
        String m=getmonth(month);
        return year+"-"+month+"-"+day;
    }
    public String makedate2(int year,int month,int day)
    {
        return year+"-"+month+"-"+day;
    }
    public String getmonth(int month) {
        String mon="";
        if (month==1) mon="JAN";
        if (month==2) mon="FEB";
        if (month==3) mon="MAR";
        if (month==4) mon="APR";
        if (month==5) mon="MAY";
        if (month==6) mon="JUN";
        if (month==7) mon="JUL";
        if (month==8) mon="AUG";
        if (month==9) mon="SEP";
        if (month==10) mon="OCT";
        if (month==11) mon="NOV";
        if (month==12) mon="DEC";
        return mon;
    }
}