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
import Views.Formularios.frmClientes;
import Views.Formularios.frmModal;
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
public class ClienteController implements MouseListener, KeyListener, ItemListener {

    private frmClientes frmCliente;
    private bPersona bCliente;
    private mPersona mCliente;
    frmModal frmModal = new frmModal();
    ModalPersona modalCliente = new ModalPersona(frmModal, true);

    DefaultTableModel dtm = new DefaultTableModel();
    Tipeo tipeo = new Tipeo();

    public ClienteController(frmClientes frmCliente, bPersona bCliente, mPersona mCliente) {
        this.frmCliente = frmCliente;
        this.bCliente = bCliente;
        this.mCliente = mCliente;
        iconBotones();
        Combo(modalCliente.txtTipoDoc);
        listar(frmCliente.tblClientes);
        this.frmCliente.btnNuevo.addMouseListener(this);
        this.frmCliente.txtBuscar.addKeyListener(this);
        this.frmCliente.SelectBuscar.addItemListener(this);
        modalCliente.btnGuardar.addMouseListener(this);
        modalCliente.btnCancelar.addMouseListener(this);
    }

    private void iconBotones() {
        Imagenes.SetImagenBtn(frmCliente.btnNuevo, "src/archivos/img/anadir.png", 30, 30);
        Imagenes.SetImagenBtn(modalCliente.btnCancelar, "src/archivos/img/cancel.png", 30, 30);
        Imagenes.SetImagenBtn(modalCliente.btnGuardar, "src/archivos/img/salvar.png", 30, 30);
    }

    public void Guardar() throws SQLException {
        //Recibiendo datos de formulario
        String nombre = modalCliente.txtNombre.getText().toUpperCase();
        String tipo = "CLIENTE";
        String tipo_doc = modalCliente.txtTipoDoc.getSelectedItem().toString().toUpperCase();
        String num_doc = modalCliente.txtDocumento.getText();
        String direccion = modalCliente.txtDireccion.getText().toUpperCase();
        String telefono = modalCliente.txtTelefono.getText();
        String email = modalCliente.txtEmail.getText();
        if (nombre.equals("") || num_doc.equals("") || direccion.equals("") || telefono.equals("") || email.equals("")) {
            JOptionPane.showMessageDialog(frmCliente, "Todos los campos son obligatorios");
        } else {
            bCliente.setNombre(nombre);
            bCliente.setTipo_persona(tipo);
            bCliente.setTipo_documento(tipo_doc);
            bCliente.setNum_documento(num_doc);
            bCliente.setDireccion(direccion);
            bCliente.setTelefono(telefono);
            bCliente.setEmail(email);
            if (mCliente.Insertar(bCliente) == true) {
                listar(frmCliente.tblClientes);
                btnCancelar();
                JOptionPane.showMessageDialog(frmCliente, "Registrado con exito");
            } else {
                JOptionPane.showMessageDialog(frmCliente, "No se pudo registrar");
            }
        }
    }

    public void Editar() throws SQLException {
        //Recibiendo datos de formulario
        int id = Integer.parseInt(modalCliente.txtId.getText());
        String nombre = modalCliente.txtNombre.getText().toUpperCase();
        String tipo_doc = modalCliente.txtTipoDoc.getSelectedItem().toString().toUpperCase();
        String num_doc = modalCliente.txtDocumento.getText();
        String direccion = modalCliente.txtDireccion.getText().toUpperCase();
        String telefono = modalCliente.txtTelefono.getText();
        String email = modalCliente.txtEmail.getText();
        if (nombre.equals("") || num_doc.equals("") || direccion.equals("") || telefono.equals("") || email.equals("")) {
            JOptionPane.showMessageDialog(frmCliente, "Todos los campos son obligatorios");
        } else {
            bCliente.setNombre(nombre);
            bCliente.setTipo_documento(tipo_doc);
            bCliente.setNum_documento(num_doc);
            bCliente.setDireccion(direccion);
            bCliente.setTelefono(telefono);
            bCliente.setEmail(email);
            bCliente.setIdPersona(id);
            if (mCliente.Editar(bCliente) == true) {
                listar(frmCliente.tblClientes);
                btnCancelar();
                JOptionPane.showMessageDialog(frmCliente, "Modificado con exito");
            } else {
                JOptionPane.showMessageDialog(frmCliente, "No se pudo registrar");
            }
        }
    }

    private void btnCancelar() {
        LimpiarFrm();
        modalCliente.setVisible(false);
    }

    private void Combo(JComboBox tipoDoc) {
        tipoDoc.addItem("DNI");
        tipoDoc.addItem("RUC");
        tipoDoc.addItem("PASAPORTE");
        tipoDoc.addItem("C.E.");
    }

    private void LimpiarFrm() {
        modalCliente.txtNombre.setText("");
        modalCliente.txtTipoDoc.setSelectedItem("DNI");
        modalCliente.txtDocumento.setText("");
        modalCliente.txtDireccion.setText("");
        modalCliente.txtTelefono.setText("");
        modalCliente.txtEmail.setText("");
        modalCliente.txtId.setText("0");
    }

    private void listar(JTable tabla) {
        try {
            String buscar = frmCliente.txtBuscar.getText();
            String campo = frmCliente.SelectBuscar.getSelectedItem().toString();
            if (campo.equals("DOCUMENTO")) {
                campo = "num_documento";
            }
            List<bPersona> listaCliente = mCliente.Listar(buscar, campo, "CLIENTE");
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
        frmCliente.MenuTbl.removeAll();
        String rutaEdit = "src/archivos/img/editar.png";
        String rutaDelete = "src/archivos/img/switch-off.png";
        String rutaUp = "src/archivos/img/on-button.png";
        JMenuItem delate = new JMenuItem("DAR DE BAJA");
        JMenuItem up = new JMenuItem("ACTIVAR");
        JMenuItem edit = new JMenuItem("EDITAR");
        Imagenes.SetImagenItem(edit, rutaEdit, 20, 20);
        Imagenes.SetImagenItem(delate, rutaDelete, 20, 20);
        Imagenes.SetImagenItem(up, rutaUp, 20, 20);
        frmCliente.tblClientes.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frmCliente.MenuTbl.removeAll();
                int row = frmCliente.tblClientes.rowAtPoint(e.getPoint());
                String est = frmCliente.tblClientes.getValueAt(row, 8).toString();
                if (est.equals("ACTIVO")) {
                    frmCliente.MenuTbl.add(delate);
                }
                if (est.equals("INACTIVO")) {
                    frmCliente.MenuTbl.add(up);
                }
                frmCliente.MenuTbl.add(edit);
                frmCliente.tblClientes.setComponentPopupMenu(frmCliente.MenuTbl);
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
        frmCliente.tblClientes.setComponentPopupMenu(frmCliente.MenuTbl);
        delate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = frmCliente.tblClientes.getSelectedRow();
                if (fila >= 0) {
                    int idU = Integer.parseInt(frmCliente.tblClientes.getValueAt(fila, 0).toString());
                    String nameU = frmCliente.tblClientes.getValueAt(fila, 2).toString();
                    int rpta = JOptionPane.showConfirmDialog(frmCliente, "¿DESEA DAR DE BAJA AL CLIENTE " + nameU + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
                    if (rpta == 0) {
                        cambiarestado("INACTIVO", idU);
                        listar(frmCliente.tblClientes);
                    }
                } else {
                    JOptionPane.showMessageDialog(frmCliente, "Por favor, seleccione una fila para poder eliminar");
                }
            }
        });
        up.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = frmCliente.tblClientes.getSelectedRow();
                if (fila >= 0) {
                    int idU = Integer.parseInt(frmCliente.tblClientes.getValueAt(fila, 0).toString());
                    String nameU = frmCliente.tblClientes.getValueAt(fila, 2).toString();
                    int rpta = JOptionPane.showConfirmDialog(frmCliente, "¿DESEA ACTIVAR AL CLIENTE " + nameU + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
                    if (rpta == 0) {
                        cambiarestado("ACTIVO", idU);
                        listar(frmCliente.tblClientes);
                    }
                } else {
                    JOptionPane.showMessageDialog(frmCliente, "Por favor, seleccione una fila para poder eliminar");
                }
            }
        });

        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int fila = frmCliente.tblClientes.getSelectedRow();
                    if (fila >= 0) {
                        int idU = Integer.parseInt(frmCliente.tblClientes.getValueAt(fila, 0).toString());
                        bCliente = mCliente.Extraer(idU);
                        modalCliente.lblTitulo.setText("MODIFICAR CLIENTE");
                        modalCliente.txtId.setText(String.valueOf(bCliente.idPersona));
                        modalCliente.txtNombre.setText(bCliente.nombre);
                        modalCliente.txtTipoDoc.setSelectedItem(bCliente.tipo_documento);
                        modalCliente.txtDocumento.setText(bCliente.num_documento);
                        modalCliente.txtDireccion.setText(bCliente.direccion);
                        modalCliente.txtTelefono.setText(bCliente.telefono);
                        modalCliente.txtEmail.setText(bCliente.email);
                        modalCliente.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(modalCliente, "Por favor, seleccione una fila para poder editar");
                    }

                } catch (HeadlessException | NumberFormatException | SQLException ex) {
                    Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    public void cambiarestado(String accion, int id) {
        if (mCliente.estado(id, accion) == true) {
            JOptionPane.showMessageDialog(frmCliente, "EL ESTADO DEL CLIENTE CAMBIO A " + accion);
        } else {
            JOptionPane.showMessageDialog(frmCliente, "NO SE LOGRO CAMBIAR EL ESTADO DEL CLIENTE");
        }
        listar(frmCliente.tblClientes);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(frmCliente.btnNuevo)) {
            modalCliente.lblTitulo.setText("NUEVO CLIENTE");
            modalCliente.setVisible(true);
        }

        if (e.getSource().equals(modalCliente.btnCancelar)) {
            btnCancelar();
        }

        if (e.getSource().equals(modalCliente.btnGuardar)) {
            if (modalCliente.lblTitulo.getText().equals("NUEVO CLIENTE")) {
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
        if (e.getSource().equals(frmCliente.txtBuscar)) {
            if (frmCliente.SelectBuscar.getSelectedItem() == "DOCUMENTO") {
                int key = e.getKeyChar();
                int tam = frmCliente.txtBuscar.getText().trim().length();
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
            listar(frmCliente.tblClientes);
        }

        if (e.getSource().equals(modalCliente.txtNombre)) {
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
        if (e.getSource().equals(frmCliente.SelectBuscar)) {
            frmCliente.txtBuscar.setText("");
            listar(frmCliente.tblClientes);
        }
    }

}
