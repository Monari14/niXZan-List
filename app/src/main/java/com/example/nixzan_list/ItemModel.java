package com.example.nixzan_list;

public class ItemModel {
    private int id;
    private String description;
    private boolean checked;

    public ItemModel(int id, String description, boolean checked) {
        this.id = id;
        this.description = description;
        this.checked = checked;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}