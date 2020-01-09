package com.alexandreobs.testeconcrete.model.data.remote;

import com.alexandreobs.testeconcrete.model.pojo.repositorio.GitResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GitAPI {

    @GET("search/repositories?q=language:Java&sort=stars")

    Observable<GitResult> getALLRepo(
            @Query("page") int pagina

    );

    @GET("repos/{login}/{name}/pulls")
    Observable<GitResult> getAllPullRe(@Path("login") String login,
                                            @Path("name") String name,
                                       @Query("page") int pagina
    );
}
