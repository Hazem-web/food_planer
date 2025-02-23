package com.example.foodplaner.meal.views;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.foodplaner.R;
import com.example.foodplaner.database.MealsLocalDataSourceImp;
import com.example.foodplaner.meal.presenter.MealPresenter;
import com.example.foodplaner.meal.presenter.MealPresenterImp;
import com.example.foodplaner.models.CountryParser;
import com.example.foodplaner.models.IngredientMeal;
import com.example.foodplaner.models.Meal;
import com.example.foodplaner.models.MealPlanned;
import com.example.foodplaner.models.Urls;
import com.example.foodplaner.models.repository.RepositoryImp;
import com.example.foodplaner.network.MealsRemoteDataSourceImp;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.disposables.Disposable;

public class MealFragment extends Fragment implements MealView{

    private ImageView back,plan,fullImg,countryImg;
    private TextView title,countryText,categoryText,instructions,videoText;
    private ChipGroup tags;
    private RecyclerView ingredients;
    private FloatingActionButton favFAB;
    private WebView video;
    private FirebaseUser currentUser;
    private Disposable show,favBtn,favShow,planAdd;
    private MealPresenter presenter;
    private View myView;
    private Meal myMeal;
    public MealFragment() {
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
        return inflater.inflate(R.layout.fragment_meal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myView=view;
        String id=MealFragmentArgs.fromBundle(getArguments()).getId();
        initialize();
        presenter=new MealPresenterImp(this, RepositoryImp.getInstance(MealsRemoteDataSourceImp.getInstance(), MealsLocalDataSourceImp.getInstance(getContext())));
        show=presenter.showData(id);
        back.setOnClickListener(v -> {
            Navigation.findNavController(myView).navigateUp();
        });

    }

    private void initialize() {
        back=myView.findViewById(R.id.back_btn);
        plan=myView.findViewById(R.id.calendar_btn);
        fullImg=myView.findViewById(R.id.thump_img);
        countryImg=myView.findViewById(R.id.area_icon);
        title=myView.findViewById(R.id.title_meal_txt);
        countryText=myView.findViewById(R.id.area_meal_txt);
        categoryText=myView.findViewById(R.id.cat_meal_txt);
        instructions=myView.findViewById(R.id.instarction_txt);
        videoText=myView.findViewById(R.id.video);
        tags=myView.findViewById(R.id.tags_cont);
        ingredients=myView.findViewById(R.id.ing_list);
        favFAB =myView.findViewById(R.id.fab_fav_btn);
        video=myView.findViewById(R.id.video_soruce);
        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
    }

    @Override
    public void showFav() {
        favDispose();
        favFAB.setImageResource(R.drawable.favorite);
    }

    private void favDispose() {
        if (favBtn != null) {
            favBtn.dispose();
        }
        if (favShow != null) {
            favShow.dispose();
        }
    }

    @Override
    public void showNotFav() {
        favDispose();
        favFAB.setImageResource(R.drawable.favorite_border);
    }

    @Override
    public void showData(Meal meal) {
        myMeal=meal;
        showMeal();
    }

    private void showMeal() {
        title.setText(myMeal.getName());
        countryText.setText(myMeal.getArea());
        categoryText.setText(myMeal.getCategory());
        instructions.setText(myMeal.getInstructions());
        Glide.with(getContext()).load(myMeal.getImg()).placeholder(R.drawable.splash_img).into(fullImg);
        Glide.with(getContext()).load(Urls.FLAGS_URL+ CountryParser.getCode(myMeal.getArea())+".png").into(countryImg);
        setIngredients();
        checkVideo();
        userHandle();
        setTags();

    }

    private void setTags() {
        List<String> tagsString=List.of(myMeal.getTags().split(","));
        tagsString.forEach(s -> {
            Chip chip=new Chip(getContext());
            chip.setText(s);
            chip.getShapeAppearanceModel().withCornerSize(20);
            tags.addView(chip);
        });
    }

    private void userHandle() {
        if (currentUser != null) {
            favShow=presenter.showFav(myMeal.getId());
            favFAB.setOnClickListener(v -> {
                if (myMeal.isFav()){
                    favBtn=presenter.removeFromFav(myMeal);
                }
                else {
                    favBtn=presenter.addToFav(myMeal);
                }
            });
            savePlannedMeal();
        }
    }

    private void savePlannedMeal() {
        plan.setOnClickListener(v -> {
            Calendar calendar=Calendar.getInstance();
            DatePickerDialog dialog=new DatePickerDialog(getContext());
            dialog.updateDate(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
            dialog.getDatePicker().setMinDate(calendar.getTimeInMillis()+ TimeUnit.MILLISECONDS.convert(12, TimeUnit.HOURS));
            dialog.setOnDateSetListener((view, year, month, dayOfMonth) -> {
                MealPlanned mealPlanned=new MealPlanned(myMeal);
                mealPlanned.setDate(new GregorianCalendar(year, month, dayOfMonth).getTime());
                planAdd=presenter.addToPlanned(mealPlanned);

            });
            dialog.show();
        });
    }

    private void checkVideo() {
        if(myMeal.getVideo().isEmpty()){
            hideVideo(View.GONE);
        }
        else {
            displayVideo();
        }
    }

    private void hideVideo(int gone) {
        videoText.setVisibility(gone);
        video.setVisibility(gone);
    }

    private void displayVideo() {
        videoText.setVisibility(View.VISIBLE);
        video.setVisibility(View.VISIBLE);
        String link="<iframe width=\"100%\" height=\"100%\" src=\""+myMeal.getVideo().replace("watch?v=","embed/")+"\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" referrerpolicy=\"strict-origin-when-cross-origin\" allowfullscreen></iframe>";
        video.loadData(link,"text/html","utf-8");
        video.getSettings().setJavaScriptEnabled(true);
        video.setWebChromeClient(new WebChromeClient());
    }

    private void setIngredients() {
        List<IngredientMeal> ingredientMeals=new ArrayList<>();
        String[] ings=myMeal.getIngredients().split(",");
        String[] measrs=myMeal.getMeasures().split(",");
        for (int i = 0; i < ings.length; i++) {
            ingredientMeals.add(new IngredientMeal(ings[i],measrs[i]));
        }
        IngredientsAdapter adapter=new IngredientsAdapter(ingredientMeals,getContext());
        ingredients.setAdapter(adapter);
    }

    @Override
    public void addToPlanned() {
        planAdd.dispose();
        Toast.makeText(getContext(), "Meal has been added successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void onStop() {
        super.onStop();
        show.dispose();
    }
}