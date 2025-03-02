package com.example.nixzan_list;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView textName;
    private EditText editDescription;
    private Button btAdicionar;
    private CheckBox marcado;
    private ListView listaT;
    private DatabaseHelper dbHelper;
    private MyAdapter adapter;
    private ArrayList<ItemModel> itemList;
    private Switch themeSwitch;  // Switch para alternar os temas

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Verificar a preferência de tema e aplicar
        int theme = ThemeUtils.getTheme(this);
        if (theme == ThemeUtils.THEME_DARK) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        setContentView(R.layout.activity_main);

        // Inicializando as views
        textName = findViewById(R.id.textName);
        editDescription = findViewById(R.id.editDescription);
        btAdicionar = findViewById(R.id.btAdicionar);
        marcado = findViewById(R.id.marcado);  // Referência ao CheckBox
        listaT = findViewById(R.id.listaT);
        themeSwitch = findViewById(R.id.themeSwitch); // Switch para alternar temas

        // Inicializando o banco de dados e a lista
        dbHelper = new DatabaseHelper(this);
        itemList = dbHelper.getAllItems();
        adapter = new MyAdapter(this, itemList);
        listaT.setAdapter(adapter);

        // Configurar o Switch para o tema atual
        themeSwitch.setChecked(theme == ThemeUtils.THEME_DARK);
        themeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                ThemeUtils.setTheme(MainActivity.this, ThemeUtils.THEME_DARK);  // Salvar a preferência
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                ThemeUtils.setTheme(MainActivity.this, ThemeUtils.THEME_LIGHT);  // Salvar a preferência
            }
        });

        // Adicionar item ao banco de dados quando o botão for clicado
        btAdicionar.setOnClickListener(v -> {
            String descricao = editDescription.getText().toString();
            boolean isChecked = marcado.isChecked(); // Captura o estado do CheckBox

            if (!descricao.isEmpty()) {
                dbHelper.addItem(descricao, isChecked); // Salva o item com o estado do CheckBox
                loadItems(); // Atualiza a lista
                editDescription.setText(""); // Limpa o campo de descrição
                marcado.setChecked(false); // Reseta o estado do CheckBox
            } else {
                Toast.makeText(MainActivity.this, "Por favor, insira uma descrição.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Carregar itens do banco de dados para a lista
    private void loadItems() {
        itemList.clear();
        itemList.addAll(dbHelper.getAllItems());
        adapter.notifyDataSetChanged();
    }
}