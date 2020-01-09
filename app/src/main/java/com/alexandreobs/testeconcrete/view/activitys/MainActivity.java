package com.alexandreobs.testeconcrete.view.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.alexandreobs.testeconcrete.R;
import com.alexandreobs.testeconcrete.model.pojo.repositorio.GitResult;
import com.alexandreobs.testeconcrete.model.pojo.repositorio.Item;
import com.alexandreobs.testeconcrete.view.adapter.GitAdapter;
import com.alexandreobs.testeconcrete.view.interfaces.RepositorioOnClick;
import com.alexandreobs.testeconcrete.viewmodel.RepositorioViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RepositorioOnClick {

    private RecyclerView recyclerView;
    private RepositorioViewModel viewModel;
    private GitAdapter adapter;
    private List<Item> itemList = new ArrayList<>();
    private ProgressBar progressBar;
    private  int pagina = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.recyclerRepos);
        viewModel = ViewModelProviders.of(this).get(RepositorioViewModel.class);
        adapter = new GitAdapter(itemList, this);


        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        setScrollView();

        viewModel.getRepositorios(pagina);
        viewModel.getListaRespositorios().observe(this, new Observer<List<Item>>() {
            @Override
            public void onChanged(List<Item> results1) {
                adapter.update(results1);
            }
        });

        progressBar = findViewById(R.id.progress_bar);
        viewModel.getLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean loading) {
                if (loading) {
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

    }

    @Override
    public void OnClick(Item result) {
        Intent intent = new Intent(MainActivity.this, PullReqActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("Item", result);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    private void setScrollView(){
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                int totalItemCount = layoutManager.getItemCount();
                int lastVisible = layoutManager.findLastVisibleItemPosition();
                boolean ultimoItem = lastVisible + 5 >= totalItemCount;

                if (totalItemCount > 0 && ultimoItem){
                    pagina++;
                    viewModel.getRepositorios(pagina);
                }

            }
        });
    }
}
