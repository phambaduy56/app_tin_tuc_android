package com.example.app_tin_tuc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.app_tin_tuc.Adapter.ArticlesAdapter;
import com.example.app_tin_tuc.Adapter.adapter;
import com.example.app_tin_tuc.Class.Articles;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class Home_Category extends AppCompatActivity {

    ListView lvCategory_Artiles;
    ArrayList<Articles> arrayList;
    adapter adapter;
    Articles articles;
    androidx.appcompat.widget.Toolbar toolbarhome_category;
    String url = "http://www.sports.somee.com/api/Articles";
    public static int CHUYEN_MAN_HINH = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__category);

        Controller();

        setevent();
        ActionBar();

        ReadJSON(url);
    }


    private void ActionBar() {
        Intent intent = getIntent();
        setSupportActionBar(toolbarhome_category);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarhome_category.setNavigationIcon(R.drawable.back);
        toolbarhome_category.setTitle(intent.getStringExtra("name_category"));
        toolbarhome_category.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home_Category.this, MainActivity.class));
            }
        });

    }

    private void setevent() {
        lvCategory_Artiles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Home_Category.this, Home.class);

                articles =  arrayList.get(position);
                intent.putExtra("id_articles", articles.getId());
                intent.putExtra("nameArticle", articles.getNameArticle());
                intent.putExtra("author", articles.getAuthor());
                intent.putExtra("datetime", articles.getDatetime());
                intent.putExtra("contentArticle", articles.getContentArticle());
                intent.putExtra("desciption", articles.getDesciption());
                intent.putExtra("img", articles.getImg());
                intent.putExtra("name_category", articles.getName_category());
                intent.putExtra("id_category", articles.getId_category());
                intent.putExtra("CHUYEN_MAN_HINH", CHUYEN_MAN_HINH);


                startActivity(intent);
            }
        });
    }


    private void Controller() {
        lvCategory_Artiles = (ListView) findViewById(R.id.listViewCategoryArticles);
        toolbarhome_category = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbarhome_category);

        arrayList = new ArrayList<>();

//        adapter = new adapter(Home_Category.this, R.layout.line_articles, arrayList);
//        lvCategory_Artiles.setAdapter(adapter);

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

    private void ReadJSON(String url)
    {
        Intent intent = getIntent();
        int id_category = intent.getIntExtra("id_category", 12333);
        RequestQueue requestQueue = Volley.newRequestQueue(Home_Category.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++)
                        {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                if(id_category == object.getInt("id_category")){
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
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        Collections.reverse(arrayList);
                        adapter = new adapter(Home_Category.this, R.layout.line_articles, arrayList);
                        lvCategory_Artiles.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Home_Category.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }


}