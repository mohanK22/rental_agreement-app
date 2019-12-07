package in.aartimkarande.rentalagreement.myrentalagreement.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import in.aartimkarande.rentalagreement.myrentalagreement.R;
import in.aartimkarande.rentalagreement.myrentalagreement.admin.UserDataActivity;
import in.aartimkarande.rentalagreement.myrentalagreement.model.User;
import in.aartimkarande.rentalagreement.myrentalagreement.model.UserInfo;

public class UsersListAdapter extends RecyclerView.Adapter<UsersListAdapter.MyViewHolder> {

    private Context context;
    private List<UserInfo> userInfoList;

    public UsersListAdapter() {
    }

    public UsersListAdapter(Context context) {
        this.context = context;
    }

    public UsersListAdapter(Context context, List<UserInfo> userInfoList) {
        this.context = context;
        this.userInfoList = userInfoList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View myView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_list_layout, viewGroup, false);
        return new MyViewHolder(myView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {

        final UserInfo userInfo = userInfoList.get(i);

        myViewHolder.textViewUserName.setText(userInfo.getUserFullName());
        myViewHolder.textViewUsername.setText("Username : " + userInfo.getUserUsername());

        myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, UserDataActivity.class).putExtra("user_id", userInfo.getId()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return userInfoList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        TextView textViewUserName;
        TextView textViewUsername;

        public MyViewHolder(@NonNull View myView) {
            super(myView);

            cardView = myView.findViewById(R.id.card_view);
            textViewUserName = myView.findViewById(R.id.user_name);
            textViewUsername = myView.findViewById(R.id.username);

        }

    }

}
