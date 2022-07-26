/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Beans;

/**
 *
 * @author USUARIO
 */
public class bIngreso {
    public int idingreso;
    public int idproveedor;
    public int idusuario;
    public String tipo_comprobante;
    public String serie_comprobante;
    public String num_comprobante;
    public String fecha_hora;
    public Double impuesto;
    public Double total_compra;
    public String estado;
    public String proveedor;
    public String usuario;

    public bIngreso() {
    }

    public bIngreso(int idingreso, int idproveedor, int idusuario, String tipo_comprobante, String serie_comprobante, String num_comprobante, String fecha_hora, Double impuesto, Double total_compra, String estado, String proveedor, String usuario) {
        this.idingreso = idingreso;
        this.idproveedor = idproveedor;
        this.idusuario = idusuario;
        this.tipo_comprobante = tipo_comprobante;
        this.serie_comprobante = serie_comprobante;
        this.num_comprobante = num_comprobante;
        this.fecha_hora = fecha_hora;
        this.impuesto = impuesto;
        this.total_compra = total_compra;
        this.estado = estado;
        this.proveedor = proveedor;
        this.usuario = usuario;
    }

    public int getIdingreso() {
        return idingreso;
    }

    public void setIdingreso(int idingreso) {
        this.idingreso = idingreso;
    }

    public int getIdproveedor() {
        return idproveedor;
    }

    public void setIdproveedor(int idproveedor) {
        this.idproveedor = idproveedor;
    }

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public String getTipo_comprobante() {
        return tipo_comprobante;
    }

    public void setTipo_comprobante(String tipo_comprobante) {
        this.tipo_comprobante = tipo_comprobante;
    }

    public String getSerie_comprobante() {
        return serie_comprobante;
    }

    public void setSerie_comprobante(String serie_comprobante) {
        this.serie_comprobante = serie_comprobante;
    }

    public String getNum_comprobante() {
        return num_comprobante;
    }

    public void setNum_comprobante(String num_comprobante) {
        this.num_comprobante = num_comprobante;
    }

    public String getFecha_hora() {
        return fecha_hora;
    }

    public void setFecha_hora(String fecha_hora) {
        this.fecha_hora = fecha_hora;
    }

    public Double getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(Double impuesto) {
        this.impuesto = impuesto;
    }

    public Double getTotal_compra() {
        return total_compra;
    }

    public void setTotal_compra(Double total_compra) {
        this.total_compra = total_compra;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
}
