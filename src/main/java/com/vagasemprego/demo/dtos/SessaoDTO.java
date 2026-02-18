package com.vagasemprego.demo.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record SessaoDTO(String refreshToken, String token, String nome) {

}
