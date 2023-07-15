package com.assignment.openinappdashboard.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.openinappdashboard.Api.ApiInterface
import com.assignment.openinappdashboard.Api.RetrofitInstance
import com.assignment.openinappdashboard.model.ChartData
import com.assignment.openinappdashboard.model.LinkInfoData
import com.assignment.openinappdashboard.model.LinkModelForAdapterItemUI
import com.assignment.openinappdashboard.model.LinkPojoModel
import com.assignment.openinappdashboard.parsersandconverters.TimeAndDateProvider
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import kotlin.collections.ArrayList

class MainViewModel : ViewModel() {

    private var retrofitInstance: Retrofit

    private var _topLinkItemListForAdapter: MutableLiveData<ArrayList<LinkModelForAdapterItemUI>> =
        MutableLiveData()
    private var _recentLinkItemListForAdapter: MutableLiveData<ArrayList<LinkModelForAdapterItemUI>> =
        MutableLiveData()
    private var _chartDataProvider: MutableLiveData<ChartData> = MutableLiveData()

    val topLinkItemListForAdapter: LiveData<ArrayList<LinkModelForAdapterItemUI>> =
        _topLinkItemListForAdapter
    val recentLinkItemListForAdapter: LiveData<ArrayList<LinkModelForAdapterItemUI>> =
        _recentLinkItemListForAdapter
    val chartDataProvider: LiveData<ChartData> = _chartDataProvider

    init {
        retrofitInstance = RetrofitInstance.getRetrofitInstance()
        viewModelScope.launch {
            callApi()
        }
    }

    private  fun callApi() {
        val call: Call<LinkPojoModel> =
            retrofitInstance.create(ApiInterface::class.java).getLinkData()

        call.enqueue(object : Callback<LinkPojoModel> {
            override fun onResponse(
                call: Call<LinkPojoModel>,
                response: Response<LinkPojoModel>
            ) {
                if (response.isSuccessful) {
                    val topLinkInfoDataList = response.body()?.data?.top_links
                    val recentLinkInfoDataList = response.body()?.data?.recent_links

                    //fetching toplinks data
                    _topLinkItemListForAdapter.value=fetch(topLinkInfoDataList!!)

                    //fetching recent links data
                    _recentLinkItemListForAdapter.value=fetch(recentLinkInfoDataList!!)

                    //fetching chart data
                    _chartDataProvider.value = ChartData(
                        response.body()?.data?.overall_url_chart
                    )
                }

            }

            override fun onFailure(call: Call<LinkPojoModel>, t: Throwable) {
            }
        })
    }

    private fun fetch(LinkInfoDataList: List<LinkInfoData>): ArrayList<LinkModelForAdapterItemUI> {
        val tempList:ArrayList<LinkModelForAdapterItemUI> = ArrayList()
        for (item in LinkInfoDataList) {
            val date = TimeAndDateProvider.convertStringToDateObject(
                item.created_at.substring(
                    0,
                    10
                )
            )
            tempList.add(
                LinkModelForAdapterItemUI(
                    item.title,
                    item.web_link,
                    item.total_clicks.toString(),
                    date.date + " " + date.month + " " + date.year
                )
            )
        }
        return tempList
    }
}