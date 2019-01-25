package p.vasylprokudin.axon.ui.fragments.fragment_users;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import p.vasylprokudin.axon.R;
import p.vasylprokudin.axon.data.network.model.Results;
import p.vasylprokudin.axon.ui.fragments.fragment_user_details.FragmentUserDetails;

public class AdapterUsers extends RecyclerView.Adapter<AdapterUsers.ViewHolder> {

    private OnItemClicked onClick;
    private List<Results> resultsList;
    private Context context;

    public AdapterUsers(List<Results> resultsList, OnItemClicked onClick, Context context) {
        this.resultsList = resultsList;
        this.onClick = onClick;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_item_user, parent, false);
        return new AdapterUsers.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Results result = resultsList.get(i);
        String firstname = result.getName().getFirst();
        String lastname = result.getName().getLast();

        viewHolder.tvFullName.setText(String.format("%s %s", firstname, lastname));
        viewHolder.tvGender.setText(result.getGender());
        viewHolder.tvPhone.setText(result.getCell());

        Glide.with(context).load(result.getPicture().getLarge()).into(viewHolder.civUserAvatar);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.onItemClick(result);
            }
        });

    }

    @Override
    public int getItemCount() {
        return resultsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvFullName)
        TextView tvFullName;
        @BindView(R.id.tvGender)
        TextView tvGender;
        @BindView(R.id.tvPhone)
        TextView tvPhone;
        @BindView(R.id.civUserAvatar)
        CircleImageView civUserAvatar;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void loadMoreData(List<Results> result){
        for (Results results : result){
            resultsList.add(results);
        }
        notifyDataSetChanged();
    }

    public interface OnItemClicked {
        void onItemClick(Results result);
    }

}
