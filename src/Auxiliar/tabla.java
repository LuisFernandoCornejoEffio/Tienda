/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Auxiliar;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author USUARIO
 */
public class tabla extends DefaultTableCellRenderer {

    private final int columna;

    public tabla(int columna) {
        this.columna = columna;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        switch (table.getValueAt(row, columna).toString()) {
            case "ACTIVO":
            case "ACTIVA":
                setForeground(Color.black);
                break;
            case "INACTIVO":
            case "ANULADO":
                setForeground(Color.red);
                break;
            default:
                break;
        }
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        return this;
    }
}
