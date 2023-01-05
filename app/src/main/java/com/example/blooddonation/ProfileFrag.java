package com.example.blooddonation;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.blooddonation.databinding.FragmentDonarlistBinding;
import com.example.blooddonation.databinding.FragmentProfileBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFrag extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFrag newInstance(String param1, String param2) {
        ProfileFrag fragment = new ProfileFrag();
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
    com.example.blooddonation.databinding.FragmentProfileBinding binding;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private String name,phone,area,district,bloodg,status,lastblooddonation,numofdonate;
    private String name2,phone2,area2,district2,bloodg2,status2,lastblooddonation2,numofdonate2;
    private SharedClass sharedClass;
    DatePickerDialog datePickerDialog;
    Animation animation;
    long year1,day1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding =FragmentProfileBinding.inflate(inflater, container, false);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        sharedClass=new SharedClass(getContext());
        animation= AnimationUtils.loadAnimation(getContext(),R.anim.topanim);
        binding.profileeee.setAnimation(animation);
        if (sharedClass.getuserauth()==true)
        {
            HashMap<String,String>map=sharedClass.getuserdetails();
            name=map.get(sharedClass.NAME);
            phone=map.get(sharedClass.PHONE);
            area=map.get(sharedClass.AREA);
            district=map.get(sharedClass.DISTRICT);
            lastblooddonation=map.get(sharedClass.LASTBLOODDONATION);
            status=map.get(sharedClass.STATUS);
            numofdonate=map.get(sharedClass.NUMOFDONATE);
            bloodg=map.get(sharedClass.BLOODG);
            binding.name.setText(name);
            binding.phone.setText(phone);
            binding.area.setText(area);
            binding.district.setText(district);
            binding.lastdonate.setText("Last Blood Donate\n"+lastblooddonation);
            binding.ss.setText("Status\n"+status);
            binding.blood.setText("Blood Group: "+bloodg);
            binding.numofd.setText("Num of Donation: "+numofdonate);
        }
        else
        {
            firebaseFirestore.collection("users")
                    .document(firebaseAuth.getCurrentUser().getUid())
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            UsersModel usersModel=documentSnapshot.toObject(UsersModel.class);
                            name=usersModel.getName();
                            phone=usersModel.getPhone();
                            area=usersModel.getArea();
                            district=usersModel.getDistrict();
                            status=usersModel.getStatus();
                            lastblooddonation=usersModel.getLastDonation();
                            bloodg=usersModel.getBloodGroup();
                            numofdonate=String.valueOf(usersModel.getNumofdonation());
                            binding.name.setText(name);
                            binding.phone.setText(phone);
                            binding.area.setText(area);
                            binding.district.setText(district);
                            binding.lastdonate.setText("Last Blood Donate\n"+lastblooddonation);
                            binding.ss.setText("Status\n"+status);
                            binding.blood.setText("Blood Group: "+bloodg);
                            binding.numofd.setText("Num of Donation: "+numofdonate);
                            sharedClass.createloginsession(name,phone,area,district,status,lastblooddonation,numofdonate,bloodg);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(),e.toString(),Toast.LENGTH_LONG).show();
                        }
                    });
        }

        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(getContext(),R.array.bloodgroup, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.bg.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter2=ArrayAdapter.createFromResource(getContext(),R.array.numofdonate, android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.numofdonate.setAdapter(adapter2);

        ArrayAdapter<CharSequence> adapter3=ArrayAdapter.createFromResource(getContext(),R.array.status, android.R.layout.simple_spinner_dropdown_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.status.setAdapter(adapter3);

        int initialposition=binding.bg.getSelectedItemPosition();
        binding.bg.setSelection(initialposition, false);
        int initialposition2=binding.status.getSelectedItemPosition();
        binding.status.setSelection(initialposition2, false);
        int initialposition3=binding.numofdonate.getSelectedItemPosition();
        binding.numofdonate.setSelection(initialposition3, false);

        binding.bg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bloodg=adapterView.getItemAtPosition(i).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        binding.status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                status=adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        binding.numofdonate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                numofdonate=adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        binding.updatedetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name=binding.name.getText().toString();
                phone=binding.phone.getText().toString();
                area=binding.area.getText().toString();
                district=binding.district.getText().toString();
                int numofdona=0;
                numofdona = Integer.parseInt(numofdonate);
                boolean donar=false;
                if (status.contains("Want to Donate"))
                {
                    donar=true;
                }
                firebaseFirestore.collection("users")
                        .document(firebaseAuth.getCurrentUser().getUid())
                        .update("name",name,"phone",phone,"area",area,
                                "district",district,"bloodGroup",bloodg,"lastDonation",lastblooddonation,
                                "numofdonation",numofdona,
                                "status",status,"isDonar",donar)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                sharedClass.createloginsession(name,phone,area,district,status,lastblooddonation,numofdonate,bloodg);
                                Toast.makeText(getContext(),"Updated successfully",Toast.LENGTH_LONG).show();
                                binding.name.setText(name);
                                binding.phone.setText(phone);
                                binding.area.setText(area);
                                binding.district.setText(district);
                                calculateday();
                                //binding.lastdonate.setText("Last Blood Donate\n"+lastblooddonation);
                                binding.ss.setText("Status\n"+status);
                                binding.blood.setText("Blood Group: "+bloodg);
                                binding.numofd.setText("Num of Donation: "+numofdonate);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(),"Update Fails try Again",Toast.LENGTH_LONG).show();
                            }
                        });

            }
        });
        binding.lastdonate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month=month+1;
                        String date=makedate(year,month,day);
                        String date2=makedate2(year,month,day);
                        binding.lastdonate.setText("Last Blood Donate\n"+date);
                        lastblooddonation=date2;
                    }
                };
                Calendar calendar=Calendar.getInstance();
                int year=calendar.get(Calendar.YEAR);
                int month=calendar.get(Calendar.MONTH);
                int day=calendar.get(Calendar.DAY_OF_MONTH);
                int style= AlertDialog.THEME_HOLO_LIGHT;
                datePickerDialog=new DatePickerDialog(getContext(), style,dateSetListener,year,month,day);
                datePickerDialog.show();
            }
        });
        return binding.getRoot();
    }

    private void calculateday() {
        String date1=lastblooddonation;
        Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        String date2=makedate2(year,month,day);

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date d1=simpleDateFormat.parse(date1);
            Date d2=simpleDateFormat.parse(date2);

            if (d1.getTime()<=d2.getTime())
            {
                long differece=d2.getTime()-d1.getTime();
                year1= (differece
                        / (1000l * 60 * 60 * 24 * 365));
                day1= (differece
                        / (1000 * 60 * 60 * 24))
                        % 365;
                binding.lastdonate.setText("Last Blood Donate\n+"+lastblooddonation+"\n"+String.valueOf(year1)+"year-"+String.valueOf(day1)+"Day Ago");
            }
            else
            {
                binding.lastdonate.setText("Last Blood Donate\n you enter invalid date");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String makedate(int year,int month,int day)
    {
        String m=getmonth(month);
        return m+"/"+day+"/"+year;
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