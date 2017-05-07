package com.example.ivonneortega.nueva;

import android.net.Uri;

public class Nota {

    private String _name, _phone;
    private int _id;

    public Nota (int id, String name, String phone) {
        _id = id;
        _name = name;
        _phone = phone;
    }

    public int getId() { return _id; }

    public String obtener_titulo() {
        return _name;
    }

    public String obtener_texto() {
        return _phone;
    }

}