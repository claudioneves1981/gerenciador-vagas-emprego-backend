package com.vagasemprego.demo.controllers;

import com.vagasemprego.demo.dtos.VagasRequestDTO;
import com.vagasemprego.demo.dtos.VagasResponseDTO;
import com.vagasemprego.demo.services.VagasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vagas")
public class VagasController {

    @Autowired
    private VagasService vagasService;


    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<VagasResponseDTO>> getMyVagas() {
        List<VagasResponseDTO> vagasResponseDTOList = vagasService.findVagasByUser();
        return ResponseEntity.ok(vagasResponseDTOList);
    }

    @GetMapping("/me/situacao/{situacao}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<VagasResponseDTO>> situacao(@PathVariable("situacao")String situacao){
        List<VagasResponseDTO> vagasResponseDTOList = vagasService.findByUsuarioIdAndSituacao(situacao);
        return ResponseEntity.ok(vagasResponseDTOList);
    }

    @GetMapping("/me/interesse/{interesse}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<VagasResponseDTO>> interesse(@PathVariable("interesse") String interesse){
        List<VagasResponseDTO> vagasResponseDTOList = vagasService.findByUsuarioIdAndInteresse(interesse);
        return ResponseEntity.ok(vagasResponseDTOList);
    }

    @GetMapping("/me/contrato/{contrato}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<VagasResponseDTO>> contrato(@PathVariable("contrato")String contrato){
        List<VagasResponseDTO> vagasResponseDTOList = vagasService.findByUsuarioIdAndContrato(contrato);
        return ResponseEntity.ok(vagasResponseDTOList);
    }


    @GetMapping("/me/tipo/{tipo}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<VagasResponseDTO>> tipo(@PathVariable("tipo")String tipo){
        List<VagasResponseDTO> vagasResponseDTOList = vagasService.findByUsuarioIdAndTipo(tipo);
        return ResponseEntity.ok(vagasResponseDTOList);
    }


    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<VagasResponseDTO> create(@RequestBody VagasRequestDTO vagasDTO) {
        VagasResponseDTO vagasResponseDTO = vagasService.create(vagasDTO);
        return new ResponseEntity<>(vagasResponseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<VagasResponseDTO> update(@PathVariable Long id, @RequestBody VagasRequestDTO vagasDTO) {
        VagasResponseDTO vagasResponseDTO = vagasService.update(id, vagasDTO);
        return ResponseEntity.ok(vagasResponseDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        vagasService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
