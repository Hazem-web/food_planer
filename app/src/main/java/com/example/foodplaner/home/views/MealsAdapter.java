package com.example.foodplaner.home.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplaner.R;
import com.example.foodplaner.database.MealsLocalDataSourceImp;
import com.example.foodplaner.home.presenter.MealsPresenter;
import com.example.foodplaner.home.presenter.MealsPresenterImp;
import com.example.foodplaner.models.Meal;
import com.example.foodplaner.models.repository.RepositoryImp;
import com.example.foodplaner.network.MealsRemoteDataSourceImp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import io.reactivex.rxjava3.disposables.Disposable;

public class MealsAdapter extends RecyclerView.Adapter<MealsAdapter.ViewHolder> implements MealView {
    private final Context context;
    private List<Meal> meals;
    private final FirebaseUser currentUser;
    private final MealsPresenter presenter;
    private Disposable btnDisposable;
    private Disposable[] showDisposable;

    private HomePageHandler pageHandler;

    public MealsAdapter(Context context, List<Meal> meals,HomePageHandler pageHandler) {
        this.context = context;
        this.meals = meals;
        this.pageHandler=pageHandler;
        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        currentUser= mAuth.getCurrentUser();
        presenter=new MealsPresenterImp(this, RepositoryImp.getInstance(MealsRemoteDataSourceImp.getInstance(), MealsLocalDataSourceImp.getInstance(context)));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.meal_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Meal meal=meals.get(position);
        holder.name.setText(meal.getName());
        Glide.with(context).load(meal.getImg()).placeholder(R.drawable.splash_img).into(holder.img);
        checkUser(holder, position, meal);
        holder.viewBtn.setOnClickListener(v -> {
            pageHandler.viewRecipe(meal.getId());
        });
    }

    private void checkUser(@NonNull ViewHolder holder, int position, Meal meal) {
        if (currentUser != null) {
            if (showDisposable[position]==null) {
                showDisposable[position] = presenter.showFav(meal.getId(), position);
            }
            else {
                showDisposable[position].dispose();
            }
            checkFav(holder, position, meal);

        }
    }

    private void checkFav(@NonNull ViewHolder holder, int position, Meal meal) {
        if(meal.isFav()) {
            favHandle(holder, position, meal);
        }
        else {
            notFavHandle(holder, position, meal);
        }
    }

    private void notFavHandle(@NonNull ViewHolder holder, int position, Meal meal) {
        holder.fav.setImageResource(R.drawable.favorite_border);
        holder.fav.setOnClickListener(v -> {
            btnDisposable= presenter.addToFav(meal, position);
        });
    }

    private void favHandle(@NonNull ViewHolder holder, int position, Meal meal) {
        holder.fav.setImageResource(R.drawable.favorite);
        holder.fav.setOnClickListener(v -> {
            btnDisposable= presenter.removeFromFav(meal, position);
        });
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public void showData(List<Meal> meals){
        this.meals=meals;
        showDisposable=new Disposable[meals.size()];
        notifyDataSetChanged();
    }

    @Override
    public void showFav(int position) {
        dispose();
        meals.get(position).setFav(true);
        notifyItemChanged(position);
    }

    private void dispose() {
        if (btnDisposable != null) {
            btnDisposable.dispose();
        }
    }

    @Override
    public void showNotFav(int position) {
        dispose();
        meals.get(position).setFav(false);
        notifyItemChanged(position);
    }

    @Override
    public void showError(String msg) {
        pageHandler.handleError(msg);
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img,fav;
        TextView name;
        Button viewBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.img_content);
            fav=itemView.findViewById(R.id.fav_icon);
            name=itemView.findViewById(R.id.title_txt);
            viewBtn=itemView.findViewById(R.id.view_recipe);
        }
    }
}
