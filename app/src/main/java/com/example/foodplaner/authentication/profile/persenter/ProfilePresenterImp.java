package com.example.foodplaner.authentication.profile.persenter;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.foodplaner.authentication.profile.views.ProfileView;
import com.example.foodplaner.models.Meal;
import com.example.foodplaner.models.MealPlanned;
import com.example.foodplaner.models.repository.Repository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProfilePresenterImp implements ProfilePresenter{
    private ProfileView view;
    private Repository repository;
    private FirebaseDatabase root;

    private DatabaseReference meals,planned;
    private String email;
    public ProfilePresenterImp(ProfileView view, Repository repository) {
        this.view = view;
        this.repository = repository;
        root=FirebaseDatabase.getInstance();
        FirebaseAuth m=FirebaseAuth.getInstance();
        email= Objects.requireNonNull(m.getCurrentUser()).getUid();
        meals= root.getReference("meals");
        planned= root.getReference("planned");
    }

    @Override
    public Disposable backupPlanned() {
        Log.i("TAG", "backupPlanned: "+repository);
        return repository.getPlannedMeals().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mealPlanneds -> {
                    mealPlanneds.forEach(mealPlanned -> {
                        planned.child(email).child(mealPlanned.getMeal_num()+"").setValue(mealPlanned);
                    });
                    view.planMealDone();
                },throwable -> {
                    throwable.printStackTrace();
                });

    }

    @Override
    public Disposable backupFav() {
        return repository.getFavMeals().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(favmeals -> {
                    favmeals.forEach(mealPlanned -> {
                        meals.child(email).child(mealPlanned.getId()+"").setValue(mealPlanned);
                    });
                    view.favMealDone();
                },throwable -> {
                    throwable.printStackTrace();
                });
    }

    @Override
    public void syncPlanned() {
        Query allPlanned=planned.child(email);
        allPlanned.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                snapshot.getChildren().forEach(dataSnapshot -> {
                    DataSnapshot meal=dataSnapshot;
                    Log.i("TAG", "onDataChange: "+dataSnapshot.getKey());
                    MealPlanned mealPlanned=new MealPlanned( meal.child("meal_num").getValue(Long.class).intValue(),
                            (String) meal.child("id").getValue(String.class),
                            (String) meal.child("name").getValue(String.class),
                            (String) meal.child("category").getValue(String.class),
                            (String) meal.child("area").getValue(String.class),
                            (String) meal.child("instructions").getValue(String.class),
                            (String) meal.child("img").getValue(String.class),
                            (String) meal.child("tags").getValue(String.class),
                            (String) meal.child("video").getValue(String.class),
                            (String) meal.child("source").getValue(String.class),
                            (String) meal.child("ingredients").getValue(String.class),
                            (String) meal.child("measures").getValue(String.class),
                            new Date((Long) meal.child("date").child("time").getValue(Long.class))
                    );
                    repository.insertPlannedMeal(mealPlanned).subscribeOn(Schedulers.io()).subscribe();
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void syncFav() {
        Query allFav=meals.child(email);
        allFav.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                snapshot.getChildren().forEach(dataSnapshot -> {
                    DataSnapshot meal=dataSnapshot;
                    Meal mealPlanned=new Meal(
                            (String) meal.child("id").getValue(String.class),
                            (String) meal.child("name").getValue(String.class),
                            (String) meal.child("category").getValue(String.class),
                            (String) meal.child("area").getValue(String.class),
                            (String) meal.child("instructions").getValue(String.class),
                            (String) meal.child("img").getValue(String.class),
                            (String) meal.child("tags").getValue(String.class),
                            (String) meal.child("video").getValue(String.class),
                            (String) meal.child("source").getValue(String.class),
                            (String) meal.child("ingredients").getValue(String.class),
                            (String) meal.child("measures").getValue(String.class),
                            false);
                    repository.insertFavMeal(mealPlanned).subscribeOn(Schedulers.io()).subscribe();
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
