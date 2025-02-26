package com.example.foodplaner.searchresult.views;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodplaner.R;
import com.example.foodplaner.database.MealsLocalDataSourceImp;
import com.example.foodplaner.models.Meal;
import com.example.foodplaner.models.repository.RepositoryImp;
import com.example.foodplaner.network.MealsRemoteDataSourceImp;
import com.example.foodplaner.searchresult.presenter.SearchResultPresenter;
import com.example.foodplaner.searchresult.presenter.SearchResultPresenterImp;

import java.util.List;

import io.reactivex.rxjava3.disposables.Disposable;


public class SearchResultFragment extends Fragment implements SearchResultView,SearchResultHandler{
    private RecyclerView recyclerView;
    private TextView typeText;
    private ImageView back;
    private SearchResultPresenter presenter;
    private View myView;
    private Disposable show;
    private String type,name;
    private Button reloadBtn;
    private ConstraintLayout noInternet;

    public SearchResultFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter=new SearchResultPresenterImp(this, RepositoryImp.getInstance(MealsRemoteDataSourceImp.getInstance(), MealsLocalDataSourceImp.getInstance(getContext())));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myView=view;
        initialize();
        typeText.setText(name);
        reload();
        back.setOnClickListener(v -> {
            Navigation.findNavController(myView).navigateUp();
        });
    }

    private void reload() {
        recyclerView.setVisibility(View.VISIBLE);
        noInternet.setVisibility(View.GONE);
        show=presenter.getData(type,name);
    }

    private void initialize() {
        recyclerView=myView.findViewById(R.id.search_res_rec);
        typeText=myView.findViewById(R.id.search_type_res_txt);
        noInternet=myView.findViewById(R.id.no_internet);
        reloadBtn=myView.findViewById(R.id.reload_btn);
        back=myView.findViewById(R.id.back_btn);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        type= SearchResultFragmentArgs.fromBundle(getArguments()).getType();
        name=SearchResultFragmentArgs.fromBundle(getArguments()).getName();
        reloadBtn.setOnClickListener(v -> {
            reload();
        });
    }

    @Override
    public void viewRecipe(String id) {
        SearchResultFragmentDirections.ActionSearchResultFragmentToMealFragment toMealFragment=
                SearchResultFragmentDirections.actionSearchResultFragmentToMealFragment(id);
        Navigation.findNavController(myView).navigate(toMealFragment);
    }

    @Override
    public void notAuthorizedUser() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.ask_login);
        builder.setPositiveButton(R.string.sign_in, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Navigation.findNavController(myView).navigate(R.id.action_mealFragment_to_loginFragment);
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancels the dialog.
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void handleError(String error) {
        recyclerView.setVisibility(View.GONE);
        noInternet.setVisibility(View.VISIBLE);
    }

    @Override
    public void showData(List<Meal> meals) {
        MealsAdapter adapter=new MealsAdapter(getContext(),meals,this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showError(String error) {
        recyclerView.setVisibility(View.GONE);
        noInternet.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStop() {
        super.onStop();
        show.dispose();
    }
}