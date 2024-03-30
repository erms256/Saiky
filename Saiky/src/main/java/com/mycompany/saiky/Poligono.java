/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.saiky;

import java.util.ArrayList;
import org.locationtech.jts.geom.Coordinate;

/**
 *
 * @author Erwin
 */
public class Poligono {
    private String id ;
    private long gentrification ;
    private ArrayList<Coordinate> coordenadas;

    public Poligono(String id, long gentrification, ArrayList<Coordinate> coordenadas) {
        this.id = id;
        this.gentrification = gentrification;
        this.coordenadas = coordenadas;
    }

    public Poligono() {
    }

    
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getGentrification() {
        return gentrification;
    }

    public void setGentrification(long gentrification) {
        this.gentrification = gentrification;
    }

    public ArrayList<Coordinate> getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(ArrayList<Coordinate> coordenadas) {
        this.coordenadas = coordenadas;
    }

    @Override
    public String toString() {
        return "Poligono{" + "id=" + id + ", gentrification=" + gentrification + ", coordenadas=" + coordenadas.size() + '}';
    }
    
    
    
    
    
            
}
