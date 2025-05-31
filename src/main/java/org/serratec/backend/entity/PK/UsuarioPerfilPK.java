package org.serratec.backend.entity.PK;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.serratec.backend.entity.Perfil;
import org.serratec.backend.entity.Usuario;

import java.util.Objects;

@Embeddable
public class UsuarioPerfilPK {

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_perfil")
    private Perfil perfil;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UsuarioPerfilPK that = (UsuarioPerfilPK) o;
        return Objects.equals(usuario, that.usuario) && Objects.equals(perfil, that.perfil);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuario, perfil);
    }
}
