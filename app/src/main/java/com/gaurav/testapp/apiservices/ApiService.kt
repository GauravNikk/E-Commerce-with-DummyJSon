package com.gaurav.testapp.apiservices

import com.gaurav.testapp.model.Category
import com.gaurav.testapp.model.ProductDetail
import com.gaurav.testapp.model.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call
import retrofit2.http.Path

interface ApiService {


    @GET("products/categories")
    fun getCategories(): Call<List<Category>>

    //this is for all prodct with 20 limit
    @GET("products")
    fun getProducts(@Query("limit") limit: Int): Call<ProductResponse>

    //Category basd product
    @GET("products/category/{category}")
    fun getProductsByCategory(
        @Path("category") category: String,
        @Query("limit") limit: Int
    ): Call<ProductResponse>


    //https://dummyjson.com/products/search?q=data
    @GET("products/search")
    fun searchProducts(
        //@Query("query") query: String,
        @Query("q") query: String,
        @Query("limit") limit: Int
    ): Call<ProductResponse>


    @GET("products/{product_id}")
    fun getProductDetails(
        @Path("product_id") productId: Int
    ): Call<ProductDetail>

}
