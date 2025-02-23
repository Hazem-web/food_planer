package com.example.foodplaner.home.views;

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
import com.example.foodplaner.database.MealsLocalDataSourceImp;
import com.example.foodplaner.home.presenter.CategoryPresenter;
import com.example.foodplaner.home.presenter.CategoryPresenterImp;
import com.example.foodplaner.models.Category;
import com.example.foodplaner.models.Meal;
import com.example.foodplaner.models.repository.RepositoryImp;
import com.example.foodplaner.network.MealsRemoteDataSourceImp;

import java.util.ArrayList;
import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> implements CategoryView {
    private Context context;
    private List<Category> categories;
    private MealsAdapter[] adapters;
    private CategoryPresenter presenter;
    private HomePageHandler pageHandler;

    public CategoriesAdapter(Context context, List<Category> categories, HomePageHandler pageHandler) {
        this.context = context;
        this.categories = categories;
        presenter=new CategoryPresenterImp(this, RepositoryImp.getInstance(MealsRemoteDataSourceImp.getInstance(), MealsLocalDataSourceImp.getInstance(context)));
        this.pageHandler = pageHandler;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.card_category_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category category=categories.get(position);
        holder.categoryTxt.setText(category.getName());
        Glide.with(context).load(category.getImg()).into(holder.categoryImg);
        if (adapters[position] == null) {
            adapters[position]=new MealsAdapter(context,new ArrayList<>(),pageHandler);
        }
        holder.mealsRec.setAdapter(adapters[position]);
        presenter.getData(category.getName(),position);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void setCategories(List<Category> categories){
        this.categories=categories;
        adapters=new MealsAdapter[categories.size()];
        notifyDataSetChanged();
    }

    @Override
    public void showData(List<Meal> meals, int position) {
        adapters[position].showData(meals);
        notifyItemChanged(position);
    }

    @Override
    public void showError(String error) {

    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView categoryTxt;
        ImageView categoryImg;
        RecyclerView mealsRec;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryTxt=itemView.findViewById(R.id.category_txt);
            categoryImg=itemView.findViewById(R.id.cat_img);
            mealsRec=itemView.findViewById(R.id.meal_cat_rec);
        }
    }
}
