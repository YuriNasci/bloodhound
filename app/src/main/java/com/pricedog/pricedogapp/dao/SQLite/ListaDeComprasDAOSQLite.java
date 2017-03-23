package com.pricedog.pricedogapp.dao.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.pricedog.pricedogapp.dao.ListaDeComprasDAO;
import com.pricedog.pricedogapp.dao.PriceDogDB;
import com.pricedog.pricedogapp.modelo.ListaDeCompras;


/**
 * Created by yurinasci on 28/02/17.
 */

public class ListaDeComprasDAOSQLite extends SQLiteOpenHelper implements ListaDeComprasDAO {
    public ListaDeComprasDAOSQLite(Context context) {
        super(context, PriceDogDB.NOME, null, PriceDogDB.VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String ddl = "create table " + TABELA + " (id integer primary key autoincrement, "
                + " nome text not null);";

        db.execSQL(ddl);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "drop table if exists " + TABELA;
        db.execSQL(sql);
        onCreate(db);
    }

    @Override
    public void inserir(ListaDeCompras lista) {
        ContentValues valores = new ContentValues();
        valores.put("nome", lista.getNome());

        lista.setId(getWritableDatabase().insert(TABELA, null, valores));
    }

    @Override
    public void alterar(ListaDeCompras listaDeCompras) {
        ContentValues values = new ContentValues();

        values.put("nome", listaDeCompras.getNome());
        getWritableDatabase().update(TABELA, values, "id=?",
                new String[]{String.valueOf(listaDeCompras.getId())});
    }

    @Override
    public void remover(ListaDeCompras lista) {
        String args[] = {String.valueOf(lista.getId())};
        getWritableDatabase().delete(TABELA, "id=?", args);
    }

    public void close()  {
        super.close();
    }
}