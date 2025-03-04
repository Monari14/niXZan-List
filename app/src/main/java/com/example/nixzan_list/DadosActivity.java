package com.example.nixzan_list;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DadosActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    private List<ItemModel> itemList = new ArrayList<>();
    private DatabaseHelper dbHelper;
    private String tableName;
    private EditText editDescription;
    private Button btAdicionar, btVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados);

        // Vincula os componentes da UI
        recyclerView = findViewById(R.id.recyclerView);
        editDescription = findViewById(R.id.editDescription);
        btAdicionar = findViewById(R.id.btAdicionar);
        btVoltar = findViewById(R.id.btVoltar);

        dbHelper = new DatabaseHelper(this);

        // Recebe o nome da tabela da Intent
        tableName = getIntent().getStringExtra("TABLE_NAME");

        // Verifique se o nome da tabela foi recebido corretamente
        if (tableName != null) {
            Toast.makeText(this, "Tabela selecionada: " + tableName, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Erro ao receber o nome da tabela", Toast.LENGTH_SHORT).show();
            finish(); // Finaliza a activity se não houver nome de tabela
        }

        // Buscar itens da tabela no banco de dados
        itemList = dbHelper.getItemsFromTable(tableName);

        // Verifique se a lista de itens está vazia
        if (itemList.isEmpty()) {
            Toast.makeText(this, "Nenhum item encontrado na tabela", Toast.LENGTH_SHORT).show();
        }

        // Configurar o RecyclerView com o adaptador
        itemAdapter = new ItemAdapter(this, itemList, tableName);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(itemAdapter);

        // Ação do botão adicionar
        btAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String description = editDescription.getText().toString();
                if (!description.isEmpty()) {
                    // Adiciona o novo item na tabela
                    ItemModel newItem = new ItemModel(0, description, false, ItemModel.getCurrentDate());
                    dbHelper.addItemToTable(newItem, tableName);
                    itemList.add(newItem); // Adiciona o item à lista
                    itemAdapter.notifyDataSetChanged(); // Notifica o adaptador para atualizar a UI
                    editDescription.setText(""); // Limpa o campo de descrição
                } else {
                    Toast.makeText(DadosActivity.this, "Descrição não pode estar vazia", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Ação do botão voltar
        btVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Volta para a MainActivity
            }
        });
    }
}
