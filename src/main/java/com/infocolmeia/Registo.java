package com.infocolmeia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/*
Projeto: PAP – Prova de Aptidão Profissional
Curso: Técnico de Informática – Sistemas 2018/2021
Autor: Rodrigo Pires
Script: Código da classe Registo.java
Data: 2 de março de 2021
*/

public class Registo extends AppCompatActivity implements View.OnClickListener {

    String info = "";
    Button bRegistro;
    EditText etPrimNome, etUltNome, etUsername, etEmail, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registo_temp);

        etPrimNome = (EditText) findViewById(R.id.etPrimNome);
        etUltNome = (EditText) findViewById(R.id.etUltNome);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bRegistro = (Button) findViewById(R.id.bRegistro);

        bRegistro.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //Declaração de variaveis.
        String primNome, ultNome, username, email, password;

        //Iniciar as variaveis.
        primNome = String.valueOf(etPrimNome.getText());
        ultNome = String.valueOf(etUltNome.getText());
        username = String.valueOf(etUsername.getText());
        email = String.valueOf(etEmail.getText());
        password = String.valueOf(etPassword.getText());

        //Fazer um switch case no caso de haver mais botões,para saber qual é o que está a ser clicado. Caso contrário ao clicar em um botão, o IDE intrepetaria como clicar em todos, o que causaria um problema.
        switch (v.getId()) {
            case R.id.bRegistro:
                if(!primNome.equals("") && !ultNome.equals("") && !username.equals("") && !email.equals("") && !password.equals("")) {
                    volleyPost();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Tens que preencher todos os campos.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void volleyPost(){
        String primNome, ultNome, username, email, password;

        primNome = String.valueOf(etPrimNome.getText());
        ultNome = String.valueOf(etUltNome.getText());
        username = String.valueOf(etUsername.getText());
        email = String.valueOf(etEmail.getText());
        password = String.valueOf(etPassword.getText());

        String postUrl = "https://iot-dc.herokuapp.com/api/register";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject object = new JSONObject();
        try {
            object.put("primNome", primNome);
            object.put("ultimoNome", ultNome);
            object.put("username", username);
            object.put("email", email);
            object.put("password", password);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //System.out.println(response);
                try {
                    info = response.getString("message");
                    //System.out.println(info);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(info.equals("Username existente")){
                    Toast.makeText(getApplicationContext(), "Este nome de utilizador já está registado!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Conta criada com sucesso!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), Login.class);
                    startActivity(intent);
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