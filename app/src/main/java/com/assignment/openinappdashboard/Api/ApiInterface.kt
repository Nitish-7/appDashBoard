package com.assignment.openinappdashboard.Api

import com.assignment.openinappdashboard.model.LinkPojoModel
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("v1/dashboardNew")
     fun getLinkData(): Call<LinkPojoModel>

}