package com.example.nixzan_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.AppCompatImageView;
import java.util.List;

public class MyAdapter extends ArrayAdapter<ItemModel> {

    private Context context;
    private List<ItemModel> items;
    private DatabaseHelper dbHelper;

    public MyAdapter(Context context, List<ItemModel> items) {
        super(context, 0, items);
        this.context = context;
        this.items = items;
        this.dbHelper = new DatabaseHelper(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
        }

        ItemModel item = items.get(position);

        TextView description = convertView.findViewById(R.id.item_description);
        CheckBox checkBox = convertView.findViewById(R.id.item_checkbox);
        AppCompatImageView deleteBtn = convertView.findViewById(R.id.delete_button);

        description.setText(item.getDescription());
        checkBox.setChecked(item.isChecked());

        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            dbHelper.updateChecked(item.getId(), isChecked);
            item.setChecked(isChecked);
        });

        deleteBtn.setOnClickListener(v -> {
            dbHelper.deleteItem(item.getId());
            items.remove(position);
            notifyDataSetChanged();
            Toast.makeText(context, "Item excluído", Toast.LENGTH_SHORT).show();
        });

        return convertView;
    }
}