package com.anderson.bolsomovel

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_tela_inicial.*
import kotlinx.android.synthetic.main.toolbar.*

class TelaInicial : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var produtos = listOf<Produto>()
    private var produto = listOf<Produto>()
    private val context: Context get() = this
    private var REQUEST_CADASTRO = 1
    private var REQUEST_REMOVE = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_inicial)

        recyclerProdutos?.setLayoutManager(GridLayoutManager(this, 2))
        recyclerProdutos?.itemAnimator = DefaultItemAnimator()
        recyclerProdutos?.setHasFixedSize(true)

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Produtos"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        configuraMenuLateral()

        val fab: View = findViewById(R.id.button_add_fab)

        fab.setOnClickListener {
            val intent = Intent(context, ProdutoCadastroActivity::class.java)
            startActivityForResult(intent, REQUEST_CADASTRO)
        }
    }

    override fun onResume() {
        super.onResume()
        taskProdutos()
    }

    fun taskProdutos() {
        Thread {
            produtos = ProdutoService.getProdutos(context)
            runOnUiThread {
                recyclerProdutos?.adapter = ProdutoAdapter(produtos) { onClickProduto(it) }
            }
        }.start()
    }

    //populando a recyclerview com apenas um produto
    fun taskProduto(id: Long) {
        Thread {
            produto = ProdutoService.getListOneProduto(context, id)
            runOnUiThread {
                recyclerProdutos?.adapter = ProdutoAdapter(produto) { onClickProduto(it) }
            }
        }.start()
    }


    // tratamento do evento de clicar em uma disciplina
    fun onClickProduto(produto: Produto) {
        Toast.makeText(context, "Clicou disciplina ${produto.nome}", Toast.LENGTH_SHORT).show()
        val intent = Intent(context, ProdutoActivity::class.java)
        intent.putExtra("produto", produto)
        startActivityForResult(intent, REQUEST_REMOVE)
    }

    private fun configuraMenuLateral() {
        var toogle = ActionBarDrawerToggle(
            this,
            cardView,
            toolbar,
            R.string.nav_open,
            R.string.nav_close)
        cardView.addDrawerListener(toogle)
        toogle.syncState()

        menu_lateral.setNavigationItemSelectedListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        //serachview
        val manager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu?.findItem(R.id.action_buscar)
        val searchView = searchItem?.actionView as SearchView

        searchView.setSearchableInfo(manager.getSearchableInfo(componentName))

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                searchView.setQuery("", false)
                searchItem.collapseActionView()
                //Toast.makeText(this@TelaInicial, "Looking for $query", Toast.LENGTH_LONG).show()

                for (p in produtos) {
                    if (query == p.nome) {
                        Toast.makeText(this@TelaInicial, "Achamos um resultado para: $query", Toast.LENGTH_LONG).show()
                        taskProduto(p.id)
                    }
                }
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item?.itemId

        if (id == R.id.action_buscar) {
            Toast.makeText(this, "Clicou em Buscar", Toast.LENGTH_LONG).show()
          //evento do botao atualizar
        } else if (id == R.id.action_atualizar) {
            taskProdutos()
          //evento do botao configurar
        } else if (id == R.id.action_config) {
            val intent = Intent(context, TelaVendedor::class.java)
            startActivityForResult(intent, REQUEST_CADASTRO)
          //evento do botao voltar
        } else if (id == android.R.id.home) {finish()}

        return super.onOptionsItemSelected(item)
    }

    // esperar o retorno do cadastro da disciplina
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CADASTRO || requestCode == REQUEST_REMOVE) {
            // atualizar lista de disciplinas
            taskProdutos()
        }
    }

    //navigation drawer
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            //evento navigation drawer nova disciplina
            R.id.nav_novo_produto -> {
                val intent = Intent(context, ProdutoCadastroActivity::class.java)
                startActivityForResult(intent, REQUEST_CADASTRO)}

            //evento navigation drawer nova localizacao
            R.id.nav_maps_locazacao -> {startActivity(Intent(this, MapasActivity::class.java))}
        }
        cardView.closeDrawer(GravityCompat.START)
        return true
    }
}




