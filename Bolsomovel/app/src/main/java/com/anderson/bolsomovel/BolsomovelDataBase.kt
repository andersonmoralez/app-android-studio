package com.anderson.bolsomovel

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities=arrayOf(Produto::class, Vendedor::class), version = 1)
abstract class BolsomovelDataBase: RoomDatabase() {
    abstract fun produtoDAO(): ProdutoDAO
    abstract fun vendedorDAO(): VendedorDAO
}