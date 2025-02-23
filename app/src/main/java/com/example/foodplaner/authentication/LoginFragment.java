package com.example.foodplaner.authentication;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodplaner.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginFragment extends Fragment {

    private FirebaseAuth mAuth;
    TextInputLayout emailText,passwordText;
    Button loginBtn,guestBtn,googleBtn;
    TextView signupText;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeComponent(view);
        guestHandler(view);
        signupNav(view);
        googleBtn.setOnClickListener(v -> {

        });
        if(!transferLoggedUser(view)) {
            getAccountInfo(view);
            handleLoginButton(view);
        }
    }

    private void signupNav(@NonNull View view) {
        signupText.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_signupFragment);
        });
    }

    private void guestHandler(@NonNull View view) {
        guestBtn.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_homeFragment);
        });
    }

    private void handleLoginButton(@NonNull View view) {
        loginBtn.setOnClickListener(v -> {
            boolean valid=true;
            if (!emailText.getEditText().getText().toString().trim().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")){
                emailText.setError(getString(R.string.email_not_correct));
                valid=false;
            }
            if (!passwordText.getEditText().getText().toString().trim().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$")){
                passwordText.setError(getString(R.string.valid_password));
                valid=false;
            }
            if (valid){
                loginHandler(emailText.getEditText().getText().toString().trim(),passwordText.getEditText().getText().toString().trim(), view);
            }
        });
    }

    @SuppressLint("SuspiciousIndentation")
    private void loginHandler(String email, String password, View view) {
        if(!email.equals("")||!password.equals(""))
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            saveAccountInfo(email,password);
                            transferLoggedUser(view);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private boolean transferLoggedUser(@NonNull View view) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Handler handler=new Handler();
            Runnable runnable=new Runnable() {
                @Override
                public void run() {
                    Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_homeFragment);
                }
            };
            handler.postDelayed(runnable,1000);
            return true;
        }
        return false;
    }

    private void getAccountInfo(View view) {
        SharedPreferences sharedPreferences= getActivity().getSharedPreferences(getString(R.string.app_name)+" Preferences",getContext().MODE_PRIVATE);
        String email=sharedPreferences.getString("email","");
        String password=sharedPreferences.getString("password","");
    }

    private void saveAccountInfo(String email, String password) {
        SharedPreferences sharedPreferences= getActivity().getSharedPreferences(getString(R.string.app_name)+" Preferences",getContext().MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("email", email);
        editor.putString("password", password);
        editor.apply();
    }

    private void initializeComponent(@NonNull View view) {
        emailText= view.findViewById(R.id.email_txt);
        passwordText= view.findViewById(R.id.password_txt);
        googleBtn=view.findViewById(R.id.google_btn);
        guestBtn=view.findViewById(R.id.guest_btn);
        loginBtn= view.findViewById(R.id.sign_in_btn);
        signupText= view.findViewById(R.id.signup_txt);
        mAuth = FirebaseAuth.getInstance();
    }
}