/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Auxiliar.Combo;
import Auxiliar.Fecha;
import Auxiliar.Imagenes;
import Auxiliar.tabla;
import Beans.bArticulo;
import Beans.bDetalleIngreso;
import Beans.bIngreso;
import Beans.bPersona;
import Models.mArticulo;
import Models.mIngreso;
import Models.mPersona;
import Views.Formularios.frmIngresos;
import Views.Formularios.frmModal;
import Views.Modal.ModalBuscarProducto;
import Views.Modal.ModalIngreso;
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
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Calendar;
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
public class IngresoController implements MouseListener, KeyListener, ItemListener, PropertyChangeListener {

    private frmIngresos frmingreso;
    private bIngreso bIngreso;
    private mIngreso mIngreso;
    bDetalleIngreso bBuyD = new bDetalleIngreso();
    frmModal frmModal = new frmModal();
    ModalIngreso modalIngreso = new ModalIngreso(frmModal, true);
    mPersona mProv = new mPersona();
    mArticulo mArt = new mArticulo();
    ModalBuscarProducto modalBuscarP = new ModalBuscarProducto(frmModal, true);
    DefaultTableModel dtm = new DefaultTableModel();
    DecimalFormat df = new DecimalFormat("#.00");

    public IngresoController(frmIngresos frmingreso, bIngreso bIngreso, mIngreso mIngreso) {
        this.frmingreso = frmingreso;
        this.bIngreso = bIngreso;
        this.mIngreso = mIngreso;
        iconBotones();
        ComboProducto(0);
        ComboProveedor(0);
        listar(frmingreso.tblIngresos);
        //Eventos frm Ingresos
        this.frmingreso.btnNuevo.addMouseListener(this);
        this.frmingreso.SelectBuscar.addItemListener(this);
        this.frmingreso.txtBuscar.setVisible(false);
        this.frmingreso.lblUser.setVisible(true);
        this.frmingreso.txtFecha.setVisible(false);
        this.frmingreso.txtBuscar.setVisible(true);
        this.frmingreso.txtBuscar.addKeyListener(this);
        this.frmingreso.txtFecha.addPropertyChangeListener(this);
        //Eventos frm modal ingresos
        modalIngreso.btnAgregarProducto.addMouseListener(this);
        modalIngreso.btnCancelar.addMouseListener(this);
        modalIngreso.btnGuardar.addMouseListener(this);
        btnNuevo();
        modalIngreso.txtIdIngreso.setVisible(false);
        //Eventos frm modal productos
        modalBuscarP.txtProducto.addItemListener(this);
        modalBuscarP.txtCantidad.addKeyListener(this);
        modalBuscarP.txtSubTotal.addKeyListener(this);
        modalBuscarP.btnAgregarProd.addMouseListener(this);
        modalBuscarP.btnCancelarProd.addMouseListener(this);
    }

    private void ComboProveedor(int select) {
        try {
            modalIngreso.txtProveedor.removeAllItems();
            List<bPersona> lista = mProv.Listar("", "", "PROVEEDOR");
            for (int i = 0; i < lista.size(); i++) {
                modalIngreso.txtProveedor.addItem(new Combo(lista.get(i).idPersona, lista.get(i).nombre));
                if (select == 0) {
                    modalIngreso.txtProveedor.setSelectedItem(new Combo(lista.get(i).idPersona));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ArticuloController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void ComboProducto(int select) {
        try {
            modalBuscarP.txtProducto.removeAllItems();
            List<bArticulo> lista = mArt.Listar("", "");
            for (int i = 0; i < lista.size(); i++) {
                modalBuscarP.txtProducto.addItem(new Combo(lista.get(i).idArticulo, lista.get(i).nombre));
                if (select == 0) {
                    modalBuscarP.txtProducto.setSelectedItem(new Combo(lista.get(i).idArticulo));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ArticuloController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void iconBotones() {
        Imagenes.SetImagenBtn(frmingreso.btnNuevo, "src/archivos/img/anadir.png", 30, 30);
        Imagenes.SetImagenBtn(modalIngreso.btnCancelar, "src/archivos/img/cancel.png", 30, 30);
        Imagenes.SetImagenBtn(modalIngreso.btnGuardar, "src/archivos/img/salvar.png", 30, 30);
    }

    public void listar(JTable tabla) {
        try {
            String buscar = frmingreso.txtBuscar.getText();
            String campo = frmingreso.SelectBuscar.getSelectedItem().toString();
            if (campo.equals("USUARIO")) {
                campo = "u.nombre";
            }
            if (campo.equals("PROVEEDOR")) {
                campo = "p.nombre";
            }
            if (campo.equals("FECHA")) {
                int año, dia;
                String mes;
                if (frmingreso.txtFecha.getCalendar() != null) {
                    campo = "fecha_hora";
                    año = frmingreso.txtFecha.getCalendar().get(Calendar.YEAR);
                    mes = String.valueOf(frmingreso.txtFecha.getCalendar().get(Calendar.MONTH) + 1);
                    if (mes.length() == 1) {
                        mes = "0" + mes;
                    }
                    dia = frmingreso.txtFecha.getCalendar().get(Calendar.DAY_OF_MONTH);
                    buscar = año + "-" + mes + "-" + dia;
                }
            }
            List<bIngreso> listaBuy = mIngreso.Listar(buscar, campo);
            TablaFormat(listaBuy, tabla);
        } catch (SQLException ex) {
            Logger.getLogger(IngresoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void TablaFormat(List<bIngreso> lista, JTable tabla) {
        dtm = (DefaultTableModel) tabla.getModel();
        dtm.setRowCount(0);
        Object[] objeto = new Object[9];
        for (int i = 0; i < lista.size(); i++) {
            objeto[0] = lista.get(i).idingreso;
            objeto[1] = i + 1;
            objeto[2] = lista.get(i).fecha_hora;
            objeto[3] = lista.get(i).proveedor;
            objeto[4] = lista.get(i).usuario;
            objeto[5] = lista.get(i).tipo_comprobante;
            objeto[6] = lista.get(i).serie_comprobante + " - " + lista.get(i).num_comprobante;
            objeto[7] = lista.get(i).total_compra;
            objeto[8] = lista.get(i).estado.toUpperCase();

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

    private void Guardar() {
        //Recibiendo datos de formulario
        Combo itemProveedor = (Combo) modalIngreso.txtProveedor.getSelectedItem();
        int idProveedor = itemProveedor.getId();
        int idUsuario = Integer.parseInt(frmingreso.lblUser.getText());
        String comprobante = modalIngreso.txtComprobante.getSelectedItem().toString();
        String serie = modalIngreso.txtSerie.getText();
        String numero = modalIngreso.txtNumero.getText();
        String fecha = Fecha.getFechaHora();
        double igvC = Double.parseDouble(modalIngreso.lblIgv.getText());
        double totalC = Double.parseDouble(modalIngreso.lblTotal.getText());
        if (serie.equals("") || numero.equals("")) {
            JOptionPane.showMessageDialog(modalIngreso, "Todos los campos con * son obligatorios");
        } else {
            try {
                bIngreso.setIdproveedor(idProveedor);
                bIngreso.setIdusuario(idUsuario);
                bIngreso.setTipo_comprobante(comprobante);
                bIngreso.setSerie_comprobante(serie);
                bIngreso.setNum_comprobante(numero);
                bIngreso.setFecha_hora(fecha);
                bIngreso.setImpuesto(igvC);
                bIngreso.setTotal_compra(totalC);
                int idBuyNew = mIngreso.Insertar(bIngreso);
                int registroExitoso = 0;
                int cantArt = modalIngreso.tblDetalle.getRowCount();
                if (idBuyNew != 0) {
                    int i = 0;
                    while (i < cantArt) {
                        int idIngreso = idBuyNew;
                        int idArticulo = Integer.parseInt(modalIngreso.tblDetalle.getValueAt(i, 0).toString());
                        int cantidad = Integer.parseInt(modalIngreso.tblDetalle.getValueAt(i, 5).toString());
                        double precioCompra = Double.parseDouble(modalIngreso.tblDetalle.getValueAt(i, 6).toString());

                        bBuyD.setIdingreso(idIngreso);
                        bBuyD.setIdarticulo(idArticulo);
                        bBuyD.setCantidad(cantidad);
                        bBuyD.setPrecio_compra(precioCompra);

                        if (mIngreso.InsertarDetalle(bBuyD) == true) {
                            registroExitoso++;
                        }
                        i++;
                    }
                    if (registroExitoso == cantArt) {
                        JOptionPane.showMessageDialog(modalIngreso, "COMPRA EXITOSA");
                    } else {
                        JOptionPane.showMessageDialog(modalIngreso, "FALLO AL REGISTRAR COMPRA");
                    }

                } else {
                    JOptionPane.showMessageDialog(modalIngreso, "No se pudo registrar ingreso");
                }
                listar(frmingreso.tblIngresos);
            } catch (SQLException ex) {
                Logger.getLogger(IngresoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void listarDetalle(JTable tabla, int idIngreso) {
        try {
            List<bDetalleIngreso> listaDetalleI = mIngreso.ExtraerDetalle(idIngreso);
            dtm = (DefaultTableModel) tabla.getModel();
            dtm.setRowCount(0);
            Object[] objeto = new Object[8];
            for (int i = 0; i < listaDetalleI.size(); i++) {
                objeto[0] = listaDetalleI.get(i).idarticulo;
                objeto[1] = i + 1;
                objeto[2] = listaDetalleI.get(i).pCodigo;
                objeto[3] = listaDetalleI.get(i).producto;
                objeto[4] = listaDetalleI.get(i).pCategoria;
                objeto[5] = listaDetalleI.get(i).cantidad;
                objeto[6] = listaDetalleI.get(i).precio_compra;
                objeto[7] = listaDetalleI.get(i).precio_compra * listaDetalleI.get(i).cantidad;
                dtm.addRow(objeto);
                int tDI = dtm.getRowCount();
                Double total = calcularTotal(tDI);
                Double subTotal = calcularSubTotal(total);
                Double igv = calcularIgv(total, subTotal);
                modalIngreso.lblIgv.setText(igv.toString());
                modalIngreso.lblSubTotal.setText(subTotal.toString());
                modalIngreso.lblTotal.setText(total.toString());
            }
            tabla.setModel(dtm);
            for (int c = 0; c < tabla.getColumnCount(); c++) {
                Class<?> col_class = tabla.getColumnClass(c);
                tabla.setDefaultEditor(col_class, null); // remove editor
            }
            DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
            headerRenderer.setBackground(new Color(0, 0, 102));
            headerRenderer.setForeground(new Color(255, 255, 255));

            for (int i = 0; i < tabla.getModel().getColumnCount(); i++) {
                tabla.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
            }
        } catch (SQLException ex) {
            Logger.getLogger(IngresoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void tablaDetalle(JTable ingresoDetalle) {
        dtm = (DefaultTableModel) ingresoDetalle.getModel();
        Combo itemCat = (Combo) modalBuscarP.txtProducto.getSelectedItem();
        int idProduc = itemCat.getId();
        String producName = itemCat.getName();
        Object[] objeto = new Object[8];
        ingresoDetalle.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
        ingresoDetalle.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
        ingresoDetalle.getTableHeader().getColumnModel().getColumn(1).setMaxWidth(40);
        ingresoDetalle.getTableHeader().getColumnModel().getColumn(1).setMinWidth(40);
        ingresoDetalle.getTableHeader().getColumnModel().getColumn(2).setMaxWidth(120);
        ingresoDetalle.getTableHeader().getColumnModel().getColumn(2).setMinWidth(120);
        ingresoDetalle.getTableHeader().getColumnModel().getColumn(5).setMaxWidth(50);
        ingresoDetalle.getTableHeader().getColumnModel().getColumn(5).setMinWidth(50);
        objeto[0] = idProduc;
        objeto[1] = dtm.getRowCount() + 1;
        objeto[2] = modalBuscarP.txtCodigo.getText();
        objeto[3] = producName;
        objeto[4] = modalBuscarP.txtCategoria.getText();
        objeto[5] = modalBuscarP.txtCantidad.getText();
        objeto[6] = modalBuscarP.txtPrecio.getText();
        objeto[7] = modalBuscarP.txtSubTotal.getText();
        dtm.addRow(objeto);
        int tDI = dtm.getRowCount();
        Double total = calcularTotal(tDI);
        Double subTotal = calcularSubTotal(total);
        Double igv = calcularIgv(total, subTotal);
        modalIngreso.lblIgv.setText(igv.toString());
        modalIngreso.lblSubTotal.setText(subTotal.toString());
        modalIngreso.lblTotal.setText(total.toString());
        poputTableDetalle();
    }

    public Double calcularTotal(int tbl) {
        double total = 0.00;
        for (int i = 0; i < tbl; i++) {
            total += Double.valueOf(modalIngreso.tblDetalle.getValueAt(i, 7).toString());
        }
        System.out.println(total);
        String precioT = "";
        String[] parts = String.valueOf(df.format(total)).split("\\.");
        String part1 = parts[0];
        System.out.println("part1: " + part1);
        String part2 = parts[1];
        System.out.println("part2: " + part2);
        if (part1.length() > 0) {
            precioT = part1 + ".";
        } else {
            precioT = "0.";
        }

        if (part2.length() < 2) {
            precioT = precioT + part2 + "0";
        } else {
            precioT = precioT + part2;
        }
        return Double.parseDouble(precioT);
    }

    private Double calcularSubTotal(Double total) {
        double subT = total / 1.18;
        return Double.parseDouble(df.format(subT));
    }

    private Double calcularIgv(double total, double subT) {
        double igv = total - subT;
        return Double.parseDouble(df.format(igv));
    }

    private void poputTableDetalle() {
        modalIngreso.MenuTbl.removeAll();
        String rutaRemove = "src/archivos/img/quitar-del-carrito.png";
        JMenuItem quitar = new JMenuItem("QUITAR");
        Imagenes.SetImagenItem(quitar, rutaRemove, 20, 20);
        modalIngreso.tblDetalle.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                modalIngreso.MenuTbl.removeAll();
                modalIngreso.MenuTbl.add(quitar);
                modalIngreso.tblDetalle.setComponentPopupMenu(modalIngreso.MenuTbl);
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
        quitar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = modalIngreso.tblDetalle.getSelectedRow();
                System.out.println(fila);
                if (fila >= 0) {
                    int rpta = JOptionPane.showConfirmDialog(modalIngreso, "¿DESEA QUITAR DE LA COMPRA DICHO PRODUCTO?", "Confirmar", JOptionPane.YES_NO_OPTION);
                    if (rpta == 0) {
                        DefaultTableModel dtmD = (DefaultTableModel) modalIngreso.tblDetalle.getModel();
                        dtmD.removeRow(fila);
                        int tDI = dtm.getRowCount();
                        Double total = calcularTotal(tDI);
                        Double subTotal = calcularSubTotal(total);
                        Double igv = calcularIgv(total, subTotal);
                        modalIngreso.lblIgv.setText(igv.toString());
                        modalIngreso.lblSubTotal.setText(subTotal.toString());
                        modalIngreso.lblTotal.setText(total.toString());
                    }
                } else {
                    JOptionPane.showMessageDialog(modalIngreso, "Por favor, seleccione una fila para poder anular");
                }
            }
        });
    }

    public void poputTable() {
        frmingreso.MenuTbl.removeAll();
        String rutaVer = "src/archivos/img/contrato.png";
        String rutaAnular = "src/archivos/img/switch-off.png";
        JMenuItem anular = new JMenuItem("ANULAR");
        JMenuItem ver = new JMenuItem("VER");
        Imagenes.SetImagenItem(ver, rutaVer, 20, 20);
        Imagenes.SetImagenItem(anular, rutaAnular, 20, 20);
        frmingreso.tblIngresos.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frmingreso.MenuTbl.removeAll();
                int row = frmingreso.tblIngresos.rowAtPoint(e.getPoint());
                String est = frmingreso.tblIngresos.getValueAt(row, 8).toString();
                if (est.equals("ACTIVA")) {
                    frmingreso.MenuTbl.add(anular);
                }
                frmingreso.MenuTbl.add(ver);
                frmingreso.tblIngresos.setComponentPopupMenu(frmingreso.MenuTbl);
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
        frmingreso.tblIngresos.setComponentPopupMenu(frmingreso.MenuTbl);
        anular.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = frmingreso.tblIngresos.getSelectedRow();
                if (fila >= 0) {
                    int idU = Integer.parseInt(frmingreso.tblIngresos.getValueAt(fila, 0).toString());
                    int rpta = JOptionPane.showConfirmDialog(frmingreso, "¿DESEA ANULAR LA COMPRA?", "Confirmar", JOptionPane.YES_NO_OPTION);
                    if (rpta == 0) {
                        cambiarestado("Anulado", idU);
                        listar(frmingreso.tblIngresos);
                    }
                } else {
                    JOptionPane.showMessageDialog(frmingreso, "Por favor, seleccione una fila para poder anular");
                }
            }
        });

        ver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int fila = frmingreso.tblIngresos.getSelectedRow();
                    if (fila >= 0) {
                        btnEdit();
                        int idU = Integer.parseInt(frmingreso.tblIngresos.getValueAt(fila, 0).toString());
                        bIngreso = mIngreso.Extraer(idU);
                        modalIngreso.lblTitulo.setText("VER COMPRA");
                        modalIngreso.txtIdIngreso.setText(String.valueOf(bIngreso.idingreso));
                        modalIngreso.lblProveedor.setText(bIngreso.proveedor);
                        modalIngreso.lblComprobante.setText(bIngreso.tipo_comprobante);
                        modalIngreso.lblSerie.setText(bIngreso.serie_comprobante);
                        modalIngreso.lblNumero.setText(bIngreso.num_comprobante);
                        //Detalle
                        listarDetalle(modalIngreso.tblDetalle, bIngreso.idingreso);
                        modalIngreso.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(frmingreso, "Por favor, seleccione una fila para poder ver");
                    }

                } catch (HeadlessException | NumberFormatException | SQLException ex) {
                    Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private void btnEdit() {
        modalIngreso.btnAgregarProducto.setVisible(false);
        modalIngreso.btnGuardar.setVisible(false);
        modalIngreso.btnCancelar.setText("REGRESAR");
        modalIngreso.txtComprobante.setVisible(false);
        modalIngreso.txtNumero.setVisible(false);
        modalIngreso.txtProveedor.setVisible(false);
        modalIngreso.txtSerie.setVisible(false);
        modalIngreso.lblComprobante.setVisible(true);
        modalIngreso.lblNumero.setVisible(true);
        modalIngreso.lblProveedor.setVisible(true);
        modalIngreso.lblSerie.setVisible(true);
    }

    private void btnNuevo() {
        modalIngreso.lblTitulo.setText("NUEVA COMPRA");
        modalIngreso.btnAgregarProducto.setVisible(true);
        modalIngreso.btnGuardar.setVisible(true);
        modalIngreso.btnCancelar.setText("CANCELAR");
        modalIngreso.txtComprobante.setVisible(true);
        modalIngreso.txtNumero.setVisible(true);
        modalIngreso.txtProveedor.setVisible(true);
        modalIngreso.txtSerie.setVisible(true);
        modalIngreso.lblComprobante.setVisible(false);
        modalIngreso.lblNumero.setVisible(false);
        modalIngreso.lblProveedor.setVisible(false);
        modalIngreso.lblSerie.setVisible(false);
    }

    public void limpiar() {
        modalIngreso.txtSerie.setText("");
        modalIngreso.txtNumero.setText("");
        modalIngreso.lblIgv.setText("");
        modalIngreso.lblSubTotal.setText("");
        modalIngreso.lblTotal.setText("");
    }

    public void cambiarestado(String accion, int id) {
        try {
            if (mIngreso.Anular(id) == true) {
                JOptionPane.showMessageDialog(frmingreso, "EL ESTADO DE LA COMPRA CAMBIO A " + accion.toUpperCase());
            } else {
                JOptionPane.showMessageDialog(frmingreso, "NO SE LOGRO CAMBIAR EL ESTADO DE LA COMPRA");
            }
            listar(frmingreso.tblIngresos);
        } catch (SQLException ex) {
            Logger.getLogger(IngresoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //evento frmIngreso
        if (e.getSource().equals(frmingreso.btnNuevo)) {
            btnNuevo();
            limpiar();
            modalIngreso.setVisible(true);
        }

        //evento modalIngreso
        if (e.getSource().equals(modalIngreso.btnAgregarProducto)) {
            modalBuscarP.setVisible(true);
        }

        if (e.getSource().equals(modalIngreso.btnCancelar)) {
            modalIngreso.setVisible(false);
            limpiar();
            //Limpiar tabla detalle
            dtm = (DefaultTableModel) modalIngreso.tblDetalle.getModel();
            dtm.setRowCount(0);
        }

        if (e.getSource().equals(modalIngreso.btnGuardar)) {
            Guardar();
            limpiar();
            modalIngreso.setVisible(false);
        }

        //Modal ingreso de productos
        if (e.getSource().equals(modalBuscarP.btnCancelarProd)) {
            modalBuscarP.setVisible(false);
        }

        if (e.getSource().equals(modalBuscarP.btnAgregarProd)) {

            tablaDetalle(modalIngreso.tblDetalle);
            modalBuscarP.setVisible(false);
            limpiarAgregarProd();
        }
        if (e.getSource().equals(frmingreso.txtFecha)) {
            listar(frmingreso.tblIngresos);
        }
    }

    private void limpiarAgregarProd() {
        modalBuscarP.txtCantidad.setText("0");
        modalBuscarP.txtCategoria.setText("");
        modalBuscarP.txtCodigo.setText("");
        modalBuscarP.txtPrecio.setText("");
        modalBuscarP.txtSubTotal.setText("");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource().equals(frmingreso.txtFecha)) {
            listar(frmingreso.tblIngresos);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getSource().equals(frmingreso.txtFecha)) {
            listar(frmingreso.tblIngresos);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getSource().equals(frmingreso.txtBuscar)) {
            listar(frmingreso.tblIngresos);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource().equals(modalBuscarP.txtCantidad) || e.getSource().equals(modalBuscarP.txtSubTotal)) {
            int cant;
            Double subt, precioC;
            if (modalBuscarP.txtCantidad.getText().equals("")) {
                cant = 0;
            } else {
                cant = Integer.parseInt(modalBuscarP.txtCantidad.getText());
            }

            if (modalBuscarP.txtSubTotal.getText().equals("")) {
                subt = 0.00;
            } else {
                subt = Double.parseDouble(modalBuscarP.txtSubTotal.getText());
            }
            precioC = subt / cant;
            String precioCom = "";
            String[] parts = String.valueOf(df.format(precioC)).split("\\.");
            String part1 = parts[0];
            String part2 = parts[1];
            if (part1.length() > 0) {
                precioCom = part1 + ".";
            } else {
                precioCom = "0.";
            }

            if (part2.length() < 2) {
                precioCom = precioCom + part2 + "0";
            } else {
                precioCom = precioCom + part2;
            }
            modalBuscarP.txtPrecio.setText(precioCom);
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource().equals(frmingreso.SelectBuscar)) {
            if (frmingreso.SelectBuscar.getSelectedItem().equals("FECHA")) {
                frmingreso.txtBuscar.setVisible(false);
                frmingreso.txtFecha.setVisible(true);
            } else {
                frmingreso.txtFecha.setVisible(false);
                frmingreso.txtBuscar.setVisible(true);
            }
            frmingreso.txtBuscar.setText("");
            frmingreso.txtFecha.setCalendar(null);
            listar(frmingreso.tblIngresos);
        }

        if (e.getSource().equals(modalBuscarP.txtProducto)) {
            try {
                Combo itemCat = (Combo) modalBuscarP.txtProducto.getSelectedItem();
                int id;
                if (itemCat != null) {
                    id = itemCat.getId();
                    bArticulo ba = mArt.Extraer(id);
                    modalBuscarP.txtCodigo.setText(ba.codigo);
                    modalBuscarP.txtCategoria.setText(ba.NomCat);
                }
            } catch (SQLException ex) {
                Logger.getLogger(IngresoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        listar(frmingreso.tblIngresos);
    }
}
