package com.anderson.bolsomovel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageButton2.setImageResource(R.drawable.lunne_logo)

        var lembrar = Prefs.getBoolean("lembrar")

        if (lembrar) {
            var lembrarNome = Prefs.getString("lembrarNome")
            var lembrarSenha = Prefs.getString("lembrarSenha")
            inputUser.setText(lembrarNome)
            password.setText(lembrarSenha)
            checkBoxLogin.isChecked = lembrar
        }

        button.setOnClickListener { onClickLogin() }
    }

    fun onClickLogin() {
        val nameUser = inputUser.text.toString()
        val passwordUser = password.text.toString()

        Prefs.setBoolean("lembrar", checkBoxLogin.isChecked)
        // verificar se é para pembrar nome e senha
        if (checkBoxLogin.isChecked) {
            Prefs.setString("lembrarNome", nameUser)
            Prefs.setString("lembrarSenha", passwordUser)
        } else{
            Prefs.setString("lembrarNome", "")
            Prefs.setString("lembrarSenha", "")
        }

        progressBar.visibility = View.VISIBLE

        var intent = Intent(this, TelaInicial::class.java)

        if (nameUser == "aluno" && passwordUser == "impacta") {
            Toast.makeText(this, "Bem vindo usuário: $nameUser!", Toast.LENGTH_SHORT).show()
            progressBar.visibility = View.INVISIBLE
            startActivity(intent)
        } else {
            Toast.makeText(this, "Usuário ou Senha incorreto!", Toast.LENGTH_SHORT).show()
            progressBar.visibility = View.INVISIBLE
        }

    }
}
