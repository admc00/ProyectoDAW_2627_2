/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daw.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author admc0
 */
@Entity
@Table(name = "reseña")
public class Reseña implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    String texto;
    float puntuacion;
    String fecha_reseña;

    public Reseña(String texto, float puntuacion, String fecha_reseña) {
        this.texto = texto;
        this.puntuacion = puntuacion;
        this.fecha_reseña = fecha_reseña;
    }

    public Reseña() {
    }
    
    

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public float getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(float puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getFecha_reseña() {
        return fecha_reseña;
    }

    public void setFecha_reseña(String fecha_reseña) {
        this.fecha_reseña = fecha_reseña;
    }
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuarios getUsuario() {
        return usuarios;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuarios = usuario;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }
    
    
    
     //Relaciones
    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuarios usuarios;
    
    @ManyToOne
    @JoinColumn(name = "idPelicula")
    private Pelicula pelicula;
    
    @OneToMany(mappedBy = "resena")
    private List<Comentario> comentarios;
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reseña)) {
            return false;
        }
        Reseña other = (Reseña) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "daw.model.Rese\u00f1a[ id=" + id + " ]";
    }
    
}
