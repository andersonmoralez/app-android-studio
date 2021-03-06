package com.anderson.bolsomovel

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.GsonBuilder
import java.net.URL

object ProdutoService {

    //trocar pela URL da sua api
    val host = "https://fesousa.pythonanywhere.com"
    val TAG = "WS_LMSApp"


    fun getProduto(context: Context, id: Long): Produto {
        val url = "$host/disciplinas/${id}"
        val json = HttpHelper.get(url)
        val produto = parserJson<Produto>(json)

        return produto
    }

    fun getListOneProduto(context: Context, id: Long) : List<Produto> {
        val url = "$host/disciplinas"
        val json = HttpHelper.get(url)
        var produto = parserJson<List<Produto>>(json)

        if (AndroidUtils.isInternetDisponivel(context)) {
            for (p in produto) {
                if (p.id == id) {
                    produto = listOf(p)
                }
            }
            return produto
        } else {
            var dao = DataBaseManager.getProdutoDAO()

            for (p in dao.findAll()) {
                if (p.id == id) {
                 produto = listOf(p)
                }
            }
            return produto
        }
    }

    fun getProdutos(context: Context): List<Produto> {

        val produtos = mutableListOf<Produto>()

        if (AndroidUtils.isInternetDisponivel(context)) {
            val url = "$host/disciplinas"
            val json = HttpHelper.get(url)
            var produtos = parserJson<List<Produto>>(json)

            for (p in produtos) {
                saveOf(p)
            }

            Log.d(TAG, json)

            return produtos
        } else {
            var dao = DataBaseManager.getProdutoDAO()
            return dao.findAll()
        }
    }

    fun saveOf(produto: Produto): Boolean {
        val dao = DataBaseManager.getProdutoDAO()

        if (!existeProduto(produto)) {
            dao.insert(produto)
        }
        return true
    }

    fun existeProduto(produto: Produto): Boolean {
        val dao = DataBaseManager.getProdutoDAO()
        return dao.getById(produto.id) != null
    }

    fun save(produto: Produto): Response {
        if (AndroidUtils.isInternetDisponivel(BolsomovelApplication.getInstance().applicationContext)) {
            //alterar host/disciplina para host/produtos
            val json = HttpHelper.post("$host/disciplinas", produto.toJson())
            return parserJson<Response>(json)
        } else {
            saveOf(produto)
            return Response("OK", "Disciplina salva no dispositivo")
        }

    }

    fun delete(produto: Produto): Response {
        if (AndroidUtils.isInternetDisponivel(BolsomovelApplication.getInstance().applicationContext)) {
            //alterar host/disciplina para host/produtos
            val url = "$host/disciplinas/${produto.id}"
            val json = HttpHelper.delete(url)
            return parserJson<Response>(json)
        } else {
            val dao = DataBaseManager.getProdutoDAO()
            dao.delete(produto)
            return Response(status = "OK", msg = "Dados salvos localmente")
        }

    }

    inline fun <reified T> parserJson(json: String): T {
        val type = object : TypeToken<T>() {}.type
        return Gson().fromJson<T>(json, type)
    }
}