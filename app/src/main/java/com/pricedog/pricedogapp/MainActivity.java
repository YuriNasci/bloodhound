package com.pricedog.pricedogapp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.pricedog.pricedogapp.dao.ItemDAO;
import com.pricedog.pricedogapp.dao.ListaDeComprasDAO;
import com.pricedog.pricedogapp.dao.SQLite.ItemDAOSQLite;
import com.pricedog.pricedogapp.dao.SQLite.ListaDeComprasDAOSQLite;
import com.pricedog.pricedogapp.modelo.Item;
import com.pricedog.pricedogapp.modelo.ListaDeCompras;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private List<ListaDeCompras> listasDeCompras;
    private ListaDeCompras listaDeComprasAtual;
    private List<Item> itensExcluidos;
    boolean listaSalva;
    private ListView listaDeComprasView;
    private static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        listasDeCompras = new ArrayList<>();
        listaDeComprasAtual = new ListaDeCompras();
        itensExcluidos = new ArrayList<>();
        listaSalva = false;

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
        registerForContextMenu(listaDeComprasView);
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
                android.R.layout.simple_list_item_1, listaDeComprasAtual.getItens());

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
                intent.putParcelableArrayListExtra("listaDeCompras", listaDeComprasAtual.getItens());
                startActivityForResult(intent, REQUEST_CODE);
                return false;
            case R.id.salvar_lista:
                salvarLista();
                Toast.makeText(MainActivity.this, "Lista salva com sucesso!",
                        Toast.LENGTH_SHORT).show();

                setListaSalva(true);
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
                        setListaSalva(false);
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

    private void salvarLista() {
        ListaDeComprasDAO listaDeComprasDAO = new ListaDeComprasDAOSQLite(MainActivity.this);
        ItemDAO itemDAO = new ItemDAOSQLite(MainActivity.this);

        if (listaDeComprasAtual.getId() == 0) {
            //Inserir a Lista de Compras na Lista com todas a listas do usuário
            listasDeCompras.add(listaDeComprasAtual);
            //Inserir a lista no banco de dados

            listaDeComprasDAO.inserir(listaDeComprasAtual);
            //Inserir os itens no banco de dados
            for (Item itemDaLista: listaDeComprasAtual.getItens()) {
                itemDaLista.setId_lista(listaDeComprasAtual.getId());
                itemDAO.inserir(itemDaLista);
            }
        }  else  {
            listaDeComprasDAO.alterar(listaDeComprasAtual);
            //Verificar os itens da lista para gravar ou alterar no banco de dados
            for (Item itemDaLista: listaDeComprasAtual.getItens())  {
                if (itemDaLista.getId_lista() == 0) {
                    itemDaLista.setId_lista(listaDeComprasAtual.getId());
                    itemDAO.inserir(itemDaLista);
                }  else itemDAO.alterar(itemDaLista);
            }
            //Verficar se há itens para excluir da base de dados
            for (Item itemExcluido: itensExcluidos)  {
                if (itemExcluido.getId_lista() != 0 && itemExcluido.getId_Produto() != 0)
                    itemDAO.remover(itemExcluido);

                itensExcluidos.remove(itemExcluido);
            }
        }

        itemDAO.close();
        listaDeComprasDAO.close();
    }

    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo)  {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Item itemSelecionado = (Item) listaDeComprasView.getAdapter().getItem(info.position);

        MenuItem excluir = menu.add("Excluir");
        excluir.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                listaDeComprasAtual.getItens().remove(itemSelecionado);
                itensExcluidos.add(itemSelecionado);
                Toast.makeText(MainActivity.this, "\"" + itemSelecionado.getProduto().toString()
                        + "\" excluído com sucesso!",
                        Toast.LENGTH_SHORT).show();
                onResume();
                return false;
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if(resultCode == Activity.RESULT_OK)
                listaDeComprasAtual.setItens(data.<Item>getParcelableArrayListExtra("listaDeCompras"));

            setListaSalva(false);
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
        setListaSalva(false);
        carregaLista();
    }

    public void setListaSalva(boolean result)  {
        listaSalva = result;
        if (listaSalva == true)
            setTitle(listaDeComprasAtual.getNome());
        else
            setTitle("*" + listaDeComprasAtual.getNome());
    }
}