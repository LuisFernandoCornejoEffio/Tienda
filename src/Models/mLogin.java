/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import Beans.bUsuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author USUARIO
 */
public class mLogin {

    Conexion cn = new Conexion();

    public bUsuario Login(String email, String clave) throws SQLException {
        Connection con = cn.Conectar();
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM usuario WHERE email = ? AND clave = ? AND condicion = 'ACTIVO'";
        bUsuario bUser = new bUsuario();
        try {
            con = cn.Conectar();
            pst = con.prepareStatement(sql);
            pst.setString(1, email);
            pst.setString(2, clave);
            rs = pst.executeQuery();
            while(rs.next()){
                bUser.setIdUsuario(rs.getInt("idusuario"));
                bUser.setNombre(rs.getString("nombre"));
                bUser.setTipo_documento(rs.getString("tipo_documento"));
                bUser.setNum_documento(rs.getString("num_documento"));
                bUser.setDireccion(rs.getString("direccion"));
                bUser.setTelefono(rs.getString("telefono"));
                bUser.setCargo(rs.getString("cargo"));
                bUser.setEmail(rs.getString("email"));                
                bUser.setImagen(rs.getString("imagen"));
                bUser.setCondicion(rs.getString("condicion"));
                return bUser;
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
}
