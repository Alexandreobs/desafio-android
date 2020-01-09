package com.alexandreobs.testeconcrete.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.alexandreobs.testeconcrete.model.data.repository.GitRepository;
import com.alexandreobs.testeconcrete.model.pojo.repositorio.Item;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class PullResquestViewModel extends AndroidViewModel {

    private MutableLiveData<List<Item>> listaPull = new MutableLiveData<>();
    private GitRepository gitRepository = new GitRepository();
    private CompositeDisposable disposable = new CompositeDisposable();
    private MutableLiveData<Boolean> loading = new MutableLiveData<>();


    public PullResquestViewModel(@NonNull Application application) {
        super(application);
    }


    public LiveData<List<Item>> getListaPullRe(){
        return this.listaPull;
    }

    public LiveData<Boolean> getLoading(){
        return this.loading;
    }

    public void getPullRes(String login, String name, int pagina){
        disposable.add(
                gitRepository.getPullReq(login, name, pagina)

                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable -> loading.setValue(true))
                        .doAfterTerminate(()-> {
                            loading.setValue(false);
                        })
                        .subscribe(gitResult -> {
                                    listaPull.setValue(gitResult.getItems());
                                },
                                throwable -> {
                                    Log.i("LOG", "Erro" + throwable.getMessage());
                                }
                        )
        );

    }

}

