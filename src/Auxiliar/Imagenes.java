/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Auxiliar;

import Views.Formularios.frmUsuarios;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author USUARIO
 */
public class Imagenes {

    public static void SetImagenLabel(JLabel labelname, String root, int w, int h) {
        ImageIcon img = new ImageIcon(root);
        Icon icon = new ImageIcon(
                img.getImage().getScaledInstance(w, h, Image.SCALE_DEFAULT)
        );
        labelname.setIcon(icon);
    }

    public static void SetImagenBtn(JButton btn, String root, int w, int h) {
        ImageIcon img = new ImageIcon(root);
        Icon icon = new ImageIcon(
                img.getImage().getScaledInstance(w, h, Image.SCALE_DEFAULT)
        );
        btn.setIcon(icon);
    }

    public static void SetImagenItem(JMenuItem item, String ruta, int w, int h) {
        try {
            BufferedImage bufferedImage = ImageIO.read(new File(ruta));
            Image image = bufferedImage.getScaledInstance(w, h, Image.SCALE_DEFAULT);
            Imagenes img = new Imagenes();
            item.setIcon(new ImageIcon(image));
        } catch (IOException ex) {
            Logger.getLogger(Imagenes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Image icon(String ruta, int w, int h) {
        try {
            BufferedImage bufferedImage = ImageIO.read(new File(ruta));
            Image image = bufferedImage.getScaledInstance(w, h, Image.SCALE_DEFAULT);
            return image;

        } catch (IOException ex) {
            Logger.getLogger(Iconos.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static void setCargarImagen(JPanel frm, JLabel label, int w, int h, JLabel lblOrigen) {
        String origen = null;
        //Abrimos el cuadro de dialogo para seleccionar imagen
        JFileChooser jfc = new JFileChooser();
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("JPG, PNG & GIF", "PNG", "GIF", "JPG");
        jfc.setFileFilter(filtro);
        int rpta = jfc.showOpenDialog(frm);
        File archivo = jfc.getSelectedFile();
        //Verificamos que seleccione una imagen
        if (rpta == JFileChooser.APPROVE_OPTION) {
            if (archivo != null) {
                //Obtenemos la ruta
                origen = archivo.getPath();
//            Mostramos la imagen en el label
                SetImagenLabel(label, origen, w, h);
                lblOrigen.setText(origen);
            } else {
                JOptionPane.showMessageDialog(frm, "POR FAVOR ESCOGA UNA IMAGEN");
            }
        }

    }

    public static void CopiarImagen(String carpeta, String nameFile, String rutaOrigen) {

        try {
            String rutaDestino = System.getProperty("user.dir") + "/src/archivos/" + carpeta + "/" + nameFile;
            Path destino = Paths.get(rutaDestino);
            Path origen = Paths.get(rutaOrigen);

            Files.copy(origen, destino, REPLACE_EXISTING);
        } catch (IOException ex) {
            Logger.getLogger(Imagenes.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
