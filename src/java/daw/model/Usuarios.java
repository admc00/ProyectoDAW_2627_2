/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daw.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author FX506
 */
@Entity
@Table(name = "usuarios")
@NamedQueries({
    @NamedQuery(name = "Usuarios.findAll", query = "SELECT u From Usuarios u"),
    @NamedQuery(name = "Usuarios.findByEmail", query = "SELECT u FROM Usuarios u WHERE u.email =:email")

})
public class Usuarios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Usuarios(String nombre, String email, String contraseña, String perfilUrl, String Bibliografía) {
        this.nombre = nombre;
        this.email = email;
        this.contraseña = contraseña;
        this.perfilUrl = perfilUrl;
        this.Bibliografía = Bibliografía;
    }

    public Usuarios(String nombre, String email, String contraseña) {
        this.nombre = nombre;
        this.email = email;
        this.contraseña = contraseña;

    }

    public Usuarios() {
    }

    private String nombre;
    private String email;
    private String contraseña;
    private String perfilUrl;
    private String Bibliografía;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getPerfilUrl() {
        return perfilUrl;
    }

    public void setPerfilUrl(String perfilUrl) {
        this.perfilUrl = perfilUrl;
    }

    public String getBibliografía() {
        return Bibliografía;
    }

    public void setBibliografía(String Bibliografía) {
        this.Bibliografía = Bibliografía;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Reseña> getResenas() {
        return resenas;
    }

    public void setResenas(List<Reseña> resenas) {
        this.resenas = resenas;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }
    
    
    
    //Relaciones
    
    @OneToMany(mappedBy = "usuarios")
    private List<Reseña> resenas;
    
    @OneToMany(mappedBy = "usuarios")
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
        if (!(object instanceof Usuarios)) {
            return false;
        }
        Usuarios other = (Usuarios) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "daw.model.Usuarios[ id=" + id + " ]";
    }

}
