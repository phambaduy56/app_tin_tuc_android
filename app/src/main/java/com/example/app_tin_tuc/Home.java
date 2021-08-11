package com.example.app_tin_tuc;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app_tin_tuc.Class.Category;

import java.text.DateFormat;
import java.util.HashMap;
import java.util.Map;

public class Home extends AppCompatActivity {

    TextView txtNameArticle, txtAuthor, txtDatetime, txtContentArticle, txtDesciption;
    ImageView imgHinhAnh2;
    androidx.appcompat.widget.Toolbar toolbarhome;
    public static int id_category;
    public static String name_category;
    public static int CHUYEN_MAN_HINH;
    public static int CHUYEN_MAN_HINH_DANG_NHAP = 0;
    public static int id_article;
    public static int id_user;
    Button btnWriteCmt, btnReadCmt;


    SharedPreferences sharedPreferences;

    public static EditText edt_cmt;

    public static String url = "http://www.sports.somee.com/api/Comment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        set_controller();

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 1235);

        txtNameArticle.setText(intent.getStringExtra("nameArticle"));
        txtAuthor.setText(intent.getStringExtra("author"));
        txtDatetime.setText(intent.getStringExtra("datetime"));

        id_user = intent.getIntExtra("id_user", 1233);

        txtContentArticle.setText(intent.getStringExtra("contentArticle"));

        txtContentArticle.setMovementMethod(new ScrollingMovementMethod());
        id_category = intent.getIntExtra("id_category", 123131);
        name_category = intent.getStringExtra("name_category");
        CHUYEN_MAN_HINH = intent.getIntExtra("CHUYEN_MAN_HINH", 123445);

        id_article = intent.getIntExtra("id_articles", 1233);

        txtDesciption.setText(intent.getStringExtra("desciption"));



        String url = intent.getStringExtra("img");
        ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                imgHinhAnh2.setImageBitmap(response);
            }
        }, 0, 0, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
              imgHinhAnh2.setImageResource(R.mipmap.ic_launcher);
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(Home.this);
        requestQueue.add(imageRequest);

        ActionBar();

        setevent();
    }

    private void Them_cmt(String url) {
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
                        Toast.makeText(Home.this, "xay ra loi", Toast.LENGTH_SHORT).show();
                        Log.d("AAA", error.toString());
                    }
                }
                ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String , String> params = new HashMap<>();
                SharedPreferences sharedPreferences = getSharedPreferences("datalogin", MODE_PRIVATE);

                String id_use = String.valueOf(sharedPreferences.getInt("id_user", 123));
                String id_ar = String.valueOf(id_article);
                params.put("id_article",id_ar);
                params.put("id_user", id_use);
                params.put("comment_content", edt_cmt.getText().toString().trim());

                return params;
            }
        } ;
        requestQueue.add(stringRequest);
    }

    private void Dialog_Chuyen_Dang_Nhap() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle("THÔNG BÁO");
        dialog.setMessage("Bạn chưa đăng nhập. \n Bạn có muốn đăng nhập không!");

        dialog.setNegativeButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent3 = new Intent(Home.this, Users.class);
                startActivity(intent3);
            }
        });

        dialog.setPositiveButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();
    }

    private void dialog_show_cmt() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_cmt);
        Button btnCmt = dialog.findViewById(R.id.button_cmt);
        edt_cmt = dialog.findViewById(R.id.editText_cmt);


        btnCmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Them_cmt(url);
                sharedPreferences = getSharedPreferences("datalogin", MODE_PRIVATE);
               // Toast.makeText(Home.this, ""+sharedPreferences.getString("taikhoan", ""), Toast.LENGTH_SHORT).show();
                String taikhoan = sharedPreferences.getString("taikhoan", "");

                if(edt_cmt.getText().toString().equals(""))
                {
                    Toast.makeText(Home.this, "BÌNH LUẬN BẠN KHÔNG ĐƯỢC ĐỂ TRỐNG!", Toast.LENGTH_SHORT).show();
                }
                if(taikhoan.equals(""))
                {
                    Dialog_Chuyen_Dang_Nhap();
                    dialog.dismiss();
                }
                else
                {
                    Them_cmt(url);
                     dialog.dismiss();
                }
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(1200, 600);
    }

    private void setevent() {

        btnReadCmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Cmt.class);
                intent.putExtra("id_article", id_article);
                startActivity(intent);
            }
        });

        btnWriteCmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_show_cmt();

            }
        });

    }

    private void ActionBar() {
        setSupportActionBar(toolbarhome);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarhome.setNavigationIcon(R.drawable.back);
        toolbarhome.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(CHUYEN_MAN_HINH == 1)
               {
                   Intent intent = new Intent(Home.this, MainActivity.class);
                   intent.putExtra("id_user", id_user);
                   startActivity(intent);


               }
               if (CHUYEN_MAN_HINH == 0)
               {
                   Intent intent = new Intent(Home.this, Home_Category.class);
                   intent.putExtra("id_category", id_category);
                   intent.putExtra("name_category", name_category);
                   startActivity(intent);
               }
            }
        });

    }

    private void set_controller(){
        txtNameArticle = findViewById(R.id.textViewNameArticleHome);
        txtAuthor = findViewById(R.id.textViewAuthorHome);
        txtDatetime = findViewById(R.id.textViewDateTimeHome);
        txtContentArticle = (TextView) findViewById(R.id.textViewContentArticleHome);
        txtDesciption = findViewById(R.id.textViewDesciptionHome);
        imgHinhAnh2 = findViewById(R.id.imageViewHome2);
        toolbarhome = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbarhome);
        btnWriteCmt = findViewById(R.id.buttonWriteCmt);
        btnReadCmt = findViewById(R.id.buttonReadCmt);
    }




}