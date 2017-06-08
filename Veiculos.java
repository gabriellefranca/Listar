package com.example.aluno.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
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

public class Veiculos extends AppCompatActivity {
    String[] items;

    ArrayList<String> listItems;

    ArrayAdapter<String> adapter;
    public static final String JSON_URL = "http://switchautos.com.br/Get.php";
    public static final String KEY_ID_VEICULO = "id_veiculo";
    ListView veiculos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.veiculos);
        veiculos = (ListView) findViewById(R.id.veiculos);
        sendRequest();
        veiculos.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // ListView Clicked item index
                int itemPosition = position;

                // ListView Clicked item value
                String  itemValue = (String) veiculos.getItemAtPosition(position);
                Intent detalhes = new Intent(Veiculos.this, Veiculoinfo.class);
                detalhes.putExtra(KEY_ID_VEICULO, itemValue);
                startActivity(detalhes);

                // Show Alert

                //    Toast.makeText(getApplicationContext(), "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG).show();

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
                        Toast.makeText(Veiculos.this,error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void showJSON(String json){
        ParseJson_veiculos pj = new ParseJson_veiculos(json);
        pj.parseJSON();
        CustomList_veiculos cl = new CustomList_veiculos(this, ParseJson_veiculos.id_veiculos,ParseJson_veiculos.nome_veiculos,ParseJson_veiculos.imagem_veiculos, ParseJson_veiculos.preco_veiculos);
        veiculos.setAdapter(cl);
    }
}
