package com.pricedog.pricedogapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pricedog.pricedogapp.dao.DescricaoDAO;
import com.pricedog.pricedogapp.dao.ProdutoDAO;
import com.pricedog.pricedogapp.dao.SQLite.DescricaoDAOSQLite;
import com.pricedog.pricedogapp.dao.SQLite.ProdutoDAOSQLite;
import com.pricedog.pricedogapp.modelo.Descricao;
import com.pricedog.pricedogapp.modelo.Item;
import com.pricedog.pricedogapp.modelo.ListaDeCompras;
import com.pricedog.pricedogapp.modelo.Produto;

import java.util.ArrayList;


public class IncluirItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incluir_produto);

        final EditText quantidade = (EditText) findViewById(R.id.quantidade);
        final EditText precoUnitario = (EditText) findViewById(R.id.preco_unitario);
        final EditText valorTotal = (EditText) findViewById(R.id.valor_total);
        final Button botao_menos = (Button) findViewById(R.id.botao_menos);
        final Button botao_mais = (Button) findViewById(R.id.botao_mais);
        final Button botaoOK = (Button) findViewById(R.id.botao_confirmar_produto);

        quantidade.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                Float preco = Float.valueOf(0);
                Float q = Float.valueOf(0);

                String stringPreco = precoUnitario.getText().toString().trim();
                if(!stringPreco.equals("")){
                    preco = Float.valueOf(stringPreco);
                }

                String stringS = s.toString().trim();
                if(!stringS.equals("")){
                    q = Float.valueOf(stringS);
                }

                Float total = q * preco;
                valorTotal.setText(total.toString());
            }
        });

        precoUnitario.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                Float preco = Float.valueOf(0);
                Float q = Float.valueOf(0);

                if (!s.toString().equals(""))
                    preco = Float.valueOf(s.toString());

                if (!quantidade.getText().toString().equals(""))
                    q = Float.valueOf(quantidade.getText().toString());

                Float total = q * preco;
                valorTotal.setText(total.toString());
            }
        });

        //Subtrai 1 da quantidade de produtos
        botao_menos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Float i = Float.parseFloat(quantidade.getText().toString());
                if (i > 1) {
                    i--;

                    if (i - i.intValue() == 0) {
                        int j = i.intValue();
                        quantidade.setText(Integer.toString(j));
                    } else quantidade.setText(i.toString());
                }
                //Uma vez atualizado o valor unitário o valor total também será alterado
                Float total = i * Float.parseFloat(precoUnitario.getText().toString());
                valorTotal.setText(total.toString());
            }
        });
        //Soma 1 a quantidade de produtos

        botao_mais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Float i = Float.parseFloat(quantidade.getText().toString());
                i++;

                if (i - i.intValue() == 0) {
                    int j = i.intValue();
                    quantidade.setText(Integer.toString(j));
                }
                else quantidade.setText(i.toString());
                //Uma vez atualizado o valor unitário o valor total também será alterado
                Float total = i * Float.parseFloat(precoUnitario.getText().toString());
                valorTotal.setText(total.toString());
            }
        });

        botaoOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Item> itens = getIntent().getParcelableArrayListExtra("listaDeCompras");
                IncluirItemHelper helper = new IncluirItemHelper(IncluirItemActivity.this);


                Item item = helper.getItemDoFormulario();
                Produto produto = item.getProduto();
                itens.add(item);

                Descricao descricao = produto.getDescricao();

                // Por enquanto o produto é salvo no banco de dados assim q o usuário aperta "OK"
                DescricaoDAO descricaoDao = new DescricaoDAOSQLite(IncluirItemActivity.this);
                ProdutoDAO produtoDAO = new ProdutoDAOSQLite(IncluirItemActivity.this);

                // Se a descricao já existe na base de dados não é necessario inserir novamente
                if (descricaoDao.getDescricaoId(descricao) > 0) {
                    descricao.setId(descricaoDao.getDescricaoId(descricao));
                    produto.setId(produtoDAO.getId(produto));
                }
                else {
                    //Por enquanto produto e descricao são a mesma coisa para o usuaŕio
                    descricao.setId(descricaoDao.inserir(descricao));
                    produto.setId(produtoDAO.inserir(produto));
                }
                descricaoDao.close();
                produtoDAO.close();

                Intent returnIntent = new Intent();
                returnIntent.putParcelableArrayListExtra("listaDeCompras", itens);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });
    }
}