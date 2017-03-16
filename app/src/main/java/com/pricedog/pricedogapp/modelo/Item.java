package com.pricedog.pricedogapp.modelo;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by yurinasci on 09/03/17.
 */

public class Item  implements Parcelable{
    private Produto produto;
    private float quantidade, precoUnitario;

    protected Item(Parcel in) {
        quantidade = in.readFloat();
        precoUnitario = in.readFloat();
        produto = (Produto) in.readValue(Produto.class.getClassLoader());
    }

    public Item()  {
        produto = new Produto();
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