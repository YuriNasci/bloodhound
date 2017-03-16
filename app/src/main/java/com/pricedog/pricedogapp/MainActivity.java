package com.pricedog.pricedogapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.pricedog.pricedogapp.dao.ListaDeComprasDAO;
import com.pricedog.pricedogapp.dao.SQLite.ListaDeComprasDAOSQLite;
import com.pricedog.pricedogapp.modelo.Item;
import com.pricedog.pricedogapp.modelo.ListaDeCompras;
import com.pricedog.pricedogapp.modelo.Produto;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private List<ListaDeCompras> listasDeCompras = new ArrayList<>();
    private ListaDeCompras listaDeComprasAtual = new ListaDeCompras();
    boolean listaSalva = false;
    private ListView listaDeComprasView;
    private static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_open);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        drawer.openDrawer(GravityCompat.START);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        listaDeComprasView = (ListView) findViewById(R.id.lista_de_compras);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void onResume()  {
        super.onResume();
        this.carregaLista();
    }

    private void carregaLista() {
        ArrayAdapter<Item> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, listaDeComprasAtual.itens);

        listaDeComprasView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onPrepareOptionsMenu(Menu menu)  {
        // A opção excluir só pode ser habilitada após a lista ser salva
        MenuItem excluir = menu.findItem(R.id.excluir_lista);
        if (excluir.isEnabled() != listaSalva)
            excluir.setEnabled(listaSalva);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())  {
            case R.id.incluir_produto:
                Intent intent = new Intent(MainActivity.this, IncluirItemActivity.class);
                intent.putParcelableArrayListExtra("listaDeCompras", listaDeComprasAtual.itens);
                startActivityForResult(intent, REQUEST_CODE);
                return false;
            case R.id.salvar_lista:
                if (listaDeComprasAtual.getId() == 0) {
                    listasDeCompras.add(listaDeComprasAtual);
                    ListaDeComprasDAO listaDeComprasDAO = new ListaDeComprasDAOSQLite(MainActivity.this);
                    listaDeComprasDAO.inserir(listaDeComprasAtual);
                    listaDeComprasDAO.close();
                }
                else {
                    ListaDeComprasDAO dao = new ListaDeComprasDAOSQLite(MainActivity.this);
                    dao.alterar(listaDeComprasAtual);
                }
                Toast.makeText(MainActivity.this, "Lista salva com sucesso!",
                        Toast.LENGTH_SHORT).show();

                listaSalva = true;
                setTitle(listaDeComprasAtual.getNome());
                return true;
            case R.id.renomear_lista:
                final EditText edt = new EditText(this);
                AlertDialog.Builder construtor = new AlertDialog.Builder(MainActivity.this);

                edt.setText(listaDeComprasAtual.getNome());
                edt.setSelectAllOnFocus(true);
                construtor.setTitle("Digite um novo nome para a sua lista: ");
                construtor.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String novoNome = edt.getText().toString();
                        Toast.makeText(MainActivity.this, "Lista \""+ listaDeComprasAtual.getNome()
                                        +"" + "\" renomeada com sucesso!",
                                Toast.LENGTH_SHORT).show();
                        listaDeComprasAtual.setNome(novoNome);
                        setTitle("*" + listaDeComprasAtual.getNome());
                    }
                });
                construtor.setView(edt);

                AlertDialog dialogo = construtor.create();
                dialogo.show();

                return false;
            case R.id.excluir_lista:
                listasDeCompras.remove(listaDeComprasAtual);
                ListaDeComprasDAO dao = new ListaDeComprasDAOSQLite(MainActivity.this);
                dao.remover(listaDeComprasAtual);

                Toast.makeText(MainActivity.this, "Lista \""+ listaDeComprasAtual.getNome() +"\" " +
                        "excluída com sucesso!", Toast.LENGTH_SHORT).show();
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.openDrawer(GravityCompat.START);
                criarNovaLista();
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if(resultCode == Activity.RESULT_OK)
                listaDeComprasAtual.itens = data.getParcelableArrayListExtra("listaDeCompras");
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nova_lista)  {
            criarNovaLista();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void criarNovaLista()  {
        listaDeComprasAtual = new ListaDeCompras();
        listaDeComprasAtual.setNome("Nova Lista");
        setTitle("*" + listaDeComprasAtual.getNome());
        listaSalva = false;
        carregaLista();
    }
}