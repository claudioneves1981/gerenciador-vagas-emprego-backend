package com.vagasemprego.demo.controllers;

import com.vagasemprego.demo.dtos.SessaoDTO;
import com.vagasemprego.demo.dtos.UserRequestDTO;
import com.vagasemprego.demo.dtos.UserResponseDTO;
import com.vagasemprego.demo.mappers.UserMapper;
import com.vagasemprego.demo.models.Usuario;
import com.vagasemprego.demo.security.JWTCreator;
import com.vagasemprego.demo.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class LoginController {

    @Autowired
    private UsuarioService service;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTCreator jwtCreator;

    @PostMapping("/login")
    public ResponseEntity<SessaoDTO> logar(@RequestBody UserRequestDTO login) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login.username(), login.password()));
        String token = jwtCreator.generateToken(authentication);
        String refreshToken = jwtCreator.generateRefreshToken(authentication);
        return ResponseEntity.ok(new SessaoDTO(refreshToken, token, login.username()));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid UserRequestDTO data){
        boolean isFirstUser = service.count() == 0;
        if (!isFirstUser && !isAdminAuthenticated()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Usuario.Role role = isFirstUser ? Usuario.Role.ADMIN : data.role();
        Usuario newUser = new Usuario(data.username(), data.password(), role);
        this.service.create(UserMapper.toRequestDto(newUser));

        return ResponseEntity.ok().build();
    }

    private boolean isAdminAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof Usuario user)) {
            return false;
        }
        return user.getRole() == Usuario.Role.ADMIN;
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> findByUsuario() {
        Usuario usuario = jwtCreator.getCurrentUser();
        return ResponseEntity.ok(UserMapper.toDto(usuario));
    }
}
