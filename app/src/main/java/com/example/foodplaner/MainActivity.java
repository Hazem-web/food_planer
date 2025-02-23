package com.example.foodplaner;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentContainerView;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    NavHostFragment hostFragment;
    BottomNavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        navigationView=findViewById(R.id.bottomNavigationView);
        hostFragment= (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        if (hostFragment != null) {
            NavController navController=hostFragment.getNavController();
            NavigationUI.setupWithNavController(navigationView,navController);
            navController.addOnDestinationChangedListener((navController1, navDestination, bundle) -> {
                if(navDestination.getId()==R.id.splashFragment || navDestination.getId()==R.id.loginFragment || navDestination.getId()==R.id.signupFragment){
                    navigationView.setVisibility(BottomNavigationView.GONE);
                }
                else {
                    navigationView.setVisibility(BottomNavigationView.VISIBLE);
                }
            });
        }
    }
}