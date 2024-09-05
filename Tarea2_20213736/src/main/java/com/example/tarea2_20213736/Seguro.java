package com.example.tarea2_20213736;

public class Seguro {
    private int id;
    private String nombre;
    private double cobertura;
    private double tarifa;

    public Seguro(int id, String nombre, double cobertura, double tarifa) {
        this.setId(id);
        this.setNombre(nombre);
        this.setCobertura(cobertura);
        this.setTarifa(tarifa);
    }

    public Seguro(){

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getCobertura() {
        return cobertura;
    }

    public void setCobertura(double cobertura) {
        this.cobertura = cobertura;
    }

    public double getTarifa() {
        return tarifa;
    }

    public void setTarifa(double tarifa) {
        this.tarifa = tarifa;
    }
}
