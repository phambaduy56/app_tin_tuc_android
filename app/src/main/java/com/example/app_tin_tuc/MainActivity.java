package com.example.app_tin_tuc;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.app_tin_tuc.Adapter.ArticlesAdapter;
import com.example.app_tin_tuc.Adapter.CategoryAdapter;
import com.example.app_tin_tuc.Class.Articles;
import com.example.app_tin_tuc.Class.Category;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    ListView lvArticles, lvDsTheLoai;
    ArticlesAdapter adapter;
    ArrayList<Articles> arrayList;
    Articles articles;
    androidx.appcompat.widget.Toolbar toolbarDsTheLoai;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    CategoryAdapter categoryAdapter;
    ArrayList<Category> categoryArrayList;
    ArrayList<Category> categoryArrayList_reverse;
    TextView txtLogin;
    public static int CHUYEN_MAN_HINH = 1;
    public static int CHUYEN_MAN_HINH_DANG_NHAP = 1;
    public static String name;
    public static int sdt;
    public static String email;
    public static int id_user;


    String UrlGet_Articles = "http://www.sports.somee.com/api/Articles";
    String UrlGet_Catagory = "http://www.sports.somee.com/api/Category";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Controller();

        Intent intent4 = getIntent();

        name = intent4.getStringExtra("name");
        sdt = intent4.getIntExtra("sdt", 123);
        email = intent4.getStringExtra("email");
        id_user = intent4.getIntExtra("id_user", 1233);

        SharedPreferences sharedPreferences = getSharedPreferences("datalogin", MODE_PRIVATE);
        String kiemtra = sharedPreferences.getString("taikhoan", "");

        if(kiemtra.equals(""))
        {
            txtLogin.setText("ĐĂNG NHẬP");
        }
        else
        {
            txtLogin.setText("THÔNG TIN");
        }

        ReadJSON(UrlGet_Articles);

        ReadJSON_Category(UrlGet_Catagory);

        ActionBar();
        
        set_event();
    }

    private void ActionBar() {
        setSupportActionBar(toolbarDsTheLoai);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarDsTheLoai.setNavigationIcon(R.drawable.menu);
        toolbarDsTheLoai.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_timkiem, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText.trim());
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void ReadJSON_Category(String url)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++)
                        {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                categoryArrayList.add(new Category(
                                        object.getInt("id_category"),
                                        object.getString("name_category")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        categoryAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }

    private void ReadJSON(String url)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++)
                        {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                arrayList.add(new Articles(
                                        object.getInt("id_article"),
                                        object.getString("nameArticle"),
                                        object.getString("author"),
                                        object.getString("datetime_article"),
                                        object.getString("contentArticle"),
                                        object.getString("desciption"),
                                        object.getString("img"),
                                        object.getString("name_category"),
                                        object.getInt("id_category")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        Collections.reverse(arrayList);
                        adapter = new ArticlesAdapter(MainActivity.this, R.layout.line_articles, arrayList);
                        lvArticles.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }



    private void Controller()
    {
        lvArticles = (ListView) findViewById(R.id.listviewArticles);
        toolbarDsTheLoai = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbarDsthelaoi);
        navigationView = findViewById(R.id.navigationView);
        lvDsTheLoai = findViewById(R.id.listViewDanhSachTheLoai);
        drawerLayout = findViewById(R.id.drawerlayout);

        txtLogin = findViewById(R.id.txtthongtinuser);

        arrayList = new ArrayList<>();


        categoryArrayList = new ArrayList<>();


//        adapter = new ArticlesAdapter(MainActivity.this, R.layout.line_articles, arrayList);
//        lvArticles.setAdapter(adapter);

        categoryAdapter = new CategoryAdapter(MainActivity.this, R.layout.line_category, categoryArrayList);
        lvDsTheLoai.setAdapter(categoryAdapter);



    }

    private void Dialog_Chuyen_Dang_Nhap() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle("THÔNG BÁO");
        dialog.setMessage("Bạn chưa đăng nhập. \n Bạn có muốn đăng nhập không!");

        dialog.setNegativeButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent3 = new Intent(MainActivity.this, Users.class);
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

    private  void set_event()
    {
        // listview
        lvArticles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent1 = new Intent(MainActivity.this, Home.class);

                articles =  arrayList.get(position);
                intent1.putExtra("id_articles", articles.getId());
                intent1.putExtra("nameArticle", articles.getNameArticle());
                intent1.putExtra("author", articles.getAuthor());
                intent1.putExtra("datetime", articles.getDatetime());
                intent1.putExtra("contentArticle", articles.getContentArticle());
                intent1.putExtra("desciption", articles.getDesciption());
                intent1.putExtra("img", articles.getImg());
                intent1.putExtra("name_category", articles.getName_category());
                intent1.putExtra("id_category", articles.getId_category());
                intent1.putExtra("CHUYEN_MAN_HINH", CHUYEN_MAN_HINH);
                intent1.putExtra("id_user", id_user);

                startActivity(intent1);

            }
        });

        lvDsTheLoai.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              Intent intent2 = new Intent(MainActivity.this, Home_Category.class);
              Category category = categoryArrayList.get(position);
              intent2.putExtra("id_category", category.getId_category());
              intent2.putExtra("name_category", category.getName_category());
              startActivity(intent2);
          }
      });

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("datalogin", MODE_PRIVATE);
                String kiemtra = sharedPreferences.getString("taikhoan", "");
                if(kiemtra.equals(""))
                {
                    Dialog_Chuyen_Dang_Nhap();
                }
                else{
                    startActivity(new Intent(MainActivity.this, Infor_User.class));
                }
            }
        });

    }

}