package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.TextView
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOError
import java.io.IOException


class MainActivity : AppCompatActivity() {

    //private val targetUrl="https://data.epa.gov.tw/api/v2/aqx_p_02?api_key=e8dd42e6-9b8b-43f8-991e-b3dee723a52d&limit=1000&sort=datacreationdate desc&format=JSON"
    private val targetUrl="https://data.epa.gov.tw/api/v2/aqx_p_432?api_key=e8dd42e6-9b8b-43f8-991e-b3dee723a52d&limit=1000&sort=ImportDate desc&format=JSON"
    private var getString:String=""
    private lateinit var allData:ArrayList<OneItem>
    private lateinit var selectData:ArrayList<OneItem>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn=findViewById<Button>(R.id.btnUpdate)
        val myRecyclerView= findViewById<RecyclerView>(R.id.rv)
        val myspinner= findViewById<Spinner>(R.id.myspinner)
        val myprogressBar= findViewById<ProgressBar>(R.id.progressBar)
        myprogressBar.setVisibility(View.GONE)
        myRecyclerView.layoutManager = LinearLayoutManager(this)
        allData=ArrayList<OneItem>()
        selectData=ArrayList<OneItem>()



        btn.setOnClickListener{
            myprogressBar.setVisibility(View.VISIBLE)
            val client= OkHttpClient.Builder().build()
            val request=Request.Builder().url(targetUrl).build()

            GlobalScope.launch {
                val getData= runBlocking { var response = client.newCall(request).execute()

                    response.body.run {
                        getString = string()
                        Log.d("myTag", getString)
                        try {
                            val allRecord = JSONObject(getString).getJSONArray("records")

                            var jo: JSONObject
                            var jsite: String
                            var jcounty: String
                            var jstatus: String
                            var jpm25: String
                            var jwind: String

                                for (i in 0 until allRecord.length()) {
                                    jo = allRecord.getJSONObject(i)
                                    jsite = jo.getString("sitename")
                                    jcounty = jo.getString("county")
                                    jstatus = jo.getString("status")
                                    jpm25 = jo.getString("pm2.5")
                                    jwind=jo.getString("wind_speed")
                                    allData.add(OneItem(jsite, jcounty, jstatus, jpm25,jwind))
                                    if (myspinner.selectedItem.toString()==allData[i].icounty){
                                        selectData.add(OneItem(jsite, jcounty, jstatus, jpm25,jwind))
                                    }
//                                    Log.d("myTag", "in for ${allData[i].iname}")
                            }
                        } catch (e: IOException) {
                            Log.d("myTag", e.toString())
                        }

                    }
                }
                runOnUiThread{
                    myprogressBar.setVisibility(View.GONE)
                    if (myspinner.selectedItem.toString()!="請選擇") {
                        var myAdapter=MyRecyclerViewAdapter(selectData)
                        myRecyclerView.adapter=myAdapter
                    }else
                    {
                        var myAdapter=MyRecyclerViewAdapter(allData)
                        myRecyclerView.adapter=myAdapter
                    }
                    }
            }
            selectData.clear()
            }


            }


        }


