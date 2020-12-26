package com.projetobeta.vidanoparque;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.projetobeta.vidanoparque.generalfunctions.Fullscreen;
import com.projetobeta.vidanoparque.generalfunctions.SlidePager;
import com.projetobeta.vidanoparque.parques.Parque_Pituacu;
import com.projetobeta.vidanoparque.parques.Texto_Introducao;

import java.util.ArrayList;
import java.util.List;

public class Escolher_Parque extends AppCompatActivity {
    private ViewPager pager;
    private PagerAdapter adapter;
    private List<Fragment> screens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Fullscreen(this);
        setContentView(R.layout.activity_escolher__parque);
        iniciaObjetos();
        nextScreen();
    }

    private void iniciaObjetos(){
       screens = new ArrayList<>();
       screens.add(new Texto_Introducao());
       screens.add(new Parque_Pituacu());
       pager = (ViewPager) findViewById(R.id.vpage);
       adapter = new SlidePager(getSupportFragmentManager(),screens);
    }

    private void nextScreen(){
        pager.setAdapter(adapter);
        guide(screens.size(),0);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                guide(screens.size(),position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void guide(int tam, int pos){
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.botoes);
        linearLayout.removeAllViews();
        for (int i = 0; i<tam; i++){
            TextView textView = new TextView(this);
            textView.setText(getText(R.string.bolinhas));
            textView.setTextSize(35f);
            if(i == pos) textView.setTextColor(Color.parseColor("#20bf6b"));
            else textView.setTextColor(Color.parseColor("#3a5f8b"));
            linearLayout.addView(textView);
        }
    }

}