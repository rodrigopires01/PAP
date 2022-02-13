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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/*
Projeto: PAP – Prova de Aptidão Profissional
Curso: Técnico de Informática – Sistemas 2018/2021
Autor: Rodrigo Pires
Script: Código da classe MainActivity.java
Data: 2 de março de 2021
*/

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static TextView etIDColmeia, etPeso, etTemp;
    TextView username;
    public static EditText etInput;
    Button bLogout, bRefresh, bGraficos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        etIDColmeia = (TextView) findViewById(R.id.etIDColmeia);
        etPeso = (TextView) findViewById(R.id.etPeso);
        etTemp = (TextView) findViewById(R.id.etTemp);
        username = (TextView) findViewById(R.id.username);
        username.setText(Login.primNome);

        etInput = (EditText) findViewById(R.id.etInput);

        bLogout = (Button) findViewById(R.id.bLogout);
        bRefresh = (Button) findViewById(R.id.bRefresh);
        bGraficos = (Button) findViewById(R.id.bGraficos);

        bLogout.setOnClickListener(this);
        bRefresh.setOnClickListener(this);
        bGraficos.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String input;

        input = String.valueOf(etInput.getText());
        switch(v.getId()){
            case R.id.bLogout:

                startActivity(new Intent(this, Login.class));

            break;

            case R.id.bRefresh:
                if(!input.equals("")){
                    FetchData process = new FetchData();

                    process.execute();
                } 
                else {
                    Toast.makeText(getApplicationContext(), "Tens que indicar um ID.", Toast.LENGTH_SHORT).show();
                }

            break;

            case R.id.bGraficos:
                if(!input.equals("")) {
                    startActivity(new Intent(this, EscolherGrafico.class));
                }
                else {
                    Toast.makeText(getApplicationContext(), "Tens que indicar um ID.", Toast.LENGTH_SHORT).show();
                }
            break;
        }
    }
}