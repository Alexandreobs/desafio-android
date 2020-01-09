package com.alexandreobs.testeconcrete.view.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alexandreobs.testeconcrete.R;
import com.alexandreobs.testeconcrete.model.pojo.pullrequest.PullResult;
import com.alexandreobs.testeconcrete.model.pojo.repositorio.Item;
import com.alexandreobs.testeconcrete.view.adapter.PullAdapter;
import com.alexandreobs.testeconcrete.view.interfaces.PullRequestOnClick;
import com.alexandreobs.testeconcrete.viewmodel.PullResquestViewModel;
import com.alexandreobs.testeconcrete.viewmodel.RepositorioViewModel;

import java.util.ArrayList;
import java.util.List;

public class PullReqActivity extends AppCompatActivity implements PullRequestOnClick {

    private TextView numeroOpen;
    private TextView numeroClosed;
    private RecyclerView recyclerView;
    private PullAdapter adapter;
    private PullResquestViewModel viewModel;
    private String login;
    private String name;
    private List<PullResult> itemList = new ArrayList<>();
    private ProgressBar progressBar;
    private int pagina = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_req);

        initView();

        if (getIntent() != null) {
            Item item = getIntent().getParcelableExtra("Item");
           login = item.getOwner().getLogin();
            name = item.getName();
        }

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



    private void initView() {

        viewModel = ViewModelProviders.of(this).get(PullResquestViewModel.class);
        adapter = new PullAdapter(itemList, this);

        progressBar = findViewById(R.id.progress_bar);
        recyclerView = findViewById(R.id.recyclerPUll);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        setScrollView();
    }

    @Override
    public void PullOnClick(PullResult result) {

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
                    viewModel.getPullRes(login, name, pagina);
                }

            }
        });
    }
}
