package com.pricedog.pricedogapp.modelo;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by yurinasci on 20/02/17.
 */

public class ListaDeCompras {
    private long id;
    private String nome;
    public ArrayList<Item> itens;

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