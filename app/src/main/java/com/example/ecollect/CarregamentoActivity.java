package com.example.ecollect;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;

public class CarregamentoActivity extends AppCompatActivity {

    BlurView blurView;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carregamento);

        blurView = findViewById(R.id.blurView);
        blurBackground();
    }

    private void blurBackground(){
        float radius = 15f;

        View decorView = getWindow().getDecorView();

        ViewGroup rootView = (ViewGroup) decorView.findViewById(android.R.id.content);

        Drawable windowsBackground = decorView.getBackground();

        blurView.setupWith(rootView)
                .setFrameClearDrawable(windowsBackground)
                .setBlurAlgorithm(new RenderScriptBlur(this))
                .setBlurRadius(radius)
                .setBlurAutoUpdate(true)
                .setHasFixedTransformationMatrix(true);
    }
}