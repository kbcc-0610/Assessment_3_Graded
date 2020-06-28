package com.example.assignment_3.Model;

public class Setting {
    private int id;
    private String firstColor;
    private String secondColor;
    private String size;

    public Setting(int id, String firstColor, String secondColor, String size) {
        this.id = id;
        this.firstColor = firstColor;
        this.secondColor = secondColor;
        this.size = size;
    }

    public Setting(String firstColor, String secondColor, String size) {
        this.firstColor = firstColor;
        this.secondColor = secondColor;
        this.size = size;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstColor() {
        return firstColor;
    }

    public void setFirstColor(String firstColor) {
        this.firstColor = firstColor;
    }

    public String getSecondColor() {
        return secondColor;
    }

    public void setSecondColor(String secondColor) {
        this.secondColor = secondColor;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
