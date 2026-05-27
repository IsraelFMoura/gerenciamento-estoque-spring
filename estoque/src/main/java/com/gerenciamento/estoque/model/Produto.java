package com.gerenciamento.estoque.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_produto")
@Data // Gera Getters, Setters, toString e equals automaticamente pelo Lombok
@NoArgsConstructor // Gera o construtor vazio exigido pelo Hibernate
@AllArgsConstructor // Gera um construtor com todos os atributos
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nome;
    private String descricao;
    private Integer quantidade;
    private Double preco;
    private String categoria;
}