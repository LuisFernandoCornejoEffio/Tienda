/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import Auxiliar.Fecha;
import Beans.bDetalleVenta;
import Beans.bVenta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author USUARIO
 */
public class mVenta {

    Conexion cn = new Conexion();

    //Metodo total de articulos
    public int totalVentasHoy() throws SQLException {
        Connection con = cn.Conectar();
        PreparedStatement pst = null;
        ResultSet rs = null;
        int total;
        String fechaInicio = Fecha.getDiaActual();
        String fechaFin = Fecha.getDiaSig();
        try {
            String sql = "select count(*) as total from venta WHERE fecha_hora BETWEEN '" + fechaInicio + "' and '" + fechaFin + "'";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                total = Integer.parseInt(rs.getString("total"));
                return total;
            }
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (pst != null) {
                pst.close();
            }
            cn.Desconectar(con);
        }
        return 0;
    }

    public bVenta Extraer(int id) throws SQLException {
        Connection con = cn.Conectar();
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "SELECT v.idventa, v.idcliente, v.idusuario, v.tipo_comprobante, v.serie_comprobante, v.num_comprobante, v.fecha_hora, v.impuesto, v.total_venta, v.estado, "
                + "p.nombre as cliente, u.nombre as usuario FROM venta v INNER JOIN persona p ON v.idcliente = p.idpersona INNER JOIN usuario u ON v.idusuario = u.idusuario "
                + "WHERE v.idventa = ?";
        bVenta bVenta = new bVenta();
        try {
            con = cn.Conectar();
            pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            while (rs.next()) {
                bVenta.setIdVenta(rs.getInt("v.idventa"));
                bVenta.setIdCliente(rs.getInt("v.idcliente"));
                bVenta.setTipo_comprobante(rs.getString("v.tipo_comprobante"));
                bVenta.setSerie_comprobante(rs.getString("v.serie_comprobante"));
                bVenta.setNum_comprobante(rs.getString("v.num_comprobante"));
                bVenta.setTotal_venta(rs.getDouble("v.total_venta"));
                bVenta.setImpuesto(rs.getDouble("v.impuesto"));
                bVenta.setCliente(rs.getString("cliente"));
                //Mostrar detalle

                return bVenta;
            }
            return null;
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (pst != null) {
                pst.close();
            }
            cn.Desconectar(con);
        }
    }

    //Mostrar detalle
    public List<bDetalleVenta> ExtraerDetalle(int id) throws SQLException {
        Connection con = cn.Conectar();
        PreparedStatement pst = null;
        ResultSet rs = null;
        List<bDetalleVenta> Lista = null;
        try {
            String sql = "SELECT dv.iddetalle_venta, dv.idventa, dv.idarticulo, dv.cantidad, dv.precio_venta, a.nombre as producto, a.codigo as pCodigo, "
                    + "a.idcategoria as idcategoria, c.nombre as categoria FROM detalle_venta dv INNER JOIN articulo a ON dv.idarticulo = a.idarticulo INNER JOIN categoria c "
                    + "ON c.idcategoria = a.idcategoria WHERE dv.idventa = " + id;
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            Lista = new ArrayList();
            while (rs.next()) {
                bDetalleVenta bDetalleV = new bDetalleVenta();
                bDetalleV.setIdDetalle_venta(rs.getInt("dv.iddetalle_venta"));
                bDetalleV.setIdVenta(rs.getInt("dv.idventa"));
                bDetalleV.setIdArticulo(rs.getInt("dv.idarticulo"));
                bDetalleV.setCantidad(rs.getInt("dv.cantidad"));
                bDetalleV.setPrecio_venta(rs.getDouble("dv.precio_venta"));
                bDetalleV.setProducto(rs.getString("producto"));
                bDetalleV.setpCodigo(rs.getString("pCodigo"));
                bDetalleV.setIdCategoriap(rs.getInt("idcategoria"));
                bDetalleV.setpCategoria(rs.getString("categoria"));
                Lista.add(bDetalleV);
            }
            return Lista;
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (pst != null) {
                pst.close();
            }
            cn.Desconectar(con);
        }
    }

    //Metodo Listar
    public List<bVenta> Listar(String buscar, String campo) throws SQLException {
        Connection con = cn.Conectar();
        PreparedStatement pst = null;
        ResultSet rs = null;
        List<bVenta> Lista = null;
        try {
            String sql;
            if (buscar.equals("")) {
                sql = "SELECT v.idventa, v.idcliente, v.idusuario, v.tipo_comprobante, v.serie_comprobante, v.num_comprobante, v.fecha_hora, v.impuesto, v.total_venta, v.estado, "
                        + "p.nombre as cliente, u.nombre as usuario FROM venta v INNER JOIN persona p ON v.idcliente = p.idpersona INNER JOIN usuario u ON v.idusuario = u.idusuario";
            } else {
                sql = "SELECT v.idventa, v.idcliente, v.idusuario, v.tipo_comprobante, v.serie_comprobante, v.num_comprobante, v.fecha_hora, v.impuesto, v.total_venta, v.estado, "
                        + "p.nombre as cliente, u.nombre as usuario FROM venta v INNER JOIN persona p ON v.idcliente = p.idpersona INNER JOIN usuario u ON v.idusuario = u.idusuario "
                        + "WHERE " + campo + " LIKE '" + buscar + "%'";
            }
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            Lista = new ArrayList();
            while (rs.next()) {
                bVenta bVenta = new bVenta();
                bVenta.setIdVenta(rs.getInt("v.idventa"));
                bVenta.setIdCliente(rs.getInt("v.idcliente"));
                bVenta.setIdUsuario(rs.getInt("v.idusuario"));
                bVenta.setTipo_comprobante(rs.getString("v.tipo_comprobante"));
                bVenta.setSerie_comprobante(rs.getString("v.serie_comprobante"));
                bVenta.setNum_comprobante(rs.getString("v.num_comprobante"));
                bVenta.setFecha_hora(rs.getString("v.fecha_hora"));
                bVenta.setImpuesto(rs.getDouble("v.impuesto"));
                bVenta.setTotal_venta(rs.getDouble("v.total_venta"));
                bVenta.setEstado(rs.getString("v.estado"));
                bVenta.setCliente(rs.getString("cliente"));
                bVenta.setUsuario(rs.getString("usuario"));
                Lista.add(bVenta);
            }
            return Lista;
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (pst != null) {
                pst.close();
            }
            cn.Desconectar(con);
        }
    }

    //Metodo Insertar
    public int Insertar(bVenta bVenta) throws SQLException {
        Connection con = cn.Conectar();
        PreparedStatement pst = null;
        ResultSet rs = null;
        int id = 0;
        try {
            String sql = "INSERT INTO venta (idventa, idcliente, idusuario, tipo_comprobante, serie_comprobante, num_comprobante, fecha_hora, impuesto, total_venta, estado) "
                    + "VALUES (null, ?, ?, ?, ?, ?, ?, ?, ?, 'activa')";
            pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, bVenta.getIdCliente());
            pst.setInt(2, bVenta.getIdUsuario());
            pst.setString(3, bVenta.getTipo_comprobante());
            pst.setString(4, bVenta.getSerie_comprobante());
            pst.setString(5, bVenta.getNum_comprobante());
            pst.setString(6, bVenta.getFecha_hora());
            pst.setDouble(7, bVenta.getImpuesto());
            pst.setDouble(8, bVenta.getTotal_venta());
            pst.executeUpdate();

            rs = pst.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
                System.out.println(id);
                return id;
            } else {
                System.out.println("Error - registro");
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            cn.Desconectar(con);
        }
        return id;
    }

    public boolean InsertarDetalle(bDetalleVenta bDetalleV) throws SQLException {
        Connection con = cn.Conectar();
        PreparedStatement pst = null;
        int N = 0;
        try {
            String sql = "INSERT INTO detalle_venta (iddetalle_venta, idventa, idarticulo, cantidad, precio_venta) "
                    + "VALUES (null, ?, ?, ?, ?)";
            pst = con.prepareStatement(sql);
            pst.setInt(1, bDetalleV.getIdVenta());
            pst.setInt(2, bDetalleV.getIdArticulo());
            pst.setInt(3, bDetalleV.getCantidad());
            pst.setDouble(4, bDetalleV.getPrecio_venta());

            N = pst.executeUpdate();
            if (N != 0) {
                System.out.println("Registrado con exito");
                return true;
            } else {
                System.out.println("Error - registro");
            }
        } finally {
            if (pst != null) {
                pst.close();
            }
            cn.Desconectar(con);
        }
        return false;
    }

    public boolean Anular(int idIngreso) throws SQLException {
        Connection con = cn.Conectar();
        PreparedStatement pst = null;
        int N = 0;
        try {
            String sql = "UPDATE venta SET estado= 'Anulado' WHERE idventa = ?";
            pst = con.prepareStatement(sql);
            pst.setInt(1, idIngreso);
            N = pst.executeUpdate();
            if (N != 0) {
                System.out.println("Anulado con exito");
                return true;
            } else {
                System.out.println("No se logro anular");
                return false;
            }
        } finally {
            if (pst != null) {
                pst.close();
            }
            cn.Desconectar(con);
        }
    }

    public String num_comp(String tipo) throws SQLException {
        Connection con = cn.Conectar();
        PreparedStatement pst = null;
        ResultSet rs = null;
        String num_comp, cer = "";
        try {
            String sql = "select * FROM venta WHERE tipo_comprobante = '" + tipo + "' ORDER BY num_comprobante DESC LIMIT 1";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                num_comp = String.valueOf(Integer.parseInt(rs.getString("num_comprobante")) + 1);
                if (num_comp.length() < 4) {
                    int dif = 4 - num_comp.length();
                    for (int i = 0; i < dif; i++) {
                        cer += "0";
                    }
                }
                num_comp = cer + num_comp;
                return num_comp;
            }
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (pst != null) {
                pst.close();
            }
            cn.Desconectar(con);
        }
        return null;
    }    
}
