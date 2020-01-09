package com.alexandreobs.testeconcrete.model.data.repository;

import com.alexandreobs.testeconcrete.model.pojo.repositorio.GitResult;

import io.reactivex.Observable;

import static com.alexandreobs.testeconcrete.model.data.remote.RetrofitService.getApiService;

public class GitRepository {

    public Observable<GitResult> getRepo(int pagina) {
        return getApiService().getALLRepo(pagina);
    }

    public Observable<GitResult> getPullReq(String login, String name, int pagina) {
        return getApiService().getAllPullRe(login, name, pagina);
    }

}
