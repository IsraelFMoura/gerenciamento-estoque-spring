package com.gerenciamento.estoque.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.gerenciamento.estoque.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    // Herdando o JpaRepository, nós ganhamos de graça os métodos:
    // save(), findAll(), findById(), deleteById(), etc.
}