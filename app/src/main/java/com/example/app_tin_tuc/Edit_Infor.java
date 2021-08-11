package com.example.app_tin_tuc;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Edit_Infor extends AppCompatActivity {

    EditText edt_name, edt_email, edt_sdt;
    Button btn_thoat, btn_chinhsua;
    ImageView imgBack;
    SharedPreferences sharedPreferences;

    public static String Url = "http://www.sports.somee.com/api/Users";

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__infor);

        controler();

        sharedPreferences = getSharedPreferences("datalogin", MODE_PRIVATE);


        edt_name.setText(sharedPreferences.getString("name", "duy"));
        edt_email.setText(sharedPreferences.getString("email", ""));
        edt_sdt.setText("0"+ sharedPreferences.getInt("sdt", 123));

        setvent();
    }

    private void setvent() {
        btn_chinhsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 if (edt_name.getText().toString().trim().equals(""))
                {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Edit_Infor.this);
                    dialog.setTitle("THÔNG BÁO");
                    dialog.setMessage("TÊN KHÔNG ĐƯỢC ĐỂ TRỐNG!");
                    dialog.show();
                }
                else if (edt_email.getText().toString().trim().equals(""))
                {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Edit_Infor.this);
                    dialog.setTitle("THÔNG BÁO");
                    dialog.setMessage("EMAIL KHÔNG ĐƯỢC ĐỂ TRỐNG!");
                    dialog.show();
                }
                else if (!isValidEmail(edt_email.getText().toString().trim()))
                {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Edit_Infor.this);
                    dialog.setTitle("THÔNG BÁO");
                    dialog.setMessage("ĐỊNH DẠNG EMAIL KHÔNG CHÍNH XÁC!");
                    dialog.show();
                }
                else if (edt_sdt.getText().toString().trim().equals(""))
                {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Edit_Infor.this);
                    dialog.setTitle("THÔNG BÁO");
                    dialog.setMessage("SỐ ĐIỆN THOẠI KHÔNG ĐƯỢC ĐỂ TRỐNG");
                    dialog.show();
                }
                else
                {
                    CHINHSUA(Url);
                    Toast.makeText(Edit_Infor.this, "CHỈNH SỬA THÀNH CÔNG!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Edit_Infor.this, Infor_User.class));
                    finish();
                }
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Edit_Infor.this, Infor_User.class));
            }
        });

    }

    private void controler() {

        edt_name = findViewById(R.id.edittext_name_edit);
        edt_email = findViewById(R.id.edittext_email_edit);
        edt_sdt = findViewById(R.id.edittext_sdt_edit);
        imgBack = findViewById(R.id.imageback_edit);
        btn_chinhsua = findViewById(R.id.CHINHSUA);

    }

    private void CHINHSUA(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Edit_Infor.this, "ĐĂNG KÝ THẤT BẠI" + error.toString(), Toast.LENGTH_SHORT).show();
                        Log.d("AAA", error.toString());
                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String , String> params = new HashMap<>();

                sharedPreferences = getSharedPreferences("datalogin", MODE_PRIVATE);
                String id_use = String.valueOf(sharedPreferences.getInt("id_user", 123));

                params.put("id_user", id_use);
                params.put("name", edt_name.getText().toString().trim());
                params.put("sdt", edt_sdt.getText().toString().trim());
                params.put("email", edt_email.getText().toString().trim());

                SharedPreferences.Editor editor = sharedPreferences.edit();
                startActivity(new Intent(Edit_Infor.this, Infor_User.class));
                editor.putString("name", edt_name.getText().toString());
                editor.putString("email", edt_email.getText().toString());
                editor.putInt("sdt", Integer.valueOf(edt_sdt.getText().toString()));
                editor.commit();

                return params;
            }
        } ;
        requestQueue.add(stringRequest);
    }
}