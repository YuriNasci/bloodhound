package com.pricedog.pricedogapp.dao;

import com.pricedog.pricedogapp.modelo.Produto;

/**
 * Created by yurinasci on 03/03/17.
 */

public interface ProdutoDAO {
    String TABELA = "produtos";

    long inserir(Produto produto);

    void alterar(Produto produto);

    void remover (Produto produto);
    long getId(Produto produto);

    void close();
}
