package com.example.foodplaner.favorites.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.foodplaner.R;
import com.example.foodplaner.database.MealsLocalDataSourceImp;
import com.example.foodplaner.favorites.presenter.FavoritesPresenter;
import com.example.foodplaner.favorites.presenter.FavoritesPresenterImp;
import com.example.foodplaner.models.Meal;
import com.example.foodplaner.models.repository.RepositoryImp;
import com.example.foodplaner.network.MealsRemoteDataSourceImp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.disposables.Disposable;

public class FavoritesFragment extends Fragment implements FavoritesView,FavoritesHandler{

    private RecyclerView recyclerView;
    private FirebaseUser currentUser;
    private View myView;
    private MealsAdapter adapter;
    private FavoritesPresenter presenter;
    Disposable btnDisposable,showDisposable;
    private ConstraintLayout content,notAuth;
    private Button sign;
    public FavoritesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter=new FavoritesPresenterImp(this, RepositoryImp.getInstance(MealsRemoteDataSourceImp.getInstance(), MealsLocalDataSourceImp.getInstance(getContext())));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myView=view;
        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        currentUser= mAuth.getCurrentUser();
        initialize();
        if (currentUser != null) {
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
            Navigation.findNavController(myView).navigate(R.id.action_favoritesFragment_to_loginFragment);
        });
    }

    private void initialize() {
        content=myView.findViewById(R.id.content);
        notAuth=myView.findViewById(R.id.not_author);
        sign=myView.findViewById(R.id.sign_error_btn);
        recyclerView=myView.findViewById(R.id.fav_rec);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));

    }

    private void handleUser() {
        content.setVisibility(View.VISIBLE);
        notAuth.setVisibility(View.GONE);
        adapter=new MealsAdapter(getContext(),new ArrayList<>(),this);
        recyclerView.setAdapter(adapter);
        showDisposable=presenter.getFav();
    }

    @Override
    public void showData(List<Meal> meals) {
        adapter.showData(meals);
    }

    @Override
    public void mealRemoved() {
        if (btnDisposable != null) {
            btnDisposable.dispose();
        }
        Toast.makeText(getContext(), "Meal removed successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void viewRecipe(String id) {
        FavoritesFragmentDirections.ActionFavoritesFragmentToLocalMealFragment toMealFragment=
                FavoritesFragmentDirections.actionFavoritesFragmentToLocalMealFragment(id,"fav");
        Navigation.findNavController(myView).navigate(toMealFragment);
    }

    @Override
    public void removeFromFav(Meal meal) {
        btnDisposable=presenter.removeFav(meal);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (showDisposable!=null)
            showDisposable.dispose();
    }
}