/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Auxiliar.Imagenes;
import Auxiliar.Tipeo;
import Auxiliar.tabla;
import Beans.bPersona;
import Models.mPersona;
import Views.Formularios.frmModal;
import Views.Formularios.frmProveedores;
import Views.Modal.ModalPersona;
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
import javax.swing.JComboBox;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author USUARIO
 */
public class ProveedorController implements MouseListener, KeyListener, ItemListener{
    private frmProveedores frmProveedor;
    private bPersona bProveedor;
    private mPersona mProveedor;
    frmModal frmModal = new frmModal();
    ModalPersona modalProveedor = new ModalPersona(frmModal, true);

    DefaultTableModel dtm = new DefaultTableModel();
    Tipeo tipeo = new Tipeo();

    public ProveedorController(frmProveedores frmProveedor, bPersona bProveedor, mPersona mProveedor) {
        this.frmProveedor = frmProveedor;
        this.bProveedor = bProveedor;
        this.mProveedor = mProveedor;
        iconBotones();
        Combo(modalProveedor.txtTipoDoc);
        listar(frmProveedor.tblProveedores);
        this.frmProveedor.btnNuevo.addMouseListener(this);
        this.frmProveedor.txtBuscar.addKeyListener(this);
        this.frmProveedor.SelectBuscar.addItemListener(this);
        modalProveedor.btnGuardar.addMouseListener(this);
        modalProveedor.btnCancelar.addMouseListener(this);
    }

    private void iconBotones() {
        Imagenes.SetImagenBtn(frmProveedor.btnNuevo, "src/archivos/img/anadir.png", 30, 30);
        Imagenes.SetImagenBtn(modalProveedor.btnCancelar, "src/archivos/img/cancel.png", 30, 30);
        Imagenes.SetImagenBtn(modalProveedor.btnGuardar, "src/archivos/img/salvar.png", 30, 30);
    }

    public void Guardar() throws SQLException {
        //Recibiendo datos de formulario
        String nombre = modalProveedor.txtNombre.getText().toUpperCase();
        String tipo = "PROVEEDOR";
        String tipo_doc = modalProveedor.txtTipoDoc.getSelectedItem().toString().toUpperCase();
        String num_doc = modalProveedor.txtDocumento.getText();
        String direccion = modalProveedor.txtDireccion.getText().toUpperCase();
        String telefono = modalProveedor.txtTelefono.getText();
        String email = modalProveedor.txtEmail.getText();
        if (nombre.equals("") || num_doc.equals("") || direccion.equals("") || telefono.equals("") || email.equals("")) {
            JOptionPane.showMessageDialog(frmProveedor, "Todos los campos son obligatorios");
        } else {
            bProveedor.setNombre(nombre);
            bProveedor.setTipo_persona(tipo);
            bProveedor.setTipo_documento(tipo_doc);
            bProveedor.setNum_documento(num_doc);
            bProveedor.setDireccion(direccion);
            bProveedor.setTelefono(telefono);
            bProveedor.setEmail(email);
            if (mProveedor.Insertar(bProveedor) == true) {
                listar(frmProveedor.tblProveedores);
                btnCancelar();
                JOptionPane.showMessageDialog(frmProveedor, "Registrado con exito");
            } else {
                JOptionPane.showMessageDialog(frmProveedor, "No se pudo registrar");
            }
        }
    }

    public void Editar() throws SQLException {
        //Recibiendo datos de formulario
        int id = Integer.parseInt(modalProveedor.txtId.getText());
        String nombre = modalProveedor.txtNombre.getText().toUpperCase();
        String tipo_doc = modalProveedor.txtTipoDoc.getSelectedItem().toString().toUpperCase();
        String num_doc = modalProveedor.txtDocumento.getText();
        String direccion = modalProveedor.txtDireccion.getText().toUpperCase();
        String telefono = modalProveedor.txtTelefono.getText();
        String email = modalProveedor.txtEmail.getText();
        if (nombre.equals("") || num_doc.equals("") || direccion.equals("") || telefono.equals("") || email.equals("")) {
            JOptionPane.showMessageDialog(frmProveedor, "Todos los campos son obligatorios");
        } else {
            bProveedor.setNombre(nombre);
            bProveedor.setTipo_documento(tipo_doc);
            bProveedor.setNum_documento(num_doc);
            bProveedor.setDireccion(direccion);
            bProveedor.setTelefono(telefono);
            bProveedor.setEmail(email);
            bProveedor.setIdPersona(id);
            if (mProveedor.Editar(bProveedor) == true) {
                listar(frmProveedor.tblProveedores);
                btnCancelar();
                JOptionPane.showMessageDialog(frmProveedor, "Modificado con exito");
            } else {
                JOptionPane.showMessageDialog(frmProveedor, "No se pudo registrar");
            }
        }
    }

    private void btnCancelar() {
        LimpiarFrm();
        modalProveedor.setVisible(false);
    }

    private void Combo(JComboBox tipoDoc) {
        tipoDoc.addItem("DNI");
        tipoDoc.addItem("RUC");
        tipoDoc.addItem("PASAPORTE");
        tipoDoc.addItem("C.E.");
    }

    private void LimpiarFrm() {
        modalProveedor.txtNombre.setText("");
        modalProveedor.txtTipoDoc.setSelectedItem("DNI");
        modalProveedor.txtDocumento.setText("");
        modalProveedor.txtDireccion.setText("");
        modalProveedor.txtTelefono.setText("");
        modalProveedor.txtEmail.setText("");
        modalProveedor.txtId.setText("0");
    }

    private void listar(JTable tabla) {
        try {
            String buscar = frmProveedor.txtBuscar.getText();
            String campo = frmProveedor.SelectBuscar.getSelectedItem().toString();
            if (campo.equals("DOCUMENTO")) {
                campo = "num_documento";
            }
            List<bPersona> listaCliente = mProveedor.Listar(buscar, campo, "PROVEEDOR");
            TablaFormat(listaCliente, tabla);
        } catch (SQLException ex) {
            Logger.getLogger(CategoriaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void TablaFormat(List<bPersona> lista, JTable tabla) {
        dtm = (DefaultTableModel) tabla.getModel();
        dtm.setRowCount(0);
        Object[] objeto = new Object[9];
        for (int i = 0; i < lista.size(); i++) {
            objeto[0] = lista.get(i).idPersona;
            objeto[1] = i + 1;
            objeto[2] = lista.get(i).nombre.toUpperCase();
            objeto[3] = lista.get(i).tipo_documento;
            objeto[4] = lista.get(i).num_documento;
            objeto[5] = lista.get(i).direccion;
            objeto[6] = lista.get(i).telefono;
            objeto[7] = lista.get(i).email;
            objeto[8] = lista.get(i).estado;

            dtm.addRow(objeto);
        }
        tabla.setModel(dtm);
        for (int c = 0; c < tabla.getColumnCount(); c++) {
            Class<?> col_class = tabla.getColumnClass(c);
            tabla.setDefaultEditor(col_class, null); // remove editor
        }

        for (int i = 0; i < tabla.getColumnCount(); i++) {
            tabla color = new tabla(8);
            tabla.getColumnModel().getColumn(i).setCellRenderer(color);
        }

        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setBackground(new Color(0, 0, 102));
        headerRenderer.setForeground(new Color(255, 255, 255));

        for (int i = 0; i < tabla.getModel().getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }

        poputTable();
    }

    public void poputTable() {
        frmProveedor.MenuTbl.removeAll();
        String rutaEdit = "src/archivos/img/editar.png";
        String rutaDelete = "src/archivos/img/switch-off.png";
        String rutaUp = "src/archivos/img/on-button.png";
        JMenuItem delate = new JMenuItem("DAR DE BAJA");
        JMenuItem up = new JMenuItem("ACTIVAR");
        JMenuItem edit = new JMenuItem("EDITAR");
        Imagenes.SetImagenItem(edit, rutaEdit, 20, 20);
        Imagenes.SetImagenItem(delate, rutaDelete, 20, 20);
        Imagenes.SetImagenItem(up, rutaUp, 20, 20);
        frmProveedor.tblProveedores.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frmProveedor.MenuTbl.removeAll();
                int row = frmProveedor.tblProveedores.rowAtPoint(e.getPoint());
                String est = frmProveedor.tblProveedores.getValueAt(row, 8).toString();
                if (est.equals("ACTIVO")) {
                    frmProveedor.MenuTbl.add(delate);
                }
                if (est.equals("INACTIVO")) {
                    frmProveedor.MenuTbl.add(up);
                }
                frmProveedor.MenuTbl.add(edit);
                frmProveedor.tblProveedores.setComponentPopupMenu(frmProveedor.MenuTbl);
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
        frmProveedor.tblProveedores.setComponentPopupMenu(frmProveedor.MenuTbl);
        delate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = frmProveedor.tblProveedores.getSelectedRow();
                if (fila >= 0) {
                    int idU = Integer.parseInt(frmProveedor.tblProveedores.getValueAt(fila, 0).toString());
                    String nameU = frmProveedor.tblProveedores.getValueAt(fila, 2).toString();
                    int rpta = JOptionPane.showConfirmDialog(frmProveedor, "¿DESEA DAR DE BAJA AL PROVEEDOR " + nameU + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
                    if (rpta == 0) {
                        cambiarestado("INACTIVO", idU);
                        listar(frmProveedor.tblProveedores);
                    }
                } else {
                    JOptionPane.showMessageDialog(frmProveedor, "Por favor, seleccione una fila para poder eliminar");
                }
            }
        });
        up.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = frmProveedor.tblProveedores.getSelectedRow();
                if (fila >= 0) {
                    int idU = Integer.parseInt(frmProveedor.tblProveedores.getValueAt(fila, 0).toString());
                    String nameU = frmProveedor.tblProveedores.getValueAt(fila, 2).toString();
                    int rpta = JOptionPane.showConfirmDialog(frmProveedor, "¿DESEA ACTIVAR AL PROVEEDOR " + nameU + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
                    if (rpta == 0) {
                        cambiarestado("ACTIVO", idU);
                        listar(frmProveedor.tblProveedores);
                    }
                } else {
                    JOptionPane.showMessageDialog(frmProveedor, "Por favor, seleccione una fila para poder eliminar");
                }
            }
        });

        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int fila = frmProveedor.tblProveedores.getSelectedRow();
                    if (fila >= 0) {
                        int idU = Integer.parseInt(frmProveedor.tblProveedores.getValueAt(fila, 0).toString());
                        bProveedor = mProveedor.Extraer(idU);
                        modalProveedor.lblTitulo.setText("MODIFICAR PROVEEDOR");
                        modalProveedor.txtId.setText(String.valueOf(bProveedor.idPersona));
                        modalProveedor.txtNombre.setText(bProveedor.nombre);
                        modalProveedor.txtTipoDoc.setSelectedItem(bProveedor.tipo_documento);
                        modalProveedor.txtDocumento.setText(bProveedor.num_documento);
                        modalProveedor.txtDireccion.setText(bProveedor.direccion);
                        modalProveedor.txtTelefono.setText(bProveedor.telefono);
                        modalProveedor.txtEmail.setText(bProveedor.email);
                        modalProveedor.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(modalProveedor, "Por favor, seleccione una fila para poder editar");
                    }

                } catch (HeadlessException | NumberFormatException | SQLException ex) {
                    Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    public void cambiarestado(String accion, int id) {
        if (mProveedor.estado(id, accion) == true) {
            JOptionPane.showMessageDialog(frmProveedor, "EL ESTADO DEL PROVEEDOR CAMBIO A " + accion);
        } else {
            JOptionPane.showMessageDialog(frmProveedor, "NO SE LOGRO CAMBIAR EL ESTADO DEL PROVEEDOR");
        }
        listar(frmProveedor.tblProveedores);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(frmProveedor.btnNuevo)) {
            modalProveedor.lblTitulo.setText("NUEVO PROVEEDOR");
            modalProveedor.setVisible(true);
        }

        if (e.getSource().equals(modalProveedor.btnCancelar)) {
            btnCancelar();
        }

        if (e.getSource().equals(modalProveedor.btnGuardar)) {
            if (modalProveedor.lblTitulo.getText().equals("NUEVO PROVEEDOR")) {
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
                    Logger.getLogger(CategoriaController.class.getName()).log(Level.SEVERE, null, ex);
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
        if (e.getSource().equals(frmProveedor.txtBuscar)) {
            if (frmProveedor.SelectBuscar.getSelectedItem() == "DOCUMENTO") {
                int key = e.getKeyChar();
                int tam = frmProveedor.txtBuscar.getText().trim().length();
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
            listar(frmProveedor.tblProveedores);
        }

        if (e.getSource().equals(modalProveedor.txtNombre)) {
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
        if (e.getSource().equals(frmProveedor.SelectBuscar)) {
            frmProveedor.txtBuscar.setText("");
            listar(frmProveedor.tblProveedores);
        }
    }
}
