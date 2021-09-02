package com.example.guessthecartoon;

public class Cartoon {
    private String name;
    private int image;


    public Cartoon(String name, int image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }


    public int getImage() {
        return image;
    }

}
