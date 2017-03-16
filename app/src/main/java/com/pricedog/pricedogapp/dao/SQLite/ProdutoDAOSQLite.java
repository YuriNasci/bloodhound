package com.pricedog.pricedogapp.dao.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.pricedog.pricedogapp.dao.PriceDogDB;
import com.pricedog.pricedogapp.dao.ProdutoDAO;
import com.pricedog.pricedogapp.modelo.Produto;

/**
 * Created by yurinasci on 03/03/17.
 */

public class ProdutoDAOSQLite extends SQLiteOpenHelper implements ProdutoDAO {
    public ProdutoDAOSQLite(Context context) {
        super(context, PriceDogDB.NOME, null,  PriceDogDB.VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE "+ TABELA +
                " (id INTEGER primary key AUTOINCREMENT, " +
                " id_descricao integer not null, foreign key (id_descricao) " +
                "references descricoes(id));";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "drop table if exists " + TABELA;
        db.execSQL(sql);
        onCreate(db);
    }

    @Override
    public long inserir(Produto produto) {
        ContentValues valores = new ContentValues();

        valores.put("id_descricao", produto.getDescricao().getId());
        return getWritableDatabase().insert(TABELA, null, valores);
    }

    @Override
    public void alterar(Produto produto) {

    }

    @Override
    public void remover(Produto produto) {

    }

    public long getId(Produto produto)  {
        Cursor cursor = getWritableDatabase().rawQuery("select * from " + TABELA + " where id_descricao = " +
                produto.getDescricao().getId() + ";", null);

        cursor.moveToFirst();

        if (cursor.getCount() > 0)
            return cursor.getLong(cursor.getColumnIndex("id"));

        return 0;
    }
}