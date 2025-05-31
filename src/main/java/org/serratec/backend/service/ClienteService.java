package org.serratec.backend.service;

import jakarta.transaction.Transactional;
import org.serratec.backend.config.MailConfig;
import org.serratec.backend.dto.ClienteRequestDTO;
import org.serratec.backend.dto.ClienteResponseDTO;
import org.serratec.backend.entity.Cliente;
import org.serratec.backend.entity.Endereco;
import org.serratec.backend.exception.ClienteException;
import org.serratec.backend.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoService enderecoService;

    @Autowired
    private MailConfig mailConfig;

    public List<ClienteResponseDTO> listar() {
        List<Cliente> clientes = clienteRepository.findAll();
        List<ClienteResponseDTO> clienteResponseDTOs = new ArrayList<>();

        for (Cliente cliente : clientes) {
            clienteResponseDTOs.add(new ClienteResponseDTO(cliente.getNome(), cliente.getEmail(), cliente.getTelefone(), cliente.getEndereco()));
        }
        return clienteResponseDTOs;
    }

    @Transactional
    public ClienteResponseDTO inserir(ClienteRequestDTO cliente) {
        if (clienteRepository.findByEmail(cliente.getEmail()).isPresent()) {
            throw new ClienteException("Cliente já cadastrado!");
        }

        enderecoService.buscar(cliente.getCep());
        Endereco endereco = enderecoService.buscarEndereco(cliente.getCep());

        Cliente clienteEntity = new Cliente();
        clienteEntity.setNome(cliente.getNome());
        clienteEntity.setEmail(cliente.getEmail());
        clienteEntity.setTelefone(cliente.getTelefone());
        clienteEntity.setCpf(cliente.getCpf());
        clienteEntity.setEndereco(endereco);

        clienteEntity = clienteRepository.save(clienteEntity);

        mailConfig.enviar(clienteEntity.getEmail(), "Confirmação de cadastro", cliente.toString());

        return new ClienteResponseDTO(clienteEntity.getNome(), clienteEntity.getEmail(), clienteEntity.getTelefone(), clienteEntity.getEndereco());
    }

    public ClienteResponseDTO atualizar(ClienteRequestDTO cliente) {
        Cliente clienteEntity = clienteRepository.findById(cliente.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente inexistente!"));

        enderecoService.buscar(cliente.getCep());
        Endereco endereco = enderecoService.buscarEndereco(cliente.getCep());

        clienteEntity.setNome(cliente.getNome());
        clienteEntity.setEmail(cliente.getEmail());
        clienteEntity.setTelefone(cliente.getTelefone());
        clienteEntity.setCpf(cliente.getCpf());
        clienteEntity.setEndereco(endereco);

        clienteEntity = clienteRepository.save(clienteEntity);

        mailConfig.enviar(clienteEntity.getEmail(), "Confirmação de cadastro", cliente.toString());

        return new ClienteResponseDTO(clienteEntity.getNome(), clienteEntity.getEmail(), clienteEntity.getTelefone(), clienteEntity.getEndereco());
    }

    public void deletar(Long id) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente inexistente!"));
        clienteRepository.delete(cliente);
    }
}

