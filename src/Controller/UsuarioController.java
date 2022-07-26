/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Auxiliar.Imagenes;
import Auxiliar.Tipeo;
import Auxiliar.tabla;
import Beans.bUsuario;
import Models.mUsuario;
import Views.Formularios.frmModal;
import Views.Formularios.frmUsuarios;
import Views.Modal.ModalUsuario;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author USUARIO
 */
public class UsuarioController implements MouseListener, KeyListener, ItemListener {

    private frmUsuarios frmUsuarios;
    private bUsuario bUsuario;
    private mUsuario mUsuario;
    frmModal frmModal = new frmModal();
    ModalUsuario modalUsuario = new ModalUsuario(frmModal, true);

    DefaultTableModel dtm = new DefaultTableModel();
    Tipeo tipeo = new Tipeo();

    public UsuarioController(frmUsuarios frmUsuarios, bUsuario bUsuario, mUsuario mUsuario) {
        try {
            this.frmUsuarios = frmUsuarios;
            this.bUsuario = bUsuario;
            this.mUsuario = mUsuario;
            iconBotones();
            listar(frmUsuarios.tblUsuarios);
            this.frmUsuarios.btnNuevo.addMouseListener(this);
            this.frmUsuarios.tblUsuarios.addMouseListener(this);
            this.frmUsuarios.txtBuscar.addKeyListener(this);
            this.frmUsuarios.SelectBuscar.addItemListener(this);
            modalUsuario.btnCancelar.addMouseListener(this);
            modalUsuario.btnCargarImagen.addMouseListener(this);
            modalUsuario.btnGuardar.addMouseListener(this);
            modalUsuario.txtNombre.addKeyListener(this);
            modalUsuario.txtImagenUlbl.setVisible(false);
            modalUsuario.txtIdUsuario.setVisible(false);

        } catch (SQLException ex) {
            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void iconBotones() {
        Imagenes.SetImagenBtn(frmUsuarios.btnNuevo, "src/archivos/img/anadir.png", 30, 30);
        Imagenes.SetImagenBtn(modalUsuario.btnCancelar, "src/archivos/img/cancel.png", 30, 30);
        Imagenes.SetImagenBtn(modalUsuario.btnGuardar, "src/archivos/img/salvar.png", 30, 30);
    }

    public void Guardar() throws SQLException {
        //Recibiendo datos de formulario
        String nombreU = modalUsuario.txtNombre.getText().toUpperCase();
        String tipoDocU = modalUsuario.txtTipoDoc.getSelectedItem().toString();
        String numDocU = modalUsuario.txtDocumento.getText();
        String direccionU = modalUsuario.txtDireccion.getText().toUpperCase();
        String telefonoU = modalUsuario.txtTelefono.getText();
        String cargoU = modalUsuario.txtCargo.getSelectedItem().toString();
        String emailU = modalUsuario.txtEmail.getText();
        String rutaImagen = modalUsuario.txtImagenUlbl.getText();
        if (nombreU.equals("") || numDocU.equals("") || direccionU.equals("") || telefonoU.equals("") || emailU.equals("") || rutaImagen.equals("")) {
            JOptionPane.showMessageDialog(frmUsuarios, "Todos los campos son obligatorios");
        } else {
            bUsuario.setNombre(nombreU);
            bUsuario.setTipo_documento(tipoDocU);
            bUsuario.setNum_documento(numDocU);
            bUsuario.setDireccion(direccionU);
            bUsuario.setTelefono(telefonoU);
            bUsuario.setCargo(cargoU);
            bUsuario.setEmail(emailU);
            bUsuario.setClave(numDocU);
            int idKey = mUsuario.Insertar(bUsuario);
            if (idKey != 0) {
                String img = idKey + ".png";
                if (mUsuario.InsertarImg(idKey, img) == true) {
                    Imagenes.CopiarImagen("Usuarios", img, rutaImagen);
                    listar(frmUsuarios.tblUsuarios);
                    btnCancelar();
                    JOptionPane.showMessageDialog(frmUsuarios, "Registrado con exito");
                } else {
                    JOptionPane.showMessageDialog(frmUsuarios, "FALLO AL MOMENTO DE REGISTRAR");
                }
            } else {
                JOptionPane.showMessageDialog(frmUsuarios, "No se pudo registrar");
            }
        }
    }

    public void Editar() throws SQLException {
        //Recibiendo datos de formulario
        int idU = Integer.parseInt(modalUsuario.txtIdUsuario.getText());
        String nombreU = modalUsuario.txtNombre.getText().toUpperCase();
        String tipoDocU = modalUsuario.txtTipoDoc.getSelectedItem().toString();
        String numDocU = modalUsuario.txtDocumento.getText();
        String direccionU = modalUsuario.txtDireccion.getText().toUpperCase();
        String telefonoU = modalUsuario.txtTelefono.getText();
        String cargoU = modalUsuario.txtCargo.getSelectedItem().toString();
        String emailU = modalUsuario.txtEmail.getText();
        String imagenU = idU + ".png";
        String rutaImagen = modalUsuario.txtImagenUlbl.getText();
        if (nombreU.equals("") || numDocU.equals("") || direccionU.equals("") || telefonoU.equals("") || emailU.equals("") || rutaImagen.equals("")) {
            JOptionPane.showMessageDialog(frmUsuarios, "Todos los campos son obligatorios");
        } else {
            bUsuario.setNombre(nombreU);
            bUsuario.setTipo_documento(tipoDocU);
            bUsuario.setNum_documento(numDocU);
            bUsuario.setDireccion(direccionU);
            bUsuario.setTelefono(telefonoU);
            bUsuario.setCargo(cargoU);
            bUsuario.setEmail(emailU);
            bUsuario.setClave(numDocU);
            bUsuario.setImagen(imagenU);
            bUsuario.setIdUsuario(idU);

            if (mUsuario.Editar(bUsuario) == true) {
                String rutaO = System.getProperty("user.dir") + "src/archivos/usuarios/" + imagenU;
                if (!rutaImagen.equals(rutaO)) {
                    Imagenes.CopiarImagen("Usuarios", imagenU, rutaImagen);
                }
                listar(frmUsuarios.tblUsuarios);
                btnCancelar();
                JOptionPane.showMessageDialog(frmUsuarios, "DATOS DE USUARIO MODIFICADO CON EXITO");
            } else {
                JOptionPane.showMessageDialog(frmUsuarios, "NO SE LOGRO MODIFICAR LOS DATOS");
            }
        }
    }

    private void listar(JTable tablaUser) throws SQLException {
        String buscar = frmUsuarios.txtBuscar.getText();
        String campo = frmUsuarios.SelectBuscar.getSelectedItem().toString();
        if (campo.equals("DOCUMENTO")) {
            campo = "num_documento";
        }
        List<bUsuario> listaUser = mUsuario.Listar(buscar, campo);
        TablaFormat(listaUser, tablaUser);
    }

    private void LimpiarFrm() {
        modalUsuario.txtNombre.setText("");
        modalUsuario.txtCargo.setSelectedItem("ADMINISTRADOR");
        modalUsuario.txtDireccion.setText("");
        modalUsuario.txtDocumento.setText("");
        modalUsuario.txtEmail.setText("");
        modalUsuario.txtIdUsuario.setText("0");
        modalUsuario.txtImagen.setIcon(null);
        modalUsuario.txtImagenUlbl.setText("");
        modalUsuario.txtTelefono.setText("");
        modalUsuario.txtTipoDoc.setSelectedItem("DNI");
    }

    public void TablaFormat(List<bUsuario> lista, JTable tablaUser) {
        dtm = (DefaultTableModel) tablaUser.getModel();
        dtm.setRowCount(0);
        Object[] objeto = new Object[10];
        for (int i = 0; i < lista.size(); i++) {
            objeto[0] = lista.get(i).idUsuario;
            objeto[1] = i + 1;
            objeto[2] = lista.get(i).nombre.toUpperCase();
            objeto[3] = lista.get(i).tipo_documento;
            objeto[4] = lista.get(i).num_documento;
            objeto[5] = lista.get(i).direccion.toUpperCase();
            objeto[6] = lista.get(i).telefono;
            objeto[7] = lista.get(i).email;
            objeto[8] = lista.get(i).cargo;
            objeto[9] = lista.get(i).condicion;
            dtm.addRow(objeto);
        }
        tablaUser.setModel(dtm);
        for (int c = 0; c < tablaUser.getColumnCount(); c++) {
            Class<?> col_class = tablaUser.getColumnClass(c);
            tablaUser.setDefaultEditor(col_class, null); // remove editor
        }

        for (int i = 0; i < tablaUser.getColumnCount(); i++) {
            tabla color = new tabla(9);
            tablaUser.getColumnModel().getColumn(i).setCellRenderer(color);
        }

        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setBackground(new Color(0, 0, 102));
        headerRenderer.setForeground(new Color(255, 255, 255));

        for (int i = 0; i < tablaUser.getModel().getColumnCount(); i++) {
            tablaUser.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }

        poputTable();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(frmUsuarios.btnNuevo)) {
            modalUsuario.lblTitulo.setText("NUEVO USUARIO");
            modalUsuario.setVisible(true);
        }

        if (e.getSource().equals(modalUsuario.btnCancelar)) {
            btnCancelar();
        }

        if (e.getSource().equals(modalUsuario.btnCargarImagen)) {
            Imagenes.setCargarImagen(frmUsuarios, modalUsuario.txtImagen, modalUsuario.txtImagen.getWidth() - 5, modalUsuario.txtImagen.getHeight() - 5, modalUsuario.txtImagenUlbl);
        }

        if (e.getSource().equals(modalUsuario.btnGuardar)) {
            if (modalUsuario.lblTitulo.getText().equals("NUEVO USUARIO")) {
                try {
                    Guardar();
                } catch (SQLException ex) {
                    Logger.getLogger(UsuarioController.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    Editar();
                } catch (SQLException ex) {
                    Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void poputTable() {
        frmUsuarios.MenuTbl.removeAll();
        String rutaEdit = "src/archivos/img/editar.png";
        String rutaDelete = "src/archivos/img/switch-off.png";
        String rutaUp = "src/archivos/img/on-button.png";
        JMenuItem delate = new JMenuItem("DAR DE BAJA");
        JMenuItem up = new JMenuItem("ACTIVAR");
        JMenuItem edit = new JMenuItem("EDITAR");
        Imagenes.SetImagenItem(edit, rutaEdit, 20, 20);
        Imagenes.SetImagenItem(delate, rutaDelete, 20, 20);
        Imagenes.SetImagenItem(up, rutaUp, 20, 20);
        frmUsuarios.tblUsuarios.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frmUsuarios.MenuTbl.removeAll();
                int row = frmUsuarios.tblUsuarios.rowAtPoint(e.getPoint());
                String est = frmUsuarios.tblUsuarios.getValueAt(row, 9).toString();
                if (est.equals("ACTIVO")) {
                    frmUsuarios.MenuTbl.add(delate);
                }
                if (est.equals("INACTIVO")) {
                    frmUsuarios.MenuTbl.add(up);
                }
                frmUsuarios.MenuTbl.add(edit);
                frmUsuarios.tblUsuarios.setComponentPopupMenu(frmUsuarios.MenuTbl);
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
        });
        frmUsuarios.tblUsuarios.setComponentPopupMenu(frmUsuarios.MenuTbl);
        delate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = frmUsuarios.tblUsuarios.getSelectedRow();
                if (fila >= 0) {
                    int idU = Integer.parseInt(frmUsuarios.tblUsuarios.getValueAt(fila, 0).toString());
                    String nameU = frmUsuarios.tblUsuarios.getValueAt(fila, 2).toString();
                    int rpta = JOptionPane.showConfirmDialog(frmUsuarios, "¿DESEA DAR DE BAJA AL USUARIO " + nameU + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
                    if (rpta == 0) {
                        try {
                            cambiarestado("INACTIVO", idU);
                            listar(frmUsuarios.tblUsuarios);
                        } catch (SQLException ex) {
                            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(frmUsuarios, "Por favor, seleccione una fila para poder eliminar");
                }
            }
        });
        up.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = frmUsuarios.tblUsuarios.getSelectedRow();
                if (fila >= 0) {
                    int idU = Integer.parseInt(frmUsuarios.tblUsuarios.getValueAt(fila, 0).toString());
                    String nameU = frmUsuarios.tblUsuarios.getValueAt(fila, 2).toString();
                    int rpta = JOptionPane.showConfirmDialog(frmUsuarios, "¿DESEA ACTIVAR AL USUARIO " + nameU + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
                    if (rpta == 0) {
                        try {
                            cambiarestado("ACTIVO", idU);
                            listar(frmUsuarios.tblUsuarios);
                        } catch (SQLException ex) {
                            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(frmUsuarios, "Por favor, seleccione una fila para poder eliminar");
                }
            }
        });

        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int fila = frmUsuarios.tblUsuarios.getSelectedRow();
                    if (fila >= 0) {
                        int idU = Integer.parseInt(frmUsuarios.tblUsuarios.getValueAt(fila, 0).toString());
                        bUsuario = mUsuario.Extraer(idU);
                        modalUsuario.lblTitulo.setText("MODIFICAR USUARIO");
                        modalUsuario.txtIdUsuario.setText(String.valueOf(bUsuario.idUsuario));
                        modalUsuario.txtNombre.setText(bUsuario.nombre);
                        modalUsuario.txtTipoDoc.setSelectedItem(bUsuario.tipo_documento);
                        modalUsuario.txtDocumento.setText(bUsuario.num_documento);
                        modalUsuario.txtDireccion.setText(bUsuario.direccion);
                        modalUsuario.txtTelefono.setText(bUsuario.telefono);
                        modalUsuario.txtCargo.setSelectedItem(bUsuario.cargo.toUpperCase());
                        modalUsuario.txtEmail.setText(bUsuario.email);
                        //imagen user
                        String imagen = "src/archivos/usuarios/" + bUsuario.imagen;
                        Imagenes.SetImagenLabel(modalUsuario.txtImagen, imagen, 155, 155);
                        String ruta = System.getProperty("user.dir") + imagen;
                        modalUsuario.txtImagenUlbl.setText(ruta);
                        modalUsuario.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(modalUsuario, "Por favor, seleccione una fila para poder editar");
                    }

                } catch (HeadlessException | NumberFormatException | SQLException ex) {
                    Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    public void cambiarestado(String accion, int id) {
        try {
            if (mUsuario.estado(id, accion) == true) {
                JOptionPane.showMessageDialog(frmUsuarios, "EL ESTADO DEL USUARIO CAMBIO A " + accion);
            } else {
                JOptionPane.showMessageDialog(frmUsuarios, "NO SE LOGRO CAMBIAR EL ESTADO DEL USUARIO");
            }
            listar(frmUsuarios.tblUsuarios);
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void btnCancelar() {
        LimpiarFrm();
        modalUsuario.setVisible(false);
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
        if (e.getSource().equals(frmUsuarios.txtBuscar)) {
            if (frmUsuarios.SelectBuscar.getSelectedItem() == "DOCUMENTO") {
                int key = e.getKeyChar();
                int tam = frmUsuarios.txtBuscar.getText().trim().length();
                System.out.println(tam);
                boolean doc = tipeo.setDocumento(key, tam);
                if (doc == true) {
                    e.consume();
                }
            } else {
                int key = e.getKeyChar();
                boolean let = tipeo.setLetras(key);
                if (let == true) {
                    e.consume();
                }
            }
            try {
                listar(frmUsuarios.tblUsuarios);
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (e.getSource().equals(modalUsuario.txtNombre)) {
            int key = e.getKeyChar();
            boolean let = tipeo.setLetras(key);
            if (let == true) {
                e.consume();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource().equals(frmUsuarios.SelectBuscar)) {
            try {
                frmUsuarios.txtBuscar.setText("");
                listar(frmUsuarios.tblUsuarios);

            } catch (SQLException ex) {
                Logger.getLogger(UsuarioController.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
