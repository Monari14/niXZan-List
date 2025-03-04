package com.example.nixzan_list;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CriarTabelaActivity extends AppCompatActivity {

    private EditText editTableName;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_tabela);

        editTableName = findViewById(R.id.editTableName);
        dbHelper = new DatabaseHelper(this);
    }

    public void onCreateTableClick(View view) {
        String tableName = editTableName.getText().toString().trim();

        if (tableName.isEmpty()) {
            Toast.makeText(this, "Por favor, insira um nome para a lista", Toast.LENGTH_SHORT).show();
            return;
        }

        // Abrindo o banco de dados para escrever
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Corrigindo a chamada para createTable, passando o db e o nome da tabela
        dbHelper.createTable(dbHelper.getWritableDatabase(), tableName);

        Toast.makeText(this, "Lista " + tableName + " criada com sucesso!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(CriarTabelaActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
