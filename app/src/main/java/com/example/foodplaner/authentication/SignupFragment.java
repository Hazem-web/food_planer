package com.example.foodplaner.authentication;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

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


public class SignupFragment extends Fragment {

    private FirebaseAuth mAuth;
    TextInputLayout emailText,passwordText,confPasswordText;
    Button signupBtn,googleBtn;
    TextView loginText;

    public SignupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeComponent(view);
        transferLoggedUser(view);
        handleSignupButton(view);
        handleLoginClick(view);
        googleBtn.setOnClickListener(v -> {
        });
    }

    private void handleLoginClick(@NonNull View view) {
        loginText.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_signupFragment_to_loginFragment);
        });
    }

    private void handleSignupButton(@NonNull View view) {
        signupBtn.setOnClickListener(v -> {
            boolean valid=true;
            if (!emailText.getEditText().getText().toString().trim().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")){
                emailText.setError(getString(R.string.email_not_correct));
                valid=false;
            }
            if (!passwordText.getEditText().getText().toString().trim().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$")){
                passwordText.setError(getString(R.string.valid_password));
                valid=false;
            }
            if (!confPasswordText.getEditText().getText().toString().trim().equals(passwordText.getEditText().getText().toString())){
                confPasswordText.setError(getString(R.string.conf_password_not_equal));
                valid=false;
            }
            if (valid){
                signup(emailText.getEditText().getText().toString().trim(),passwordText.getEditText().getText().toString().trim(), view);
            }
        });
    }

    private void signup(String email,String password,View view) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            saveAccountInfo(email, password);
                            transferLoggedUser(view);
                        } else {
                            Toast.makeText(getContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void saveAccountInfo(String email, String password) {
        SharedPreferences sharedPreferences= getActivity().getSharedPreferences(getString(R.string.app_name)+" Preferences",getContext().MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("email", email);
        editor.putString("password", password);
        editor.apply();
    }

    private void transferLoggedUser(@NonNull View view) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Navigation.findNavController(view).navigate(R.id.action_signupFragment_to_homeFragment);
        }
    }

    private void initializeComponent(@NonNull View view) {
        emailText= view.findViewById(R.id.email_txt);
        passwordText= view.findViewById(R.id.password_txt);
        confPasswordText= view.findViewById(R.id.conf_password_txt);
        googleBtn=view.findViewById(R.id.google_btn);
        signupBtn= view.findViewById(R.id.signup_btn);
        loginText= view.findViewById(R.id.login_txt);
        mAuth = FirebaseAuth.getInstance();
    }
}