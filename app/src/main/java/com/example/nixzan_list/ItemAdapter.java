package com.example.nixzan_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private Context context;
    private List<ItemModel> itemList;
    private DatabaseHelper dbHelper;
    private String tableName;

    public ItemAdapter(Context context, List<ItemModel> itemList, String tableName) {
        this.context = context;
        this.itemList = itemList;
        this.tableName = tableName;
        dbHelper = new DatabaseHelper(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Usando o layout item_list.xml para cada item
        View view = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ItemModel item = itemList.get(position);
        holder.description.setText(item.getDescription());
        holder.checkbox.setChecked(item.isChecked());  // Restaura o estado do CheckBox

        // Definindo o listener para o CheckBox
        holder.checkbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Atualiza o estado no banco de dados quando o CheckBox for marcado/desmarcado
            dbHelper.updateChecked(tableName, item.getId(), isChecked);
            item.setChecked(isChecked);  // Atualiza o estado do item localmente
            Toast.makeText(context, "Estado atualizado", Toast.LENGTH_SHORT).show();
        });

        // Definindo a ação para o botão de excluir
        holder.deleteButton.setOnClickListener(v -> {
            dbHelper.deleteItem(tableName, item.getId());
            itemList.remove(position);  // Remove o item da lista local
            notifyItemRemoved(position);  // Atualiza a RecyclerView
            Toast.makeText(context, "Item excluído", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView description;
        CheckBox checkbox;
        View deleteButton;

        public ViewHolder(View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.item_description);
            checkbox = itemView.findViewById(R.id.item_checkbox);
            deleteButton = itemView.findViewById(R.id.delete_button);
        }
    }
}
