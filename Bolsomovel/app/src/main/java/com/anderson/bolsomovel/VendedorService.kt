package com.anderson.bolsomovel

import android.content.Context

object VendedorService {

    fun getVendedores(context: Context): List<Vendedor> {
        val vendedores = mutableListOf<Vendedor>()

        for (i in 1..10) {
            val v = Vendedor()
            v.nome = "Vendedor $i"
            v.codigoVendedor = 152431
            v.email = "email@email.com"
            v.senha = "12345abc"
            vendedores.add(v)
        }
        return vendedores
    }
}