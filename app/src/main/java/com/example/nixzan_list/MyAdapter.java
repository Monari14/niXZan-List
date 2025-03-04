package com.example.nixzan_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends ArrayAdapter<String> {

    private Context context;
    private List<String> tableNames;  // Lista de nomes de tabelas
    private DatabaseHelper dbHelper;

    public MyAdapter(Context context, List<String> tableNames) {
        super(context, 0, tableNames);
        this.context = context;
        this.tableNames = tableNames;
        this.dbHelper = new DatabaseHelper(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Verifica se a view já foi inflada, se não, infla uma nova
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_table, parent, false); // item_table é o layout do item na lista
        }

        // Pega o nome da tabela na posição atual
        String tableName = tableNames.get(position);

        // Pega o TextView do layout onde vamos exibir o nome da tabela
        TextView tableNameTextView = convertView.findViewById(R.id.table_name);  // Assumindo que existe um TextView com id "table_name"

        // Define o nome da tabela no TextView
        tableNameTextView.setText(tableName);

        // Define o que acontece quando um item é clicado (exemplo: abrir a lista de itens dessa tabela)
        convertView.setOnClickListener(v -> {
            // Exemplo: você pode abrir uma nova Activity passando o nome da tabela
            // Intent intent = new Intent(context, ItemListActivity.class);
            // intent.putExtra("tableName", tableName); // Passa o nome da tabela para a próxima Activity
            // context.startActivity(intent);
        });

        return convertView;
    }
}
