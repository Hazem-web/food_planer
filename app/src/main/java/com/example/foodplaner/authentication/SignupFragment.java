package com.example.foodplaner.authentication;

import static com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.credentials.Credential;
import androidx.credentials.CredentialManager;
import androidx.credentials.CredentialManagerCallback;
import androidx.credentials.GetCredentialRequest;
import androidx.credentials.GetCredentialResponse;
import androidx.credentials.exceptions.GetCredentialException;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.CancellationSignal;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodplaner.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.identity.googleid.GetGoogleIdOption;
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.concurrent.Executors;


public class SignupFragment extends Fragment {

    private FirebaseAuth mAuth;
    TextInputLayout emailText,passwordText,confPasswordText;
    Button signupBtn,googleBtn;
    TextView loginText;
    private CredentialManager credentialManager;
    private View myView;
    private GoogleSignInClient googleSignInClient;
    private final ActivityResultLauncher<Intent> googleSignInLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == getActivity().RESULT_OK && result.getData() != null) {
                    handleGoogleSignInResult(result.getData());
                } else {

                }
            }
    );
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
        myView=view;
        initializeComponent(view);
        transferLoggedUser(view);
        handleSignupButton(view);
        handleLoginClick(view);
        googleBtn.setOnClickListener(v -> {
            googleHandle();
        });
    }
    private void handleGoogleSignInResult(Intent data){
        GoogleSignIn.getSignedInAccountFromIntent(data).addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                String idToken = task.getResult().getIdToken();
                firebaseAuthWithGoogle(idToken);

            } else {
            }
        });
    }
    private void googleHandle() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();

        // Create the Credential Manager request
        googleSignInClient = GoogleSignIn.getClient(getContext(), gso);
        Intent signInIntent = googleSignInClient.getSignInIntent();
        googleSignInLauncher.launch(signInIntent);
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), task -> {
                    if (task.isSuccessful()) {

                        FirebaseUser user = mAuth.getCurrentUser();
                        saveAccountGoogle(idToken);
                        transferLoggedUser(myView);
                    } else {
                        Toast.makeText(getContext(), "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveAccountGoogle(String token) {
        SharedPreferences sharedPreferences= getActivity().getSharedPreferences(getString(R.string.app_name)+" Preferences",getContext().MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("token", token);
        editor.apply();
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
        credentialManager = CredentialManager.create(getContext());
    }
}