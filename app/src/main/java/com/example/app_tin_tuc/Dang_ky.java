package com.example.app_tin_tuc;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.Edits;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app_tin_tuc.Class.Articles;
import com.example.app_tin_tuc.Class.users;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Dang_ky extends AppCompatActivity {

    EditText edt_user, edt_pass, edt_name, edt_sdt, edt_email, edt_pass_nhaplai;
    Button btn_dangky, btn_thoat;
    ArrayList<users> arrayList;
    public static int result =0;
    public static String username;

    public static String Url = "http://www.sports.somee.com/api/Users";
    public static String Url_user = "http://www.sports.somee.com/api/Users";
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);

        controler();
        setEvent();
        ReadJSON(Url_user);
    }

    private void setEvent() {
        btn_dangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edt_user.getText().toString().equals(""))
                {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Dang_ky.this);
                    dialog.setTitle("THÔNG BÁO");
                    dialog.setMessage("TÊN TÀI KHOẢN KHÔNG ĐƯỢC ĐỂ TRỐNG!");
                    dialog.show();
                }
                else if (edt_pass.getText().toString().trim().equals(""))
                {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Dang_ky.this);
                    dialog.setTitle("THÔNG BÁO");
                    dialog.setMessage("MẬT KHẨU KHÔNG ĐƯỢC ĐỂ TRỐNG!");
                    dialog.show();
                }
                else if (edt_pass_nhaplai.getText().toString().trim().equals(""))
                {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Dang_ky.this);
                    dialog.setTitle("THÔNG BÁO");
                    dialog.setMessage("MẬT KHẨU KHÔNG ĐƯỢC ĐỂ TRỐNG!");
                    dialog.show();
                }
                else if(!edt_pass.getText().toString().trim().equals(edt_pass_nhaplai.getText().toString().trim()))
                {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Dang_ky.this);
                    dialog.setTitle("THÔNG BÁO");
                    dialog.setMessage("MẬT KHẨU KHÔNG TRÙNG XIN KIỂM TRA LẠI!");
                    dialog.show();
                }
                else if (edt_name.getText().toString().trim().equals(""))
                {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Dang_ky.this);
                    dialog.setTitle("THÔNG BÁO");
                    dialog.setMessage("TÊN KHÔNG ĐƯỢC ĐỂ TRỐNG!");
                    dialog.show();
                }
                else if (edt_email.getText().toString().trim().equals(""))
                {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Dang_ky.this);
                    dialog.setTitle("THÔNG BÁO");
                    dialog.setMessage("EMAIL KHÔNG ĐƯỢC ĐỂ TRỐNG!");
                    dialog.show();
                }
                else if (!isValidEmail(edt_email.getText().toString().trim()))
                {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Dang_ky.this);
                    dialog.setTitle("THÔNG BÁO");
                    dialog.setMessage("ĐỊNH DẠNG EMAIL KHÔNG CHÍNH XÁC!");
                    dialog.show();
                }
                else if (edt_sdt.getText().toString().trim().equals(""))
                {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Dang_ky.this);
                    dialog.setTitle("THÔNG BÁO");
                    dialog.setMessage("SỐ ĐIỆN THOẠI KHÔNG ĐƯỢC ĐỂ TRỐNG");
                    dialog.show();
                }
                else
                {
                    Toast.makeText(Dang_ky.this, "ĐĂNG KÝ THÀNH CÔNG", Toast.LENGTH_SHORT).show();
                    Dangky(Url);
                    Intent intent = new Intent(Dang_ky.this, Users.class);
                    startActivity(intent);
                   finish();
                }
            }
        });

        btn_thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dang_ky.this, Users.class));
               finish();
            }
        });

    }


    private void controler() {

        edt_user = findViewById(R.id.editTextUsername_dangky);
        edt_pass = findViewById(R.id.editTextPassword_dangky);
        edt_pass_nhaplai = findViewById(R.id.editTextPassword_nhaplai);
        edt_name = findViewById(R.id.editTextname_dangky);
        edt_email = findViewById(R.id.editTextEmail_dangky);
        edt_sdt = findViewById(R.id.editText_sdt);

        btn_dangky = findViewById(R.id.button_DangKy);
        btn_thoat = findViewById(R.id.button_dangxuat);
        arrayList = new ArrayList<>();
    }


    private void Dangky(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Dang_ky.this, "ĐĂNG KÝ THẤT BẠI" + error.toString(), Toast.LENGTH_SHORT).show();
                        Log.d("AAA", error.toString());
                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String , String> params = new HashMap<>();

                params.put("username",edt_user.getText().toString().trim());
                params.put("password", edt_pass.getText().toString().trim());
                params.put("name", edt_name.getText().toString().trim());
                params.put("sdt", edt_sdt.getText().toString().trim());
                params.put("email", edt_email.getText().toString().trim());

                return params;
            }
        } ;
        requestQueue.add(stringRequest);
    }

    private void ReadJSON(String url)
    {

        RequestQueue requestQueue = Volley.newRequestQueue(Dang_ky.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++)
                        {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                arrayList.add(new users(
                                        object.getInt("id_user"),
                                        object.getString("username"),
                                        object.getString("password"),
                                        object.getString("name"),
                                        object.getInt("sdt"),
                                        object.getString("email")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Dang_ky.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }

}