/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Auxiliar;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author USUARIO
 */
public class Fecha {
    public static String getFechaActual() {
        Date ahora = new Date();
        SimpleDateFormat dia = new SimpleDateFormat("dd");
        SimpleDateFormat mes = new SimpleDateFormat("MMMM");
        SimpleDateFormat año = new SimpleDateFormat("yyyy");
        String fecha = dia.format(ahora) + " de " + mes.format(ahora) + " del " + año.format(ahora);
        return fecha;
    }
    public static String getHoraActual() {
        Date ahora = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("hh:mm:ss");
        return formateador.format(ahora);
    }
    
    public static String getDiaActual(){        
        String fecha = String.valueOf(LocalDate.now()) + " 00:00:00";
        return fecha;
    }
    
    public static String getDiaSig(){        
        String fecha = String.valueOf(LocalDate.now().plusDays(1)) + " 00:00:00";
        return fecha;
    }
    
    public static String getFechaHora(){
        String fecha = String.valueOf(LocalDate.now()) + " "+getHoraActual();
        return fecha;
    }
}
