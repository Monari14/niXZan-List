package com.example.nixzan_list;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btAdicionar;
    private ListView listaT;
    private DatabaseHelper dbHelper;
    private MyAdapter adapter;
    private List<ItemModel> itemList;
    private Switch themeSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int theme = ThemeUtils.getTheme(this);
        if (theme == ThemeUtils.THEME_DARK) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        setContentView(R.layout.activity_main);

        btAdicionar = findViewById(R.id.btAdicionarLista);
        listaT = findViewById(R.id.listaT);
        themeSwitch = findViewById(R.id.themeSwitch);

        dbHelper = new DatabaseHelper(this);
        itemList = dbHelper.getAllItems();
        adapter = new MyAdapter(this, itemList);
        listaT.setAdapter(adapter);

        themeSwitch.setChecked(theme == ThemeUtils.THEME_DARK);
        themeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                ThemeUtils.setTheme(MainActivity.this, ThemeUtils.THEME_DARK);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                ThemeUtils.setTheme(MainActivity.this, ThemeUtils.THEME_LIGHT);
            }
        });

        btAdicionar.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DadosActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
