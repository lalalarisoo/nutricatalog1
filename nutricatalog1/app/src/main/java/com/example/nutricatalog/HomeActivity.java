package com.example.nutricatalog;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Button;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nutricatalog.adapter.FoodAdapter;
import com.example.nutricatalog.model.FoodItem;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SearchView searchView;
    private Spinner spinnerCategory, spinnerNutrition;
    private List<FoodItem> foodList;
    private FoodAdapter adapter;

    private EditText searchIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.rv_food_list);
        searchView = findViewById(R.id.search_food);
        spinnerCategory = findViewById(R.id.spinner_category);
        spinnerNutrition = findViewById(R.id.spinner_nutrition);
        searchIngredients = findViewById(R.id.search_ingredients);

        Button btnAbout = findViewById(R.id.btn_about);
        btnAbout.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, AboutActivity.class);
            startActivity(intent);
        });

        // Data makanan dummy
        foodList = generateDummyData();

        // Inisialisasi adapter dan layout
        adapter = new FoodAdapter(this, foodList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Setup Spinner kategori
        ArrayAdapter<CharSequence> catAdapter = ArrayAdapter.createFromResource(this,
                R.array.category_array, android.R.layout.simple_spinner_item);
        catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(catAdapter);

        // Setup Spinner kandungan gizi
        ArrayAdapter<CharSequence> nutAdapter = ArrayAdapter.createFromResource(this,
                R.array.nutrition_array, android.R.layout.simple_spinner_item);
        nutAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNutrition.setAdapter(nutAdapter);

        // Trigger filter saat spinner berubah
        AdapterView.OnItemSelectedListener filterListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        };

        spinnerCategory.setOnItemSelectedListener(filterListener);
        spinnerNutrition.setOnItemSelectedListener(filterListener);

        // Filter saat teks di search berubah
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override public boolean onQueryTextSubmit(String query) { return false; }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterData();
                return true;
            }
        });

        searchIngredients.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterData(); // update list saat teks berubah
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    // Filter berdasarkan kategori, gizi, dan pencarian
    private void filterData() {
        String selectedCategory = spinnerCategory.getSelectedItem().toString();
        String selectedNutrition = spinnerNutrition.getSelectedItem().toString();
        String queryName = searchView.getQuery().toString().toLowerCase();
        String queryIngredients = searchIngredients.getText().toString().toLowerCase();

        List<FoodItem> filtered = new ArrayList<>();

        for (FoodItem item : foodList) {
            String name = item.getName() != null ? item.getName().toLowerCase() : "";
            boolean matchName = name.contains(queryName);

            String ingredients = item.getIngredients() != null ? item.getIngredients().toLowerCase() : "";
            boolean matchIngredients = ingredients.contains(queryIngredients);

            String category = item.getCategory() != null ? item.getCategory() : "";
            boolean matchCategory = selectedCategory.equals("Semua Kategori") || category.equalsIgnoreCase(selectedCategory);

            String nutrition = item.getNutrition() != null ? item.getNutrition().toLowerCase() : "";
            boolean matchNutrition = selectedNutrition.equals("Semua Gizi") || nutrition.contains(selectedNutrition.toLowerCase());

            if (matchCategory && matchNutrition && matchName && matchIngredients) {
                filtered.add(item);
            }
        }

        adapter = new FoodAdapter(this, filtered);
        recyclerView.setAdapter(adapter);
    }

    // Dummy data untuk testing
    private List<FoodItem> generateDummyData() {
        List<FoodItem> list = new ArrayList<>();
        list.add(new FoodItem("Bubur Ayam", "Anak-anak", "Ringan", "Tinggi Karbohidrat", R.drawable.bubur_ayam));
        list.add(new FoodItem("Salmon Panggang", "Orang Dewasa", "Berat", "Tinggi Protein", R.drawable.salmon));
        list.add(new FoodItem("Smoothie Pisang", "Anak dengan Stunting", "Cemilan", "Tinggi Kalori & Vitamin", R.drawable.smoothie));

        list.add(new FoodItem(
                "Sop Ayam",
                "Dewasa",
                "Berprotein tinggi",
                "Berkuah",
                "Daging ayam, wortel, kentang, bawang putih, garam",
                R.drawable.sop_ayam
        ));

        list.add(new FoodItem(
                "Bubur Bayi Sehat",
                "Anak-anak",
                "Karbohidrat, Serat",
                "Lembut",
                "Beras merah, brokoli, wortel, susu",
                R.drawable.bubur_bayi
        ));
        // Tambahkan data lainnya jika perlu
        return list;
    }
}
