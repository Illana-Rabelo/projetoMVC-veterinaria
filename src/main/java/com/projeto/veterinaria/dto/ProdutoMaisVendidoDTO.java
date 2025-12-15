package com.projeto.veterinaria.dto;

public class ProdutoMaisVendidoDTO {
    private Long produtoId;
    private String descricao;
    private Long quantidadeVendida;

    public ProdutoMaisVendidoDTO(Long produtoId, String descricao, Long quantidadeVendida) {
        this.produtoId = produtoId;
        this.descricao = descricao;
        this.quantidadeVendida = quantidadeVendida;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getQuantidadeVendida() {
        return quantidadeVendida;
    }

    public void setQuantidadeVendida(Long quantidadeVendida) {
        this.quantidadeVendida = quantidadeVendida;
    }
}
