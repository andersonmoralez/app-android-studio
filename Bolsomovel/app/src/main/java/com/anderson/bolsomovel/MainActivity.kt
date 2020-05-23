package com.anderson.bolsomovel

<<<<<<< HEAD
=======
import android.content.Context
>>>>>>> AC05
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
<<<<<<< HEAD
=======
import com.google.firebase.messaging.RemoteMessage
>>>>>>> AC05
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

<<<<<<< HEAD
=======
    private val context: Context get() = this

    private var REQUEST_CADASTRO = 1

    var logNotification: Boolean = false

>>>>>>> AC05
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

<<<<<<< HEAD
        button.setOnClickListener { onClickLogin() }
    }

    fun onClickLogin() {
=======
        if(this.intent.hasExtra("produtoId")) {
            button.setOnClickListener { onClickLoginNotification() }
        } else {button.setOnClickListener { onClickLogin() }}
    }

    /*
    override fun onResume() {
        super.onResume()

        // abre o produto caso clique na notificacao com app em segundo plano
        //abrirProduto()
        button.setOnClickListener { onClickLoginNotification() }

        // mostra no log o tokem do firebase
        Log.d("firebase", "Firebase Token: ${Prefs.getString("FB_TOKEN")}")

    }
    */

    fun onClickLogin() {

>>>>>>> AC05
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

<<<<<<< HEAD
    override fun onResume() {
        super.onResume()

        // abre o produto caso clique na notificacao com app em segundo plano
        abrirProduto()

        // mostra no log o tokem do firebase
        Log.d("firebase", "Firebase Token: ${Prefs.getString("FB_TOKEN")}")

    }

    fun abrirProduto() {
        // verifica se existe id do produto na intent
        if (intent.hasExtra("produtoId")) {
=======
    /**/
    fun onClickLoginNotification() {
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

        //abre o produto apos login
        if (nameUser == "aluno" && passwordUser == "impacta") {
            Toast.makeText(this, "Bem vindo usuário: $nameUser!", Toast.LENGTH_SHORT).show()
            progressBar.visibility = View.INVISIBLE
            val intent = Intent(context, ProdutoCadastroActivity::class.java)
            startActivityForResult(intent, REQUEST_CADASTRO)
        } else {
            Toast.makeText(this, "Usuário ou Senha incorreto!", Toast.LENGTH_SHORT).show()
            progressBar.visibility = View.INVISIBLE
        }

    }


    /**/
    fun abrirProduto() {
        // verifica se existe id do produto na intent
        if (this.intent.hasExtra("produtoId")) {
>>>>>>> AC05
            Thread {
                var produtoId = intent.getStringExtra("produtoId")?.toLong()!!
                val produto = ProdutoService.getProduto(this, produtoId)
                runOnUiThread {
                    val intentProduto = Intent(this, ProdutoActivity::class.java)
                    intentProduto.putExtra("produto", produto)
<<<<<<< HEAD
=======

>>>>>>> AC05
                    startActivity(intentProduto)
                }
            }.start()
        }

    }
<<<<<<< HEAD
=======



>>>>>>> AC05
}
