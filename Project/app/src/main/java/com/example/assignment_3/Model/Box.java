package com.example.assignment_3.Model;

import com.example.assignment_3.R;

public class Box {
    public int colorSource;
    public String color;
    public int id;

    public Box(int colorSource, String color, int id) {
        this.colorSource = colorSource;
        this.color = color;
        this.id = id;
    }

    public Box() {
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getColorSource() {
        return colorSource;
    }

    public void setColorSource(int colorSource) {
        this.colorSource = colorSource;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int changeColor(int num, int color1, int color2) {
        if (num % 2 == 1) {
            this.setColorSource(color1);
            return color1;
        } else {
            this.setColorSource(color2);
            return color2;
        }
    }
}
