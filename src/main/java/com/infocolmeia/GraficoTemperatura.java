package com.infocolmeia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import org.json.JSONException;

import java.util.ArrayList;

/*
Projeto: PAP – Prova de Aptidão Profissional
Curso: Técnico de Informática – Sistemas 2018/2021
Autor: Rodrigo Pires
Script: Código da classe GraficoTemperatura.java
Data: 2 de março de 2021
*/

public class GraficoTemperatura extends AppCompatActivity implements View.OnClickListener{

    TextView vlrAlto, vlrBaixo, vlrMedia;
    double alto, baixo, media, total = 0;
    String teste = "";
    Button bVoltar;
    double array[] = new double[7];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafico_temperatura);

        vlrAlto = (TextView) findViewById(R.id.vlrAlto);
        vlrBaixo = (TextView) findViewById(R.id.vlrBaixo);
        vlrMedia = (TextView) findViewById(R.id.vlrMedia);

        bVoltar = (Button) findViewById(R.id.bVoltar);

        BarChart barChart = findViewById(R.id.grafico);

        ArrayList<BarEntry> temperatura = new ArrayList<>();

        FetchDataGraficoTemperatura process = new FetchDataGraficoTemperatura();

        process.execute();

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int length = FetchDataGraficoTemperatura.jsonArray.length();

        teste = String.valueOf(FetchDataGraficoTemperatura.jsonArray);

        System.out.println(teste + " grafico");

        for(int i = 0; i < length; i++){
            try {
                double valor = FetchDataGraficoTemperatura.jsonArray.getDouble(i);
                array[i] = FetchDataGraficoTemperatura.jsonArray.getDouble(i);
                temperatura.add(new BarEntry(i+1, (float) valor));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        for(int i = 0; i < length; i++){
            if(array[i] > alto){
                alto = array[i];
            }
        }

        baixo = array[0];

        for(int i = 0; i < length; i++){
            if(array[i] < baixo){
                baixo = array[i];
            }
        }

        for(int i = 0; i < length; i++){
            total = total + array[i];
        }

        System.out.println(total);

        media = total / length;

        vlrAlto.setText(String.valueOf(alto));
        vlrBaixo.setText(String.valueOf(baixo));
        vlrMedia.setText(String.valueOf(media));

        BarDataSet barDataSet = new BarDataSet(temperatura, "Temperatura");
        barDataSet.setColors(Color.rgb(227, 168, 87));
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        BarData barData = new BarData(barDataSet);

        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("Gráfico");
        barChart.animateY(1200);

        bVoltar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bVoltar:

                startActivity(new Intent(this, EscolherGrafico.class));

                break;
        }
    }
}