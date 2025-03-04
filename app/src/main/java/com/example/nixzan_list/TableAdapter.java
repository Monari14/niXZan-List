package com.example.nixzan_list;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class TableAdapter extends ArrayAdapter<String> {

    public TableAdapter(Context context, List<String> tableNames) {
        super(context, android.R.layout.simple_list_item_1, tableNames); // Usando layout simples para a lista
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        TextView textView = (TextView) view.findViewById(android.R.id.text1);
        textView.setText(getItem(position));  // Atualiza o texto da listagem
        return view;
    }
}
