/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Views.Formularios;

import Beans.bIngreso;
import Controller.IngresoController;
import Models.mIngreso;

/**
 *
 * @author USUARIO    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

 */
public class frmIngresos extends javax.swing.JPanel {

    /**
     * Creates new form frmIngresos
     */
    bIngreso bIngreso = new bIngreso();
    mIngreso mIngreso = new mIngreso();
    public frmIngresos() {
        initComponents();
        IngresoController ingresoC = new IngresoController(this, bIngreso, mIngreso);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        MenuTbl = new javax.swing.JPopupMenu();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblIngresos = new javax.swing.JTable();
        btnNuevo = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        SelectBuscar = new javax.swing.JComboBox<>();
        txtBuscar = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        lblUser = new javax.swing.JLabel();
        txtFecha = new com.toedter.calendar.JDateChooser();
        MenuTbl.getAccessibleContext().setAccessibleParent(tblIngresos);

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Rockwell", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("COMPRAS");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 20, 250, 40));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));

        tblIngresos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "N°", "FECHA", "PROVEEDOR", "USUARIO", "COMPROBANTE", "NUMERO", "TOTAL", "ESTADO"
            }
        ));
        jScrollPane1.setViewportView(tblIngresos);
        if (tblIngresos.getColumnModel().getColumnCount() > 0) {
            tblIngresos.getColumnModel().getColumn(0).setMinWidth(0);
            tblIngresos.getColumnModel().getColumn(0).setMaxWidth(0);
            tblIngresos.getColumnModel().getColumn(1).setMinWidth(30);
            tblIngresos.getColumnModel().getColumn(1).setMaxWidth(30);
            tblIngresos.getColumnModel().getColumn(2).setMinWidth(150);
            tblIngresos.getColumnModel().getColumn(2).setMaxWidth(150);
            tblIngresos.getColumnModel().getColumn(5).setMinWidth(120);
            tblIngresos.getColumnModel().getColumn(5).setMaxWidth(120);
            tblIngresos.getColumnModel().getColumn(6).setMinWidth(120);
            tblIngresos.getColumnModel().getColumn(6).setMaxWidth(120);
            tblIngresos.getColumnModel().getColumn(7).setMinWidth(120);
            tblIngresos.getColumnModel().getColumn(7).setMaxWidth(120);
            tblIngresos.getColumnModel().getColumn(8).setMinWidth(120);
            tblIngresos.getColumnModel().getColumn(8).setMaxWidth(120);
        }

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 1210, 670));

        btnNuevo.setBackground(new java.awt.Color(0, 0, 102));
        btnNuevo.setFont(new java.awt.Font("Rockwell", 1, 14)); // NOI18N
        btnNuevo.setForeground(new java.awt.Color(255, 255, 255));
        btnNuevo.setText("NUEVO");
        add(btnNuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 150, 50));

        jLabel2.setFont(new java.awt.Font("Rockwell", 1, 16)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("BUSCAR:");
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 90, 80, 50));

        SelectBuscar.setBackground(new java.awt.Color(255, 255, 255));
        SelectBuscar.setFont(new java.awt.Font("Rockwell", 1, 14)); // NOI18N
        SelectBuscar.setForeground(new java.awt.Color(0, 0, 0));
        SelectBuscar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "USUARIO", "FECHA", "PROVEEDOR" }));
        add(SelectBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 90, 150, 50));

        txtBuscar.setBackground(new java.awt.Color(255, 255, 255));
        txtBuscar.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        txtBuscar.setForeground(new java.awt.Color(0, 0, 0));
        txtBuscar.setBorder(null);
        add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 90, 350, 50));
        add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 140, 350, 10));
        add(lblUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 30, 20, 20));
        add(txtFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 90, 350, 50));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JPopupMenu MenuTbl;
    public javax.swing.JComboBox<String> SelectBuscar;
    public javax.swing.JButton btnNuevo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    public javax.swing.JLabel lblUser;
    public javax.swing.JTable tblIngresos;
    public javax.swing.JTextField txtBuscar;
    public com.toedter.calendar.JDateChooser txtFecha;
    // End of variables declaration//GEN-END:variables
}
