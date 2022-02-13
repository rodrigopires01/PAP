package com.infocolmeia;

import android.os.AsyncTask;
import android.widget.Toast;

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
Script: Código da classe FetchData.java
Data: 2 de março de 2021
*/


public class FetchData extends AsyncTask<Void, Void, Void> {

    String info = "", id, id_colmeia, peso, temp;

    @Override
    protected Void doInBackground(Void... voids) {
        id = String.valueOf(MainActivity.etInput.getText());

        try {
            URL url = new URL("https://iot-dc.herokuapp.com/api/data/" + id);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            InputStream inputStream = httpURLConnection.getInputStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line = "";

            while(line != null){
                line = bufferedReader.readLine();
                info = info + line;
            }

            JSONObject object = new JSONObject(info);

            System.out.println(object);

            id_colmeia = object.getString("IDcolmeia");
            temp = object.getString("temperatura");
            peso = object.getString("peso");


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        MainActivity.etIDColmeia.setText(id_colmeia);
        MainActivity.etPeso.setText(peso + " KG");
        MainActivity.etTemp.setText(temp + " ºC");
    }
}