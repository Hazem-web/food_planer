package com.example.foodplaner;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SplashFragment extends Fragment {

    private Runnable runnable;
    private Handler handler=new Handler();
    public SplashFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        runnable=new Runnable() {
            @Override
            public void run() {
                if(getActivity().getSharedPreferences("user",getContext().MODE_PRIVATE)==null)
                    Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_loginFragment);
                else
                    Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_homeFragment);
            }
        };

        handler.postDelayed(runnable,3000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}