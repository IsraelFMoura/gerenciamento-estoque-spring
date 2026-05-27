package com.gerenciamento.estoque.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gerenciamento.estoque.model.Produto;
import com.gerenciamento.estoque.repository.ProdutoRepository;

@RestController
@RequestMapping("/produtos") // Define que todas as rotas deste controller começam com /produtos
public class ProdutoController {

    @Autowired
    private ProdutoRepository repository; // Injeta o repository para usarmos os métodos do banco
    
    @Autowired
    private com.gerenciamento.estoque.service.ProdutoService service;
    // Rota para Listar todos os produtos (GET http://localhost:PORTA/produtos)
    @GetMapping
    public List<Produto> listarTodos() {
        return repository.findAll();
    }

    // Rota para Cadastrar um produto (POST http://localhost:PORTA/produtos)
    @PostMapping
    public Produto cadastrar(@RequestBody Produto produto) {
        return repository.save(produto);
    }
// Rota para Atualizar um produto existente (PUT http://localhost:PORTA/produtos/{id})
    @org.springframework.web.bind.annotation.PutMapping("/{id}")
    public Produto atualizar(@org.springframework.web.bind.annotation.PathVariable Long id, @org.springframework.web.bind.annotation.RequestBody Produto produtoAtualizado) {
        return repository.findById(id)
                .map(produto -> {
                    produto.setNome(produtoAtualizado.getNome());
                    produto.setDescricao(produtoAtualizado.getDescricao());
                    produto.setPreco(produtoAtualizado.getPreco());
                    produto.setQuantidade(produtoAtualizado.getQuantidade());
                    produto.setCategoria(produtoAtualizado.getCategoria());
                    return repository.save(produto);
                }).orElseThrow(() -> new RuntimeException("Produto não encontrado com o id: " + id));
    }

    // Rota para Deletar um produto (DELETE http://localhost:PORTA/produtos/{id})
    @org.springframework.web.bind.annotation.DeleteMapping("/{id}")
    public String deletar(@org.springframework.web.bind.annotation.PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return "Produto deletado com sucesso!";
        }
        return "Produto não encontrado!";
    }
// Rota para dar entrada no estoque (POST http://localhost:PORTA/produtos/{id}/entrada)
    @org.springframework.web.bind.annotation.PostMapping("/{id}/entrada")
    public Produto darEntrada(@org.springframework.web.bind.annotation.PathVariable Long id, @org.springframework.web.bind.annotation.RequestParam Integer quantidade) {
        return service.darEntrada(id, quantidade);
    }

    // Rota para dar saída do estoque (POST http://localhost:PORTA/produtos/{id}/saida)
    @org.springframework.web.bind.annotation.PostMapping("/{id}/saida")
    public Produto darSaida(@org.springframework.web.bind.annotation.PathVariable Long id, @org.springframework.web.bind.annotation.RequestParam Integer quantidade) {
        return service.darSaida(id, quantidade);
    }
}