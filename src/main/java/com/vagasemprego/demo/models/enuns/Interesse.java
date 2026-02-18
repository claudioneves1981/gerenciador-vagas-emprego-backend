package com.vagasemprego.demo.models.enuns;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

public enum Interesse {

    BAIXISSIMO,
    BAIXO,
    MEDIO,
    ALTO,
    ALTISSIMO
}
