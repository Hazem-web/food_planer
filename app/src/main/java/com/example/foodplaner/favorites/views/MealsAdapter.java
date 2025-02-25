package com.example.foodplaner.favorites.views;

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
import com.example.foodplaner.home.views.MealView;
import com.example.foodplaner.models.CountryParser;
import com.example.foodplaner.models.Meal;
import com.example.foodplaner.models.Urls;
import com.example.foodplaner.models.repository.RepositoryImp;
import com.example.foodplaner.network.MealsRemoteDataSourceImp;
import com.example.foodplaner.searchresult.views.SearchResultHandler;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import io.reactivex.rxjava3.disposables.Disposable;

public class MealsAdapter extends RecyclerView.Adapter<MealsAdapter.ViewHolder>  {
    private final Context context;
    private List<Meal> meals;
    private FavoritesHandler pageHandler;

    public MealsAdapter(Context context, List<Meal> meals, FavoritesHandler pageHandler) {
        this.context = context;
        this.meals = meals;
        this.pageHandler = pageHandler;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.full_meal_list_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Meal meal=meals.get(position);
        holder.name.setText(meal.getName());
        holder.category.setText(meal.getCategory());
        holder.country.setText(meal.getArea());
        Glide.with(context).load(Urls.FLAGS_URL+ CountryParser.getCode(meal.getArea())+".png").into(holder.countryImg);
        Glide.with(context).load(meal.getImg()).placeholder(R.drawable.splash_img).into(holder.img);
        favHandle(holder, meal);
        holder.viewBtn.setOnClickListener(v -> {
            pageHandler.viewRecipe(meal.getId());
        });
    }

    private void favHandle(@NonNull ViewHolder holder, Meal meal) {
        holder.fav.setImageResource(R.drawable.favorite);
        holder.fav.setOnClickListener(v -> {
            pageHandler.removeFromFav(meal);
        });
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public void showData(List<Meal> meals){
        this.meals=meals;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView name,country,category;
        ImageView img,countryImg,fav;
        Button viewBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.title_txt);
            country=itemView.findViewById(R.id.country_full_txt);
            category=itemView.findViewById(R.id.cat_full_txt);
            img=itemView.findViewById(R.id.img_content);
            countryImg=itemView.findViewById(R.id.country_full_img);
            fav=itemView.findViewById(R.id.fav_icon);
            viewBtn=itemView.findViewById(R.id.view_recipe);
        }
    }
}
