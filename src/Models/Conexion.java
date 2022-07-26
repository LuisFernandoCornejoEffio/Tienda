/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author USUARIO
 */
public class Conexion {

    public String db = "dbmitienda";
    public String url = "jdbc:mysql://localhost:3306/" + db;
    public String user = "root";
    public String pass = "";
    Connection cn = null;

    public Connection Conectar() {
        try {
            //Cargare el driver de la conexion
            Class.forName("org.gjt.mm.mysql.Driver");
            //Creo un enlace hacia las Base de datos
            return DriverManager.getConnection(this.url, this.user, this.pass);
        } //Clic en el mensaje para decirle que sean excepciones especificas.
        catch (ClassNotFoundException | SQLException e) {
            //Mostrando mensaje del posible error que tenga
            JOptionPane.showMessageDialog(null, "Error de acceso:\n No hay conexion a la base de datos:\n" + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    public void Desconectar(Connection cn) throws SQLException {
        if (cn != null) {
            cn.close();
        }
    }
}
