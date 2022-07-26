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
import Beans.bDetalleVenta;
import Beans.bPersona;
import Beans.bVenta;
import Models.mArticulo;
import Models.mPersona;
import Models.mVenta;
import Views.Formularios.frmModal;
import Views.Formularios.frmVentas;
import Views.Modal.ModalProductoVenta;
import Views.Modal.ModalVenta;
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
public class VentaController implements MouseListener, KeyListener, ItemListener, PropertyChangeListener {

    private frmVentas frmVentas;
    private bVenta bVenta;
    private mVenta mVenta;
    bDetalleVenta bVentaD = new bDetalleVenta();
    frmModal frmModal = new frmModal();
    ModalVenta modalVenta = new ModalVenta(frmModal, true);
    mPersona mProv = new mPersona();
    mArticulo mArt = new mArticulo();
    ModalProductoVenta modalProductoV = new ModalProductoVenta(frmModal, true);
    DefaultTableModel dtm = new DefaultTableModel();
    DecimalFormat df = new DecimalFormat("#.00");

    public VentaController(frmVentas frmVentas, bVenta bVenta, mVenta mVenta) {
        this.frmVentas = frmVentas;
        this.bVenta = bVenta;
        this.mVenta = mVenta;
        ComboCliente(0);
        ComboProducto(0);
        iconBotones();
        listar(frmVentas.tblVentas);
        //Eventos frm Ingresos
        this.frmVentas.btnNuevo.addMouseListener(this);
        this.frmVentas.SelectBuscar.addItemListener(this);
        this.frmVentas.txtBuscar.setVisible(false);
        this.frmVentas.lblUser.setVisible(true);
        this.frmVentas.txtFecha.setVisible(false);
        this.frmVentas.txtBuscar.setVisible(true);
        this.frmVentas.txtBuscar.addKeyListener(this);
        this.frmVentas.txtFecha.addPropertyChangeListener(this);
        //Eventos frm modal ingresos
        modalVenta.btnAgregarProducto.addMouseListener(this);
        modalVenta.btnCancelar.addMouseListener(this);
        modalVenta.btnGuardar.addMouseListener(this);
        modalVenta.txtComprobante.addItemListener(this);
        btnNuevo();
        modalVenta.txtIdIngreso.setVisible(false);
        //Eventos frm modal productos
        modalProductoV.txtProducto.addItemListener(this);
        modalProductoV.txtCantidad.addKeyListener(this);
        modalProductoV.txtSubTotal.addKeyListener(this);
        modalProductoV.btnAgregarProd.addMouseListener(this);
        modalProductoV.btnCancelarProd.addMouseListener(this);
    }

    private void ComboCliente(int select) {
        try {
            modalVenta.txtCliente.removeAllItems();
            List<bPersona> lista = mProv.Listar("", "", "CLIENTE");
            for (int i = 0; i < lista.size(); i++) {
                modalVenta.txtCliente.addItem(new Combo(lista.get(i).idPersona, lista.get(i).nombre));
                if (select == 0) {
                    modalVenta.txtCliente.setSelectedItem(new Combo(lista.get(i).idPersona));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ArticuloController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void comprobante(String tipo) {
        System.out.println("tipo: " + tipo);
        if (tipo.equals("BOLETA")) {
            modalVenta.lblSerie.setText("B-01");
        }
        if (tipo.equals("FACTURA")) {
            modalVenta.lblSerie.setText("F-01");
        }
        if (tipo.equals("TICKET")) {
            modalVenta.lblSerie.setText("T-01");
        }
        try {

            modalVenta.lblNumero.setText(mVenta.num_comp(tipo));
        } catch (SQLException ex) {
            Logger.getLogger(VentaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void ComboProducto(int select) {
        try {
            modalProductoV.txtProducto.removeAllItems();
            List<bArticulo> lista = mArt.Listar("", "");
            for (int i = 0; i < lista.size(); i++) {
                modalProductoV.txtProducto.addItem(new Combo(lista.get(i).idArticulo, lista.get(i).nombre));
                if (select == 0) {
                    modalProductoV.txtProducto.setSelectedItem(new Combo(lista.get(i).idArticulo));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ArticuloController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void iconBotones() {
        Imagenes.SetImagenBtn(frmVentas.btnNuevo, "src/archivos/img/anadir.png", 30, 30);
        Imagenes.SetImagenBtn(modalVenta.btnCancelar, "src/archivos/img/cancel.png", 30, 30);
        Imagenes.SetImagenBtn(modalVenta.btnGuardar, "src/archivos/img/salvar.png", 30, 30);
    }

    private void listar(JTable tabla) {
        try {
            String buscar = frmVentas.txtBuscar.getText();
            String campo = frmVentas.SelectBuscar.getSelectedItem().toString();
            if (campo.equals("USUARIO")) {
                campo = "u.nombre";
            }
            if (campo.equals("CLIENTE")) {
                campo = "p.nombre";
            }
            if (campo.equals("FECHA")) {
                int año, dia;
                String mes;
                if (frmVentas.txtFecha.getCalendar() != null) {
                    campo = "fecha_hora";
                    año = frmVentas.txtFecha.getCalendar().get(Calendar.YEAR);
                    mes = String.valueOf(frmVentas.txtFecha.getCalendar().get(Calendar.MONTH) + 1);
                    if (mes.length() == 1) {
                        mes = "0" + mes;
                    }
                    dia = frmVentas.txtFecha.getCalendar().get(Calendar.DAY_OF_MONTH);
                    buscar = año + "-" + mes + "-" + dia;
                }
            }
            List<bVenta> listaVenta = mVenta.Listar(buscar, campo);
            TablaFormat(listaVenta, tabla);
        } catch (SQLException ex) {
            Logger.getLogger(IngresoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void TablaFormat(List<bVenta> lista, JTable tabla) {
        dtm = (DefaultTableModel) tabla.getModel();
        dtm.setRowCount(0);
        Object[] objeto = new Object[9];
        for (int i = 0; i < lista.size(); i++) {
            objeto[0] = lista.get(i).idVenta;
            objeto[1] = i + 1;
            objeto[2] = lista.get(i).fecha_hora;
            objeto[3] = lista.get(i).cliente;
            objeto[4] = lista.get(i).usuario;
            objeto[5] = lista.get(i).tipo_comprobante;
            objeto[6] = lista.get(i).serie_comprobante + " - " + lista.get(i).num_comprobante;
            objeto[7] = lista.get(i).total_venta;
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

    public void poputTable() {
        frmVentas.MenuTbl.removeAll();
        String rutaVer = "src/archivos/img/contrato.png";
        String rutaAnular = "src/archivos/img/switch-off.png";
        JMenuItem anular = new JMenuItem("ANULAR");
        JMenuItem ver = new JMenuItem("VER");
        Imagenes.SetImagenItem(ver, rutaVer, 20, 20);
        Imagenes.SetImagenItem(anular, rutaAnular, 20, 20);
        frmVentas.tblVentas.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frmVentas.MenuTbl.removeAll();
                int row = frmVentas.tblVentas.rowAtPoint(e.getPoint());
                String est = frmVentas.tblVentas.getValueAt(row, 8).toString();
                if (est.equals("ACTIVA")) {
                    frmVentas.MenuTbl.add(anular);
                }
                frmVentas.MenuTbl.add(ver);
                frmVentas.tblVentas.setComponentPopupMenu(frmVentas.MenuTbl);
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
        frmVentas.tblVentas.setComponentPopupMenu(frmVentas.MenuTbl);
        anular.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = frmVentas.tblVentas.getSelectedRow();
                if (fila >= 0) {
                    int idU = Integer.parseInt(frmVentas.tblVentas.getValueAt(fila, 0).toString());
                    int rpta = JOptionPane.showConfirmDialog(frmVentas, "¿DESEA ANULAR LA VENTA?", "Confirmar", JOptionPane.YES_NO_OPTION);
                    if (rpta == 0) {
                        cambiarestado("Anulado", idU);
                        listar(frmVentas.tblVentas);
                    }
                } else {
                    JOptionPane.showMessageDialog(frmVentas, "Por favor, seleccione una fila para poder anular");
                }
            }
        });

        ver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int fila = frmVentas.tblVentas.getSelectedRow();
                    if (fila >= 0) {
                        btnEdit();
                        int idU = Integer.parseInt(frmVentas.tblVentas.getValueAt(fila, 0).toString());
                        bVenta = mVenta.Extraer(idU);
                        modalVenta.lblTitulo.setText("VER VENTA");
                        modalVenta.txtIdIngreso.setText(String.valueOf(bVenta.idVenta));
                        modalVenta.lblProveedor.setText(bVenta.cliente);
                        modalVenta.lblComprobante.setText(bVenta.tipo_comprobante);
                        modalVenta.lblSerie.setText(bVenta.serie_comprobante);
                        modalVenta.lblNumero.setText(bVenta.num_comprobante);
                        modalVenta.btnGuardar.setText("IMPRIMIR");
                        Imagenes.SetImagenBtn(modalVenta.btnGuardar, "src/archivos/img/impresora.png", 30, 30);
                        //Detalle
                        listarDetalle(modalVenta.tblDetalle, bVenta.idVenta);
                        modalVenta.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(frmVentas, "Por favor, seleccione una fila para poder ver");
                    }

                } catch (HeadlessException | NumberFormatException | SQLException ex) {
                    Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private void btnEdit() {
        modalVenta.btnAgregarProducto.setVisible(false);
        modalVenta.btnGuardar.setVisible(true);
        modalVenta.btnCancelar.setText("REGRESAR");
        modalVenta.txtComprobante.setVisible(false);
        modalVenta.lblComprobante.setVisible(true);
        modalVenta.lblProveedor.setVisible(true);
        modalVenta.txtCliente.setVisible(false);

    }

    private void btnNuevo() {
        modalVenta.lblTitulo.setText("NUEVA COMPRA");
        modalVenta.btnAgregarProducto.setVisible(true);
        modalVenta.btnGuardar.setVisible(true);
        modalVenta.btnCancelar.setText("CANCELAR");
        modalVenta.txtComprobante.setVisible(true);
        modalVenta.lblComprobante.setVisible(false);
        modalVenta.lblProveedor.setVisible(false);
        modalVenta.txtCliente.setVisible(true);
    }

    public void limpiar() {
        modalVenta.lblIgv.setText("");
        modalVenta.lblSubTotal.setText("");
        modalVenta.lblTotal.setText("");
        dtm = (DefaultTableModel) modalVenta.tblDetalle.getModel();
        dtm.setRowCount(0);
    }

    public void cambiarestado(String accion, int id) {
        try {
            if (mVenta.Anular(id) == true) {
                JOptionPane.showMessageDialog(frmVentas, "EL ESTADO DE LA VENTA CAMBIO A " + accion.toUpperCase());
            } else {
                JOptionPane.showMessageDialog(frmVentas, "NO SE LOGRO CAMBIAR EL ESTADO DE LA COMPRA");
            }
            listar(frmVentas.tblVentas);
        } catch (SQLException ex) {
            Logger.getLogger(IngresoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void listarDetalle(JTable tabla, int idIngreso) {
        try {
            List<bDetalleVenta> listaDetalleV = mVenta.ExtraerDetalle(idIngreso);
            dtm = (DefaultTableModel) tabla.getModel();
            dtm.setRowCount(0);
            Object[] objeto = new Object[8];
            for (int i = 0; i < listaDetalleV.size(); i++) {
                objeto[0] = listaDetalleV.get(i).idArticulo;
                objeto[1] = i + 1;
                objeto[2] = listaDetalleV.get(i).pCodigo;
                objeto[3] = listaDetalleV.get(i).producto;
                objeto[4] = listaDetalleV.get(i).pCategoria;
                objeto[5] = listaDetalleV.get(i).cantidad;
                objeto[6] = listaDetalleV.get(i).precio_venta;
                objeto[7] = listaDetalleV.get(i).precio_venta * listaDetalleV.get(i).cantidad;
                dtm.addRow(objeto);
                int tDI = dtm.getRowCount();
                Double total = calcularTotal(tDI);
                Double subTotal = calcularSubTotal(total);
                Double igv = calcularIgv(total, subTotal);
                modalVenta.lblIgv.setText(igv.toString());
                modalVenta.lblSubTotal.setText(subTotal.toString());
                modalVenta.lblTotal.setText(total.toString());
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
        Combo itemCat = (Combo) modalProductoV.txtProducto.getSelectedItem();
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
        objeto[2] = modalProductoV.txtCodigo.getText();
        objeto[3] = producName;
        objeto[4] = modalProductoV.txtCategoria.getText();
        objeto[5] = modalProductoV.txtCantidad.getText();
        objeto[6] = modalProductoV.txtPrecio.getText();
        objeto[7] = modalProductoV.txtSubTotal.getText();
        dtm.addRow(objeto);
        int tDI = dtm.getRowCount();
        Double total = calcularTotal(tDI);
        Double subTotal = calcularSubTotal(total);
        Double igv = calcularIgv(total, subTotal);
        modalVenta.lblIgv.setText(igv.toString());
        modalVenta.lblSubTotal.setText(subTotal.toString());
        modalVenta.lblTotal.setText(total.toString());
        poputTableDetalle();
    }

    public Double calcularTotal(int tbl) {
        double total = 0.00;
        for (int i = 0; i < tbl; i++) {
            total += Double.valueOf(modalVenta.tblDetalle.getValueAt(i, 7).toString());
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
        modalVenta.MenuTbl.removeAll();
        String rutaRemove = "src/archivos/img/quitar-del-carrito.png";
        JMenuItem quitar = new JMenuItem("QUITAR");
        Imagenes.SetImagenItem(quitar, rutaRemove, 20, 20);
        modalVenta.tblDetalle.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                modalVenta.MenuTbl.removeAll();
                modalVenta.MenuTbl.add(quitar);
                modalVenta.tblDetalle.setComponentPopupMenu(modalVenta.MenuTbl);
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
                int fila = modalVenta.tblDetalle.getSelectedRow();
                System.out.println(fila);
                if (fila >= 0) {
                    int rpta = JOptionPane.showConfirmDialog(modalVenta, "¿DESEA QUITAR DE LA VENTA DICHO PRODUCTO?", "Confirmar", JOptionPane.YES_NO_OPTION);
                    if (rpta == 0) {
                        DefaultTableModel dtmD = (DefaultTableModel) modalVenta.tblDetalle.getModel();
                        dtmD.removeRow(fila);
                        int tDI = dtm.getRowCount();
                        Double total = calcularTotal(tDI);
                        Double subTotal = calcularSubTotal(total);
                        Double igv = calcularIgv(total, subTotal);
                        modalVenta.lblIgv.setText(igv.toString());
                        modalVenta.lblSubTotal.setText(subTotal.toString());
                        modalVenta.lblTotal.setText(total.toString());
                    }
                } else {
                    JOptionPane.showMessageDialog(modalVenta, "Por favor, seleccione una fila para poder anular");
                }
            }
        });
    }

    private void limpiarAgregarProd() {
        modalProductoV.txtCantidad.setText("");
        modalProductoV.txtCategoria.setText("");
        modalProductoV.txtCodigo.setText("");
        modalProductoV.txtPrecio.setText("");
        modalProductoV.txtSubTotal.setText("0.00");
        Combo itemCat = (Combo) modalProductoV.txtProducto.getSelectedItem();
        int id;
        if (itemCat != null) {
            try {
                id = itemCat.getId();
                bArticulo ba = mArt.Extraer(id);
                modalProductoV.txtCodigo.setText(ba.codigo);
                modalProductoV.txtCategoria.setText(ba.NomCat);
                modalProductoV.txtPrecio.setText(String.valueOf(ba.precio));
            } catch (SQLException ex) {
                Logger.getLogger(VentaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void Guardar() {
        //Recibiendo datos de formulario
        Combo itemProveedor = (Combo) modalVenta.txtCliente.getSelectedItem();
        int idCliente = itemProveedor.getId();
        int idUsuario = Integer.parseInt(frmVentas.lblUser.getText());
        String comprobante = modalVenta.txtComprobante.getSelectedItem().toString();
        String serie = modalVenta.lblSerie.getText();
        String numero = modalVenta.lblNumero.getText();
        String fecha = Fecha.getFechaHora();
        double igvC = Double.parseDouble(modalVenta.lblIgv.getText());
        double totalC = Double.parseDouble(modalVenta.lblTotal.getText());
        if (serie.equals("") || numero.equals("")) {
            JOptionPane.showMessageDialog(modalVenta, "Todos los campos con * son obligatorios");
        } else {
            try {
                bVenta.setIdCliente(idCliente);
                bVenta.setIdUsuario(idUsuario);
                bVenta.setTipo_comprobante(comprobante);
                bVenta.setSerie_comprobante(serie);
                bVenta.setNum_comprobante(numero);
                bVenta.setFecha_hora(fecha);
                bVenta.setImpuesto(igvC);
                bVenta.setTotal_venta(totalC);
                int idVentaNew = mVenta.Insertar(bVenta);
                int registroExitoso = 0;
                int cantArt = modalVenta.tblDetalle.getRowCount();
                if (idVentaNew != 0) {
                    int i = 0;
                    while (i < cantArt) {
                        int idVenta = idVentaNew;
                        int idArticulo = Integer.parseInt(modalVenta.tblDetalle.getValueAt(i, 0).toString());
                        int cantidad = Integer.parseInt(modalVenta.tblDetalle.getValueAt(i, 5).toString());
                        double precioVenta = Double.parseDouble(modalVenta.tblDetalle.getValueAt(i, 6).toString());

                        bVentaD.setIdVenta(idVenta);
                        bVentaD.setIdArticulo(idArticulo);
                        bVentaD.setCantidad(cantidad);
                        bVentaD.setPrecio_venta(precioVenta);

                        if (mVenta.InsertarDetalle(bVentaD) == true) {
                            registroExitoso++;
                        }
                        i++;
                    }
                    if (registroExitoso == cantArt) {
                        JOptionPane.showMessageDialog(modalVenta, "VENTA EXITOSA");
                    } else {
                        JOptionPane.showMessageDialog(modalVenta, "FALLO AL REGISTRAR VENTA");
                    }

                } else {
                    JOptionPane.showMessageDialog(modalVenta, "No se pudo registrar venta");
                }
                listar(frmVentas.tblVentas);
            } catch (SQLException ex) {
                Logger.getLogger(IngresoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //evento frmIngreso
        if (e.getSource().equals(frmVentas.btnNuevo)) {
            comprobante(modalVenta.txtComprobante.getSelectedItem().toString());
            btnNuevo();
            limpiar();
            modalVenta.setVisible(true);
        }

        //evento modalIngreso
        if (e.getSource().equals(modalVenta.btnAgregarProducto)) {
            Combo itemCat = (Combo) modalProductoV.txtProducto.getSelectedItem();
            int id;
            if (itemCat != null) {
                try {
                    id = itemCat.getId();
                    bArticulo ba = mArt.Extraer(id);
                    modalProductoV.txtCodigo.setText(ba.codigo);
                    modalProductoV.txtCategoria.setText(ba.NomCat);
                    modalProductoV.txtPrecio.setText(String.valueOf(ba.precio));
                    modalProductoV.txtCantidad.setText("0");
                    int cant = Integer.parseInt(modalProductoV.txtCantidad.getText());
                    double precioV = Double.parseDouble(modalProductoV.txtPrecio.getText());
                    double subt = cant * precioV;
                    modalProductoV.txtSubTotal.setText(df.format(subt));
                } catch (SQLException ex) {
                    Logger.getLogger(VentaController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            modalProductoV.setVisible(true);
        }

        if (e.getSource().equals(modalVenta.btnCancelar)) {
            modalVenta.setVisible(false);
            limpiar();
            //Limpiar tabla detalle
            dtm = (DefaultTableModel) modalVenta.tblDetalle.getModel();
            dtm.setRowCount(0);
        }

        if (e.getSource().equals(modalVenta.btnGuardar)) {
            if (modalVenta.btnGuardar.getText().equals("GUARDAR")) {
                Guardar();
                limpiar();
                modalVenta.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(frmVentas, "SOY EL BTN IMPRIMIR");
            }

        }

        //Modal ingreso de productos
        if (e.getSource().equals(modalProductoV.btnCancelarProd)) {
            modalProductoV.setVisible(false);
        }

        if (e.getSource().equals(modalProductoV.btnAgregarProd)) {
            tablaDetalle(modalVenta.tblDetalle);
            modalProductoV.setVisible(false);
            limpiarAgregarProd();
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
        if (e.getSource().equals(modalProductoV.txtCantidad)) {
            int cant;
            Double subt, precioC;
            if (modalProductoV.txtCantidad.getText().equals("")) {
                cant = 0;
            } else {
                cant = Integer.parseInt(modalProductoV.txtCantidad.getText());
            }
        }

        if (e.getSource().equals(frmVentas.txtBuscar)) {
            listar(frmVentas.tblVentas);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource().equals(modalProductoV.txtCantidad)) {
            int cant;
            Double subt, precioV;
            if (modalProductoV.txtCantidad.getText().equals("")) {
                cant = 0;
            } else {
                cant = Integer.parseInt(modalProductoV.txtCantidad.getText());
            }
            precioV = Double.parseDouble(modalProductoV.txtPrecio.getText());
            subt = cant * precioV;
            modalProductoV.txtSubTotal.setText(df.format(subt));
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource().equals(frmVentas.SelectBuscar)) {
            if (frmVentas.SelectBuscar.getSelectedItem().equals("FECHA")) {
                frmVentas.txtBuscar.setVisible(false);
                frmVentas.txtFecha.setVisible(true);
            } else {
                frmVentas.txtFecha.setVisible(false);
                frmVentas.txtBuscar.setVisible(true);
            }
            frmVentas.txtBuscar.setText("");
            frmVentas.txtFecha.setCalendar(null);
            listar(frmVentas.tblVentas);
        }

        if (e.getSource().equals(modalVenta.txtComprobante)) {
            comprobante(modalVenta.txtComprobante.getSelectedItem().toString());
        }

        if (e.getSource().equals(modalProductoV.txtProducto)) {
            try {
                Combo itemCat = (Combo) modalProductoV.txtProducto.getSelectedItem();
                int id;
                if (itemCat != null) {
                    id = itemCat.getId();
                    bArticulo ba = mArt.Extraer(id);
                    modalProductoV.txtCodigo.setText(ba.codigo);
                    modalProductoV.txtCategoria.setText(ba.NomCat);
                    modalProductoV.txtPrecio.setText(String.valueOf(ba.precio));

                }
            } catch (SQLException ex) {
                Logger.getLogger(IngresoController.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        listar(frmVentas.tblVentas);
    }
}
