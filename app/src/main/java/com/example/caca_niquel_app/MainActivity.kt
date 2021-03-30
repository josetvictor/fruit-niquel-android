package com.example.caca_niquel_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    // flags
    var choice1 = false
    var choice2 = false
    var choice3 = false
    var aux = 0
    var contador1 = 0
    var contador2 = 0
    var contador3 = 0
    var speed: Int = 1000
    var score = 0
    var scoreTemp = 0
    var life = 3
    var level = 1
    var record = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val scoreTextView: TextView = findViewById(R.id.score)
        val lifeTextView: TextView = findViewById(R.id.life)
        val recordTextView: TextView = findViewById(R.id.record)
        val levelTextView: TextView = findViewById(R.id.level)
        // passo 1: fazer a imagem girar
        Thread(Runnable {

            // Slots de frutas
            val iv1: ImageView = findViewById(R.id.iv1) // 100 pontos
            val iv2: ImageView = findViewById(R.id.iv2) // 200 pontos
            val iv3: ImageView = findViewById(R.id.iv3) // 300 pontos
            val listIVs = intArrayOf(R.drawable.banana, R.drawable.cerejaaa, R.drawable.melanciafofa)

            fun cont(): Int {
                return (0..2).random()
            }

            while (true){
                if(!choice1){
                    contador1 = cont()
                    iv1.setImageResource(listIVs[contador1])
                }
                if(!choice2){
                    contador2 = cont()
                    iv2.setImageResource(listIVs[contador2])
                }
                if(!choice3){
                    contador3 = cont()
                    iv3.setImageResource(listIVs[contador3])
                }

                try {
                    Thread.sleep(speed.toLong())
                } catch (e: InterruptedException){
                    e.printStackTrace()
                }
            }
        }).start()

        fun score(cont: Int){
            if(cont == 0){
                score += 100
                scoreTemp += 100
            }
             else if(cont == 1){
                score += 200
                scoreTemp += 200
            }
             else if(cont == 2){
                score += 300
                scoreTemp += 300
            }
            scoreTextView.text = "Score: $score"
        }

        fun life(){
            if(scoreTemp==300 || scoreTemp==600 || scoreTemp==900){
                scoreTemp = 0
                score*=1000
                level++
                speed-=250
                scoreTextView.text = "Score: $score"
                levelTextView.text = "Level: $level"
                choice1 = false
                choice2 = false
                choice3 = false
                aux = 0
            } else {
                scoreTemp=0
                life--
                lifeTextView.text = "Life: $life"
                choice1 = false
                choice2 = false
                choice3 = false
                aux = 0
            }

            if(life == 0){
                if(score > record)
                    record = score
                score=0
                level=1
                life=3
                speed=1000
                recordTextView.text = "Record: $record"
                scoreTextView.text = "Score: $score"
                levelTextView.text = "Level: $level"
                lifeTextView.text = "Life: $life"
                choice1 = false
                choice2 = false
                choice3 = false
                aux = 0
            }
        }

        //passo 2: fazer imagem parar
        val button: Button = findViewById(R.id.button)
        button.setOnClickListener(View.OnClickListener {
            if (!choice3 && aux==2) {
                choice3 = true
                aux++
                score(contador3)
                life()
            }
            else if (!choice2 && aux==1) {
                choice2 = true
                aux++
                score(contador2)
            }
            else if (!choice1 && aux==0) {
                choice1 = true
                aux++
                score(contador1)
            }
        })

    }
}