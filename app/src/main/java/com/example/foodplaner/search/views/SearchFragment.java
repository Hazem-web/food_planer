package com.example.foodplaner.search.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.foodplaner.R;
import com.example.foodplaner.database.MealsLocalDataSourceImp;
import com.example.foodplaner.models.Displayable;
import com.example.foodplaner.models.repository.RepositoryImp;
import com.example.foodplaner.network.MealsRemoteDataSourceImp;
import com.example.foodplaner.search.presenter.SearchPresenter;
import com.example.foodplaner.search.presenter.SearchPresenterImp;
import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchFragment extends Fragment implements SearchView,ViewClickHandler{

    private Chip mealChip,categoryChip,countryChip,ingChip;
    private RecyclerView recyclerView;
    private ConstraintLayout content,noInternet;
    private Button reloadBtn;
    private List<Displayable> displayables=new ArrayList<>();
    private TextInputLayout searchText;
    private String type="Category";
    private View myView;
    private Disposable textChangeDisposable,dataDisposable;
    private SearchPresenter presenter;
    private SearchAdapter adapter;
    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter=new SearchPresenterImp(this, RepositoryImp.getInstance(MealsRemoteDataSourceImp.getInstance(), MealsLocalDataSourceImp.getInstance(getContext())));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myView=view;
        initialize();
        searchHandle();
        filterChipHandle();
        chipHandle();
    }

    private void filterChipHandle() {
        countryChip.setOnClickListener(v -> {
            type="Area";
            chipHandle();
        });
        categoryChip.setOnClickListener(v -> {
            type="Category";
            chipHandle();
        });
        ingChip.setOnClickListener(v -> {
            type="Ingredient";
            chipHandle();
        });
        mealChip.setOnClickListener(v -> {
            type="Meal";
            searchText.getEditText().setText("");
            displayables=new ArrayList<>();
            setData(displayables);
        });
    }

    private void chipHandle() {
        searchText.getEditText().setText("");
        displayables=new ArrayList<>();
        dataDisposable=presenter.getAllOf(type);
        setData(displayables);
    }

    private void searchHandle() {
        Observable textChange=Observable.create(emitter -> {
            searchText.getEditText().addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    emitter.onNext(s.toString().toLowerCase().trim());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }).debounce(2000, TimeUnit.MILLISECONDS);
        textChangeDisposable= textChange.subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe(o -> {
            filterData(o);
        });
    }

    private void filterData(Object o) {
        if (type.equals("Meal")){
            if ((o +"").isBlank())
                setData(new ArrayList<>());
            else
                dataDisposable=presenter.getMealByName(o +"");
        }
        else {
            if (type.equals("Ingredient") && (o +"").isBlank())
                setData(new ArrayList<>());
            else{

                setData(displayables.stream().filter(x->{
                    return x.getName().toLowerCase().contains(o +"");
                }).collect(Collectors.toList()));
            }
        }
    }

    private void initialize() {
        mealChip=myView.findViewById(R.id.meal_chip);
        ingChip=myView.findViewById(R.id.ingredients_chip);
        content=myView.findViewById(R.id.content);
        noInternet=myView.findViewById(R.id.no_internet);
        reloadBtn=myView.findViewById(R.id.reload_btn);
        categoryChip=myView.findViewById(R.id.cat_chip);
        countryChip=myView.findViewById(R.id.country_chip);
        searchText=myView.findViewById(R.id.search_text);
        recyclerView=myView.findViewById(R.id.search_result_rec);
        adapter=new SearchAdapter(displayables,getContext(),this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        reloadBtn.setOnClickListener(v -> {
            content.setVisibility(View.VISIBLE);
            noInternet.setVisibility(View.GONE);
            type="Category";
            chipHandle();
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        textChangeDisposable.dispose();
    }

    @Override
    public void showData(List<Displayable> data) {
        dataDisposable.dispose();
        displayables=data;
        if(!type.equals("Ingredient")){
            setData(data);
        }

    }

    @Override
    public void showError(String error) {
        content.setVisibility(View.GONE);
        noInternet.setVisibility(View.VISIBLE);
    }

    private void setData(List<Displayable> data){

        adapter.updataData(data);
    }

    @Override
    public void clickView(String type, String getBy) {
        if (type.equals("Meal")){
            SearchFragmentDirections.ActionSearchFragmentToMealFragment toMealFragment=
                    SearchFragmentDirections.actionSearchFragmentToMealFragment(getBy);
            Navigation.findNavController(myView).navigate(toMealFragment);
        }
        else {
            SearchFragmentDirections.ActionSearchFragmentToSearchResultFragment toSearchResultFragment=
                    SearchFragmentDirections.actionSearchFragmentToSearchResultFragment(getBy,type);
            Navigation.findNavController(myView).navigate(toSearchResultFragment);
        }
    }
}