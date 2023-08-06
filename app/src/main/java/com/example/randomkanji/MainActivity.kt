package com.example.randomkanji

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var data:ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loaddata()
    }
    private fun loaddata(){
        val kanjiletter: TextView =findViewById<TextView>(R.id.kanjitext)
        val queue = Volley.newRequestQueue(this)
        val url = "https://kanjiapi.dev/v1/kanji/joyo"
        val req=JsonArrayRequest(Request.Method.GET,url,null,
            Response.Listener{ response->kanjiletter.text="ia m good"
                data= ArrayList(response.length())
                if(response!=null){
                    for(i in 0 until response.length()){
                    data.add(response[i].toString());
                }
                    Log.d("DATA",response[0].toString())
                }

                },
            Response.ErrorListener { kanjiletter.text="iam broke"
                Log.e("Error",it.localizedMessage);
            })
        queue.add(req)
    }

    fun nextword(view: View) {
      val kanjiletter=findViewById<TextView>(R.id.kanjitext)
        kanjiletter.text=data[Random.nextInt(0,data.size-1)]
        val queue = Volley.newRequestQueue(this)
        val url = "https://kanjiapi.dev/v1/kanji/"+kanjiletter.text
        val req=JsonObjectRequest(Request.Method.GET,url,null,
            Response.Listener{ resonse->
                val meaning=resonse.getString("heisig_en");
                val mean=findViewById<TextView>(R.id.meaning);
                mean.text=meaning.toString()
                val hiragan=resonse.getString("kun_readings")
                val hira=findViewById<TextView>(R.id.hiragana)
                hira.text=hiragan.toString();
                Log.d("DATA",resonse.toString())},
            Response.ErrorListener {
                Log.e("Error",it.localizedMessage);
            })
        queue.add(req)
    }

}

