package com.infocolmeia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/*
Projeto: PAP – Prova de Aptidão Profissional
Curso: Técnico de Informática – Sistemas 2018/2021
Autor: Rodrigo Pires
Script: Código da classe Login.java
Data: 2 de março de 2021
*/

public class Login extends AppCompatActivity implements View.OnClickListener {

    public static String primNome = "";
    String info = "";
    Button bLogin;
    public static EditText etUtilizador;
    EditText etPassword;
    TextView tvRegistroLink, tvEsquecerPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUtilizador = (EditText) findViewById(R.id.etUtilizadora);
        etPassword = (EditText) findViewById(R.id.etPassword);
        tvRegistroLink = (TextView) findViewById(R.id.tvRegistroLink);
        tvEsquecerPass = (TextView) findViewById(R.id.tvEsquecerPass);
        bLogin = (Button) findViewById(R.id.bLogin);

        bLogin.setOnClickListener(this);
        tvRegistroLink.setOnClickListener(this);
        tvEsquecerPass.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String username, password;

        username = String.valueOf(etUtilizador.getText());
        password = String.valueOf(etPassword.getText());

        switch(v.getId()) {
            case R.id.bLogin:
                if (!username.equals("") && !password.equals("")) {
                    volleyPost();
                } else {
                    Toast.makeText(getApplicationContext(), "Tens que preencher todos os campos.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tvRegistroLink:

                startActivity(new Intent(this, Registo.class));

                break;
        }
    }

    public void volleyPost(){
        String username, password;

        username = String.valueOf(etUtilizador.getText());
        password = String.valueOf(etPassword.getText());

        String postUrl = "https://iot-dc.herokuapp.com/api/login";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject object = new JSONObject();
        try {
            object.put("username", username);
            object.put("password", password);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response);
                try {
                    info = response.getString("message");
                    primNome = response.getString("nome");
                    System.out.println(info +" "+ primNome);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(info.equals("Login com sucesso!")){
                    Toast.makeText(getApplicationContext(), "Login com sucesso!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } else if(info.equals("Password inválida!")){
                    Toast.makeText(getApplicationContext(), "Password errada!", Toast.LENGTH_SHORT).show();
                } else if(info.equals("Utilizador não encontrado")){
                    Toast.makeText(getApplicationContext(), "Utilizador não encontrado!", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(getApplicationContext(), "Erro de ligação á base de dados!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}

