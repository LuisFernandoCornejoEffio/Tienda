/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Beans;

/**
 *
 * @author USUARIO
 */
public class bDetalleVenta {
    public int idDetalle_venta;
    public int idVenta;
    public int idArticulo;
    public int cantidad;
    public Double precio_venta;
    public Double descuento;
    public String producto;
    public String pCodigo;
    public int idCategoriap;
    public String pCategoria;

    public int getIdDetalle_venta() {
        return idDetalle_venta;
    }

    public void setIdDetalle_venta(int idDetalle_venta) {
        this.idDetalle_venta = idDetalle_venta;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public int getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(int idArticulo) {
        this.idArticulo = idArticulo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecio_venta() {
        return precio_venta;
    }

    public void setPrecio_venta(Double precio_venta) {
        this.precio_venta = precio_venta;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getpCodigo() {
        return pCodigo;
    }

    public void setpCodigo(String pCodigo) {
        this.pCodigo = pCodigo;
    }

    public int getIdCategoriap() {
        return idCategoriap;
    }

    public void setIdCategoriap(int idCategoriap) {
        this.idCategoriap = idCategoriap;
    }

    public String getpCategoria() {
        return pCategoria;
    }

    public void setpCategoria(String pCategoria) {
        this.pCategoria = pCategoria;
    }

    public Double getDescuento() {
        return descuento;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }
    
}
