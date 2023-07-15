package com.assignment.openinappdashboard

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.assignment.openinappdashboard.adapters.LinkDataItemAdapter
import com.assignment.openinappdashboard.viewmodels.MainViewModel
import com.assignment.openinappdashboard.model.*
import com.assignment.openinappdashboard.databinding.ActivityMainBinding
import com.assignment.openinappdashboard.parsersandconverters.TimeAndDateProvider
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import kotlin.collections.ArrayList


class mainActivity : AppCompatActivity() {
    lateinit var adapter: LinkDataItemAdapter
    lateinit var viewModel: MainViewModel
    lateinit var binding: ActivityMainBinding

    private lateinit var chartDataProvider: ChartData
    private var topLinkItemListForAdapter: ArrayList<LinkModelForAdapterItemUI> = ArrayList()
    private var recentLinkItemListForAdapter: ArrayList<LinkModelForAdapterItemUI> = ArrayList()

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.topLinkItemListForAdapter.observe(
            this
        ) { t ->
            topLinkItemListForAdapter = t!!
        }
        viewModel.recentLinkItemListForAdapter.observe(
            this
        ) { t -> recentLinkItemListForAdapter = t!!
        }
        viewModel.chartDataProvider.observe(this) { t ->
            chartDataProvider = t
            drawChart()
            setUIdata()
        }



        binding.btRecentLink.setOnClickListener {
            changeTabStyleAndListItems(
                binding.btRecentLink,
                recentLinkItemListForAdapter,
                binding.btTopLink
            )
        }
        binding.btTopLink.setOnClickListener {
            changeTabStyleAndListItems(
                binding.btTopLink,
                topLinkItemListForAdapter,
                binding.btRecentLink
            )
        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setUIdata() {
        adapter = LinkDataItemAdapter(topLinkItemListForAdapter)
        binding.rvLinksList.layoutManager =
            LinearLayoutManager(this@mainActivity, RecyclerView.VERTICAL, false)

        val mainUIData: MainActivityModelForUi = MainActivityModelForUi(
            chartDataProvider.chartDateRange,
            TimeAndDateProvider.getGreetingTextFromLocalTime(),
            adapter
        )
        binding.data = mainUIData
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(Build.VERSION_CODES.M)
    private fun drawChart() {
        //chart graphics enable or disable
        binding.chart.data = chartDataProvider.lineData
        chartDataProvider.lineDataSet.color =
            getColor(R.color.app_theme_color)
        chartDataProvider.lineDataSet.lineWidth = 2f
        chartDataProvider.lineDataSet.setDrawCircles(false)
        chartDataProvider.lineDataSet.setDrawValues(false)
        chartDataProvider.lineDataSet.setDrawFilled(true)
        chartDataProvider.lineDataSet.fillDrawable =
            getDrawable(R.drawable.bg_line_chart_gradient)
        binding.chart.setDrawBorders(false)
        binding.chart.setClipValuesToContent(false)
        binding.chart.description.isEnabled = false
        binding.chart.animateY(1000)
        binding.chart.isHighlightPerDragEnabled = false
        binding.chart.isHighlightPerTapEnabled = false
        val visibleXRangeMinimum = 8f
        val visibleXRangeMaximum = 20f
        binding.chart.setVisibleXRangeMinimum(visibleXRangeMinimum)
        binding.chart.setVisibleXRangeMaximum(visibleXRangeMaximum)
        //activityMainBinding.chart.setPinchZoom(false)
        binding.chart.invalidate()


        val xAxisTop: XAxis = binding.chart.xAxis
        xAxisTop.position = XAxis.XAxisPosition.TOP
        xAxisTop.isEnabled = false


        val xAxis: XAxis = binding.chart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.isEnabled = true
        xAxis.textColor = getColor(R.color.off_black_1)

        val yAxisLeft: YAxis = binding.chart.axisLeft
        yAxisLeft.textColor = getColor(R.color.off_black_1)
        yAxisLeft.axisMinimum = 0f
        yAxisLeft.setDrawAxisLine(false)
        val yAxisRight: YAxis = binding.chart.axisRight
        yAxisRight.isEnabled = false

        val legend: Legend = binding.chart.legend
        legend.isEnabled = false

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(Build.VERSION_CODES.Q)
    private fun changeTabStyleAndListItems(
        selectView: TextView,
        linkItemListForAdapter: ArrayList<LinkModelForAdapterItemUI>,
        unSelectView: TextView
    ) {
        selectView.background = getDrawable(R.drawable.bg_bt_circular_selected)
        selectView.setTextColor(Color.WHITE)

        adapter.updateRecyclerView(linkItemListForAdapter)

        unSelectView.background = getDrawable(R.color.transparent)
        unSelectView.setTextColor(getColor(R.color.off_black_1))
    }
}




