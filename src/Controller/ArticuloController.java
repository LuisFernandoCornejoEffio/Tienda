/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Auxiliar.Combo;
import Auxiliar.Imagenes;
import Auxiliar.Tipeo;
import Auxiliar.tabla;
import Beans.bArticulo;
import Beans.bCategoria;
import Models.mArticulo;
import Models.mCategoria;
import Views.Formularios.frmArticulo;
import Views.Formularios.frmModal;
import Views.Modal.ModalArticulo;
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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author USUARIO
 */
public class ArticuloController implements MouseListener, KeyListener, ItemListener {

    private frmArticulo frmArticulo;
    private bArticulo bArticulo;
    private mArticulo mArticulo;
    frmModal frmModal = new frmModal();
    ModalArticulo modalArticulo = new ModalArticulo(frmModal, true);
    mCategoria mCat = new mCategoria();
    DefaultTableModel dtm = new DefaultTableModel();
    Tipeo tipeo = new Tipeo();

    public ArticuloController(frmArticulo frmArticulo, bArticulo bArticulo, mArticulo mArticulo) {
        this.frmArticulo = frmArticulo;
        this.bArticulo = bArticulo;
        this.mArticulo = mArticulo;

        modalArticulo.txtCategoria.removeAllItems();
        iconBotones();
        listar(frmArticulo.tblArticulos);
        ComboCategorias(0);
        this.frmArticulo.btnNuevo.addMouseListener(this);
        this.frmArticulo.tblArticulos.addMouseListener(this);
        this.frmArticulo.txtBuscar.addKeyListener(this);
        this.frmArticulo.SelectBuscar.addItemListener(this);
        modalArticulo.btnCancelar.addMouseListener(this);
        modalArticulo.btnCargarImagen.addMouseListener(this);
        modalArticulo.btnGuardar.addMouseListener(this);
        modalArticulo.txtNombre.addKeyListener(this);
        modalArticulo.txtImagenUlbl.setVisible(false);
        //modalArticulo.txtId.setVisible(false);
    }

    private void iconBotones() {
        Imagenes.SetImagenBtn(frmArticulo.btnNuevo, "src/archivos/img/anadir.png", 30, 30);
        Imagenes.SetImagenBtn(modalArticulo.btnCancelar, "src/archivos/img/cancel.png", 30, 30);
        Imagenes.SetImagenBtn(modalArticulo.btnGuardar, "src/archivos/img/salvar.png", 30, 30);
    }

    private void ComboCategorias(int select) {
        try {
            modalArticulo.txtCategoria.removeAllItems();
            List<bCategoria> listaC = mCat.Listar("", "");
            for (int i = 0; i < listaC.size(); i++) {                
                modalArticulo.txtCategoria.addItem(new Combo(listaC.get(i).idCategoria, listaC.get(i).nombre));
                if(select == 0){
                    modalArticulo.txtCategoria.setSelectedItem(new Combo(listaC.get(1).idCategoria));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ArticuloController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void listar(JTable tablaUser) {
        try {
            String buscar = frmArticulo.txtBuscar.getText();
            String campo = frmArticulo.SelectBuscar.getSelectedItem().toString();
            if (campo.equals("CATEGORIA")) {
                campo = "nomCat";
            }
            List<bArticulo> listaArticulos = mArticulo.Listar(buscar, campo);
            TablaFormat(listaArticulos, tablaUser);
        } catch (SQLException ex) {
            Logger.getLogger(ArticuloController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void TablaFormat(List<bArticulo> lista, JTable tablaUser) {
        dtm = (DefaultTableModel) tablaUser.getModel();
        dtm.setRowCount(0);
        Object[] objeto = new Object[9];
        for (int i = 0; i < lista.size(); i++) {
            objeto[0] = lista.get(i).idArticulo;
            objeto[1] = i + 1;
            objeto[2] = lista.get(i).codigo.toUpperCase();
            objeto[3] = lista.get(i).nombre;
            objeto[4] = lista.get(i).stock;
            objeto[5] = lista.get(i).precio;
            objeto[6] = lista.get(i).NomCat;
            objeto[7] = lista.get(i).descripcion;
            objeto[8] = lista.get(i).condicion;
            dtm.addRow(objeto);
        }
        tablaUser.setModel(dtm);
        for (int c = 0; c < tablaUser.getColumnCount(); c++) {
            Class<?> col_class = tablaUser.getColumnClass(c);
            tablaUser.setDefaultEditor(col_class, null); // remove editor
        }

        for (int i = 0; i < tablaUser.getColumnCount(); i++) {
            tabla color = new tabla(8);
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
        frmArticulo.MenuTbl.removeAll();
        String rutaEdit = "src/archivos/img/editar.png";
        String rutaDelete = "src/archivos/img/switch-off.png";
        String rutaUp = "src/archivos/img/on-button.png";
        JMenuItem delate = new JMenuItem("DAR DE BAJA");
        JMenuItem up = new JMenuItem("ACTIVAR");
        JMenuItem edit = new JMenuItem("EDITAR");
        Imagenes.SetImagenItem(edit, rutaEdit, 20, 20);
        Imagenes.SetImagenItem(delate, rutaDelete, 20, 20);
        Imagenes.SetImagenItem(up, rutaUp, 20, 20);
        frmArticulo.tblArticulos.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frmArticulo.MenuTbl.removeAll();
                int row = frmArticulo.tblArticulos.rowAtPoint(e.getPoint());
                String est = frmArticulo.tblArticulos.getValueAt(row, 8).toString();
                if (est.equals("ACTIVO")) {
                    frmArticulo.MenuTbl.add(delate);
                }
                if (est.equals("INACTIVO")) {
                    frmArticulo.MenuTbl.add(up);
                }
                frmArticulo.MenuTbl.add(edit);
                frmArticulo.tblArticulos.setComponentPopupMenu(frmArticulo.MenuTbl);
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
        frmArticulo.tblArticulos.setComponentPopupMenu(frmArticulo.MenuTbl);
        delate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = frmArticulo.tblArticulos.getSelectedRow();
                if (fila >= 0) {
                    int idU = Integer.parseInt(frmArticulo.tblArticulos.getValueAt(fila, 0).toString());
                    String nameU = frmArticulo.tblArticulos.getValueAt(fila, 2).toString();
                    int rpta = JOptionPane.showConfirmDialog(frmArticulo, "¿DESEA DAR DE BAJA AL PRODUCTO " + nameU + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
                    if (rpta == 0) {
                        cambiarestado("INACTIVO", idU);
                        listar(frmArticulo.tblArticulos);
                    }
                } else {
                    JOptionPane.showMessageDialog(frmArticulo, "Por favor, seleccione una fila para poder eliminar");
                }
            }
        });
        up.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = frmArticulo.tblArticulos.getSelectedRow();
                if (fila >= 0) {
                    int idU = Integer.parseInt(frmArticulo.tblArticulos.getValueAt(fila, 0).toString());
                    String nameU = frmArticulo.tblArticulos.getValueAt(fila, 2).toString();
                    int rpta = JOptionPane.showConfirmDialog(frmArticulo, "¿DESEA ACTIVAR AL PRODUCTO " + nameU + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
                    if (rpta == 0) {
                        cambiarestado("ACTIVO", idU);
                        listar(frmArticulo.tblArticulos);
                    }
                } else {
                    JOptionPane.showMessageDialog(frmArticulo, "Por favor, seleccione una fila para poder eliminar");
                }
            }
        });

        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int fila = frmArticulo.tblArticulos.getSelectedRow();
                    if (fila >= 0) {
                        int idU = Integer.parseInt(frmArticulo.tblArticulos.getValueAt(fila, 0).toString());
                        bArticulo = mArticulo.Extraer(idU);
                        modalArticulo.lblTitulo.setText("MODIFICAR PRODUCTO");
                        modalArticulo.txtId.setText(String.valueOf(bArticulo.idArticulo));
                        modalArticulo.txtCodigo.setText(bArticulo.codigo);
                        modalArticulo.txtNombre.setText(bArticulo.nombre);
                        modalArticulo.txtCategoria.setSelectedItem(new Combo(bArticulo.getIdCategoria()));
                        modalArticulo.txtDescripcion.setText(bArticulo.descripcion);
                        //imagen user
                        String imagen = "src/archivos/productos/" + bArticulo.imagen;
                        Imagenes.SetImagenLabel(modalArticulo.txtImagen, imagen, 155, 155);
                        String ruta = System.getProperty("user.dir") + imagen;
                        modalArticulo.txtImagenUlbl.setText(ruta);
                        modalArticulo.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(modalArticulo, "Por favor, seleccione una fila para poder editar");
                    }

                } catch (HeadlessException | NumberFormatException | SQLException ex) {
                    Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    public void cambiarestado(String accion, int id) {
        if (mArticulo.estado(id, accion) == true) {
            JOptionPane.showMessageDialog(frmArticulo, "EL ESTADO DEL PRODUCTO CAMBIO A " + accion);
        } else {
            JOptionPane.showMessageDialog(frmArticulo, "NO SE LOGRO CAMBIAR EL ESTADO DEL PRODUCTO");
        }
        listar(frmArticulo.tblArticulos);

    }

    private void btnCancelar() {
        limpiarFrm();
        modalArticulo.setVisible(false);
        modalArticulo.txtCategoria.setSelectedItem(new Combo(0));
    }

    public void limpiarFrm() {
        modalArticulo.txtId.setText("0");
        modalArticulo.txtNombre.setText("");
        modalArticulo.txtImagenUlbl.setText("");
        modalArticulo.txtImagen.setIcon(null);
        modalArticulo.txtCodigo.setText("");
        modalArticulo.txtDescripcion.setText("");
    }

    public void Guardar() {
        //Recibiendo datos de formulario
        String nom = modalArticulo.txtNombre.getText();
        Combo itemCat = (Combo) modalArticulo.txtCategoria.getSelectedItem();
        int idcat = itemCat.getId();
        String cod = modalArticulo.txtCodigo.getText();
        String descripcion = modalArticulo.txtDescripcion.getText();
        String rutaImagen = modalArticulo.txtImagenUlbl.getText();
        if (nom.equals("") || cod.equals("") || descripcion.equals("") || idcat == 0) {
            JOptionPane.showMessageDialog(frmArticulo, "Todos los campos son obligatorios");
        } else {
            try {
                bArticulo.setIdCategoria(idcat);
                bArticulo.setCodigo(cod);
                bArticulo.setNombre(nom);
                bArticulo.setDescripcion(descripcion);
                int idKey = mArticulo.Insertar(bArticulo);
                if (idKey != 0) {
                    String img = idKey + ".png";
                    if (mArticulo.InsertarImg(idKey, img) == true) {
                        Imagenes.CopiarImagen("Productos", img, rutaImagen);
                        listar(frmArticulo.tblArticulos);
                        btnCancelar();
                        JOptionPane.showMessageDialog(frmArticulo, "Registrado con exito");
                    } else {
                        JOptionPane.showMessageDialog(frmArticulo, "FALLO AL MOMENTO DE REGISTRAR");
                    }
                } else {
                    JOptionPane.showMessageDialog(frmArticulo, "No se pudo registrar");
                }
            } catch (SQLException ex) {
                Logger.getLogger(ArticuloController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void Editar() {
        //Recibiendo datos de formulario
        int idU = Integer.parseInt(modalArticulo.txtId.getText());
        String nom = modalArticulo.txtNombre.getText();
        Combo itemCat = (Combo) modalArticulo.txtCategoria.getSelectedItem();
        int idcat = itemCat.getId();
        String cod = modalArticulo.txtCodigo.getText();
        String descripcion = modalArticulo.txtDescripcion.getText();
        String imagenU = idU + ".png";
        String rutaImagen = modalArticulo.txtImagenUlbl.getText();
        if (nom.equals("") || cod.equals("") || descripcion.equals("") || idcat == 0) {
            JOptionPane.showMessageDialog(frmArticulo, "Todos los campos son obligatorios");
        } else {
            try {
                bArticulo.setIdCategoria(idcat);
                bArticulo.setCodigo(cod);
                bArticulo.setNombre(nom);
                bArticulo.setDescripcion(descripcion);
                bArticulo.setIdArticulo(idU);

                if (mArticulo.Editar(bArticulo) == true) {
                    String rutaO = System.getProperty("user.dir") + "src/archivos/productos/" + imagenU;
                    if (!rutaImagen.equals(rutaO)) {
                        Imagenes.CopiarImagen("Usuarios", imagenU, rutaImagen);
                    }
                    listar(frmArticulo.tblArticulos);
                    btnCancelar();
                    JOptionPane.showMessageDialog(frmArticulo, "DATOS DE PRODUCTO MODIFICADO CON EXITO");
                } else {
                    JOptionPane.showMessageDialog(frmArticulo, "NO SE LOGRO MODIFICAR LOS DATOS");
                }
            } catch (SQLException ex) {
                Logger.getLogger(ArticuloController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(modalArticulo.btnCancelar)) {
            btnCancelar();
        }
        if (e.getSource().equals(frmArticulo.btnNuevo)) {
            modalArticulo.lblTitulo.setText("NUEVO PRODUCTO");
            modalArticulo.setVisible(true);
        }

        if (e.getSource().equals(modalArticulo.btnGuardar)) {
            if (modalArticulo.lblTitulo.getText().equals("NUEVO PRODUCTO")) {
                Guardar();
            } else {
                Editar();
            }
        }

        if (e.getSource().equals(modalArticulo.btnCargarImagen)) {
            Imagenes.setCargarImagen(frmArticulo, modalArticulo.txtImagen, modalArticulo.txtImagen.getWidth() - 5, modalArticulo.txtImagen.getHeight() - 5, modalArticulo.txtImagenUlbl);
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
        if (e.getSource().equals(frmArticulo.txtBuscar)) {
            listar(frmArticulo.tblArticulos);
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
        if (e.getSource().equals(frmArticulo.SelectBuscar)) {
            frmArticulo.txtBuscar.setText("");
            listar(frmArticulo.tblArticulos);
        }
    }
}
