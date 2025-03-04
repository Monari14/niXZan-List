package com.example.nixzan_list;

import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.List;

public class DadosActivity extends AppCompatActivity {

    private EditText editDescription;
    private Button btAdicionar, btVoltar;
    private DatabaseHelper dbHelper;
    private List<ItemModel> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int theme = ThemeUtils.getTheme(this);
        if (theme == ThemeUtils.THEME_DARK) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        setContentView(R.layout.activity_dados);

        editDescription = findViewById(R.id.editDescription);
        btAdicionar = findViewById(R.id.btAdicionar);
        btVoltar = findViewById(R.id.btVoltar);

        dbHelper = new DatabaseHelper(this);
        itemList = dbHelper.getAllItems();

        btAdicionar.setOnClickListener(v -> {
            String descricao = editDescription.getText().toString();
            boolean checked = false;
            dbHelper.addItem(descricao, checked);
            editDescription.setText("");
            itemList.clear();
            itemList.addAll(dbHelper.getAllItems());
            MyAdapter adapter = new MyAdapter(DadosActivity.this, itemList);
            Intent intent = new Intent(DadosActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
        btVoltar.setOnClickListener(v -> {
            Intent intent = new Intent(DadosActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
