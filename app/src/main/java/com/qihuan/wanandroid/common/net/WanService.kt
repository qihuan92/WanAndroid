package com.qihuan.wanandroid.common.net

import com.qihuan.wanandroid.bean.*
import retrofit2.http.*

interface WanService {

    companion object {
        const val BASE_URL = "https://www.wanandroid.com"
    }

    @GET("/article/top/json")
    suspend fun getTopArticles(): WanResponse<List<Article>>

    @GET("/article/list/{page}/json")
    suspend fun getHomeArticles(
        @Path("page") page: Int
    ): WanResponse<WanPage<Article>>

    @GET("/banner/json")
    suspend fun getBanner(): WanResponse<List<BannerBean>>

    @GET("/tree/json")
    suspend fun getSystemTreeList(): WanResponse<List<SystemNode>>

    @GET("/article/list/{page}/json")
    suspend fun getSystemTypeDetail(
        @Path("page") page: Int,
        @Query("cid") cid: Int
    ): WanResponse<WanPage<Article>>

    @GET("/navi/json")
    suspend fun getNavigation(): WanResponse<List<Navigation>>

    @GET("/project/tree/json")
    suspend fun getProjectType(): WanResponse<List<SystemNode>>

    @GET("/wxarticle/chapters/json")
    suspend fun getBlogType(): WanResponse<List<SystemNode>>

    @GET("/wxarticle/list/{id}/{page}/json")
    suspend fun getBlogArticle(
        @Path("id") id: Int,
        @Path("page") page: Int
    ): WanResponse<WanPage<Article>>

    @GET("/project/list/{page}/json")
    suspend fun getProjectTypeDetail(
        @Path("page") page: Int,
        @Query("cid") cid: Int
    ): WanResponse<WanPage<Article>>

    @GET("/article/listproject/{page}/json")
    suspend fun getLastedProject(
        @Path("page") page: Int
    ): WanResponse<WanPage<Article>>

    @GET("/friend/json")
    suspend fun getWebsites(): WanResponse<List<SearchKey>>

    @GET("/hotkey/json")
    suspend fun hotKey(): WanResponse<List<SearchKey>>

    @FormUrlEncoded
    @POST("/article/query/{page}/json")
    suspend fun search(
        @Path("page") page: Int,
        @Field("k") key: String
    ): WanResponse<WanPage<Article>>

    @FormUrlEncoded
    @POST("/user/login")
    suspend fun login(
        @Field("username") userName: String,
        @Field("password") passWord: String
    ): WanResponse<User>

    @GET("/user/logout/json")
    suspend fun logOut(): WanResponse<Any>

    @FormUrlEncoded
    @POST("/user/register")
    suspend fun register(
        @Field("username") userName: String,
        @Field("password") passWord: String,
        @Field("repassword") rePassWord: String
    ): WanResponse<User>

    @GET("/lg/collect/list/{page}/json")
    suspend fun getCollectArticles(
        @Path("page") page: Int
    ): WanResponse<WanPage<Article>>

    @POST("/lg/collect/{id}/json")
    suspend fun collectArticle(
        @Path("id") id: Int
    ): WanResponse<WanPage<Article>>

    @POST("/lg/uncollect_originId/{id}/json")
    suspend fun cancelCollectArticle(
        @Path("id") id: Int
    ): WanResponse<WanPage<Article>>

    @GET("/user_article/list/{page}/json")
    suspend fun getSquareArticleList(
        @Path("page") page: Int
    ): WanResponse<WanPage<Article>>

    @FormUrlEncoded
    @POST("/lg/user_article/add/json")
    suspend fun shareArticle(
        @Field("title") title: String,
        @Field("link") url: String
    ): WanResponse<String>
}