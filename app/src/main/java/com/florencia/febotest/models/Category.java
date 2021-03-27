package com.florencia.febotest.models;

public class Category {
    public String id, name;
    public boolean selected;

    public Category(){
        this.id = "";
        this.name = "";
        this.selected = false;
    }

    public Category(String id, String name, boolean selected) {
        this.id = id;
        this.name = name;
        this.selected = selected;
    }
}
