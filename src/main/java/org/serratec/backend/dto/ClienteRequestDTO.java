package org.serratec.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.serratec.backend.entity.Cliente;

public class ClienteRequestDTO {
    @NotBlank
    @Size(min = 2, max = 50)
    private String nome;

    @Pattern(regexp = "^\\d{12}$", message = "Somente números e DDD")
    private String telefone;

    @Email
    private String email;

    @Pattern(regexp = "^\\d{8}$", message = "Somente números e até 8 caracteres")
    private String cep;

    public ClienteRequestDTO(Cliente cliente) {
        this.nome = cliente.getNome();
        this.telefone = cliente.getTelefone();
        this.email = cliente.getEmail();
    }

    public ClienteRequestDTO(String nome, String telefone, String email, String cep) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.cep = cep;
    }

    public @NotBlank @Size(min = 2, max = 50) String getNome() {
        return nome;
    }

    public void setNome(@NotBlank @Size(min = 2, max = 50) String nome) {
        this.nome = nome;
    }

    public @Pattern(regexp = "^\\d{12}$", message = "Somente números e DDD") String getTelefone() {
        return telefone;
    }

    public void setTelefone(@Pattern(regexp = "^\\d{12}$", message = "Somente números e DDD") String telefone) {
        this.telefone = telefone;
    }

    public @Email String getEmail() {
        return email;
    }

    public void setEmail(@Email String email) {
        this.email = email;
    }

    public @Pattern(regexp = "^\\d{8}$", message = "Somente números e até 8 caracteres") String getCep() {
        return cep;
    }

    public void setCep(@Pattern(regexp = "^\\d{8}$", message = "Somente números e até 8 caracteres") String cep) {
        this.cep = cep;
    }
}
