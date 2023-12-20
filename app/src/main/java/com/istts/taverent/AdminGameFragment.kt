package com.istts.taverent

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.androidplot.xy.*
import com.istts.taverent.databinding.FragmentAdminGameBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.text.FieldPosition
import java.text.Format
import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.*

class AdminGameFragment : Fragment() {
    private lateinit var binding: FragmentAdminGameBinding
    private val coroutine = CoroutineScope(Dispatchers.IO)
    var WS_HOST = ""
    var rented: ArrayList<Pembayaran2> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        WS_HOST = resources.getString(R.string.WS_HOST)
        binding = FragmentAdminGameBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = this.arguments
        rented = args?.getParcelableArrayList("rent")!!;
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        formatter.timeZone = TimeZone.getTimeZone("Indonesia/Jakarta");
        val xVals = arrayOf<Number>(1, 2, 3, 4, 5, 6, 7)
        val yVals = arrayOf<Number>(0, 0, 0, 0, 0,0,0)
        for (i in 0 until rented.size){
            val date = formatter.parse(rented[i].tanggal_selesai)
            val remove = date.toString()
            val finisheds = remove.substring(0,3);

            if(finisheds.equals("Mon")){
                val cek = yVals[0]
                yVals[0]=cek.toInt()+1
            }
            if(finisheds.equals("Tue")){
                val cek = yVals[1]
                yVals[1]=cek.toInt()+1
            }
            if(finisheds.equals("Wed")){
                val cek = yVals[2]
                yVals[2]=cek.toInt()+1
            }
            if(finisheds.equals("Thu")){
                val cek = yVals[3]
                yVals[3]=cek.toInt()+1
            }
            if(finisheds.equals("Fri")){
                val cek = yVals[4]
                yVals[4]=cek.toInt()+1
            }
            if(finisheds.equals("Sat")){
                val cek = yVals[5]
                yVals[5]=cek.toInt()+1
            }
            if(finisheds.equals("Sun")){
                val cek = yVals[6]
                yVals[6]=cek.toInt()+1
            }
        }
        val series: XYSeries = SimpleXYSeries(Arrays.asList(*yVals),SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,
            "Daily Rent Performance")
        val seriesformat = LineAndPointFormatter(Color.BLUE,Color.BLACK,null,null)
        binding.plot.addSeries(series,seriesformat)
        binding.plot.graph.getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).format = object:Format(){
            override fun format(
                obj: Any?,
                toAppendTo: StringBuffer?,
                pos: FieldPosition
            ): StringBuffer {
                val i = Math.round((obj as Number).toFloat())
                return toAppendTo!!.append(xVals[i])
            }

            override fun parseObject(source: String?, pos: ParsePosition?): Any? {
                return null
            }
        }
        PanZoom.attach(binding.plot)




    }
}