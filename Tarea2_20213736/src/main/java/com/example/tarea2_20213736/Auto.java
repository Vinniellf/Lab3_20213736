package com.example.tarea2_20213736;

public class Auto {
    private int id;
    private String modelo;
    private String color;
    private int kilometraje;
    private int id_sede;
    private double costo_dia;

    public Auto(int id, String modelo, String color, int kilometraje, int id_sede, double costo_dia) {
        this.id = id;
        this.modelo = modelo;
        this.color = color;
        this.kilometraje = kilometraje;
        this.id_sede = id_sede;
        this.costo_dia = costo_dia;
    }

    public Auto(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getKilometraje() {
        return kilometraje;
    }

    public void setKilometraje(int kilometraje) {
        this.kilometraje = kilometraje;
    }

    public int getId_sede() {
        return id_sede;
    }

    public void setId_sede(int id_sede) {
        this.id_sede = id_sede;
    }

    public double getCosto_dia() {
        return costo_dia;
    }

    public void setCosto_dia(double costo_dia) {
        this.costo_dia = costo_dia;
    }
}
