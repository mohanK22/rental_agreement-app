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
import in.aartimkarande.rentalagreement.myrentalagreement.model.SocialMedia;

public class SocialMediaAdapter extends RecyclerView.Adapter<SocialMediaAdapter.MyViewHolder> {

    private Context context;
    private List<SocialMedia> socialMediaList;

    public SocialMediaAdapter() {
    }

    public SocialMediaAdapter(Context context, List<SocialMedia> socialMediaList) {
        this.context = context;
        this.socialMediaList = socialMediaList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.social_media_lists, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {

        final SocialMedia socialMedia = socialMediaList.get(position);
        myViewHolder.socialName.setText(socialMedia.getName());
        myViewHolder.socialIcon.setImageResource(socialMedia.getImg());

    }

    @Override
    public int getItemCount() {
        return socialMediaList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView socialName;
        public ImageView socialIcon;

        public MyViewHolder(View view) {
            super(view);
            socialName = view.findViewById(R.id.social_name);
            socialIcon = view.findViewById(R.id.social_icon);
        }
    }
}
