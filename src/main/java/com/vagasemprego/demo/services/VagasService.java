package com.vagasemprego.demo.services;

import com.vagasemprego.demo.dtos.VagasRequestDTO;
import com.vagasemprego.demo.dtos.VagasResponseDTO;
import com.vagasemprego.demo.exceptions.EntityVagasNotFoundException;
import com.vagasemprego.demo.mappers.VagasMapper;
import com.vagasemprego.demo.models.Usuario;
import com.vagasemprego.demo.models.Vagas;
import com.vagasemprego.demo.models.enuns.Contrato;
import com.vagasemprego.demo.models.enuns.Interesse;
import com.vagasemprego.demo.models.enuns.Situacao;
import com.vagasemprego.demo.models.enuns.Tipo;
import com.vagasemprego.demo.repositories.VagasRepository;
import com.vagasemprego.demo.security.JWTCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.vagasemprego.demo.utils.ValidationUtil.validateIdOrThrowException;

@Service
public class VagasService {

    private final VagasRepository vagasRepository;
    private final JWTCreator jwtTokenProvider;
    private final SessaoService authService;

    public VagasService(VagasRepository vagasRepository, JWTCreator jwtTokenProvider, SessaoService authService) {
        this.vagasRepository = vagasRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authService = authService;
    }

   /* @Transactional(readOnly = true)
    public List<VagasResponseDTO> findAll() {
        return vagasRepository.findAll()
                .stream()
                .map(VagasMapper::toDto)
                .collect(Collectors.toList());
    }*/

    @Transactional(readOnly = true)
    public List<VagasResponseDTO> findVagasByUser() {
        Usuario currentUser = jwtTokenProvider.getCurrentUser();
        return vagasRepository.findByUsuario(currentUser)
                .stream()
                .map(VagasMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<VagasResponseDTO> findByUsuarioIdAndSituacao(String situacao) {
        Long userId = jwtTokenProvider.getCurrentUser().getId();
        return vagasRepository.findByUsuarioIdAndSituacao(userId,Situacao.valueOf(situacao.toUpperCase()))
                .stream()
                .map(VagasMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<VagasResponseDTO> findByUsuarioIdAndInteresse(String interesse) {
        Long userId = jwtTokenProvider.getCurrentUser().getId();
        return vagasRepository.findByUsuarioIdAndInteresse(userId, Interesse.valueOf(interesse.toUpperCase()))
                .stream()
                .map(VagasMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<VagasResponseDTO> findByUsuarioIdAndContrato(String contrato) {
        Long userId = jwtTokenProvider.getCurrentUser().getId();
        return vagasRepository.findByUsuarioIdAndContrato(userId, Contrato.valueOf(contrato.toUpperCase()))
                .stream()
                .map(VagasMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<VagasResponseDTO> findByUsuarioIdAndTipo(String tipo) {
        Long userId = jwtTokenProvider.getCurrentUser().getId();
        return vagasRepository.findByUsuarioIdAndTipo(userId, Tipo.valueOf(tipo.toUpperCase()))
                .stream()
                .map(VagasMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public VagasResponseDTO create(VagasRequestDTO vagasDTO) {

        Usuario currentUser = jwtTokenProvider.getCurrentUser();

        Vagas vagasToSave = new Vagas();
        vagasToSave.setUsuario(currentUser);
        vagasToSave.setVaga(vagasDTO.vaga());
        vagasToSave.setContrato(Contrato.valueOf(vagasDTO.contrato()));
        vagasToSave.setBeneficios(vagasDTO.beneficios());
        vagasToSave.setEmpresa(vagasDTO.empresa());
        vagasToSave.setInscricao(LocalDateTime.now());
        vagasToSave.setInteresse(Interesse.valueOf(vagasDTO.interesse()));
        vagasToSave.setTipo(Tipo.valueOf(vagasDTO.tipo()));
        vagasToSave.setSalario(vagasDTO.salario());
        vagasToSave.setSituacao(Situacao.valueOf(vagasDTO.situacao()));
        vagasToSave.setOrigem(vagasDTO.origem());
        vagasToSave.setObservacoes(vagasDTO.observacoes());

        Vagas savedVagas = vagasRepository.save(vagasToSave);

        return VagasMapper.toDto(savedVagas);
    }

    @Transactional
    public VagasResponseDTO update(Long id, VagasRequestDTO vagasDTO) {
        validateIdOrThrowException(id);

        Vagas vagasToSave = vagasRepository.findById(id).orElseThrow(
                () -> new EntityVagasNotFoundException(id));

        authService.validateUserOwnership(vagasToSave.getUsuario());


        if (vagasDTO != null){
            vagasToSave.setVaga(vagasDTO.vaga());
            vagasToSave.setContrato(Contrato.valueOf(vagasDTO.contrato()));
            vagasToSave.setBeneficios(vagasDTO.beneficios());
            vagasToSave.setEmpresa(vagasDTO.empresa());
            vagasToSave.setInscricao(LocalDateTime.now());
            vagasToSave.setInteresse(Interesse.valueOf(vagasDTO.interesse()));
            vagasToSave.setTipo(Tipo.valueOf(vagasDTO.tipo()));
            vagasToSave.setSalario(vagasDTO.salario());
            vagasToSave.setSituacao(Situacao.valueOf(vagasDTO.situacao()));
            vagasToSave.setOrigem(vagasDTO.origem());
            vagasToSave.setObservacoes(vagasDTO.observacoes());
        }

        Vagas savedVagas = vagasRepository.save(vagasToSave);
        return VagasMapper.toDto(savedVagas);
    }

    @Transactional
    public void delete(Long id) {
        validateIdOrThrowException(id);

        Vagas vagasById = vagasRepository.findById(id).orElseThrow(
                () -> new EntityVagasNotFoundException(id));

        authService.validateUserOwnership(vagasById.getUsuario());
        vagasRepository.delete(vagasById);
    }




}
