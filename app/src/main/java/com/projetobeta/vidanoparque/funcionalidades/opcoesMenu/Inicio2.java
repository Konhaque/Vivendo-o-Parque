package com.projetobeta.vidanoparque.funcionalidades.opcoesMenu;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.projetobeta.vidanoparque.R;
import com.projetobeta.vidanoparque.generalfunctions.SlidePager;

import java.util.ArrayList;
import java.util.List;

public class Inicio2 extends Fragment {
    private ViewPager pager;
    private PagerAdapter adapter;
    private List<Fragment> menu;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       return inflater.inflate(R.layout.inicio_2,container,false);
    }

    @Override
    public void onStart() {
        iniciarObjetos();
        nextScreen();
        super.onStart();
    }

    private void iniciarObjetos(){
        menu = new ArrayList<>();
        menu.add(new Sobre());
        menu.add(new Fauna_Flora());
        pager = (ViewPager) getActivity().findViewById(R.id.vpage);
        adapter = new SlidePager(getActivity().getSupportFragmentManager(),menu);
    }
    private void nextScreen(){
        pager.setAdapter(adapter);
        guide(menu.size(),0);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                guide(menu.size(),position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void guide(int tam, int pos){
        LinearLayout linearLayout = (LinearLayout) getActivity().findViewById(R.id.botoes);
        linearLayout.removeAllViews();
        for (int i = 0; i<tam; i++){
            TextView textView = new TextView(getContext());
            textView.setText(getText(R.string.bolinhas));
            textView.setTextSize(35f);
            if(i == pos) textView.setTextColor(Color.parseColor("#20bf6b"));
            else textView.setTextColor(Color.parseColor("#3a5f8b"));
            linearLayout.addView(textView);
        }
    }
}
