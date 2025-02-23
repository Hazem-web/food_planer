package com.example.foodplaner.meal.views;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplaner.R;
import com.example.foodplaner.home.views.CategoriesAdapter;
import com.example.foodplaner.models.IngredientMeal;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {
    private List<IngredientMeal> ingredients;
    private Context context;

    public IngredientsAdapter(List<IngredientMeal> ingredients, Context context) {
        this.ingredients = ingredients;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.ingredient_list_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        IngredientMeal ingredientMeal=ingredients.get(position);
        holder.ingredient.setText(ingredientMeal.getName());
        holder.measure.setText(ingredientMeal.getMeasure());
        Glide.with(context).load(ingredientMeal.getImg()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView ingredient,measure;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.ing_img);
            ingredient=itemView.findViewById(R.id.ingredient_name_txt);
            measure=itemView.findViewById(R.id.measure_txt);
        }
    }
}
