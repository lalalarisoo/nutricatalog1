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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.rv_food_list);
        searchView = findViewById(R.id.search_food);
        spinnerCategory = findViewById(R.id.spinner_category);
        spinnerNutrition = findViewById(R.id.spinner_nutrition);

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
    }

    // Filter berdasarkan kategori, gizi, dan pencarian
    private void filterData() {
        String selectedCategory = spinnerCategory.getSelectedItem().toString();
        String selectedNutrition = spinnerNutrition.getSelectedItem().toString();
        String queryName = searchView.getQuery().toString().toLowerCase();

        List<FoodItem> filtered = new ArrayList<>();

        for (FoodItem item : foodList) {
            String name = item.getName() != null ? item.getName().toLowerCase() : "";
            boolean matchName = name.contains(queryName);

            String category = item.getCategory() != null ? item.getCategory() : "";
            boolean matchCategory = selectedCategory.equals("Semua Kategori") || category.equalsIgnoreCase(selectedCategory);

            String nutrition = item.getNutrition() != null ? item.getNutrition().toLowerCase() : "";
            boolean matchNutrition = selectedNutrition.equals("Semua Gizi") || nutrition.contains(selectedNutrition.toLowerCase());

            if (matchCategory && matchNutrition && matchName) {
                filtered.add(item);
            }
        }

        adapter = new FoodAdapter(this, filtered);
        recyclerView.setAdapter(adapter);
    }

    // Dummy data untuk testing
    private List<FoodItem> generateDummyData() {
        List<FoodItem> list = new ArrayList<>();
        list.add(new FoodItem("Salmon Panggang", "Orang Dewasa", "Berat", "Tinggi Protein",  "Lumuri salmon dengan lemon, garam, dan lada. Panggang 180°C selama 20 menit.", R.drawable.salmon));
        list.add(new FoodItem("Smoothie Pisang", "Anak dengan Stunting", "Cemilan", "Tinggi Kalori & Vitamin", "Blender pisang, susu full cream, dan madu hingga halus.", R.drawable.smoothie));
        list.add(new FoodItem("Oatmeal Pisang", "Lansia", "Ringan", "Tinggi Serat", "Rebus oatmeal dengan air, tambahkan pisang iris dan madu.", R.drawable.oatmeal));
        list.add(new FoodItem("Ayam Rebus", "Atlet / Aktif", "Berat", "Tinggi Protein",  "Rebus dada ayam dengan bawang putih dan daun salam selama 30 menit.", R.drawable.ayam_rebus));
        list.add(new FoodItem("Salad Sayuran", "Orang Dewasa", "Ringan", "Rendah Lemak", "Campurkan selada, tomat, wortel, dan dressing rendah lemak.", R.drawable.salad));
        list.add(new FoodItem("Sup Ikan", "Anak-anak", "Berat", "Rendah Lemak", "Masak ikan dengan wortel, kentang, bawang putih dan seledri dalam kaldu ikan." , R.drawable.sup_ikan));
        list.add(new FoodItem("Susu Kedelai", "Lansia", "Cemilan", "Tinggi Protein", "Rendam kedelai semalaman, blender dan saring, lalu rebus dengan daun pandan." , R.drawable.susu_kedelai));
        list.add(new FoodItem("Telur Rebus", "Anak dengan Stunting", "Cemilan", "Tinggi Protein",  "Rebus telur selama 10 menit, kupas dan sajikan." , R.drawable.telur_rebus));
        list.add(new FoodItem("Kentang Panggang", "Atlet / Aktif", "Cemilan", "Tinggi Kalori", "Potong kentang, lumuri minyak zaitun dan panggang 200°C selama 30 menit." , R.drawable.kentang));
        list.add(new FoodItem("Pisang Kukus", "Anak-anak", "Cemilan", "Tinggi Kalori",  "Kupas pisang, kukus selama 10-15 menit sampai empuk.", R.drawable.pisang_kukus));
        list.add(new FoodItem("Roti Gandum", "Orang Dewasa", "Cemilan", "Tinggi Serat",  "Panggang roti gandum, sajikan dengan selai kacang atau alpukat." , R.drawable.roti_gandum));
        list.add(new FoodItem("Yogurt Rendah Lemak", "Orang Dewasa", "Cemilan", "Rendah Lemak", "Sajikan yogurt rendah lemak dingin, bisa ditambah potongan buah." , R.drawable.yogurt));

        return list;
    }
}
