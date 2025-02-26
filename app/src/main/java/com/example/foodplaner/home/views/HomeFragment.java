package com.example.foodplaner.home.views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.foodplaner.R;
import com.example.foodplaner.database.MealsLocalDataSourceImp;
import com.example.foodplaner.home.presenter.HomePresenter;
import com.example.foodplaner.home.presenter.HomePresenterImp;
import com.example.foodplaner.models.Category;
import com.example.foodplaner.models.CountryParser;
import com.example.foodplaner.models.Meal;
import com.example.foodplaner.models.Urls;
import com.example.foodplaner.models.repository.RepositoryImp;
import com.example.foodplaner.network.MealsRemoteDataSourceImp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.disposables.Disposable;

public class HomeFragment extends Fragment implements HomeView,HomePageHandler {
    private Button dailyBtn,reloadBtn;
    private TextView dailyName,dailyCategory,dailyCountry;
    private ImageView dailyImg,dailyCountryImg,dailyFavImg;
    private ConstraintLayout content,noInternet;
    private RecyclerView catRec,suggRec;
    private CategoriesAdapter categoriesAdapter;
    private SuggestedMealsAdapter suggestedMealsAdapter;
    private HomePresenter presenter;
    private View myView;
    private static Meal myMeal=null;
    private FirebaseUser currentUser;
    Disposable catDisposable,suggDisposable,dailyDisposable,favDisposable,btnDisposable;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter=new HomePresenterImp(this, RepositoryImp.getInstance(MealsRemoteDataSourceImp.getInstance(), MealsLocalDataSourceImp.getInstance(getContext())));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myView=view;
        initializeComponent();
        setupAdapters();
        start();

    }

    private void start() {
        content.setVisibility(View.VISIBLE);
        noInternet.setVisibility(View.GONE);
        callData();
    }

    private void callData() {
        if(myMeal==null) {
            dailyDisposable = presenter.getDayMeal();
        }
        else {
            displayMeal(myMeal);
        }
        suggDisposable= presenter.getSuggested();
        catDisposable=presenter.getCategories();
    }

    private void setupAdapters() {
        suggestedMealsAdapter=new SuggestedMealsAdapter(getContext(),new ArrayList<>(),this);
        categoriesAdapter=new CategoriesAdapter(getContext(),new ArrayList<>(),this);
        catRec.setAdapter(categoriesAdapter);
        suggRec.setAdapter(suggestedMealsAdapter);
    }

    private void initializeComponent() {
        content=myView.findViewById(R.id.content);
        noInternet=myView.findViewById(R.id.no_internet);
        reloadBtn=myView.findViewById(R.id.reload_btn);
        dailyBtn=myView.findViewById(R.id.daily_btn);
        dailyCategory=myView.findViewById(R.id.cat_daily_txt);
        dailyCountry=myView.findViewById(R.id.contry_daily_txt);
        dailyName=myView.findViewById(R.id.daily_name_txt);
        dailyCountryImg=myView.findViewById(R.id.country_daily_img);
        dailyFavImg=myView.findViewById(R.id.fav_daily);
        dailyImg=myView.findViewById(R.id.daily_img);
        catRec=myView.findViewById(R.id.cat_rec);
        suggRec=myView.findViewById(R.id.sugg_rec);
        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        reloadBtn.setOnClickListener(v -> {
            start();
        });
    }

    @Override
    public void showDailyMeal(Meal meal) {
        dailyDisposable.dispose();
        displayMeal(meal);
    }

    private void displayMeal(Meal meal) {
        myMeal= meal;
        dailyCategory.setText(meal.getCategory());
        Glide.with(myView).load(meal.getImg()).placeholder(R.drawable.splash_img).into(dailyImg);
        Glide.with(myView).load(Urls.FLAGS_URL+CountryParser.getCode(meal.getArea())+".png").into(dailyCountryImg);
        dailyName.setText(meal.getName());
        dailyCountry.setText(meal.getArea());
        dailyBtn.setOnClickListener(v -> {
            viewRecipe(myMeal.getId());
        });
        userHandle(meal);
    }

    private void userHandle(Meal meal) {
        if (currentUser!=null) {
            dailyFavImg.setOnClickListener(v -> {
                if (myMeal.isFav()) {
                    btnDisposable = presenter.removeDayFromFav(myMeal);
                } else {
                    btnDisposable = presenter.addDayToFav(myMeal);
                }
            });

            favDisposable = presenter.showDayFav(meal.getId());
        }else {
            dailyFavImg.setOnClickListener(v -> {
                goLogin();
            });
        }
    }

    private void goLogin() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.ask_login);
        builder.setPositiveButton(R.string.sign_in, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
               Navigation.findNavController(myView).navigate(R.id.action_homeFragment_to_loginFragment);
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
    public void showSuggestedMeals(List<Meal> meals) {
        suggDisposable.dispose();
        if (meals==null){
           suggDisposable= presenter.getSuggested();
        }
        else {
            suggestedMealsAdapter.setMeals(meals);
        }
    }

    @Override
    public void showFavDaily() {
        if (favDisposable!=null)
            favDisposable.dispose();
        if (btnDisposable!=null)
            btnDisposable.dispose();
        myMeal.setFav(true);
        dailyFavImg.setImageResource(R.drawable.favorite);
    }

    @Override
    public void showNotFavDaily() {
        if (favDisposable!=null)
            favDisposable.dispose();
        if (btnDisposable!=null)
            btnDisposable.dispose();
        myMeal.setFav(false);
        dailyFavImg.setImageResource(R.drawable.favorite_border);
    }

    @Override
    public void showCategories(List<Category> categories) {
        catDisposable.dispose();
        categoriesAdapter.setCategories(categories);
    }

    @Override
    public void showError(String error) {
        if (error.isEmpty()){
            suggDisposable.dispose();
            suggDisposable= presenter.getSuggested();
        }
        content.setVisibility(View.GONE);
        noInternet.setVisibility(View.VISIBLE);
    }

    @Override
    public void viewRecipe(String id) {
        HomeFragmentDirections.ActionHomeFragmentToMealFragment toMealFragment=
                HomeFragmentDirections.actionHomeFragmentToMealFragment(id);
        Navigation.findNavController(myView).navigate(toMealFragment);
    }

    @Override
    public void notAuthor() {
        goLogin();
    }

    @Override
    public void showCategory(String name) {
        HomeFragmentDirections.ActionHomeFragmentToSearchResultFragment toSearchResultFragment=
                HomeFragmentDirections.actionHomeFragmentToSearchResultFragment(name,"Category");
        Navigation.findNavController(myView).navigate(toSearchResultFragment);
    }

    @Override
    public void handleError(String error) {
        content.setVisibility(View.GONE);
        noInternet.setVisibility(View.VISIBLE);
    }
}