package in.aartimkarande.rentalagreement.myrentalagreement.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.commons.codec.binary.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.aartimkarande.rentalagreement.myrentalagreement.R;
import in.aartimkarande.rentalagreement.myrentalagreement.activity.CreateAgreementDashboardActivity;
import in.aartimkarande.rentalagreement.myrentalagreement.activity.DashboardActivity;
import in.aartimkarande.rentalagreement.myrentalagreement.activity.LoginActivity;
import in.aartimkarande.rentalagreement.myrentalagreement.activity.UserMainActivity;
import in.aartimkarande.rentalagreement.myrentalagreement.config.APIURLLists;
import in.aartimkarande.rentalagreement.myrentalagreement.config.FinalValues;
import in.aartimkarande.rentalagreement.myrentalagreement.config.IDTracker;
import in.aartimkarande.rentalagreement.myrentalagreement.config.Keys;
import in.aartimkarande.rentalagreement.myrentalagreement.config.MyAppSharedPreferences;
import in.aartimkarande.rentalagreement.myrentalagreement.model.FormStatus;
import in.aartimkarande.rentalagreement.myrentalagreement.model.UserInfo;


public class CreateAgreementFragment extends Fragment {

    private String userID;
    private int nos;

    private String maxPropertyID;
    private List<FormStatus> formStatuses;

    private String serviceType;
    private String propertyID;
    private String ownerID;
    private String tenantID;
    private String witnessID;
    private String rentID;
    private String rentTransID;
    private String tokenID;
    private String agreementID;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CreateAgreementFragment() {
        // Required empty public constructor
    }

    public static CreateAgreementFragment newInstance(String param1, String param2) {
        CreateAgreementFragment fragment = new CreateAgreementFragment();
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

        formStatuses = new ArrayList<>();

        getSharedPreferences();

        findMaxPropertyID();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_create_agreement, container, false);
    }

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
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        /*super.onViewCreated(view, savedInstanceState);*/


    }


    private void getSharedPreferences() {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(MyAppSharedPreferences.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        userID = sharedPreferences.getString(MyAppSharedPreferences.USR_ID,null);

        serviceType = sharedPreferences.getString(IDTracker.SERVICE_TYPE,null);
        propertyID = sharedPreferences.getString(IDTracker.PROP_ID,null);
        ownerID = sharedPreferences.getString(IDTracker.OWN_ID,null);
        tenantID = sharedPreferences.getString(IDTracker.TENT_ID,null);
        witnessID = sharedPreferences.getString(IDTracker.WIT_ID,null);
        rentID = sharedPreferences.getString(IDTracker.RENT_ID,null);
        rentTransID = sharedPreferences.getString(IDTracker.RENT_TRANS_ID,null);
        tokenID = sharedPreferences.getString(IDTracker.TOKEN_ID,null);
        agreementID = sharedPreferences.getString(IDTracker.AGREEMENT_ID , null);

    }

    private void findMaxPropertyID() {
        StringRequest request = new StringRequest(Request.Method.POST, APIURLLists.GET_MAX_PROP_ID,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(!response.equalsIgnoreCase(FinalValues.ERROR_MSG)){

                            Toast.makeText(getContext(), "Wait for some moment.", Toast.LENGTH_SHORT).show();

                            try {

                                JSONArray jsonArray = new JSONArray(response);


                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject data = jsonArray.getJSONObject(i);

                                    maxPropertyID = data.getString(Keys.MAX_PROP_ID);
                                }

                                propertyID = maxPropertyID;

                                Toast.makeText(getContext(), "Max Prop. ID : " + maxPropertyID, Toast.LENGTH_SHORT).show();

                                if(maxPropertyID.equalsIgnoreCase(FinalValues.NULL_VALUE)){
                                    startActivity(new Intent(getActivity(), CreateAgreementDashboardActivity.class).putExtra("status", FinalValues.STATUS_CREATE_NEW));
                                }else{
                                    checkFormStatus();
                                }



                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getContext(),"catch : " + e.getMessage(),Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            Toast.makeText(getContext(), "Error : " + response, Toast.LENGTH_SHORT).show();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Error : " + error, Toast.LENGTH_SHORT).show();
                    }
                }
        )
        {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put(Keys.ID_USER_ID, userID);

                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }

    private void checkFormStatus() {
        StringRequest request = new StringRequest(Request.Method.POST, APIURLLists.CHECK_FROM_STATUS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(!response.equalsIgnoreCase(FinalValues.ERROR_MSG)){

                            try {

                                JSONArray jsonArray = new JSONArray(response);

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    //getting event object from json array
                                    JSONObject data = jsonArray.getJSONObject(i);


                                    //adding the product to event list
                                    formStatuses.add(new FormStatus(
                                            data.getString(Keys.OWN_STAT),
                                            data.getString(Keys.TENT_STAT),
                                            data.getString(Keys.WIT_STAT),
                                            data.getString(Keys.PROP_PAY_STAT),
                                            data.getString(Keys.RENT_TRANS_STAT)

                                    ));
                                }

                                openCreateAgreementDashBoard();

                            } catch (JSONException e) {
                                Toast.makeText(getContext(),"catch : " + e.getMessage(),Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            Toast.makeText(getContext(), "Error Response : " + response, Toast.LENGTH_SHORT).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Error Msg : " + error, Toast.LENGTH_SHORT).show();
                    }
                }
        )
        {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put(Keys.ID_USER_ID, userID);
                params.put(Keys.ID_PROPERTY_ID, propertyID);

                return params;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }


    private void openCreateAgreementDashBoard() {

        for (int i=0; i<formStatuses.size(); i++){
            FormStatus formStatus = formStatuses.get(i);

            /*Toast.makeText(getContext(), "Value : " + formStatus.getOwn_stat(), Toast.LENGTH_SHORT).show();*/

            /*Toast.makeText(getContext(), "Condition : " + formStatus.getOwn_stat().equalsIgnoreCase("Null")  , Toast.LENGTH_SHORT).show();*/

            if(  ( formStatus.getOwn_stat().equalsIgnoreCase(FinalValues.NULL_VALUE) || formStatus.getTent_stat().equalsIgnoreCase(FinalValues.NULL_VALUE) || formStatus.getWit_stat().equalsIgnoreCase(FinalValues.NULL_VALUE) || formStatus.getProp_pay_stat().equalsIgnoreCase(FinalValues.NULL_VALUE) || formStatus.getRent_trans_stat().equalsIgnoreCase(FinalValues.NULL_VALUE) ) ){
                //Toast.makeText(getContext(), "Complete incomplete form\nProp ID : " + maxPropertyID, Toast.LENGTH_SHORT).show();


                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(R.string.warning_title);
                builder.setMessage(R.string.incomplete_msg);
                builder.setCancelable(false);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getActivity(), CreateAgreementDashboardActivity.class).putExtra("status", "edit").putExtra("prop_id", maxPropertyID));
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }else{
                checkAgreement();
            }

        }

    }


    private void checkAgreement(){

        StringRequest request = new StringRequest(Request.Method.POST, APIURLLists.CHECK_AGREEMENTS_CREATED,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //If we are getting success msg from server
                        if (!response.equalsIgnoreCase(FinalValues.ERROR_MSG)) {

                            //Toast.makeText(getContext(), "nos : " + response, Toast.LENGTH_SHORT).show();

                            try {
                                nos = Integer.parseInt(response);

                                if(nos > 0){

                                    //Toast.makeText(getContext(), "Already created agreement", Toast.LENGTH_SHORT).show();

                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setIcon(R.drawable.contract);
                                    builder.setTitle(R.string.warning_title);
                                    builder.setMessage("You already created " + nos + " agreement(s)\nAre sure want to create another agreement?");
                                    builder.setCancelable(false);
                                    builder.setPositiveButton(R.string.confirm_yes, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                            startActivity(new Intent(getContext(), CreateAgreementDashboardActivity.class).putExtra("status","create_new"));
                                        }
                                    });
                                    builder.setNegativeButton(R.string.confirm_cancel, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                            startActivity(new Intent(getContext(), UserMainActivity.class));
                                        }
                                    });
                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.show();

                                }

                            }catch (NumberFormatException e){
                                Toast.makeText(getContext(), "Exception : " + e, Toast.LENGTH_SHORT).show();
                            }


                        }else {
                            Toast.makeText(getContext(), "Something went wrong\n " + response, Toast.LENGTH_SHORT).show();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(MyAppSharedPreferences.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                userID = sharedPreferences.getString(MyAppSharedPreferences.USR_ID,null);

                //Adding Parameter to the request
                params.put(Keys.USER_ID, userID);

                //Returning parameter
                return params;

            }
        };

        //Adding the StringRequest to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);

    }

}
