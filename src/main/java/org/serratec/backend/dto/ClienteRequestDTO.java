package org.serratec.backend.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;
import org.serratec.backend.entity.Cliente;

public class ClienteRequestDTO {

    private Long id;

    @NotBlank
    @Size(min = 2, max = 50)
    private String nome;

    @Pattern(regexp = "^\\d{12}$", message = "digite os 12 números do telefone")
    private String telefone;

    @Email
    private String email;

    @Pattern(regexp = "^\\d{8}$", message = "digite os 8 números do cep sem pontos e traços")
    private String cep;

    @CPF
    @Column(unique = true)
    private String cpf;

    public ClienteRequestDTO() {
    }

    public ClienteRequestDTO(Cliente cliente) {
        this.id = cliente.getId();
        this.nome = cliente.getNome();
        this.telefone = cliente.getTelefone();
        this.email = cliente.getEmail();
        this.cpf = cliente.getCpf();
    }

    public ClienteRequestDTO(Long id, String nome, String telefone, String email, String cep, String cpf) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.cep = cep;
        this.cpf = cpf;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank @Size(min = 2, max = 50) String getNome() {
        return nome;
    }

    public void setNome(@NotBlank @Size(min = 2, max = 50) String nome) {
        this.nome = nome;
    }

    public @Pattern(regexp = "^\\d{12}$", message = "digite os 12 números do telefone") String getTelefone() {
        return telefone;
    }

    public void setTelefone(@Pattern(regexp = "^\\d{12}$", message = "digite os 12 números do telefone") String telefone) {
        this.telefone = telefone;
    }

    public @Email String getEmail() {
        return email;
    }

    public void setEmail(@Email String email) {
        this.email = email;
    }

    public @Pattern(regexp = "^\\d{8}$", message = "digite os 8 números do cep sem pontos e traços") String getCep() {
        return cep;
    }

    public void setCep(@Pattern(regexp = "^\\d{8}$", message = "digite os 8 números do cep sem pontos e traços") String cep) {
        this.cep = cep;
    }

    public @CPF String getCpf() {
        return cpf;
    }

    public void setCpf(@CPF String cpf) {
        this.cpf = cpf;
    }
}
