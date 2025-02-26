package com.example.foodplaner.authentication.profile.views;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodplaner.R;
import com.example.foodplaner.authentication.profile.persenter.ProfilePresenter;
import com.example.foodplaner.authentication.profile.persenter.ProfilePresenterImp;
import com.example.foodplaner.database.MealsLocalDataSourceImp;
import com.example.foodplaner.models.repository.RepositoryImp;
import com.example.foodplaner.network.MealsRemoteDataSourceImp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileFragment extends Fragment implements ProfileView{
    private View myView;
    private Button sync,logout,backup,sign;
    private TextView email;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private ProfilePresenter presenter;
    private ConstraintLayout content,notAuth;

    public ProfileFragment() {
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
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myView=view;
        initialize();
        if (currentUser!=null){
            handleUser();
        }
        else {
            handleNotUser();
        }

    }
    private void handleNotUser() {
        content.setVisibility(View.GONE);
        notAuth.setVisibility(View.VISIBLE);
        sign.setOnClickListener(v -> {
            Navigation.findNavController(myView).navigate(R.id.action_profileFragment_to_loginFragment);
        });
    }

    private void handleUser() {
        content.setVisibility(View.VISIBLE);
        notAuth.setVisibility(View.GONE);
        presenter=new ProfilePresenterImp(this, RepositoryImp.getInstance(MealsRemoteDataSourceImp.getInstance(), MealsLocalDataSourceImp.getInstance(getContext())));
        email.setText(currentUser.getEmail());
        logout.setOnClickListener(v -> {
            SharedPreferences sharedPreferences= getActivity().getSharedPreferences(getString(R.string.app_name)+" Preferences",getContext().MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.clear();
            editor.apply();
            mAuth.signOut();
            Navigation.findNavController(myView).navigate(R.id.action_profileFragment_to_loginFragment);
        });
        backup.setOnClickListener(v -> {
            presenter.backupPlanned();
            presenter.backupFav();
        });
        sync.setOnClickListener(v -> {
            presenter.syncFav();
            presenter.syncPlanned();
        });

    }

    private void initialize() {
        content=myView.findViewById(R.id.content);
        notAuth=myView.findViewById(R.id.not_author);
        sign=myView.findViewById(R.id.sign_error_btn);
        logout=myView.findViewById(R.id.logout_btn);
        sync=myView.findViewById(R.id.sync_btn);
        backup=myView.findViewById(R.id.backup_btn);
        email=myView.findViewById(R.id.email_account_txt);
        mAuth=FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
    }

    @Override
    public void planMealDone() {
        Toast.makeText(getContext(), "Planned done", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void favMealDone() {
        Toast.makeText(getContext(), "fav done", Toast.LENGTH_SHORT).show();

    }
}