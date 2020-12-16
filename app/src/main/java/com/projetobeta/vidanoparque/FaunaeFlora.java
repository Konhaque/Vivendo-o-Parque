package com.projetobeta.vidanoparque;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.projetobeta.vidanoparque.bd.Fauna;
import com.projetobeta.vidanoparque.bd.Repository;
import com.projetobeta.vidanoparque.generalfunctions.Fullscreen;

import java.util.List;

public class FaunaeFlora extends AppCompatActivity {
    private Toolbar toolbar;
    private LinearLayout linearLayout;
    private FrameLayout btn_fauna;
    private FrameLayout btn_flora;
    private List<Fauna> fauna;
    private List<Fauna> flora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Fullscreen(this);
        setContentView(R.layout.activity_fauna_flora);
        iniciaObjetos();
        setToolbar();
        btn_fauna.setBackground(getDrawable(R.drawable.btn_style));
        setLinearLayout(fauna);
        setBtn_fauna();
        setBtn_flora();
    }

    private void iniciaObjetos(){
        toolbar = (Toolbar) findViewById(R.id.tb);
        linearLayout = (LinearLayout) findViewById(R.id.setfaunaeflora);
        fauna = new Repository(this).getFauna();
        flora = new Repository(this).getFlora();
        btn_fauna = (FrameLayout) findViewById(R.id.fauna);
        btn_flora = (FrameLayout) findViewById(R.id.flora);
    }

    private void setToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }


    private void setBtn_fauna(){
        btn_fauna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_fauna.setBackground(getDrawable(R.drawable.btn_style));
                btn_flora.setBackground(getDrawable(R.drawable.btn_select));
                setLinearLayout(fauna);
            }
        });
    }

    private void setBtn_flora(){
        btn_flora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_flora.setBackground(getDrawable(R.drawable.btn_style));
                btn_fauna.setBackground(getDrawable(R.drawable.btn_select));
                setLinearLayout(flora);
            }
        });
    }


    private void setLinearLayout(final List<Fauna> lista){
        linearLayout.removeAllViews();
        for(int i = 0; i<lista.size(); i++) {
            LinearLayout linearLayout1 = new LinearLayout(this);
            LinearLayout ll = new LinearLayout(this);
            LinearLayout ll2 = new LinearLayout(this);
            ImageView imageView = new ImageView(this);
            TextView textView = new TextView(this);
            TextView textView1 = new TextView(this);
            TextView textView2 = new TextView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(10,10,10,10);
            linearLayout1.setLayoutParams(params);
            linearLayout1.setOrientation(LinearLayout.VERTICAL);
            linearLayout1.setBackground(getDrawable(R.drawable.container_ff));
            final  int ii = i;
            linearLayout1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ampliar(lista.get(ii));
                }
            });
            linearLayout1.setPadding(0,20,0,0);
            imageView.setPadding(10, 10, 10, 10);
            Glide.with(this).load(lista.get(i).getImagem()).into(imageView);
            ll.addView(imageView, 100, 100);
            textView.setPadding(20, 0, 0, 0);
            textView.setTextSize(20f);
            textView.setText(lista.get(i).getNome_popular());
            textView.setTextColor(Color.BLACK);
            ll.setGravity(Gravity.CENTER);
            ll.addView(textView);
            ll2.setOrientation(LinearLayout.VERTICAL);
            ll2.setGravity(Gravity.LEFT);
            textView1.setPadding(10, 10, 10, 10);
            textView1.setTextSize(10f);
            textView1.setText(lista.get(i).getDescricao());
            textView1.setTextColor(Color.BLACK);
            ll2.addView(textView1);
            textView2.setPadding(0, 20, 0, 10);
            textView2.setText("Nome Científico: "+lista.get(i).getNome_cientifico());
            textView2.setTextSize(10f);
            textView2.setTextColor(Color.BLACK);
            textView2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            ll2.addView(textView2);
            linearLayout.setPadding(0, 20, 0, 10);
            linearLayout1.addView(ll);
            linearLayout1.addView(ll2);
            linearLayout.addView(linearLayout1);
        }
    }

    private void ampliar(Fauna fauna){
        AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater =  getLayoutInflater();
        View view = inflater.inflate(R.layout.ampliar,null);
        ImageView imageView = view.findViewById(R.id.imagemampliar);
        TextView nomeCientifico = view.findViewById(R.id.textNomecientifico);
        TextView nomePopular = view.findViewById(R.id.textNomePopular);
        TextView descricao = view.findViewById(R.id.textDescricao);
        Glide.with(this).load(fauna.getImagem()).into(imageView);
        nomeCientifico.setText("Nome Cientifico: "+fauna.getNome_cientifico());
        nomePopular.setText("Nome Popular: "+fauna.getNome_popular());
        descricao.setText(fauna.getDescricao());
        builder.setView(view);
        builder.setCancelable(true);
        dialog = builder.create();
        dialog.show();
    }

}