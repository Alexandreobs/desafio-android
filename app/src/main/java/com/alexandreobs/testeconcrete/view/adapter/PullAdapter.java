package com.alexandreobs.testeconcrete.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alexandreobs.testeconcrete.R;
import com.alexandreobs.testeconcrete.model.pojo.pullrequest.PullResult;
import com.alexandreobs.testeconcrete.view.interfaces.PullRequestOnClick;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PullAdapter extends RecyclerView.Adapter<PullAdapter.ViewHolder> {

    private List<PullResult> resultlistPull;
    private PullRequestOnClick requestOnClick;

    public PullAdapter(List<PullResult> resultlistPull, PullRequestOnClick requestOnClick) {
        this.resultlistPull = resultlistPull;
        this.requestOnClick = requestOnClick;
    }

    @NonNull
    @Override
    public PullAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_pullreq, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PullAdapter.ViewHolder holder, int position) {
        final PullResult result = this.resultlistPull.get(position);
        holder.onBind(result);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestOnClick.PullOnClick(result);
            }
        });

    }

    @Override
    public int getItemCount() {
        return resultlistPull.size();
    }

    public void updatePull(List<PullResult> results) {
        if (this.resultlistPull.isEmpty()){
            this.resultlistPull = results;
        } else {
            this.resultlistPull.addAll(results);
        }
        notifyDataSetChanged();
    }
    public void clear() {
        this.resultlistPull.clear();
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView nomeUser;
        private TextView tituloPull;
        private TextView descricaoPUll;
        private TextView fullNamePull;
        private CircleImageView fotoUserPull;


        public ViewHolder(@NonNull View view) {
            super(view);

            nomeUser = view.findViewById(R.id.NomeUserPullRe);
            tituloPull = view.findViewById(R.id.tituloPullRes);
            descricaoPUll = view.findViewById(R.id.DescricaoPullRe);
            fullNamePull = view.findViewById(R.id.DescricaoPullRe);
            fotoUserPull = view.findViewById(R.id.fotoPerfilCardPull);

        }

        public void onBind(PullResult result) {
            nomeUser.setText(result.getUser().getLogin());
            tituloPull.setText(result.getTitle());
            descricaoPUll.setText(result.getBody());


            Picasso.get().load(result.getUser().getAvatarUrl()).into(fotoUserPull);

        }
    }
}
