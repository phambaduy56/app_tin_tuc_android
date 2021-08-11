package com.example.app_tin_tuc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Infor_User extends AppCompatActivity {

    TextView txtname, txtemail, txtsdt;
    ImageView imgBack, imgEdit;
    Button btnDangXuat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infor__user);

        controller();
        SharedPreferences sharedPreferences = getSharedPreferences("datalogin", MODE_PRIVATE);


        txtname.setText(sharedPreferences.getString("name", "duy"));
        txtemail.setText(sharedPreferences.getString("email", ""));
        txtsdt.setText("0"+ sharedPreferences.getInt("sdt", 123));

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Infor_User.this, MainActivity.class);
//                intent1.putExtra("name", txtname.getText());
//                intent1.putExtra("email", txtemail.getText());
//                intent1.putExtra("sdt", txtsdt.getText());
                startActivity(intent1);
            }
        });

        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Infor_User.this, Users.class));
            }
        });

        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Infor_User.this, Edit_Infor.class);
                intent1.putExtra("name", txtname.getText());
                intent1.putExtra("email", txtemail.getText());
                intent1.putExtra("sdt", txtsdt.getText());
                startActivity(intent1);
            }
        });

    }

    private void controller() {

        txtname = findViewById(R.id.textviewName);
        txtemail = findViewById(R.id.textviewEmail);
        txtsdt = findViewById(R.id.textviewSDT);
        imgBack = findViewById(R.id.imageback);
        imgEdit = findViewById(R.id.imgEdit);
        btnDangXuat = findViewById(R.id.dangxuat);
    }
}