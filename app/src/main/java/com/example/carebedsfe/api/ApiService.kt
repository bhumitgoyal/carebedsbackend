package com.example.carebedsfe.api

import com.example.carebedsfe.model.Bed
import com.example.carebedsfe.model.Hospital
import com.example.carebedsfe.model.LoginRequest
import com.example.carebedsfe.model.LoginResponse
import com.example.carebedsfe.model.Patient
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("/patients/login")
    suspend fun loginPatient(@Body loginRequest: LoginRequest): Response<LoginResponse>
    @POST("/hospital/login")
    suspend fun loginHospital(@Body loginRequest: LoginRequest): Response<LoginResponse>
    @GET("beds/{id}")
    suspend fun getBedById(@Path("id") id:Int):Response<Bed>
    @GET("/patients/{id}")
    suspend fun getPatientById(@Path("id") userId: Int): Response<Patient>

    @GET("/hospital/{hospitalId}/patients")
    suspend fun getPatientsByHospitalId(@Path("hospitalId")hospitalId: Int): Response<List<Patient>>
    @GET("/hospital/{hospitalId}")
    suspend fun getHospitalById(@Path("hospitalId")hospitalId: Int): Response<Hospital>
    @GET("/hospital/{hospitalId}/beds")
    suspend fun getBedsByHospitalId(@Path("hospitalId")hospitalId: Int): Response<List<Bed>>
}