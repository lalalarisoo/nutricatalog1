package com.example.nutricatalog;

import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.nutricatalog.utils.SharedPreferencesHelper;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    private Button btnFavorite;
    private ImageView foodImage;
    private TextView foodNameText, foodCategory, foodType, foodNutrition;

    private String foodName; // untuk disimpan di favorit

    private TextView tvIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Bind views
        btnFavorite = findViewById(R.id.btn_favorite);
        foodImage = findViewById(R.id.detail_image);
        foodNameText = findViewById(R.id.detail_name);
        foodCategory = findViewById(R.id.detail_category);
        foodType = findViewById(R.id.detail_type);
        foodNutrition = findViewById(R.id.detail_nutrition);
        tvIngredients = findViewById(R.id.detail_ingredients);

        // Ambil data dari Intent
        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null) {
            Bundle extras = intent.getExtras();

            foodName = extras.getString("name");
            String category = extras.getString("category");
            String type = extras.getString("type");
            String nutrition = extras.getString("nutrition");
            int imageResId = extras.getInt("imageResId", R.drawable.ic_launcher_background);
            String ingredients = extras.getString("ingredients");

            // Tampilkan data
            foodNameText.setText(foodName);
            foodCategory.setText("Kategori: " + category);
            foodType.setText("Jenis: " + type);
            foodNutrition.setText("Kandungan Gizi: " + nutrition);
            foodImage.setImageResource(imageResId);
            tvIngredients.setText("Bahan: " + ingredients);

            // Cek dan update status favorit
            boolean isFav = SharedPreferencesHelper.isFavorite(this, foodName);
            updateFavoriteButton(isFav);

            // Listener tombol
            btnFavorite.setOnClickListener(v -> {
                boolean currentFav = SharedPreferencesHelper.isFavorite(this, foodName);
                if (currentFav) {
                    SharedPreferencesHelper.removeFavorite(this, foodName);
                    Toast.makeText(this, "Dihapus dari favorit", Toast.LENGTH_SHORT).show();
                } else {
                    SharedPreferencesHelper.addFavorite(this, foodName);
                    Toast.makeText(this, "Ditambahkan ke favorit", Toast.LENGTH_SHORT).show();
                }
                updateFavoriteButton(!currentFav);
            });
        }
    }

    private void updateFavoriteButton(boolean isFavorite) {
        if (isFavorite) {
            btnFavorite.setText("❤️ Favorit Tersimpan");
        } else {
            btnFavorite.setText("🤍 Simpan ke Favorit");
        }
    }
}