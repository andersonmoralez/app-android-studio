package com.anderson.bolsomovel

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.net.URL

object VendedorService {

    //val host = "http://192.168.0.103:8080"
    val host = "http://7ristam.pythonanywhere.com"
    val TAG = "BolsomovelApp"

    fun getVendedores(context: Context): List<Vendedor> {
        if (AndroidUtils.isInternetDisponivel(context)) {
            val url = "$host/vendedores"
            val json = HttpHelper.get(url)
            var vendedores = parserJson<List<Vendedor>>(json)

            for (v in vendedores) {
                saveOf(v)
            }

            Log.v(TAG, json)

            return vendedores
        } else {
            val dao = DataBaseManager.getVendedorDAO()
            return dao.findAll() }
    }

    inline fun <reified T> parserJson(json: String): T {
        val type = object : TypeToken<T>() {}.type
        return Gson().fromJson<T>(json, type)
    }

    fun saveOf(vendedor: Vendedor): Boolean {
        val dao = DataBaseManager.getVendedorDAO()

        if(!existeVendedor(vendedor)) {
            dao.insert(vendedor)
        }
        return true
    }

    fun existeVendedor(vendedor: Vendedor): Boolean {
        val dao = DataBaseManager.getVendedorDAO()
        return dao.getById(vendedor.id) != null
    }

}