package com.pricedog.pricedogapp.modelo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yurinasci on 09/03/17.
 */

public class Item  implements Parcelable{
    private long id_lista;
    private Produto produto;
    private float quantidade, precoUnitario;

    public Item()  {
        produto = new Produto();
    }

    public long getId_Produto()  {return  produto.getId();}

    public long getId_lista() {
        return id_lista;
    }

    public void setId_lista(long id_lista) {
        this.id_lista = id_lista;
    }

    public float getTotalDoItem()  {
        return quantidade * precoUnitario;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public float getPrecoUnitario()  {
        return precoUnitario;
    }

    public void setPrecoUnitario(float precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public float getQuantidade()  {
        return quantidade;
    }

    public void setQuantidade(float quantidade) {
        this.quantidade = quantidade;
    }

    public String toString()  {
        return produto.toString() + " " + this.quantidade + " " + getTotalDoItem();
    }
    // Métodos responsáveis por implementar o Parcelable
    protected Item(Parcel in) {
        quantidade = in.readFloat();
        precoUnitario = in.readFloat();
        produto = (Produto) in.readValue(Produto.class.getClassLoader());
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(quantidade);
        dest.writeFloat(precoUnitario);
        dest.writeValue(produto);
    }
}