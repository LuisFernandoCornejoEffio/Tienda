/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Views.Formularios;

import Beans.bPersona;
import Controller.ClienteController;
import Models.mPersona;

/**
 *
 * @author USUARIO
 */
public class frmClientes extends javax.swing.JPanel {

    /**
     * Creates new form frmClientes
     */
    bPersona bCliente = new bPersona();
    mPersona mCliente = new mPersona();
    
    public frmClientes() {
        initComponents();
        ClienteController clienteC = new ClienteController(this, bCliente, mCliente);
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
        lblTitulo = new javax.swing.JLabel();
        btnNuevo = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        jSeparator1 = new javax.swing.JSeparator();
        txtBuscar = new javax.swing.JTextField();
        SelectBuscar = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitulo.setFont(new java.awt.Font("Rockwell", 1, 24)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(0, 0, 0));
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        add(lblTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 20, 250, 40));

        btnNuevo.setBackground(new java.awt.Color(0, 0, 102));
        btnNuevo.setFont(new java.awt.Font("Rockwell", 1, 14)); // NOI18N
        btnNuevo.setForeground(new java.awt.Color(255, 255, 255));
        btnNuevo.setText("NUEVO");
        btnNuevo.setBorder(null);
        add(btnNuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 120, 50));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));

        tblClientes.setBackground(new java.awt.Color(255, 255, 255));
        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "N°", "NOMBRE", "TIPO DOCUMENTO", "DOCUMENTO", "DIRECCION", "EMAIL", "TELEFONO", "ESTADO"
            }
        ));
        jScrollPane1.setViewportView(tblClientes);
        if (tblClientes.getColumnModel().getColumnCount() > 0) {
            tblClientes.getColumnModel().getColumn(0).setMinWidth(0);
            tblClientes.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 1210, 650));
        add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 130, 350, 10));

        txtBuscar.setBackground(new java.awt.Color(255, 255, 255));
        txtBuscar.setFont(new java.awt.Font("Rockwell", 0, 12)); // NOI18N
        txtBuscar.setForeground(new java.awt.Color(0, 0, 0));
        txtBuscar.setBorder(null);
        add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 90, 350, 40));

        SelectBuscar.setBackground(new java.awt.Color(255, 255, 255));
        SelectBuscar.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        SelectBuscar.setForeground(new java.awt.Color(0, 0, 0));
        SelectBuscar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NOMBRE", "DOCUMENTO" }));
        add(SelectBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 90, 150, 40));

        jLabel2.setFont(new java.awt.Font("Rockwell", 1, 16)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("BUSCAR:");
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 90, 80, 40));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JPopupMenu MenuTbl;
    public javax.swing.JComboBox<String> SelectBuscar;
    public javax.swing.JButton btnNuevo;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    public javax.swing.JLabel lblTitulo;
    public javax.swing.JTable tblClientes;
    public javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
