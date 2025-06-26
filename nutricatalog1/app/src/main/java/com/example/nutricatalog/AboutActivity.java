package com.example.nutricatalog;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;
public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TextView tujuan = findViewById(R.id.about_purpose);
        TextView tim = findViewById(R.id.about_team);

        tujuan.setText("Aplikasi Nutricatalog bertujuan untuk membantu masyarakat dalam memilih makanan sehat yang sesuai dengan kebutuhan gizi berdasarkan kategori usia dan kondisi kesehatan tertentu seperti anak stunting, lansia, atau atlet.");

        tim.setText(
                "Tim Pengembang:\n" +
                        "1. Muhammad Zuhri - 2210501046\n" +
                        "2. Idelia Larisa - 2310501087\n" +
                        "3. Choirunnisa Balqis Wahida Zahra - 2210501101\n" +
                        "4. Theodora Rustiati Masta Putri - 2310501110\n" +
                        "5. Saskia Dwi Putriyanti - 2310501111"
        );
    }
}

