package com.example.nixzan_list;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listaT;
    private Button btAdicionarLista;
    private DatabaseHelper dbHelper;
    private List<String> tableNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Vincula os componentes da UI
        listaT = findViewById(R.id.listaT);
        btAdicionarLista = findViewById(R.id.btAdicionarLista);

        // Criação do helper de banco de dados
        dbHelper = new DatabaseHelper(this);

        // Obter todas as tabelas
        tableNames = dbHelper.getAllTableNames();

        // Configura o adapter do ListView
        TableAdapter adapter = new TableAdapter(this, tableNames);
        listaT.setAdapter(adapter);

        // Ação para adicionar uma nova tabela
        btAdicionarLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CriarTabelaActivity.class);
                startActivity(intent);
            }
        });

        // Ação para abrir a tabela clicada
        listaT.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parentView, View view, int position, long id) {
                // Obter o nome da tabela clicada
                String tableName = tableNames.get(position);

                // Passa o nome da tabela para a DadosActivity
                Intent intent = new Intent(MainActivity.this, DadosActivity.class);
                intent.putExtra("TABLE_NAME", tableName); // Passando o nome da tabela como extra
                startActivity(intent);
            }
        });
    }
}
