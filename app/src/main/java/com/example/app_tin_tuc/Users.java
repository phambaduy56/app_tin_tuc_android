package com.example.app_tin_tuc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.app_tin_tuc.Class.users;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Users extends AppCompatActivity {

    EditText edtUsername, edtPassword;
    CheckBox cbRemember;
    Button btnLogIn, btnregis;
    ArrayList<users> usersArrayList;

    ImageButton img_dangy;

    public static String Url = "http://www.sports.somee.com/api/Users";
    public static int kiemtra = 0;
    public static String username;
    public static String password;
    public static String name;
    public static String email;
    public static int sdt;
    public static int id_user;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        Controller();

        sharedPreferences = getSharedPreferences("datalogin", MODE_PRIVATE);

        edtUsername.setText(sharedPreferences.getString("taikhoan", ""));
        edtPassword.setText(sharedPreferences.getString("matkhau",""));
     //   cbRemember.setChecked(sharedPreferences.getBoolean("check", false));

        ReadJSON(Url);

        setevent();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dangky, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.action_dangky){
            startActivity(new Intent(Users.this, Dang_ky.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void setevent() {

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = edtUsername.getText().toString().trim();
                password = edtPassword.getText().toString().trim();
                //password.equals(usersArrayList.get(i).getPassword())
                for (int i=0; i<usersArrayList.size(); i++)
                {
                    if(username.equals(usersArrayList.get(i).getUsername()) && password.equals(usersArrayList.get(i).getPassword()) )
                    {
                        name = usersArrayList.get(i).getName();
                        sdt = usersArrayList.get(i).getSdt();
                        email = usersArrayList.get(i).getEmail();
                        id_user = usersArrayList.get(i).getId_user();
                        kiemtra = 1;
                        break;
                    }
                }
                //

                if(cbRemember.isChecked()){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("taikhoan", username);
                    editor.putString("matkhau", password);
                    editor.putString("name", name);
                    editor.putString("email", email);
                    editor.putInt("sdt", sdt);
                    editor.putInt("id_user", id_user);
                  //  editor.putBoolean("check", true);
                    editor.commit();
                }
                else {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("taikhoan");
                    editor.remove("matkhau");
                    editor.remove("name");
                    editor.remove("email");
                    editor.remove("sdt");
                   // editor.remove("check");
                    editor.commit();
                }

                //
//                if(username.equals("") && password.equals("") )
//                {
//                    Toast.makeText(Users.this, "C?? th??? b???n ch??a nh???p t??i kho???n m???t kh???u!." + "\n" + "Vui l??ng ??i???n ?????y ????? th??ng tin ????? ti???p t???c", Toast.LENGTH_SHORT).show();
//                }
                if (username.equals(""))
                {

                    Toast.makeText(Users.this, "T??I KHO???N KH??NG ???????C ????? TR???NG\n VUI L??NG KI???M TRA L???I", Toast.LENGTH_SHORT).show();
                }
                else if (password.equals(""))
                {
                    Toast.makeText(Users.this, "M???T KH???U KH??NG ???????C ???? TR???NG \n VUI L??NG KI???M TRA L???I", Toast.LENGTH_SHORT).show();
                }
                else if(kiemtra != 1)
                {
                    Toast.makeText(Users.this, "SAI T??I KHO???N HO???C M???T KH???U ! \nVUI L??NG KI???M TRA L???I" + "\n"
                            , Toast.LENGTH_SHORT).show();
                }
                else
                {
                    dialog();
                }
            }
        });

        btnregis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("taikhoan");
                editor.remove("matkhau");
                editor.remove("name");
                editor.remove("email");
                editor.remove("sdt");
                editor.commit();
                startActivity(new Intent(Users.this, MainActivity.class));
            }
        });

        img_dangy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Users.this, Dang_ky.class));
            }
        });

    }

    private void dialog()
    {
        AlertDialog.Builder dialog = new AlertDialog.Builder(Users.this);
        dialog.setTitle("TH??NG B??O ????NG NH???P");
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setMessage("????NG NH???P TH??NH C??NG!");
        dialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Users.this, MainActivity.class);
                    startActivity(intent);
            }
        });
        dialog.show();
    }


    private void ReadJSON(String url)
    {

        RequestQueue requestQueue = Volley.newRequestQueue(Users.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++)
                        {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                usersArrayList.add(new users(
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
                        Toast.makeText(Users.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }

    private void Controller() {
        edtUsername = findViewById(R.id.editTextUsername);
        img_dangy = findViewById(R.id.img_dangky);
        edtPassword = findViewById(R.id.editTextPassword);
        cbRemember = (CheckBox) findViewById(R.id.checkRemember);
        btnLogIn = (Button) findViewById(R.id.buttonLogIn);
        btnregis = (Button) findViewById(R.id.buttonregistration);
        usersArrayList = new ArrayList<>();
    }
}