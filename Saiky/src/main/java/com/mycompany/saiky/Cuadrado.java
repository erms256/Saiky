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
public class Cuadrado {
    private int gentrification ;
    private ArrayList<Coordinate> coordenadas;

    public Cuadrado(int gentrification, ArrayList<Coordinate> coordenadas) {
        this.gentrification = gentrification;
        this.coordenadas = coordenadas;
    }

    public Cuadrado(ArrayList<Coordinate> coordenadas) {
        this.coordenadas = coordenadas;
    }
    
    

    public Cuadrado() {
    }

    public int getGentrification() {
        return gentrification;
    }

    public void setGentrification(int gentrification) {
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
        return "Cuadrado{" + "gentrification=" + gentrification + ", coordenadas=" + coordenadas + '}';
    }
    
    
    
}
