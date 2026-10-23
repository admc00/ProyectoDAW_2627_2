/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daw.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author admc0
 */
@Entity
@Table(name = "pelicula")
public class Pelicula implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    String titulo;
    String descripcion;
    String fecha_estreno;
    String posterUrl;
    float puntuacionMedia;

    public Pelicula() {
    }

    public Pelicula(String titulo, String descripcion, String fecha_estreno, String posterUrl, float puntuacionMedia) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha_estreno = fecha_estreno;
        this.posterUrl = posterUrl;
        this.puntuacionMedia = puntuacionMedia;
    }
    
    
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha_estreno() {
        return fecha_estreno;
    }

    public void setFecha_estreno(String fecha_estreno) {
        this.fecha_estreno = fecha_estreno;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public float getPuntuacionMedia() {
        return puntuacionMedia;
    }

    public void setPuntuacionMedia(float puntuacionMedia) {
        this.puntuacionMedia = puntuacionMedia;
    }
   
    public Long getId(){
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Reseña> getResena() {
        return resena;
    }

    public void setResena(List<Reseña> resena) {
        this.resena = resena;
    }
    
    
    
    //Relaciones
    
    @OneToMany(mappedBy = "pelicula")
    private List<Reseña> resena;

    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pelicula)) {
            return false;
        }
        Pelicula other = (Pelicula) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "daw.model.Pelicula[ id=" + id + " ]";
    }
    
}
