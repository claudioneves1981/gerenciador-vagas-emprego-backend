package com.vagasemprego.demo.models;

import com.vagasemprego.demo.models.enuns.Contrato;
import com.vagasemprego.demo.models.enuns.Interesse;
import com.vagasemprego.demo.models.enuns.Situacao;
import com.vagasemprego.demo.models.enuns.Tipo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Vagas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String empresa;

    private String vaga;

    @Enumerated(EnumType.STRING)
    @Column(name="SITUACAO")
    private Situacao situacao;

    @Column(name="ORIGEM")
    private String origem;

    @Enumerated(EnumType.STRING)
    @Column(name="INTERESSE")
    private Interesse interesse;

    private Double salario;

    @Enumerated(EnumType.STRING)
    @Column(name="CONTRATO")
    private Contrato contrato;

    @Enumerated(EnumType.STRING)
    @Column(name="TIPO")
    private Tipo tipo;

    private String beneficios;

    private LocalDateTime inscricao;

    private String observacoes;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Usuario usuario;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Vagas that = (Vagas) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }


}
