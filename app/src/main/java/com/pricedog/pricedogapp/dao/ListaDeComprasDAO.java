package com.pricedog.pricedogapp.dao;

import com.pricedog.pricedogapp.modelo.ListaDeCompras;

/**
 * Created by yurinasci on 28/02/17.
 */

public interface ListaDeComprasDAO {
    String TABELA = "listas";

    void inserir(ListaDeCompras lista);
    void alterar(ListaDeCompras listaDeCompras);
    void remover(ListaDeCompras lista);
    void close();
}
