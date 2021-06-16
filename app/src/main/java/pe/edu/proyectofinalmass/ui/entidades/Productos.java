package pe.edu.proyectofinalmass.ui.entidades;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Productos implements Serializable {
    private Integer idproducto;
    private String producto;
    private String precio;
    private String marca;
    private String categoria;
    private Bitmap imagen;
    private String rutaImagen;
    private String stock;
    private String vendidos;

    public Integer getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(Integer idproducto) {
        this.idproducto = idproducto;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getVendidos() {
        return vendidos;
    }

    public void setVendidos(String vendidos) {
        this.vendidos = vendidos;
    }
}
