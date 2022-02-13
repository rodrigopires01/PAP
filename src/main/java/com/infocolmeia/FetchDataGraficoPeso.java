package com.infocolmeia;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/*
Projeto: PAP – Prova de Aptidão Profissional
Curso: Técnico de Informática – Sistemas 2018/2021
Autor: Rodrigo Pires
Script: Código da classe FetchDataGraficoPeso.java
Data: 2 de março de 2021
*/

public class FetchDataGraficoPeso extends AsyncTask<Void, Void, Void> {

    public static JSONArray jsonArray;
    String info = "";
    String line = "";
    String id;

    @Override
    protected Void doInBackground(Void... voids) {

        id = String.valueOf(MainActivity.etInput.getText());

        try {
            URL url = new URL("https://iot-dc.herokuapp.com/api/stats/" + id);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            InputStream inputStream = httpURLConnection.getInputStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            while(line != null){
                line = bufferedReader.readLine();
                info = info + line;
            }

            JSONObject object = new JSONObject(info);

            jsonArray = object.getJSONArray("pesoStat");

        }  catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}