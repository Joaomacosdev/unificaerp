package br.com.unificaerp.empresa.model;

import jakarta.persistence.*;

@Entity
@Table(name = "empresas")
@SequenceGenerator(name = "seq_empresa", sequenceName = "seq_empresa", allocationSize = 1, initialValue = 1)
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_empresa")
    private Long id;
}
