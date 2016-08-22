package de.s3xy.architecturesample.search.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.s3xy.architecturesample.R;
import de.s3xy.architecturesample.github.model.Repository;

/**
 * @author Angelo Rüggeberg <s3xy4ngc@googlemail.com>
 */


public class RepositoryAdapter extends RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder> {

    List<Repository> mRepositories = Collections.emptyList();

    @Override
    public RepositoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RepositoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_repository, parent, false));
    }

    @Override
    public void onBindViewHolder(RepositoryViewHolder holder, int position) {
        Repository repository = mRepositories.get(position);
        Glide.with(holder.mRepositoryOwnerImage.getContext()).load(repository.getOwner().getAvatarUrl()).into(holder.mRepositoryOwnerImage);
        holder.mRepositoryDescription.setText(repository.getDescription());
        holder.mRepositoryFullName.setText(repository.getFullName());
        holder.mRepositoryStarCount.setText(String.format("(\t★%s)", repository.getStargazersCount().toString()));

    }

    @Override
    public int getItemCount() {
        return mRepositories.size();
    }

    public void setRepositories(List<Repository> repositories) {
        mRepositories = repositories;
        notifyDataSetChanged();
    }

    static class RepositoryViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.repository_owner_image)
        ImageView mRepositoryOwnerImage;
        @BindView(R.id.repository_star_count)
        TextView mRepositoryStarCount;
        @BindView(R.id.repository_full_name)
        TextView mRepositoryFullName;
        @BindView(R.id.repository_description)
        TextView mRepositoryDescription;

        public RepositoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
