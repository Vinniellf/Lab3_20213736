package com.example.tarea2_20213736;

public class Sede {
    private int id;
    private String distrito;
    private String direccion;

    public Sede(int id, String distrito, String direccion) {
        this.setId(id);
        this.setDistrito(distrito);
        this.setDireccion(direccion);
    }

    public Sede(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
