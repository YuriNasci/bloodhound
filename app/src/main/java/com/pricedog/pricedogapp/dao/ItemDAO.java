package com.pricedog.pricedogapp.dao;

import com.pricedog.pricedogapp.modelo.Item;
/**
 * Created by yurinasci on 17/03/17.
 */

public interface ItemDAO {
    String TABELA = "itens";

    void inserir(Item item);
    void alterar(Item item);
    void remover(Item item);
    void close();
}
