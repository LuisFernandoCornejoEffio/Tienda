/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Auxiliar;

/**
 *
 * @author USUARIO
 */
public class Combo {
    private int id;
    private String name;

    public Combo() {
    }

    public Combo(int id) {
        this.id = id;
    }
    
    public Combo(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.getName();
    }

    @Override
    public boolean equals(Object obj) {
        return this.id == ((Combo) obj).id;
    }
//
//    @Override
//    public int hashCode() {
//        int hash = 5;
//        hash = 67 * hash + this.id;
//        return hash;
//    }
    
    
}
