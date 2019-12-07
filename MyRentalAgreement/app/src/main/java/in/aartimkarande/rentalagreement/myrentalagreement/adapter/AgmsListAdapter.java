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

import java.util.List;

import in.aartimkarande.rentalagreement.myrentalagreement.R;
import in.aartimkarande.rentalagreement.myrentalagreement.activity.CreateAgreementDashboardActivity;
import in.aartimkarande.rentalagreement.myrentalagreement.admin.AgmDataActivity;
import in.aartimkarande.rentalagreement.myrentalagreement.admin.ViewAgmInfoActivity;
import in.aartimkarande.rentalagreement.myrentalagreement.model.AgreementLists;

public class AgmsListAdapter extends RecyclerView.Adapter<AgmsListAdapter.MyViewHolder> {


    private Context context;
    private List<AgreementLists> agmList;

    public AgmsListAdapter() {
    }

    public AgmsListAdapter(Context context, List<AgreementLists> agmList) {
        this.context = context;
        this.agmList = agmList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View myView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.agms_lists_layout, viewGroup, false);
        return new MyViewHolder(myView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        final AgreementLists agreement = agmList.get(i);

        myViewHolder.textViewAgmID.setText("ID : " + agreement.getAgmID());
        myViewHolder.textViewUsername.setText("created by, " + agreement.getUsername());

        myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, AgmDataActivity.class)
                        .putExtra("agm_id", agreement.getAgmID())
                        .putExtra("user_id", agreement.getUserID()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return agmList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        TextView textViewAgmID;
        TextView textViewUsername;

        public MyViewHolder(@NonNull View myView) {
            super(myView);

            cardView = myView.findViewById(R.id.card_view);
            textViewAgmID = myView.findViewById(R.id.agm_id);
            textViewUsername = myView.findViewById(R.id.username);
        }

    }
}
