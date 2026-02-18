package com.vagasemprego.demo.services;
import com.vagasemprego.demo.dtos.UserRequestDTO;
import com.vagasemprego.demo.dtos.UserResponseDTO;
import com.vagasemprego.demo.exceptions.EntityUserAlreadyExistsException;
import com.vagasemprego.demo.exceptions.EntityUserNotFoundException;
import com.vagasemprego.demo.mappers.UserMapper;
import com.vagasemprego.demo.models.Usuario;
import com.vagasemprego.demo.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.vagasemprego.demo.utils.ValidationUtil.validateIdOrThrowException;


@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


   /* public void createUser(Usuario user) {
        String pass = user.getPassword();
        List<Vagas> vagas = user.getVagas();
        user.setVagas(vagas);
        user.setPassword(encoder.encode(pass));
        usuarioRepository.save(user);
    }

    //public Usuario findByUsuario(String usuario) {
    //    return usuarioRepository.findByUsuario(usuario);
    //}

    public Usuario findBySituacao(String usuario, String situacao) {
        Usuario user = usuarioRepository.findByUsuario(usuario);
        Usuario replaceuser = new Usuario();
        List<Vagas> vagas = user.getVagas();
        List<Vagas> replacevagas = new ArrayList<>();
        for (Vagas vaga : vagas) {
            if (vaga.getSituacao().equals(Situacao.valueOf(situacao.toUpperCase()))) {
                replacevagas.add(vaga);
            }
        }
        replaceuser.setUsuario(user.getUsuario());
        replaceuser.setRoles(user.getRoles());
        replaceuser.setId(user.getId());
        replaceuser.setPassword(user.getPassword());
        replaceuser.setVagas(replacevagas);

        return replaceuser;
    }

    public Usuario findByTipo(String usuario, String tipo){
        Usuario user = usuarioRepository.findByUsuario(usuario);
        Usuario replaceuser = new Usuario();
        List<Vagas> vagas = user.getVagas();
        List<Vagas> replacevagas = new ArrayList<>();
        for (Vagas vaga : vagas) {
            if (vaga.getTipo().equals(Tipo.valueOf(tipo.toUpperCase()))) {
                replacevagas.add(vaga);
            }
        }
        replaceuser.setUsuario(user.getUsuario());
        replaceuser.setRoles(user.getRoles());
        replaceuser.setId(user.getId());
        replaceuser.setPassword(user.getPassword());
        replaceuser.setVagas(replacevagas);

        return replaceuser;
    }

    public Usuario findByContrato(String usuario, String contrato){
        Usuario user = usuarioRepository.findByUsuario(usuario);
        Usuario replaceuser = new Usuario();
        List<Vagas> vagas = user.getVagas();
        List<Vagas> replacevagas = new ArrayList<>();
        for (Vagas vaga : vagas) {
            if (vaga.getContrato().equals(Contrato.valueOf(contrato.toUpperCase()))) {
                replacevagas.add(vaga);
            }
        }
        replaceuser.setUsuario(user.getUsuario());
        replaceuser.setRoles(user.getRoles());
        replaceuser.setId(user.getId());
        replaceuser.setPassword(user.getPassword());
        replaceuser.setVagas(replacevagas);

        return replaceuser;    }

    public Usuario findByInteresse(String usuario, String interesse){
        Usuario user = usuarioRepository.findByUsuario(usuario);
        Usuario replaceuser = new Usuario();
        List<Vagas> vagas = user.getVagas();
        List<Vagas> replacevagas = new ArrayList<>();
        for (Vagas vaga : vagas) {
            if (vaga.getInteresse().equals(Interesse.valueOf(interesse.toUpperCase()))) {
                replacevagas.add(vaga);
            }
        }
        replaceuser.setUsuario(user.getUsuario());
        replaceuser.setRoles(user.getRoles());
        replaceuser.setId(user.getId());
        replaceuser.setPassword(user.getPassword());
        replaceuser.setVagas(replacevagas);

        return replaceuser;

    }*/



    @Transactional(readOnly = true)
    public List<UserResponseDTO> findAll() {
        return usuarioRepository.findAll()
                .stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<UserResponseDTO> findByUsuario(String usuario) {
        return Optional.of(usuarioRepository.findByUsuario(usuario)
                .map(UserMapper::toDto)
                .orElseThrow(() -> new EntityUserNotFoundException(usuario)));
    }

    @Transactional(readOnly = true)
    public UserResponseDTO findById(Long id) {
        return usuarioRepository.findById(id)
                .map(UserMapper::toDto)
                .orElseThrow(() -> new EntityUserNotFoundException(id));
    }


    @Transactional
    public UserResponseDTO create(UserRequestDTO userRequestDTO) {
        if (usuarioRepository.existsByUsuario(userRequestDTO.username())) {
            throw new EntityUserAlreadyExistsException(userRequestDTO.username());
        }
        Usuario user = UserMapper.toEntity(userRequestDTO);
        user.setPassword(passwordEncoder.encode(userRequestDTO.password()));
        user = usuarioRepository.save(user);
        return UserMapper.toDto(user);
    }

    @Transactional
    public UserResponseDTO update(Long id, UserRequestDTO userRequestDTO) {
        validateIdOrThrowException(id);
        Usuario user = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityUserNotFoundException(id));

        user.setUsuario(userRequestDTO.username());
        user.setPassword(passwordEncoder.encode(userRequestDTO.password()));

        user = usuarioRepository.save(user);
        return UserMapper.toDto(user);
    }

    @Transactional
    public void delete(Long id) {
        validateIdOrThrowException(id);
        Usuario user = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityUserNotFoundException(id));
        usuarioRepository.delete(user);
    }

    @Transactional
    public Long count(){
        return usuarioRepository.count();
    }


}