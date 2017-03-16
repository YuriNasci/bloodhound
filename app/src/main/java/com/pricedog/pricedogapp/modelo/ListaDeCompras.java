package com.pricedog.pricedogapp.modelo;


import java.util.ArrayList;

/**
 * Created by yurinasci on 20/02/17.
 */

public class ListaDeCompras {
    protected long id;
    protected String nome;
    protected ArrayList<Item> itens;

    public ArrayList<Item> getItens() {
        return itens;
    }

    public void setItens(ArrayList<Item> itens) {
        this.itens = itens;
    }

    public ListaDeCompras()  {
        itens = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getTotalDeItens()  {return 0;}

    public float getTotalDaLista()  {
        return 0;}

}