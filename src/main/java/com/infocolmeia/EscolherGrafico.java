package com.infocolmeia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/*
Projeto: PAP – Prova de Aptidão Profissional
Curso: Técnico de Informática – Sistemas 2018/2021
Autor: Rodrigo Pires
Script: Código da classe EscolherGrafico.java
Data: 2 de março de 2021
*/

public class EscolherGrafico extends AppCompatActivity implements View.OnClickListener{

    Button bTemperatura7Dias, bPeso7Dias, bTemperatura30Dias, bPeso30Dias, bVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolher_grafico);

        bTemperatura7Dias = (Button) findViewById(R.id.bTemperatura7Dias);
        bPeso7Dias = (Button) findViewById(R.id.bPeso7Dias);
        bTemperatura30Dias = (Button) findViewById(R.id.bTemperatura30Dias);
        bPeso30Dias = (Button) findViewById(R.id.bPeso30Dias);
        bVoltar = (Button) findViewById(R.id.bVoltar);

        bTemperatura7Dias.setOnClickListener(this);
        bPeso7Dias.setOnClickListener(this);
        bTemperatura30Dias.setOnClickListener(this);
        bPeso30Dias.setOnClickListener(this);
        bVoltar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.bTemperatura7Dias:

                startActivity(new Intent(this, GraficoTemperatura.class));

            break;

            case R.id.bPeso7Dias:

                startActivity(new Intent(this, GraficoPeso.class));

            break;

            case R.id.bTemperatura30Dias:
            case R.id.bPeso30Dias:
            case R.id.bVoltar:

                startActivity(new Intent(this, MainActivity.class));

            break;
        }
    }
}