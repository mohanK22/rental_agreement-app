package in.aartimkarande.rentalagreement.myrentalagreement.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import in.aartimkarande.rentalagreement.myrentalagreement.R;
import in.aartimkarande.rentalagreement.myrentalagreement.model.SampleModel;

public class SampleAdapter extends RecyclerView.Adapter<SampleAdapter.MyViewHolder> {

    private Context context;
    private List<SampleModel> cartList;

    public SampleAdapter(Context context, List<SampleModel> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sample_list_items, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {

        final SampleModel recipe = cartList.get(position);
        myViewHolder.name.setText(recipe.getName());
        myViewHolder.chef.setText("By " + recipe.getChef());
        myViewHolder.description.setText(recipe.getDescription());
        myViewHolder.price.setText("Price: ₹" + recipe.getPrice());
        myViewHolder.timestamp.setText(recipe.getTimestamp());

        Glide.with(context)
                .load(recipe.getThumbnail())
                .into(myViewHolder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, description, price, chef, timestamp;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            chef = view.findViewById(R.id.chef);
            description = view.findViewById(R.id.description);
            price = view.findViewById(R.id.price);
            thumbnail = view.findViewById(R.id.thumbnail);
            timestamp = view.findViewById(R.id.timestamp);
        }
    }

}
