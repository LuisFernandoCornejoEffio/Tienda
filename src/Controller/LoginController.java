/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Auxiliar.Fecha;
import Auxiliar.Imagenes;
import Auxiliar.Tipeo;
import Beans.bUsuario;
import Models.mArticulo;
import Models.mIngreso;
import Models.mLogin;
import Models.mPersona;
import Models.mUsuario;
import Models.mVenta;
import Views.Formularios.frmAdmin;
import Views.Formularios.frmAlmacen;
import Views.Formularios.frmCaja;
import Views.Formularios.frmLogin;
import Views.Formularios.frmPrincipal;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
public class LoginController implements MouseListener, KeyListener {

    private final bUsuario bUser;
    private final mLogin mLogin;
    private final frmLogin frmLogin;
    frmPrincipal frmPrincipal = new frmPrincipal();
    frmAdmin frmAdmin = new frmAdmin();
    frmAlmacen frmAlmacen = new frmAlmacen();
    frmCaja frmCaja = new frmCaja();

    Tipeo tipeo = new Tipeo();

    mArticulo mArticle = new mArticulo();
    mIngreso mIngresos = new mIngreso();
    mPersona mPersona = new mPersona();
    mUsuario mUsuarios = new mUsuario();
    mVenta mVentas = new mVenta();

    //Colores
    public final static Color ltsTxt = new Color(204, 204, 204);
    public final static Color LTSTXT = ltsTxt;

    int w = 40;
    int h = 35;

    public LoginController(frmLogin frmLogin, bUsuario bUser, mLogin mLogin) {
        this.frmLogin = frmLogin;
        this.bUser = bUser;
        this.mLogin = mLogin;
        iconos();

        //Eventos a botones
        this.frmLogin.btnCancelar.addMouseListener(this);
        this.frmLogin.btnIngresar.addMouseListener(this);

        //Eventos a textField
        this.frmLogin.txtCorreo.addKeyListener(this);
        this.frmLogin.txtClave.addKeyListener(this);
    }

    private void iconos() {
        Imagenes.SetImagenLabel(frmLogin.lblMitiTiendaImg, "src/archivos/img/comercio-electronico.png", 200, 200);
        Imagenes.SetImagenLabel(frmLogin.lblCorreoLogin, "src/archivos/img/email.png", 40, 35);
        Imagenes.SetImagenLabel(frmLogin.lblClaveLogin, "src/archivos/img/contrasena.png", 40, 35);
        Imagenes.SetImagenBtn(frmLogin.btnIngresar, "src/archivos/img/ingresar.png", 35, 35);
        Imagenes.SetImagenBtn(frmLogin.btnCancelar, "src/archivos/img/cancel.png", 35, 35);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(frmLogin.btnCancelar)) {
            String msj = "¿SEGURO QUE DESEAS SALIR DE LA APLICACIÓN?";
            int op = JOptionPane.showConfirmDialog(frmLogin, msj, "ALERTA", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
            if (op == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }

        if (e.getSource().equals(frmLogin.btnIngresar)) {
            String usuario = frmLogin.txtCorreo.getText();
            String password = String.valueOf(frmLogin.txtClave.getPassword());
            //Verificar si los campos estan vacios
            if (usuario.equals("") || usuario.equals("Ingrese su correo")
                    || password.equals("") || password.equals("******")) {
                JOptionPane.showMessageDialog(null, "INGRESE SU CORREO Y CONTRASEÑA", "ATENCIÓN", JOptionPane.OK_OPTION);
            } else {
                try {
                    bUsuario datosUser = mLogin.Login(usuario, password);
                    if (datosUser != null) {
                        frmPrincipal.setVisible(true);

                        //Fecha
                        frmPrincipal.txtFecha.setText(Fecha.getFechaActual());

                        //imagen user
                        String imagen = "src/archivos/usuarios/" + datosUser.getImagen();
                        Imagenes.SetImagenLabel(frmPrincipal.ImagenUser, imagen, 150, 150);

                        //Menu
                        String cargo = datosUser.cargo.toUpperCase();
                        frmPrincipal.lblOp1.setText("PRINCIPAL");
                        int totalProductos = mArticle.totalArticulos();
                        int totalUsuarios = mUsuarios.totalUsuarios();
                        int totalClientes = mPersona.totalClientes();
                        int totalProveedores = mPersona.totalProveedores();
                        int totalComprasHoy = mIngresos.totalComprasHoy();
                        int totalVentasHoy = mVentas.totalVentasHoy();
                        switch (cargo) {
                            case "ADMINISTRADOR":
                                frmPrincipal.lblOp2.setText(" PRODUCTOS");
                                frmPrincipal.lblOp3.setText(" CATEGORIAS");
                                frmPrincipal.lblOp4.setText(" COMPRAS");
                                frmPrincipal.lblOp5.setText(" PROVEEDORES");
                                frmPrincipal.lblOp6.setText(" VENTAS");
                                frmPrincipal.lblOp7.setText(" CLIENTES");
                                frmPrincipal.lblOp8.setText(" USUARIOS");
                                frmPrincipal.lblOp9.setText(" MIS DATOS");
                                Imagenes.SetImagenBtn(frmPrincipal.lblOp2, "src/archivos/img/paquete.png", w, h);
                                Imagenes.SetImagenBtn(frmPrincipal.lblOp3, "src/archivos/img/categorias.png", w, h);
                                Imagenes.SetImagenBtn(frmPrincipal.lblOp4, "src/archivos/img/almacen.png", w, h);
                                Imagenes.SetImagenBtn(frmPrincipal.lblOp5, "src/archivos/img/suministro.png", w, h);
                                Imagenes.SetImagenBtn(frmPrincipal.lblOp6, "src/archivos/img/punto-de-venta.png", w, h);
                                Imagenes.SetImagenBtn(frmPrincipal.lblOp7, "src/archivos/img/cliente.png", w, h);
                                Imagenes.SetImagenBtn(frmPrincipal.lblOp8, "src/archivos/img/equipo.png", w, h);
                                Imagenes.SetImagenBtn(frmPrincipal.lblOp9, "src/archivos/img/perfil.png", w, h);

                                frmAdmin.lblUsuarios.setText(String.valueOf(totalUsuarios));
                                frmAdmin.lblClientes.setText(String.valueOf(totalClientes));
                                frmAdmin.lblProveedor.setText(String.valueOf(totalProveedores));
                                frmAdmin.lblProductos.setText(String.valueOf(totalProductos));
                                frmAdmin.lblComprasHoy.setText(String.valueOf(totalComprasHoy));
                                frmAdmin.lblVentasHoy.setText(String.valueOf(totalVentasHoy));
                                contenido(frmAdmin);
                                break;
                            case "CAJERO":
                                frmPrincipal.lblOp2.setText(" VENTAS");
                                frmPrincipal.lblOp3.setText(" CLIENTES");
                                frmPrincipal.lblOp4.setText(" MIS DATOS");
                                frmPrincipal.panelMenu5.setVisible(false);
                                frmPrincipal.panelMenu6.setVisible(false);
                                frmPrincipal.panelMenu7.setVisible(false);
                                frmPrincipal.panelMenu8.setVisible(false);
                                frmPrincipal.panelMenu9.setVisible(false);
                                Imagenes.SetImagenBtn(frmPrincipal.lblOp2, "src/archivos/img/punto-de-venta.png", w, h);
                                Imagenes.SetImagenBtn(frmPrincipal.lblOp3, "src/archivos/img/cliente.png", w, h);
                                Imagenes.SetImagenBtn(frmPrincipal.lblOp4, "src/archivos/img/perfil.png", w, h);
                                
                                frmCaja.lblClientes.setText(String.valueOf(totalClientes));
                                frmCaja.lblVentasHoy.setText(String.valueOf(totalVentasHoy));
                                contenido(frmCaja);
                                break;
                            case "ALMACENERO":
                                frmPrincipal.lblOp2.setText(" PRODUCTOS");
                                frmPrincipal.lblOp3.setText(" CATEGORIAS");
                                frmPrincipal.lblOp4.setText(" COMPRAS");
                                frmPrincipal.lblOp5.setText(" PROVEEDORES");
                                frmPrincipal.lblOp6.setText(" MIS DATOS");
                                frmPrincipal.panelMenu7.setVisible(false);
                                frmPrincipal.panelMenu8.setVisible(false);
                                frmPrincipal.panelMenu9.setVisible(false);
                                Imagenes.SetImagenBtn(frmPrincipal.lblOp2, "src/archivos/img/paquete.png", w, h);
                                Imagenes.SetImagenBtn(frmPrincipal.lblOp3, "src/archivos/img/categorias.png", w, h);
                                Imagenes.SetImagenBtn(frmPrincipal.lblOp4, "src/archivos/img/almacen.png", w, h);
                                Imagenes.SetImagenBtn(frmPrincipal.lblOp5, "src/archivos/img/suministro.png", w, h);
                                Imagenes.SetImagenBtn(frmPrincipal.lblOp6, "src/archivos/img/perfil.png", w, h);                                
                                
                                frmAlmacen.lblProveedor.setText(String.valueOf(totalProveedores));
                                frmAlmacen.lblProductos.setText(String.valueOf(totalProductos));
                                frmAlmacen.lblComprasHoy.setText(String.valueOf(totalComprasHoy));
                                contenido(frmAlmacen);
                                break;
                        }
                        //nombre user
                        frmPrincipal.lblUser.setText(datosUser.nombre);
                        frmPrincipal.lblCargo.setText(datosUser.cargo.toUpperCase());
                        frmPrincipal.txtId.setText(String.valueOf(datosUser.getIdUsuario()));
                        this.frmLogin.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
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

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getSource().equals(frmLogin.txtCorreo)) {
            int key = e.getKeyChar();
            boolean emailT = tipeo.SetEmail(key);
            if (emailT == true) {
                e.consume();
            }
        }

        if (e.getSource() == frmLogin.txtCorreo) {
            if (frmLogin.txtCorreo.getText().equals("Ingrese su correo")) {
                frmLogin.txtCorreo.setText("");
                frmLogin.txtCorreo.setForeground(Color.BLACK);
            }
            if (String.valueOf(frmLogin.txtClave.getPassword()).isEmpty()) {
                frmLogin.txtClave.setText("******");
                frmLogin.txtClave.setForeground(LTSTXT);
            }
        }

        if (e.getSource() == frmLogin.txtClave) {
            if (frmLogin.txtCorreo.getText().equals("")) {
                frmLogin.txtCorreo.setText("Ingrese su correo");
                frmLogin.txtCorreo.setForeground(LTSTXT);
            }
            if (String.valueOf(frmLogin.txtClave.getPassword()).equals("******")) {
                frmLogin.txtClave.setText("");
                frmLogin.txtClave.setForeground(Color.BLACK);
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    void contenido(JPanel panelfrm) {
        // Abrir sección
        panelfrm.setSize(1250, 860);
        panelfrm.setLocation(0, 0);

        frmPrincipal.panelContenido.removeAll();
        frmPrincipal.panelContenido.add(panelfrm, BorderLayout.CENTER);
        frmPrincipal.panelContenido.revalidate();
        frmPrincipal.panelContenido.repaint();
    }
}
