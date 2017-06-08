package com.example.aluno.login;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gabi2.login.R;

import java.util.HashMap;
import java.util.Map;

import static com.example.gabi2.login.R.id.textView;

public class Users extends Usuarios {
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private TextView textView8;
    private TextView textView4;
    private TextView textView5;
    private TextView textView7;
    String id;
    private static final String URL = "http://switchautos.com.br/visualiza_mobile_user.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.users);

        textView1 = (TextView) findViewById(R.id.textView1);
        textView2=(TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);
        textView4 = (TextView) findViewById(R.id.textView4);
        textView5=(TextView) findViewById(R.id.textView5);
        textView7=(TextView) findViewById(R.id.textView8);
        Intent intent = getIntent();

        id = intent.getStringExtra(Usuarios.KEY_ID);
        textView4.setText("Usuario de id: " + id);
        SendData();
    }
    public void SendData()
    {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String[] array = response.split(";");
                        System.out.println(response);
                        textView5.setText("Nome do usuario: " + array[0]);
                        textView7.setText("E-mail do usuário " + array[1]);
                        textView1.setText("Sobrenome: " + array[2]);
                        textView2.setText("Telefone: " + array[3]);
                        textView3.setText("Perfil: " + array[4]);
                        Toast.makeText(Users.this,response,Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Users.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("id",id);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
