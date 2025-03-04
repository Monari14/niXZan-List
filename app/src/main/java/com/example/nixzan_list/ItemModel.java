package com.example.nixzan_list;

public class ItemModel {
    private int id;
    private String description;
    private boolean checked;
    private String createdAt;

    public ItemModel(int id, String description, boolean checked, String createdAt) {
        this.id = id;
        this.description = description;
        this.checked = checked;
        this.createdAt = createdAt;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public boolean isChecked() { return checked; }
    public void setChecked(boolean checked) { this.checked = checked; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    // retorna a data atual
    public static String getCurrentDate() {
        return String.valueOf(System.currentTimeMillis());
    }
}
