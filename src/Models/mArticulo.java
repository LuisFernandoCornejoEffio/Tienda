/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import Beans.bArticulo;
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
public class mArticulo {

    Conexion cn = new Conexion();

    public int totalArticulos() throws SQLException {
        Connection con = cn.Conectar();
        PreparedStatement pst = null;
        ResultSet rs = null;
        int total;
        try {
            String sql = "select count(*) as total from articulo";
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

    public bArticulo Extraer(int id) throws SQLException {
        Connection con = cn.Conectar();
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "SELECT a.idarticulo, a.idcategoria, a.codigo, a.nombre, a.stock, a.precio, a.descripcion, a.imagen, a.condicion, c.nombre as nomCat "
                + "FROM articulo a INNER JOIN categoria c ON a.idcategoria = c.idcategoria WHERE a.idarticulo = ?";
        bArticulo bArticulo = new bArticulo();
        try {
            con = cn.Conectar();
            pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            while (rs.next()) {
                bArticulo.setIdArticulo(rs.getInt("a.idarticulo"));
                bArticulo.setIdCategoria(rs.getInt("a.idcategoria"));
                bArticulo.setCodigo(rs.getString("a.codigo"));
                bArticulo.setNombre(rs.getString("a.nombre"));
                bArticulo.setStock(rs.getInt("a.stock"));
                bArticulo.setPrecio(rs.getDouble("a.precio"));
                bArticulo.setDescripcion(rs.getString("a.descripcion"));
                bArticulo.setImagen(rs.getString("a.imagen"));
                bArticulo.setCondicion(rs.getString("a.condicion"));
                bArticulo.setNomCat(rs.getString("nomCat"));
                return bArticulo;
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

    public int Insertar(bArticulo bArt) throws SQLException {
        Connection con = cn.Conectar();
        PreparedStatement pst = null;
        ResultSet rs = null;
        int id = 0;
        try {
            String sql = "INSERT INTO articulo (idarticulo, idcategoria, codigo, nombre, descripcion, condicion) "
                    + "VALUES (null, ? , ?, ?, ?, 'ACTIVO')";
            pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, bArt.getIdCategoria());
            pst.setString(2, bArt.getCodigo());
            pst.setString(3, bArt.getNombre());
            pst.setString(4, bArt.getDescripcion());
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

    public boolean InsertarImg(int id, String img) throws SQLException {
        Connection con = cn.Conectar();
        PreparedStatement pst = null;
        ResultSet rs = null;
        int N = 0;
        try {
            String sql = "UPDATE articulo SET imagen = ? WHERE idarticulo = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, img);
            pst.setInt(2, id);
            pst.executeUpdate();

            N = pst.executeUpdate();
            if (N != 0) {
                System.out.println("IMG MODIFICADO con exito");
                return true;
            } else {
                System.out.println("Error - Modificar IMG");
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
        return false;
    }

    public boolean Editar(bArticulo bArt) throws SQLException {
        Connection con = cn.Conectar();
        PreparedStatement pst = null;
        int N = 0;
        try {
            String sql = "UPDATE articulo SET idcategoria = ?, codigo = ?, nombre = ?, descripcion = ? WHERE idarticulo = ?";
            pst = con.prepareStatement(sql);
            pst.setInt(1, bArt.getIdCategoria());
            pst.setString(2, bArt.getCodigo());
            pst.setString(3, bArt.getNombre());
            pst.setString(4, bArt.getDescripcion());
            pst.setInt(5, bArt.getIdArticulo());
            N = pst.executeUpdate();
            if (N != 0) {
                System.out.println("Modificado con exito");
                return true;
            } else {
                System.out.println("Error - Modificar");
            }
        } finally {
            if (pst != null) {
                pst.close();
            }
            cn.Desconectar(con);
        }
        return false;
    }

    //Metodo Listar
    public List Listar(String buscar, String campo) throws SQLException {
        Connection con = cn.Conectar();
        PreparedStatement pst = null;
        ResultSet rs = null;
        List<bArticulo> ListaArticulos = new ArrayList();
        try {
            String sql = "";
            if (buscar.equals("")) {
                sql = "SELECT a.idarticulo, a.idcategoria, a.codigo, a.nombre, a.stock, a.precio, a.descripcion, a.imagen, a.condicion, c.nombre as nomCat FROM articulo a INNER JOIN categoria c ON a.idcategoria = c.idcategoria ORDER BY nombre ASC";
            } else {
                sql = "SELECT a.idarticulo, a.idcategoria, a.codigo, a.nombre, a.stock, a.precio, a.descripcion, a.imagen, a.condicion, c.nombre as nomCat FROM articulo a INNER JOIN categoria c ON a.idcategoria = c.idcategoria WHERE " + campo + " LIKE '" + buscar + "%' ORDER BY nombre ASC";
            }
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                bArticulo bArticulo = new bArticulo();
                bArticulo.setIdArticulo(rs.getInt("a.idarticulo"));
                bArticulo.setIdCategoria(rs.getInt("a.idcategoria"));
                bArticulo.setCodigo(rs.getString("a.codigo"));
                bArticulo.setNombre(rs.getString("a.nombre"));
                bArticulo.setStock(rs.getInt("a.stock"));
                bArticulo.setPrecio(rs.getDouble("a.precio"));
                bArticulo.setDescripcion(rs.getString("a.descripcion"));
                bArticulo.setImagen(rs.getString("a.imagen"));
                bArticulo.setCondicion(rs.getString("a.condicion"));
                bArticulo.setNomCat(rs.getString("nomCat"));
                ListaArticulos.add(bArticulo);
            }
            return ListaArticulos;
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

    public boolean estado(int id, String estado) {
        Connection con = cn.Conectar();
        PreparedStatement pst = null;
        try {
            String sql = "UPDATE articulo SET condicion = ? WHERE idarticulo = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, estado);
            pst.setInt(2, id);
            pst.execute();
            return true;
        } catch (SQLException e) {
            return false;
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (SQLException e) {
            }
        }
    }
}
