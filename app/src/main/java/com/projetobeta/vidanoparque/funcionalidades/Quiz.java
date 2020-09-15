package com.projetobeta.vidanoparque.funcionalidades;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.projetobeta.vidanoparque.R;

public class Quiz extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.quiz,container,false);
        return viewGroup;
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
