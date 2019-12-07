package in.aartimkarande.rentalagreement.myrentalagreement.fragment;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import in.aartimkarande.rentalagreement.myrentalagreement.R;
import in.aartimkarande.rentalagreement.myrentalagreement.config.APIURLLists;
import in.aartimkarande.rentalagreement.myrentalagreement.property.PropertyActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ContactFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ContactFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactFragment extends Fragment {

    private static final int REQUEST_CALL = 1;

    private AlertDialog alertDialog;

    private TextView textViewPhone1;
    private TextView textViewPhone2;
    private TextView textViewCall;

    private TextView textViewWebview;

    private String[] phoneLists;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ContactFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ContactFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ContactFragment newInstance(String param1, String param2) {
        ContactFragment fragment = new ContactFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            /*throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");*/
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        /*super.onViewCreated(view, savedInstanceState);*/

        textViewPhone1 = view.findViewById(R.id.phone1);
        textViewPhone2 = view.findViewById(R.id.phone2);

        textViewPhone1.setText(getString(R.string.phone1));
        textViewPhone2.setText(getString(R.string.phone2));

        //Call
        textViewCall = view.findViewById(R.id.txtCall);
        textViewCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall();
            }
        });


        //Website
        textViewWebview = view.findViewById(R.id.txtWebView);
        textViewWebview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse(APIURLLists.WEBSITE));
                startActivity(intentBrowser);
            }
        });


    }

    private void makePhoneCall() {

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        }else{
            // Call Permission Granted

            //Pop-up message

            phoneLists = getResources().getStringArray(R.array.phone_list);
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(R.string.phone_title);
            builder.setCancelable(true);
            /*builder.setView(R.layout.service_type_list);*/
            builder.setIcon(R.drawable.phone);
            builder.setSingleChoiceItems(phoneLists, -1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    alertDialog.dismiss();
                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneLists[which])));
                }
            });

            builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    alertDialog.dismiss();
                }
            });

            alertDialog = builder.create();
            alertDialog.setContentView(R.layout.service_type_list);
            alertDialog.show();

            /*final Dialog dialog = new Dialog(getContext());
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setTitle("Sample Dialog");
            dialog.setContentView(R.layout.service_type_list);
            dialog.show();*/


        }

    }
}
