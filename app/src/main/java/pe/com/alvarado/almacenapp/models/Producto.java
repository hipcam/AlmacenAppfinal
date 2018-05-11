package pe.com.alvarado.almacenapp.models;

/**
 * Created by Alumno on 4/05/2018.
 */

public class Producto {

    private Integer id;
    private String nombre;
    private String precio;
    private String imagen;
    private String detalles;
    private String estado;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", precio='" + precio + '\'' +
                ", imagen='" + imagen + '\'' +
                ", detalles='" + detalles + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}
