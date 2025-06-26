package com.example.nutricatalog.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.nutricatalog.DetailActivity;
import com.example.nutricatalog.R;
import com.example.nutricatalog.model.FoodItem;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private Context context;
    private List<FoodItem> foodList;

    public FoodAdapter(Context context, List<FoodItem> foodList) {
        this.context = context;
        this.foodList = foodList;
    }

    @Override
    public FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_food, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FoodViewHolder holder, int position) {
        FoodItem item = foodList.get(position);
        holder.foodName.setText(item.getName());
        holder.foodCategory.setText("Kategori: " + item.getCategory());
        holder.foodImage.setImageResource(item.getImageResId());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("name", item.getName());
            intent.putExtra("category", item.getCategory());
            intent.putExtra("type", item.getType());
            intent.putExtra("nutrition", item.getNutrition());
            intent.putExtra("recipe", item.getRecipe());
            intent.putExtra("imageResId", item.getImageResId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder {
        TextView foodName, foodCategory;
        ImageView foodImage;

        public FoodViewHolder(View itemView) {
            super(itemView);
            foodName = itemView.findViewById(R.id.food_name);
            foodCategory = itemView.findViewById(R.id.food_category);
            foodImage = itemView.findViewById(R.id.food_image);
        }
    }
}
