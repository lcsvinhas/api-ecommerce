package org.serratec.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.serratec.backend.entity.Usuario;
import org.serratec.backend.entity.UsuarioPerfil;

import java.util.HashSet;
import java.util.Set;

public class UsuarioRequestDTO {
    @NotBlank
    private String nome;
    @Email
    private String email;
    @NotBlank
    private String senha;
    @NotBlank
    @Pattern(regexp = "^\\d{8}$", message = "Somente números e até 8 caracteres")
    private String cep;

    private Set<UsuarioPerfil> perfis = new HashSet<>();

    public UsuarioRequestDTO() {
    }

    public UsuarioRequestDTO(Usuario usuario) {
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.senha = usuario.getSenha();
    }


    public UsuarioRequestDTO(String nome, String email, String senha, String cep, Set<UsuarioPerfil> perfis) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.cep = cep;
        this.perfis = perfis;
    }

    public @Pattern(regexp = "^\\d{8}$", message = "somente números e até 8 caracteres") String getCep() {
        return cep;
    }

    public void setCep(@Pattern(regexp = "^\\d{8}$", message = "somente números e até 8 caracteres") String cep) {
        this.cep = cep;
    }

    public void setPerfis(Set<UsuarioPerfil> perfis) {
        this.perfis = perfis;
    }

    public Set<UsuarioPerfil> getPerfis() {
        return perfis;
    }

    public @NotBlank String getNome() {
        return nome;
    }

    public void setNome(@NotBlank String nome) {
        this.nome = nome;
    }

    public @Email String getEmail() {
        return email;
    }

    public void setEmail(@Email String email) {
        this.email = email;
    }

    public @NotBlank String getSenha() {
        return senha;
    }

    public void setSenha(@NotBlank String senha) {
        this.senha = senha;
    }
}
