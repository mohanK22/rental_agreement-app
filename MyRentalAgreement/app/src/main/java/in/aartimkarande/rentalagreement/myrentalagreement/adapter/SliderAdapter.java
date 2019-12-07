package in.aartimkarande.rentalagreement.myrentalagreement.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import in.aartimkarande.rentalagreement.myrentalagreement.R;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    public int[] slide_imgs = {
            R.drawable.create_document_96,
            R.drawable.documents_96,
            R.drawable.pdf_96,
            R.drawable.planner_96,
            R.drawable.communication_96
    };

    public String[] slide_headings = {
            "Create Agreement",
            "View Agreement",
            "Download Agreement Report",
            "Schedule Appointment",
            "Send Message"
    };

    public String[] slide_sub_headings = {
            "Create agreement within few minutes in simple steps and easiest way.",
            "You can view created agreement any time.",
            "Once you successfully created agreement, You can view and download agreement in PDF format.",
            "Want to set schedule appointment with admin? You got it.",
            "Have any issues? Send message to us, we will help you."
    };


    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        ImageView circleImageView = view.findViewById(R.id.img);
        TextView textViewHeading = view.findViewById(R.id.txtViewHeading);
        TextView textViewSubHeading = view.findViewById(R.id.txtViewSubHeading);

        circleImageView.setImageResource(slide_imgs[position]);
        textViewHeading.setText(slide_headings[position]);
        textViewSubHeading.setText(slide_sub_headings[position]);

        container.addView(view);

        return view;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }
}
