package com.example.foodplaner.calendar.views;

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
import com.example.foodplaner.favorites.views.FavoritesHandler;
import com.example.foodplaner.models.CountryParser;
import com.example.foodplaner.models.Meal;
import com.example.foodplaner.models.MealPlanned;
import com.example.foodplaner.models.Urls;

import java.util.List;

public class MealsAdapter extends RecyclerView.Adapter<MealsAdapter.ViewHolder>  {
    private final Context context;
    private List<MealPlanned> meals;
    private CalendarHandler pageHandler;

    public MealsAdapter(Context context, List<MealPlanned> meals, CalendarHandler pageHandler) {
        this.context = context;
        this.meals = meals;
        this.pageHandler = pageHandler;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.planned_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MealPlanned meal=meals.get(position);
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

    private void favHandle(@NonNull ViewHolder holder, MealPlanned meal) {
        holder.fav.setOnClickListener(v -> {
            pageHandler.removeFromPlanned(meal);
        });
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public void showData(List<MealPlanned> meals){
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
            fav=itemView.findViewById(R.id.close_icon);
            viewBtn=itemView.findViewById(R.id.view_recipe);
        }
    }
}
