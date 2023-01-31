package com.example.mediclinic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PatientViewActivity extends AppCompatActivity {
    TextView cName;
    String clinicName;
    private RecyclerView recyclerView;
    private ArrayList<Patient> patient1;
    private  com.example.mediclinic.P_PatientAdapter p_patientAdapter;
    DatabaseReference dbRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_view);
        cName = findViewById(R.id.clinicNameTxt2);
        Intent i = getIntent();
        clinicName = i.getStringExtra("clinicName");
        recyclerView = findViewById(R.id.recycler8);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        patient1 = new  ArrayList<Patient>();
        cName.setText(clinicName);
        dbRef = FirebaseDatabase.getInstance().getReference().child("Patient");
        dbRef.addListenerForSingleValueEvent(valueEventListener);
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            for (DataSnapshot snapshot1: snapshot.getChildren()){
                Patient uData = snapshot1.getValue(Patient.class);
                patient1.add(uData);
            }
            p_patientAdapter = new  com.example.mediclinic.P_PatientAdapter( com.example.mediclinic.PatientViewActivity.this,patient1);
            recyclerView.setAdapter(p_patientAdapter);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    public void vieHome(View view){
        Intent in = new Intent(PatientViewActivity.this, app1page.class);
        startActivity(in);
    }

    public void profile(View view){
        Intent in = new Intent(PatientViewActivity.this, PatientViewProfile.class);
        startActivity(in);
    }

    public void viewNotices(View view){
        Intent in = new Intent(PatientViewActivity.this, DoctorNoticesPatientView.class);
        startActivity(in);
        Toast.makeText(this, "Showing Notices..", Toast.LENGTH_SHORT).show();
    }
}