package com.example.foodplaner.search.views;


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
import com.example.foodplaner.models.Displayable;
import com.example.foodplaner.models.IngredientMeal;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private List<Displayable> displayables;
    private Context context;
    private ViewClickHandler clickHandler;

    public SearchAdapter(List<Displayable> displayables, Context context, ViewClickHandler clickHandler) {
        this.displayables = displayables;
        this.context = context;
        this.clickHandler = clickHandler;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.displayable_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Displayable displayable=displayables.get(position);
        holder.title.setText(displayable.getName());
        Glide.with(context).load(displayable.getImg()).placeholder(R.drawable.splash_img).into(holder.img);
        holder.viewBtn.setOnClickListener(v -> {
            if (displayable.getObjectType().equals("Meal"))
                clickHandler.clickView(displayable.getObjectType(),displayable.getId());
            else
                clickHandler.clickView(displayable.getObjectType(), displayable.getName());
        });
    }

    @Override
    public int getItemCount() {
        return displayables.size();
    }

    public void updataData(List<Displayable> displayableList){
        displayables=displayableList;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView title;
        Button viewBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.dis_img_content);
            title=itemView.findViewById(R.id.dis_title_txt);
            viewBtn=itemView.findViewById(R.id.dis_view_recipe);
        }
    }
}
