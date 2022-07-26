/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import Auxiliar.Fecha;
import Beans.bDetalleIngreso;
import Beans.bIngreso;
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
public class mIngreso {

    Conexion cn = new Conexion();
    mCategoria mCategoria = new mCategoria();

    //Metodo total de articulos
    public int totalComprasHoy() throws SQLException {
        Connection con = cn.Conectar();
        PreparedStatement pst = null;
        ResultSet rs = null;
        int total;
        String fechaInicio = Fecha.getDiaActual();
        String fechaFin = Fecha.getDiaSig();
        try {
            String sql = "select count(*) as total from ingreso WHERE fecha_hora BETWEEN '" + fechaInicio + "' and '" + fechaFin + "'";
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

    public bIngreso Extraer(int id) throws SQLException {
        Connection con = cn.Conectar();
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "SELECT i.idingreso, i.idproveedor, i.idusuario, i.tipo_comprobante, i.serie_comprobante, i.num_comprobante, i.fecha_hora, i.impuesto, i.total_compra, i.estado, "
                + "p.nombre as proveedor, u.nombre as usuario FROM ingreso i INNER JOIN persona p ON i.idproveedor = p.idpersona INNER JOIN usuario u ON i.idusuario = u.idusuario "
                + "WHERE i.idingreso = ?";
        bIngreso bIngreso = new bIngreso();
        try {
            con = cn.Conectar();
            pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            while (rs.next()) {
                bIngreso.setIdingreso(rs.getInt("i.idingreso"));
                bIngreso.setIdproveedor(rs.getInt("i.idproveedor"));
                bIngreso.setTipo_comprobante(rs.getString("i.tipo_comprobante"));
                bIngreso.setSerie_comprobante(rs.getString("i.serie_comprobante"));
                bIngreso.setNum_comprobante(rs.getString("i.num_comprobante"));
                bIngreso.setTotal_compra(rs.getDouble("i.total_compra"));
                bIngreso.setImpuesto(rs.getDouble("i.impuesto"));
                bIngreso.setProveedor(rs.getString("proveedor"));
                //Mostrar detalle

                return bIngreso;
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
    public List<bDetalleIngreso> ExtraerDetalle(int id) throws SQLException {
        Connection con = cn.Conectar();
        PreparedStatement pst = null;
        ResultSet rs = null;
        List<bDetalleIngreso> Lista = null;
        try {
            String sql = "SELECT di.iddetalle_ingreso, di.idingreso, di.idarticulo, di.cantidad, di.precio_compra, a.nombre as producto, a.codigo as pCodigo, a.idcategoria as idcategoria, "
                    + "c.nombre as categoria FROM detalle_ingreso di INNER JOIN articulo a ON di.idarticulo = a.idarticulo INNER JOIN categoria c ON c.idcategoria = a.idcategoria "
                    + "WHERE di.idingreso = " + id;
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            Lista = new ArrayList();
            while (rs.next()) {
                bDetalleIngreso bDetalleI = new bDetalleIngreso();
                bDetalleI.setIddetalle_ingreso(rs.getInt("di.iddetalle_ingreso"));
                bDetalleI.setIdingreso(rs.getInt("di.idingreso"));
                bDetalleI.setIdarticulo(rs.getInt("di.idarticulo"));
                bDetalleI.setCantidad(rs.getInt("di.cantidad"));
                bDetalleI.setPrecio_compra(rs.getDouble("di.precio_compra"));
                bDetalleI.setProducto(rs.getString("producto"));
                bDetalleI.setpCodigo(rs.getString("pCodigo"));
                bDetalleI.setIdCategoriap(rs.getInt("idcategoria"));
                bDetalleI.setpCategoria(rs.getString("categoria"));
                Lista.add(bDetalleI);
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
    public List<bIngreso> Listar(String buscar, String campo) throws SQLException {
        Connection con = cn.Conectar();
        PreparedStatement pst = null;
        ResultSet rs = null;
        List<bIngreso> Lista = null;
        try {
            String sql;
            if (buscar.equals("")) {
                sql = "SELECT i.idingreso, i.idproveedor, i.idusuario, i.tipo_comprobante, i.serie_comprobante, i.num_comprobante, i.fecha_hora, i.impuesto, i.total_compra, i.estado, "
                        + "p.nombre as proveedor, u.nombre as usuario FROM ingreso i INNER JOIN persona p ON i.idproveedor = p.idpersona INNER JOIN usuario u ON i.idusuario = u.idusuario";
            } else {
                sql = "SELECT i.idingreso, i.idproveedor, i.idusuario, i.tipo_comprobante, i.serie_comprobante, i.num_comprobante, i.fecha_hora, i.impuesto, i.total_compra, i.estado, "
                        + "p.nombre as proveedor, u.nombre as usuario FROM ingreso i INNER JOIN persona p ON i.idproveedor = p.idpersona INNER JOIN usuario u ON i.idusuario = u.idusuario "
                        + "WHERE " + campo + " LIKE '" + buscar + "%'";
            }
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            Lista = new ArrayList();
            while (rs.next()) {
                bIngreso bIngreso = new bIngreso();
                bIngreso.setIdingreso(rs.getInt("i.idingreso"));
                bIngreso.setIdproveedor(rs.getInt("i.idproveedor"));
                bIngreso.setIdusuario(rs.getInt("i.idusuario"));
                bIngreso.setTipo_comprobante(rs.getString("i.tipo_comprobante"));
                bIngreso.setSerie_comprobante(rs.getString("i.serie_comprobante"));
                bIngreso.setNum_comprobante(rs.getString("i.num_comprobante"));
                bIngreso.setFecha_hora(rs.getString("i.fecha_hora"));
                bIngreso.setImpuesto(rs.getDouble("i.impuesto"));
                bIngreso.setTotal_compra(rs.getDouble("i.total_compra"));
                bIngreso.setEstado(rs.getString("i.estado"));
                bIngreso.setProveedor(rs.getString("proveedor"));
                bIngreso.setUsuario(rs.getString("usuario"));
                Lista.add(bIngreso);
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
    public int Insertar(bIngreso bIngreso) throws SQLException {
        Connection con = cn.Conectar();
        PreparedStatement pst = null;
        ResultSet rs = null;
        int id = 0;
        try {
            String sql = "INSERT INTO ingreso (idingreso, idproveedor, idusuario, tipo_comprobante, serie_comprobante, num_comprobante, fecha_hora, impuesto, total_compra, estado) "
                    + "VALUES (null, ?, ?, ?, ?, ?, ?, ?, ?, 'activa')";
            pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, bIngreso.getIdproveedor());
            pst.setInt(2, bIngreso.getIdusuario());
            pst.setString(3, bIngreso.getTipo_comprobante());
            pst.setString(4, bIngreso.getSerie_comprobante());
            pst.setString(5, bIngreso.getNum_comprobante());
            pst.setString(6, bIngreso.getFecha_hora());
            pst.setDouble(7, bIngreso.getImpuesto());
            pst.setDouble(8, bIngreso.getTotal_compra());
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

    public boolean InsertarDetalle(bDetalleIngreso bDetalleI) throws SQLException {
        Connection con = cn.Conectar();
        PreparedStatement pst = null;
        int N = 0;
        try {
            String sql = "INSERT INTO detalle_ingreso (iddetalle_ingreso, idingreso, idarticulo, cantidad, precio_compra) "
                    + "VALUES (null, ?, ?, ?, ?)";
            pst = con.prepareStatement(sql);
            pst.setInt(1, bDetalleI.getIdingreso());
            pst.setInt(2, bDetalleI.getIdarticulo());
            pst.setInt(3, bDetalleI.getCantidad());
            pst.setDouble(4, bDetalleI.getPrecio_compra());

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
            String sql = "UPDATE ingreso SET estado='Anulado' WHERE idingreso=?";
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
}
