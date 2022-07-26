/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Auxiliar;

/**
 *
 * @author USUARIO
 */
public class Tipeo {

    public boolean SetEmail(int key) {
        boolean email = false;
        boolean mayusculas = key >= 65 && key <= 90;
        boolean minusculas = key >= 97 && key <= 122;
        boolean arroba = key == 64;
        boolean numeros = key >= 48 && key <= 57;
        boolean espec = key >= 45 && key <= 46 || key == 95 || key == 8;
        if (!(minusculas || mayusculas || arroba || numeros || espec)) {
            email = true;
        }
        return email;
    }

    public boolean setDocumento(int key, int tam) {
        boolean num = false;
        boolean numeros = key >= 48 && key <= 57;

        if (!numeros || tam > 11) {
            num = true;
        }
        return num;
    }

    public boolean setLetras(int key) {
        boolean let = false;
        boolean mayusculas = key >= 65 && key <= 90;
        boolean minusculas = key >= 97 && key <= 122;
        boolean espacio = key == 32;

        if (!(minusculas || mayusculas || espacio)) {
            let = true;
        }
        return let;
    }
}
