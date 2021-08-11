package com.example.app_tin_tuc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.app_tin_tuc.Adapter.ArticlesAdapter;
import com.example.app_tin_tuc.Adapter.CmtAdapter;
import com.example.app_tin_tuc.Class.Articles;
import com.example.app_tin_tuc.Class.cmt;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Cmt extends AppCompatActivity {

    public static int id_article;
    public static String article;
    ListView lvcmt;
    ArrayList<cmt> listcmt;
    CmtAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cmt);

        controler();

        Intent intent = getIntent();

        id_article = intent.getIntExtra("id_article", 123);

        article = String.valueOf(id_article);
        String Url = "http://www.sports.somee.com/api/Comment/" + article;

        //Toast.makeText(this, ""+id_article, Toast.LENGTH_SHORT).show();

        ReadJSON(Url);
    }

    private void controler() {
        lvcmt = findViewById(R.id.lvcmt);
        listcmt = new ArrayList<>();

        adapter = new CmtAdapter(Cmt.this, R.layout.dong_cmt, listcmt);
        lvcmt.setAdapter(adapter);
    }



    private void ReadJSON(String url)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(Cmt.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i < response.length(); i++)
                        {
                            try {
                                JSONObject object = response.getJSONObject(i);

                                    listcmt.add(new cmt(
                                            object.getInt("id_comment"),
                                            object.getString("comment_content"),
                                            object.getString("name")
                                    ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Cmt.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }

}