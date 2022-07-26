/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Views.Formularios;

import Beans.bVenta;
import Controller.VentaController;
import Models.mVenta;

/**
 *
 * @author USUARIO
 */
public class frmVentas extends javax.swing.JPanel {

    /**
     * Creates new form frmVentas
     */
    bVenta bVenta = new bVenta();
    mVenta mVenta = new mVenta();
    
    public frmVentas() {
        initComponents();
        VentaController ventaC = new VentaController(this, bVenta, mVenta);
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
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblVentas = new javax.swing.JTable();
        btnNuevo = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        SelectBuscar = new javax.swing.JComboBox<>();
        txtBuscar = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        lblUser = new javax.swing.JLabel();
        txtFecha = new com.toedter.calendar.JDateChooser();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Rockwell", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("VENTAS");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 20, 250, 40));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));

        tblVentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "N°", "FECHA", "CLIENTE", "USUARIO", "COMPROBANTE", "NUMERO", "TOTAL", "ESTADO"
            }
        ));
        jScrollPane1.setViewportView(tblVentas);
        if (tblVentas.getColumnModel().getColumnCount() > 0) {
            tblVentas.getColumnModel().getColumn(0).setMinWidth(0);
            tblVentas.getColumnModel().getColumn(0).setMaxWidth(0);
            tblVentas.getColumnModel().getColumn(2).setMinWidth(150);
            tblVentas.getColumnModel().getColumn(2).setMaxWidth(150);
            tblVentas.getColumnModel().getColumn(5).setMinWidth(120);
            tblVentas.getColumnModel().getColumn(5).setMaxWidth(120);
            tblVentas.getColumnModel().getColumn(6).setMinWidth(120);
            tblVentas.getColumnModel().getColumn(6).setMaxWidth(120);
            tblVentas.getColumnModel().getColumn(7).setMinWidth(100);
            tblVentas.getColumnModel().getColumn(7).setMaxWidth(100);
            tblVentas.getColumnModel().getColumn(8).setMinWidth(120);
            tblVentas.getColumnModel().getColumn(8).setMaxWidth(120);
        }

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 1210, 670));

        btnNuevo.setBackground(new java.awt.Color(0, 0, 102));
        btnNuevo.setFont(new java.awt.Font("Rockwell", 1, 14)); // NOI18N
        btnNuevo.setForeground(new java.awt.Color(255, 255, 255));
        btnNuevo.setText("NUEVO");
        jPanel1.add(btnNuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 150, 50));

        jLabel2.setFont(new java.awt.Font("Rockwell", 1, 16)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("BUSCAR:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 90, 80, 50));

        SelectBuscar.setBackground(new java.awt.Color(255, 255, 255));
        SelectBuscar.setFont(new java.awt.Font("Rockwell", 1, 14)); // NOI18N
        SelectBuscar.setForeground(new java.awt.Color(0, 0, 0));
        SelectBuscar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "USUARIO", "FECHA", "CLIENTE" }));
        jPanel1.add(SelectBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 90, 150, 50));

        txtBuscar.setBackground(new java.awt.Color(255, 255, 255));
        txtBuscar.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        txtBuscar.setForeground(new java.awt.Color(0, 0, 0));
        txtBuscar.setBorder(null);
        jPanel1.add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 90, 350, 50));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 140, 350, 10));

        lblUser.setForeground(new java.awt.Color(0, 0, 0));
        jPanel1.add(lblUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 30, 40, 40));
        jPanel1.add(txtFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 90, 350, 50));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1250, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 860, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 860, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JPopupMenu MenuTbl;
    public javax.swing.JComboBox<String> SelectBuscar;
    public javax.swing.JButton btnNuevo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    public javax.swing.JLabel lblUser;
    public javax.swing.JTable tblVentas;
    public javax.swing.JTextField txtBuscar;
    public com.toedter.calendar.JDateChooser txtFecha;
    // End of variables declaration//GEN-END:variables
}
