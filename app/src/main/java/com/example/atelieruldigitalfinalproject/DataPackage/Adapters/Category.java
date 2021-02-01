package com.example.atelieruldigitalfinalproject.DataPackage.Adapters;

public class Category {
    private String categoryName;
    private int pictureId;

    public Category(String categoryName, int pictureId) {
        this.categoryName = categoryName;
        this.pictureId = pictureId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getPictureId() {
        return pictureId;
    }

    public void setPictureId(int pictureId) {
        this.pictureId = pictureId;
    }
}
