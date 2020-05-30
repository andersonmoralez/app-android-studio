package com.anderson.bolsomovel

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.GsonBuilder
import java.io.Serializable

@Entity(tableName="vendedor")
class Vendedor: Serializable {

    @PrimaryKey
    var id: Long = 0

    var codVendedor: String = ""
    var nome = ""
    var emailVendedor: String = ""
    var telefone: String = ""
    var celular: String = ""
    var funcionario: String = ""
    var senha: String = ""

    override fun toString(): String {
        return "Vendedor(nome='$nome')"
    }

    fun toJson(): String {
        return GsonBuilder().create().toJson(this)
    }

}