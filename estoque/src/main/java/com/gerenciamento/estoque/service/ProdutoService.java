package com.gerenciamento.estoque.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gerenciamento.estoque.model.Produto;
import com.gerenciamento.estoque.repository.ProdutoRepository;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    // Lógica para dar entrada de mercadoria no estoque
    public Produto darEntrada(Long id, Integer quantidadeEntrada) {
        Produto produto = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado!"));
        
        produto.setQuantidade(produto.getQuantidade() + quantidadeEntrada);
        return repository.save(produto);
    }

    // Lógica para dar saída de mercadoria (com validação)
    public Produto darSaida(Long id, Integer quantidadeSaida) {
        Produto produto = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado!"));
        
        if (produto.getQuantidade() < quantidadeSaida) {
            throw new RuntimeException("Saldo insuficiente no estoque! Estoque atual: " + produto.getQuantidade());
        }
        
        produto.setQuantidade(produto.getQuantidade() - quantidadeSaida);
        return repository.save(produto);
    }
}