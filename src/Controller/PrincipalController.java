/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Auxiliar.Imagenes;
import Models.mArticulo;
import Models.mPersona;
import Models.mIngreso;
import Models.mUsuario;
import Models.mVenta;
import Views.Formularios.frmAdmin;
import Views.Formularios.frmAlmacen;
import Views.Formularios.frmArticulo;
import Views.Formularios.frmCaja;
import Views.Formularios.frmCategoria;
import Views.Formularios.frmClientes;
import Views.Formularios.frmDatos;
import Views.Formularios.frmIngresos;
import Views.Formularios.frmLogin;
import Views.Formularios.frmPrincipal;
import Views.Formularios.frmProveedores;
import Views.Formularios.frmUsuarios;
import Views.Formularios.frmVentas;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author USUARIO
 */
public final class PrincipalController implements MouseListener {

    private frmPrincipal frmPrincipal;

    //Instanciar los jpanel
    frmAdmin frmAdmin = new frmAdmin();
    frmAlmacen frmAlmacen = new frmAlmacen();
    frmCaja frmCaja = new frmCaja();
    frmArticulo frmA = new frmArticulo();
    frmVentas frmV = new frmVentas();
    frmCategoria frmC = new frmCategoria();
    frmClientes frmCl = new frmClientes();
    frmDatos frmD = new frmDatos();
    frmIngresos frmI = new frmIngresos();
    frmProveedores frmPr = new frmProveedores();
    frmUsuarios frmU = new frmUsuarios();

    //Instanciar Modelos
    mUsuario mUsuarios = new mUsuario();
    mPersona mPersona = new mPersona();
    mArticulo mArticle = new mArticulo();
    mIngreso mIngresos = new mIngreso();
    mVenta mVentas = new mVenta();

    int w = 40;
    int h = 35;

    public PrincipalController(frmPrincipal frmPrincipal) {
        try {
            int totalProductos = mArticle.totalArticulos();
            frmAdmin.lblProductos.setText(String.valueOf(totalProductos));
            this.frmPrincipal = frmPrincipal;
            iconos();
            this.frmPrincipal.lblOp1.addMouseListener(this);
            this.frmPrincipal.lblOp2.addMouseListener(this);
            this.frmPrincipal.lblOp3.addMouseListener(this);
            this.frmPrincipal.lblOp4.addMouseListener(this);
            this.frmPrincipal.lblOp5.addMouseListener(this);
            this.frmPrincipal.lblOp6.addMouseListener(this);
            this.frmPrincipal.lblOp7.addMouseListener(this);
            this.frmPrincipal.lblOp8.addMouseListener(this);
            this.frmPrincipal.lblOp9.addMouseListener(this);
            this.frmPrincipal.btnCerrar.addMouseListener(this);

            setColor(frmPrincipal.panelMenu1);
            resetColor(frmPrincipal.panelMenu2);
            resetColor(frmPrincipal.panelMenu3);
            resetColor(frmPrincipal.panelMenu4);
            resetColor(frmPrincipal.panelMenu5);
            resetColor(frmPrincipal.panelMenu6);
            resetColor(frmPrincipal.panelMenu7);
            resetColor(frmPrincipal.panelMenu8);
            resetColor(frmPrincipal.panelMenu9);

        } catch (SQLException ex) {
            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void iconos() {
        Imagenes.SetImagenBtn(frmPrincipal.lblOp1, "src/archivos/img/home.png", w, h);

        Imagenes.SetImagenBtn(frmPrincipal.btnCerrar, "src/archivos/img/cerrar-sesion.png", 40, 35);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(frmPrincipal.lblOp1)) {

            try {
                setColor(frmPrincipal.panelMenu1);
                resetColor(frmPrincipal.panelMenu2);
                resetColor(frmPrincipal.panelMenu3);
                resetColor(frmPrincipal.panelMenu4);
                resetColor(frmPrincipal.panelMenu5);
                resetColor(frmPrincipal.panelMenu6);
                resetColor(frmPrincipal.panelMenu7);
                resetColor(frmPrincipal.panelMenu8);
                resetColor(frmPrincipal.panelMenu9);
                String cargo = frmPrincipal.lblCargo.getText();

                int totalProductos = mArticle.totalArticulos();
                int totalUsuarios = mUsuarios.totalUsuarios();
                int totalClientes = mPersona.totalClientes();
                int totalProveedores = mPersona.totalProveedores();
                int totalComprasHoy = mIngresos.totalComprasHoy();
                int totalVentasHoy = mVentas.totalVentasHoy();
                switch (cargo) {
                    case "ADMINISTRADOR":
                        frmAdmin.lblUsuarios.setText(String.valueOf(totalUsuarios));
                        frmAdmin.lblClientes.setText(String.valueOf(totalClientes));
                        frmAdmin.lblProveedor.setText(String.valueOf(totalProveedores));
                        frmAdmin.lblProductos.setText(String.valueOf(totalProductos));
                        frmAdmin.lblComprasHoy.setText(String.valueOf(totalComprasHoy));
                        frmAdmin.lblVentasHoy.setText(String.valueOf(totalVentasHoy));
                        contenido(frmAdmin);
                        break;
                    case "CAJERO":
                        frmCaja.lblClientes.setText(String.valueOf(totalClientes));
                        frmCaja.lblVentasHoy.setText(String.valueOf(totalVentasHoy));
                        contenido(frmCaja);
                        break;
                    case "ALMACENERO":
                        frmAlmacen.lblProveedor.setText(String.valueOf(totalProveedores));
                        frmAlmacen.lblProductos.setText(String.valueOf(totalProductos));
                        frmAlmacen.lblComprasHoy.setText(String.valueOf(totalComprasHoy));
                        contenido(frmAlmacen);
                        break;
                }
            } catch (SQLException ex) {
                Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        if (e.getSource().equals(frmPrincipal.lblOp2)) {
            resetColor(frmPrincipal.panelMenu1);
            setColor(frmPrincipal.panelMenu2);
            resetColor(frmPrincipal.panelMenu3);
            resetColor(frmPrincipal.panelMenu4);
            resetColor(frmPrincipal.panelMenu5);
            resetColor(frmPrincipal.panelMenu6);
            resetColor(frmPrincipal.panelMenu7);
            resetColor(frmPrincipal.panelMenu8);
            resetColor(frmPrincipal.panelMenu9);
            String op = frmPrincipal.lblOp2.getText();
            switch (op) {
                case " PRODUCTOS":
                    contenido(frmA);
                    break;
                case " VENTAS":
                    frmV.lblUser.setText(frmPrincipal.txtId.getText());
                    contenido(frmV);
                    break;
            }
        }

        if (e.getSource().equals(frmPrincipal.lblOp3)) {
            resetColor(frmPrincipal.panelMenu1);
            resetColor(frmPrincipal.panelMenu2);
            setColor(frmPrincipal.panelMenu3);
            resetColor(frmPrincipal.panelMenu4);
            resetColor(frmPrincipal.panelMenu5);
            resetColor(frmPrincipal.panelMenu6);
            resetColor(frmPrincipal.panelMenu7);
            resetColor(frmPrincipal.panelMenu8);
            resetColor(frmPrincipal.panelMenu9);
            String op = frmPrincipal.lblOp3.getText();
            switch (op) {
                case " CATEGORIAS":
                    contenido(frmC);
                    break;
                case " CLIENTES":
                    contenido(frmCl);
                    break;
            }
        }

        if (e.getSource().equals(frmPrincipal.lblOp4)) {
            resetColor(frmPrincipal.panelMenu1);
            resetColor(frmPrincipal.panelMenu2);
            resetColor(frmPrincipal.panelMenu3);
            setColor(frmPrincipal.panelMenu4);
            resetColor(frmPrincipal.panelMenu5);
            resetColor(frmPrincipal.panelMenu6);
            resetColor(frmPrincipal.panelMenu7);
            resetColor(frmPrincipal.panelMenu8);
            resetColor(frmPrincipal.panelMenu9);
            String op = frmPrincipal.lblOp4.getText();
            switch (op) {
                case " COMPRAS":
                    frmI.lblUser.setText(frmPrincipal.txtId.getText());
                    contenido(frmI);
                    break;
                case " MIS DATOS":
                    contenido(frmD);
                    break;
            }
        }

        if (e.getSource().equals(frmPrincipal.lblOp5)) {
            resetColor(frmPrincipal.panelMenu1);
            resetColor(frmPrincipal.panelMenu2);
            resetColor(frmPrincipal.panelMenu3);
            resetColor(frmPrincipal.panelMenu4);
            setColor(frmPrincipal.panelMenu5);
            resetColor(frmPrincipal.panelMenu6);
            resetColor(frmPrincipal.panelMenu7);
            resetColor(frmPrincipal.panelMenu8);
            resetColor(frmPrincipal.panelMenu9);
            contenido(frmPr);
        }

        if (e.getSource().equals(frmPrincipal.lblOp6)) {
            resetColor(frmPrincipal.panelMenu1);
            resetColor(frmPrincipal.panelMenu2);
            resetColor(frmPrincipal.panelMenu3);
            resetColor(frmPrincipal.panelMenu4);
            resetColor(frmPrincipal.panelMenu5);
            setColor(frmPrincipal.panelMenu6);
            resetColor(frmPrincipal.panelMenu7);
            resetColor(frmPrincipal.panelMenu8);
            resetColor(frmPrincipal.panelMenu9);
            String op = frmPrincipal.lblOp6.getText();
            switch (op) {
                case " VENTAS":
                    frmV.lblUser.setText(frmPrincipal.txtId.getText());
                    contenido(frmV);
                    break;
                case " MIS DATOS":
                    contenido(frmD);
                    break;
            }
        }

        if (e.getSource().equals(frmPrincipal.lblOp7)) {
            resetColor(frmPrincipal.panelMenu1);
            resetColor(frmPrincipal.panelMenu2);
            resetColor(frmPrincipal.panelMenu3);
            resetColor(frmPrincipal.panelMenu4);
            resetColor(frmPrincipal.panelMenu5);
            resetColor(frmPrincipal.panelMenu6);
            setColor(frmPrincipal.panelMenu7);
            resetColor(frmPrincipal.panelMenu8);
            resetColor(frmPrincipal.panelMenu9);
            contenido(frmCl);
        }

        if (e.getSource().equals(frmPrincipal.lblOp8)) {
            resetColor(frmPrincipal.panelMenu1);
            resetColor(frmPrincipal.panelMenu2);
            resetColor(frmPrincipal.panelMenu3);
            resetColor(frmPrincipal.panelMenu4);
            resetColor(frmPrincipal.panelMenu5);
            resetColor(frmPrincipal.panelMenu6);
            resetColor(frmPrincipal.panelMenu7);
            setColor(frmPrincipal.panelMenu8);
            resetColor(frmPrincipal.panelMenu9);
            contenido(frmU);
        }

        if (e.getSource().equals(frmPrincipal.lblOp9)) {
            resetColor(frmPrincipal.panelMenu1);
            resetColor(frmPrincipal.panelMenu2);
            resetColor(frmPrincipal.panelMenu3);
            resetColor(frmPrincipal.panelMenu4);
            resetColor(frmPrincipal.panelMenu5);
            resetColor(frmPrincipal.panelMenu6);
            resetColor(frmPrincipal.panelMenu7);
            resetColor(frmPrincipal.panelMenu8);
            setColor(frmPrincipal.panelMenu9);
            contenido(frmD);
        }

        if (e.getSource().equals(frmPrincipal.btnCerrar)) {
            String msj = "¿SEGURO QUE DESEAS CERRAR SESIÓN?";
            int op = JOptionPane.showConfirmDialog(frmPrincipal, msj, "ALERTA", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
            if (op == JOptionPane.YES_OPTION) {

                this.frmPrincipal.dispose();
                frmLogin frmL = new frmLogin();
                frmL.setVisible(true);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    public void setColor(JPanel panel) {
        panel.setBackground(new Color(0, 0, 51));
    }

    public void resetColor(JPanel panel) {
        panel.setBackground(new Color(0, 0, 102));
    }

    public void contenido(JPanel panelfrm) {
        // Abrir sección
        panelfrm.setSize(1250, 860);
        panelfrm.setLocation(0, 0);

        frmPrincipal.panelContenido.removeAll();
        frmPrincipal.panelContenido.add(panelfrm, BorderLayout.CENTER);
        frmPrincipal.panelContenido.revalidate();
        frmPrincipal.panelContenido.repaint();
    }
}
