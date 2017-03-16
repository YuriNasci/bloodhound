package com.pricedog.pricedogapp.dao.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.pricedog.pricedogapp.dao.DescricaoDAO;
import com.pricedog.pricedogapp.dao.PriceDogDB;
import com.pricedog.pricedogapp.modelo.Descricao;

import java.util.List;

/**
 * Created by yurinasci on 17/02/17.
 */

public class DescricaoDAOSQLite extends SQLiteOpenHelper implements DescricaoDAO {
    public DescricaoDAOSQLite(Context context) {
        super(context, PriceDogDB.NOME, null, PriceDogDB.VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE "+ TABELA +
                " (id INTEGER primary key AUTOINCREMENT, " +
                " tipo varchar(45) not null);";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "drop table if exists " + TABELA;
        db.execSQL(sql);
        onCreate(db);
    }

    @Override
    public long inserir(Descricao descricao) {
        ContentValues valores = new ContentValues();

        valores.put("tipo", descricao.getTipo());
        return getWritableDatabase().insert(TABELA, null, valores);
    }

    @Override
    public void alterar(Descricao descricao) {
    }

    @Override
    public void remover(Descricao descricao) {}

    @Override
    public Descricao getDescricao(long id) {
        return null;
    }

    @Override
    public long getDescricaoId(Descricao descricao) {
       Cursor cursor = getWritableDatabase().rawQuery("select * from " + TABELA + " where tipo = '" +
               descricao.getTipo() + "';", null);

        cursor.moveToFirst();

        if (cursor.getCount() > 0)
          return cursor.getLong(cursor.getColumnIndex("id"));

        return 0;
    }

    @Override
    public List<Descricao> getLista() {
        return null;
    }
}