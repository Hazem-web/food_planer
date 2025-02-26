package com.example.foodplaner.calendar.views;

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
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.foodplaner.R;
import com.example.foodplaner.calendar.presenter.PlannedPresenter;
import com.example.foodplaner.calendar.presenter.PlannedPresenterImp;
import com.example.foodplaner.database.MealsLocalDataSourceImp;
import com.example.foodplaner.models.MealPlanned;
import com.example.foodplaner.models.repository.RepositoryImp;
import com.example.foodplaner.network.MealsRemoteDataSourceImp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import io.reactivex.rxjava3.disposables.Disposable;

public class CalendarFragment extends Fragment implements com.example.foodplaner.calendar.views.CalendarView,CalendarHandler {

    private CalendarView calendarView;
    private RecyclerView recyclerView;
    private FirebaseUser currentUser;
    private View myView;
    private MealsAdapter adapter;
    private PlannedPresenter presenter;
    Disposable btnDisposable,showDisposable;

    private ConstraintLayout content,notAuth;
    private Button sign;

    public CalendarFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter=new PlannedPresenterImp(this, RepositoryImp.getInstance(MealsRemoteDataSourceImp.getInstance(), MealsLocalDataSourceImp.getInstance(getContext())));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendar, container, false);
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
            Navigation.findNavController(myView).navigate(R.id.action_calendarFragment_to_loginFragment);
        });
    }
    private void initialize() {
        content=myView.findViewById(R.id.content);
        notAuth=myView.findViewById(R.id.not_author);
        sign=myView.findViewById(R.id.sign_error_btn);
        calendarView=myView.findViewById(R.id.calendarView);
        recyclerView=myView.findViewById(R.id.plan_rec);
    }

    private void handleUser() {
        content.setVisibility(View.VISIBLE);
        notAuth.setVisibility(View.GONE);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        adapter=new MealsAdapter(getContext(),new ArrayList<>(),this);
        recyclerView.setAdapter(adapter);
        long millisInDay = 60 * 60 * 24 * 1000;
        long currentTime = new Date().getTime();
        long dateOnly = (currentTime / millisInDay) * millisInDay;
        Date clearDate = new Date(dateOnly);
        showDisposable=presenter.getPlanned(clearDate);
        calendarView.setOnDateChangeListener((viewer, year, month, dayOfMonth) -> {
            showDisposable.dispose();
            showDisposable=presenter.getPlanned(new GregorianCalendar(year, month, dayOfMonth).getTime());
        });
    }

    @Override
    public void showData(List<MealPlanned> meals) {
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
        CalendarFragmentDirections.ActionCalendarFragmentToLocalMealFragment toLocalMealFragment=
                CalendarFragmentDirections.actionCalendarFragmentToLocalMealFragment(id,"planned");
        Navigation.findNavController(myView).navigate(toLocalMealFragment);
    }

    @Override
    public void removeFromPlanned(MealPlanned planned) {
        btnDisposable=presenter.removeFromPlanned(planned);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (showDisposable!=null)
            showDisposable.dispose();
    }
}