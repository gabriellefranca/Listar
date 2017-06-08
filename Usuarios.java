package com.example.aluno.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gabi2.login.R;

import java.util.ArrayList;


public class Usuarios extends Activity {
    String[] items;
    public static final String KEY_ID = "id_usuario";
    ArrayList<String> listItems;

    ArrayAdapter<String> adapter;
    public static final String JSON_URL = "http://switchautos.com.br/getAllEmp.php";
    ListView listView;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuarios);
        listView = (ListView) findViewById(R.id.listView);
        sendRequest();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // ListView Clicked item index
                int itemPosition = position;

                // ListView Clicked item value
                String  itemValue = (String) listView.getItemAtPosition(position);
                Intent detalhes = new Intent(Usuarios.this, Users.class);
                detalhes.putExtra(KEY_ID, itemValue);
                startActivity(detalhes);

                // Show Alert
             //   Toast.makeText(getApplicationContext(), "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG).show();

            }
        });

    }

    private void sendRequest(){

        StringRequest stringRequest = new StringRequest(JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        showJSON(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Usuarios.this,error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void showJSON(String json){
        ParseJSON pj = new ParseJSON(json);
        pj.parseJSON();
        CustomList cl = new CustomList(this, ParseJSON.ids,ParseJSON.names,ParseJSON.emails);
        listView.setAdapter(cl);
    }



}