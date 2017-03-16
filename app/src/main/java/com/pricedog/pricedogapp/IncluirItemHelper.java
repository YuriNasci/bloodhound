package com.pricedog.pricedogapp;

import android.content.ClipData;
import android.widget.EditText;

import com.pricedog.pricedogapp.IncluirItemActivity;
import com.pricedog.pricedogapp.modelo.Descricao;
import com.pricedog.pricedogapp.modelo.Item;
import com.pricedog.pricedogapp.modelo.ListaDeCompras;
import com.pricedog.pricedogapp.modelo.Produto;

/**
 * Created by yurinasci on 27/02/17.
 */

public class IncluirItemHelper {
    private EditText descricaoProduto, quantidade, precoUnitario;
    private Item item;

    public IncluirItemHelper(IncluirItemActivity activity)  {
        descricaoProduto = (EditText) activity.findViewById(R.id.descricao_produto);
        quantidade = (EditText) activity.findViewById(R.id.quantidade);
        precoUnitario = (EditText) activity.findViewById(R.id.preco_unitario);
        item = new Item();
        item.setProduto(new Produto());
    }

    public Item getItemDoFormulario()  {
        Descricao descricao = new Descricao();
        descricao.setTipo(descricaoProduto.getText().toString());

        item.getProduto().setDescricao(descricao);
        item.setQuantidade(Float.valueOf(quantidade.getText().toString()));
        item.setPrecoUnitario(Float.valueOf(precoUnitario.getText().toString()));
        return item;
    }
}
