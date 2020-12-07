package com.projetobeta.vidanoparque.generalfunctions;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class AbreTela {

    public AbreTela(@NonNull FragmentManager manager, @NonNull Fragment fragment, int id){
        manager.beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(id,fragment).commit();
    }

}
