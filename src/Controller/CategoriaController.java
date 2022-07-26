/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Auxiliar.Imagenes;
import Auxiliar.Tipeo;
import Auxiliar.tabla;
import Beans.bCategoria;
import Models.mCategoria;
import Views.Formularios.frmCategoria;
import Views.Formularios.frmModal;
import Views.Modal.ModalCategoria;
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
public class CategoriaController implements MouseListener, KeyListener, ItemListener {

    private frmCategoria frmCategoria;
    private bCategoria bCategoria;
    private mCategoria mCategoria;
    frmModal frmModal = new frmModal();
    ModalCategoria modalCategoria = new ModalCategoria(frmModal, true);

    DefaultTableModel dtm = new DefaultTableModel();
    Tipeo tipeo = new Tipeo();

    public CategoriaController(frmCategoria frmCategoria, bCategoria bCategoria, mCategoria mCategoria) {
        this.frmCategoria = frmCategoria;
        this.bCategoria = bCategoria;
        this.mCategoria = mCategoria;

        iconBotones();
        listar(frmCategoria.tblCategorias);
        this.frmCategoria.btnNuevo.addMouseListener(this);
        this.frmCategoria.tblCategorias.addMouseListener(this);
        this.frmCategoria.txtBuscar.addKeyListener(this);
        this.frmCategoria.SelectBuscar.addItemListener(this);
        modalCategoria.btnCancelar.addMouseListener(this);
        modalCategoria.btnGuardar.addMouseListener(this);
        modalCategoria.txtNombre.addKeyListener(this);
        modalCategoria.txtId.setVisible(false);
    }

    private void iconBotones() {
        Imagenes.SetImagenBtn(frmCategoria.btnNuevo, "src/archivos/img/anadir.png", 30, 30);
        Imagenes.SetImagenBtn(modalCategoria.btnCancelar, "src/archivos/img/cancel.png", 30, 30);
        Imagenes.SetImagenBtn(modalCategoria.btnGuardar, "src/archivos/img/salvar.png", 30, 30);
    }

    public void Guardar() throws SQLException {
        //Recibiendo datos de formulario
        String nombreCat = modalCategoria.txtNombre.getText().toUpperCase();
        String descripcionCat = modalCategoria.txtDescripcion.getText().toUpperCase();
        if (nombreCat.equals("") || descripcionCat.equals("")) {
            JOptionPane.showMessageDialog(frmCategoria, "Todos los campos son obligatorios");
        } else {
            bCategoria.setNombre(nombreCat);
            bCategoria.setDescripcion(descripcionCat);
            if (mCategoria.Insertar(bCategoria) == true) {
                listar(frmCategoria.tblCategorias);
                btnCancelar();
                JOptionPane.showMessageDialog(frmCategoria, "Registrado con exito");
            } else {
                JOptionPane.showMessageDialog(frmCategoria, "No se pudo registrar");
            }
        }
    }

    public void Editar() throws SQLException {
        //Recibiendo datos de formulario
        int idU = Integer.parseInt(modalCategoria.txtId.getText());
        String nombreCat = modalCategoria.txtNombre.getText().toUpperCase();
        String descripcionCat = modalCategoria.txtDescripcion.getText().toUpperCase();
        if (nombreCat.equals("") || descripcionCat.equals("")) {
            JOptionPane.showMessageDialog(frmCategoria, "Todos los campos son obligatorios");
        } else {
            bCategoria.setNombre(nombreCat);
            bCategoria.setDescripcion(descripcionCat);
            bCategoria.setIdCategoria(idU);
            if (mCategoria.Editar(bCategoria) == true) {
                listar(frmCategoria.tblCategorias);
                btnCancelar();
                JOptionPane.showMessageDialog(frmCategoria, "Modificado con exito");
            } else {
                JOptionPane.showMessageDialog(frmCategoria, "No se pudo registrar");
            }
        }
    }

    private void btnCancelar() {
        LimpiarFrm();
        modalCategoria.setVisible(false);
    }

    private void LimpiarFrm() {
        modalCategoria.txtNombre.setText("");
        modalCategoria.txtDescripcion.setText("");
        modalCategoria.txtId.setText("0");
    }

    private void listar(JTable tablaUser) {
        try {
            String buscar = frmCategoria.txtBuscar.getText();
            String campo = frmCategoria.SelectBuscar.getSelectedItem().toString();
            List<bCategoria> listaCat = mCategoria.Listar(buscar, campo);
            TablaFormat(listaCat, tablaUser);
        } catch (SQLException ex) {
            Logger.getLogger(CategoriaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void TablaFormat(List<bCategoria> lista, JTable tablaUser) {
        dtm = (DefaultTableModel) tablaUser.getModel();
        dtm.setRowCount(0);
        Object[] objeto = new Object[5];
        for (int i = 0; i < lista.size(); i++) {
            objeto[0] = lista.get(i).idCategoria;
            objeto[1] = i + 1;
            objeto[2] = lista.get(i).nombre.toUpperCase();
            objeto[3] = lista.get(i).descripcion;
            objeto[4] = lista.get(i).condicion;
            dtm.addRow(objeto);
        }
        tablaUser.setModel(dtm);
        for (int c = 0; c < tablaUser.getColumnCount(); c++) {
            Class<?> col_class = tablaUser.getColumnClass(c);
            tablaUser.setDefaultEditor(col_class, null); // remove editor
        }

        for (int i = 0; i < tablaUser.getColumnCount(); i++) {
            tabla color = new tabla(4);
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

    public void poputTable() {
        frmCategoria.MenuTbl.removeAll();
        String rutaEdit = "src/archivos/img/editar.png";
        String rutaDelete = "src/archivos/img/switch-off.png";
        String rutaUp = "src/archivos/img/on-button.png";
        JMenuItem delate = new JMenuItem("DAR DE BAJA");
        JMenuItem up = new JMenuItem("ACTIVAR");
        JMenuItem edit = new JMenuItem("EDITAR");
        Imagenes.SetImagenItem(edit, rutaEdit, 20, 20);
        Imagenes.SetImagenItem(delate, rutaDelete, 20, 20);
        Imagenes.SetImagenItem(up, rutaUp, 20, 20);
        frmCategoria.tblCategorias.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frmCategoria.MenuTbl.removeAll();
                int row = frmCategoria.tblCategorias.rowAtPoint(e.getPoint());
                String est = frmCategoria.tblCategorias.getValueAt(row, 4).toString();
                if (est.equals("ACTIVO")) {
                    frmCategoria.MenuTbl.add(delate);
                }
                if (est.equals("INACTIVO")) {
                    frmCategoria.MenuTbl.add(up);
                }
                frmCategoria.MenuTbl.add(edit);
                frmCategoria.tblCategorias.setComponentPopupMenu(frmCategoria.MenuTbl);
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
        frmCategoria.tblCategorias.setComponentPopupMenu(frmCategoria.MenuTbl);
        delate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = frmCategoria.tblCategorias.getSelectedRow();
                if (fila >= 0) {
                    int idU = Integer.parseInt(frmCategoria.tblCategorias.getValueAt(fila, 0).toString());
                    String nameU = frmCategoria.tblCategorias.getValueAt(fila, 2).toString();
                    int rpta = JOptionPane.showConfirmDialog(frmCategoria, "¿DESEA DAR DE BAJA A LA CATEGORIA " + nameU + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
                    if (rpta == 0) {
                        cambiarestado("INACTIVO", idU);
                        listar(frmCategoria.tblCategorias);
                    }
                } else {
                    JOptionPane.showMessageDialog(frmCategoria, "Por favor, seleccione una fila para poder eliminar");
                }
            }
        });
        up.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = frmCategoria.tblCategorias.getSelectedRow();
                if (fila >= 0) {
                    int idU = Integer.parseInt(frmCategoria.tblCategorias.getValueAt(fila, 0).toString());
                    String nameU = frmCategoria.tblCategorias.getValueAt(fila, 2).toString();
                    int rpta = JOptionPane.showConfirmDialog(frmCategoria, "¿DESEA ACTIVAR LA CATEGORIA " + nameU + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
                    if (rpta == 0) {
                        cambiarestado("ACTIVO", idU);
                        listar(frmCategoria.tblCategorias);
                    }
                } else {
                    JOptionPane.showMessageDialog(frmCategoria, "Por favor, seleccione una fila para poder eliminar");
                }
            }
        });

        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int fila = frmCategoria.tblCategorias.getSelectedRow();
                    if (fila >= 0) {
                        int idU = Integer.parseInt(frmCategoria.tblCategorias.getValueAt(fila, 0).toString());
                        bCategoria = mCategoria.Extraer(idU);
                        modalCategoria.lblTitulo.setText("MODIFICAR CATEGORIA");
                        modalCategoria.txtId.setText(String.valueOf(bCategoria.idCategoria));
                        modalCategoria.txtNombre.setText(bCategoria.nombre);
                        modalCategoria.txtDescripcion.setText(bCategoria.descripcion);
                        modalCategoria.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(modalCategoria, "Por favor, seleccione una fila para poder editar");
                    }

                } catch (HeadlessException | NumberFormatException | SQLException ex) {
                    Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    public void cambiarestado(String accion, int id) {
        if (mCategoria.estado(id, accion) == true) {
            JOptionPane.showMessageDialog(frmCategoria, "EL ESTADO DE LA CATEGORIA CAMBIO A " + accion);
        } else {
            JOptionPane.showMessageDialog(frmCategoria, "NO SE LOGRO CAMBIAR EL ESTADO DE LA CATEGORIA");
        }
        listar(frmCategoria.tblCategorias);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(frmCategoria.btnNuevo)) {
            modalCategoria.lblTitulo.setText("NUEVA CATEGORIA");
            modalCategoria.setVisible(true);
        }

        if (e.getSource().equals(modalCategoria.btnCancelar)) {
            btnCancelar();
        }

        if (e.getSource().equals(modalCategoria.btnGuardar)) {
            if (modalCategoria.lblTitulo.getText().equals("NUEVA CATEGORIA")) {
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
        if (e.getSource().equals(frmCategoria.txtBuscar)) {

            listar(frmCategoria.tblCategorias);
        }

        if (e.getSource().equals(modalCategoria.txtNombre)) {
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
        if (e.getSource().equals(frmCategoria.SelectBuscar)) {
            frmCategoria.txtBuscar.setText("");
            listar(frmCategoria.tblCategorias);
        }
    }

}
