package com.anderson.bolsomovel

class Vendedor {
    var id: Long = 0
    var nome: String = ""
    var codigoVendedor: Int = 0
    var email: String = ""
    var senha:  String = ""

    override fun toString(): String {
        return "Vendedor(nome='$nome')"
    }

}