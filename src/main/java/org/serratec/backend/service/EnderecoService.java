package org.serratec.backend.service;

import org.serratec.backend.dto.EnderecoResponseDTO;
import org.serratec.backend.entity.Endereco;
import org.serratec.backend.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class EnderecoService {
    @Autowired
    private EnderecoRepository repository;

    public EnderecoResponseDTO buscar(String cep) {
        var endereco = Optional.ofNullable(repository.findByCep(cep));

        if (endereco.isPresent()) {
            return new EnderecoResponseDTO(endereco.get());
        } else {
            RestTemplate rs = new RestTemplate();
            String url = "http://viacep.com.br/ws/" + cep + "/json/";
            Optional<Endereco> enderecoViaCep = Optional.ofNullable(rs.getForObject(url, Endereco.class));

            if (enderecoViaCep.isPresent()) {
                String cepSemTraco = enderecoViaCep.get().getCep().replaceAll("-", "");
                enderecoViaCep.get().setCep(cepSemTraco);
                return inserir(enderecoViaCep.get());
            }
        }
        throw new HttpServerErrorException(HttpStatus.NOT_FOUND);
    }

    private EnderecoResponseDTO inserir(Endereco endereco) {
        return new EnderecoResponseDTO(repository.save(endereco));
    }

    public Endereco buscarEndereco(String cep) {
        return repository.findByCep(cep);
    }
}
