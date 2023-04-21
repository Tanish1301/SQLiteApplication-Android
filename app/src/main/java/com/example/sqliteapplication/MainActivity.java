package com.example.sqliteapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btnUpdate,btnView,btnInsert,btnDelete;
    EditText txtName,txtDOB,txtContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtName = findViewById(R.id.txtName);
        txtDOB = findViewById(R.id.txtDOB);
        txtContact = findViewById(R.id.txtContact);

        btnInsert = findViewById(R.id.btnInsert);
        btnDelete = findViewById(R.id.btnDelete);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnView = findViewById(R.id.btnView);

        DBHelper DB = new DBHelper(this);
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = txtName.getText().toString();
                String contact = txtContact.getText().toString();
                String dob = txtDOB.getText().toString();

                Boolean checkDBop = DB.insertUserData(name, contact,dob);
                if(checkDBop == true){
                    Toast.makeText(MainActivity.this, "New Record Inserted Successfully", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "Unable To Insert Record", Toast.LENGTH_LONG).show();
                }
            }
        });

        //Update Onclick listener
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = txtName.getText().toString();
                String contact = txtContact.getText().toString();
                String dob = txtDOB.getText().toString();

                Boolean checkupdate = DB.updateUserData(name,contact,dob);
                if(checkupdate == true){
                    Toast.makeText(MainActivity.this,"Record Updated Successfully", Toast.LENGTH_LONG).show();
                }
            }
        });
        //Delete OnClickListener
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = txtName.getText().toString();

                Boolean checkdelete = DB.DeleteUserData(name);
                if(checkdelete == true){
                    Toast.makeText(MainActivity.this,"Record Deleted Successfully", Toast.LENGTH_LONG).show();
                }
            }
        });
        //View all user records
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor records = DB.ViewUserData();
                if(records.getCount()==0){
                    Toast.makeText(MainActivity.this, "No Data Found", Toast.LENGTH_LONG).show();
                    return;
                }

                StringBuffer allRecords = new StringBuffer();
                while(records.moveToNext()){
                    allRecords.append("SN: "+records.getString(0)+ "\n");
                    allRecords.append("Name: "+records.getString(1) + "\n");
                    allRecords.append("Contact: "+records.getString(2) + "\n");
                    allRecords.append("Date Of Birth: "+records.getString(3) + "\n");
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("View All Records");
                builder.setMessage(allRecords.toString());
                builder.show();
            }
        });
    }
}