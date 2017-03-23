package com.pricedog.pricedogapp.dao.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.pricedog.pricedogapp.dao.ItemDAO;
import com.pricedog.pricedogapp.dao.PriceDogDB;
import com.pricedog.pricedogapp.modelo.Item;

/**
 * Created by yurinasci on 17/03/17.
 */

public class ItemDAOSQLite extends SQLiteOpenHelper implements ItemDAO{
    public ItemDAOSQLite(Context context) {
        super(context, PriceDogDB.NOME, null, PriceDogDB.VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String ddl = "create table " + TABELA + " (" +
                "id_lista integer not null " +
                "CONSTRAINT id_lista REFERENCES listas(id) ON DELETE CASCADE," +
                " id_produto integer not null " +
                "CONSTRAINT id_produto REFERENCES produtos(id) ON DELETE CASCADE," +
                " quantidade float," +
                " preco_unitario float);";

        db.execSQL(ddl);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "drop table if exists " + TABELA;
        db.execSQL(sql);
        onCreate(db);
    }

    @Override
    public void inserir(Item item) {
        ContentValues valores = new ContentValues();

        valores.put("id_lista", item.getId_lista());
        valores.put("id_produto", item.getId_Produto());
        valores.put("quantidade", item.getQuantidade());
        valores.put("preco_unitario", item.getPrecoUnitario());

        getWritableDatabase().insert(TABELA, null, valores);
    }

    @Override
    public void alterar(Item item) {
        ContentValues values = new ContentValues();

        values.put("id_lista", item.getId_lista());
        values.put("id_produto", item.getProduto().getId());
        values.put("quantidade_produto", item.getQuantidade());
        values.put("preco_unitario", item.getPrecoUnitario());
        getWritableDatabase().update(TABELA, values, "id_produto=? and id_lista=?",
                new String[]{String.valueOf(item.getId_Produto()),
                        String.valueOf(item.getId_lista())});
    }

    @Override
    public void remover(Item item) {
        String args[] = {String.valueOf(item.getId_Produto()),
                String.valueOf(item.getId_lista())};

        getWritableDatabase().delete(TABELA, "id_produto=? and id_lista=?", args);
    }

    public void close()  {
        super.close();
    }
}
