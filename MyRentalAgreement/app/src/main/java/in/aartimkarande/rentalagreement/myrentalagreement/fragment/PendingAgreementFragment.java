package in.aartimkarande.rentalagreement.myrentalagreement.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.aartimkarande.rentalagreement.myrentalagreement.R;
import in.aartimkarande.rentalagreement.myrentalagreement.activity.CreateAgreementDashboardActivity;
import in.aartimkarande.rentalagreement.myrentalagreement.adapter.AgreementListsAdapter;
import in.aartimkarande.rentalagreement.myrentalagreement.config.APIURLLists;
import in.aartimkarande.rentalagreement.myrentalagreement.config.FinalValues;
import in.aartimkarande.rentalagreement.myrentalagreement.config.IDTracker;
import in.aartimkarande.rentalagreement.myrentalagreement.config.Keys;
import in.aartimkarande.rentalagreement.myrentalagreement.config.MyAppSharedPreferences;
import in.aartimkarande.rentalagreement.myrentalagreement.model.AgreementLists;
import in.aartimkarande.rentalagreement.myrentalagreement.model.UserInfo;


public class PendingAgreementFragment extends Fragment {

    private ShimmerFrameLayout mShimmerViewContainer;

    private String userID;
    private String serviceType;
    private String propertyID;
    private String ownerID;
    private String tenantID;
    private String witnessID;
    private String rentID;
    private String rentTransID;
    private String tokenID;

    private LinearLayout linearLayout;
    private LinearLayout layout;

    private Button btnEdit;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private OnFragmentInteractionListener mListener;

    public PendingAgreementFragment() {

    }

    public static PendingAgreementFragment newInstance(String param1, String param2) {
        PendingAgreementFragment fragment = new PendingAgreementFragment();
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
        return inflater.inflate(R.layout.fragment_pending_agreement, container, false);
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

        sharedPreferences = getActivity().getSharedPreferences(MyAppSharedPreferences.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        initializeComponents(view);

        setVisibility();

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Toast.makeText(getContext(), "prop_id : " + propertyID, Toast.LENGTH_SHORT).show();*/
                startActivity(new Intent(getActivity(), CreateAgreementDashboardActivity.class).putExtra("status", "edit").putExtra("prop_id", propertyID));
            }
        });

    }

    private void initializeComponents(View view) {
        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container);
        linearLayout = view.findViewById(R.id.ll);
        layout = view.findViewById(R.id.layout_pending_agreement);
        btnEdit = view.findViewById(R.id.btnEdit);
    }

    private void setVisibility(){

        userID = sharedPreferences.getString(MyAppSharedPreferences.USR_ID,null);

        serviceType = sharedPreferences.getString(IDTracker.SERVICE_TYPE,null);
        propertyID = sharedPreferences.getString(IDTracker.PROP_ID,null);


        checkFormStatus();

    }

    private void checkFormStatus() {

        StringRequest request = new StringRequest(Request.Method.POST, APIURLLists.CHECK_FROM_STATUS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //If we are getting success msg from server
                        if (!response.equalsIgnoreCase(FinalValues.ERROR_MSG)) {

                            try {
                                //converting the string to json array object
                                JSONArray jsonArray = new JSONArray(response);

                                //traversing through all the object
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    //getting event object from json array
                                    JSONObject data = jsonArray.getJSONObject(i);


                                    //adding the product to event list
                                    /*userInfoList.add(new UserInfo(
                                            data.getString(Keys.GET_FULL_NAME),
                                            data.getString(Keys.GET_EMAIL_ID),
                                            data.getString(Keys.GET_MOBILE_NO),
                                            data.getString(Keys.GET_PROFESSION),
                                            data.getString(Keys.GET_DOB),
                                            data.getString(Keys.GET_USER_TYPE),
                                            data.getString(Keys.GET_USER_NAME)
                                    ));*/

                                    editor.putString(IDTracker.OWN_ID, data.getString("own_stat"));
                                    editor.putString(IDTracker.TENT_ID, data.getString("tent_stat"));
                                    editor.putString(IDTracker.WIT_ID, data.getString("wit_stat"));
                                    editor.putString(IDTracker.RENT_ID, data.getString("prop_pay_stat"));
                                    editor.putString(IDTracker.RENT_TRANS_ID, data.getString("rent_trans_stat"));

                                }

                                editor.commit();

                                ownerID = sharedPreferences.getString(IDTracker.OWN_ID,null);
                                tenantID = sharedPreferences.getString(IDTracker.TENT_ID,null);
                                witnessID = sharedPreferences.getString(IDTracker.WIT_ID,null);
                                rentID = sharedPreferences.getString(IDTracker.RENT_ID,null);
                                rentTransID = sharedPreferences.getString(IDTracker.RENT_TRANS_ID,null);

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getContext(),"catch : " + e.getMessage(),Toast.LENGTH_SHORT).show();
                            }

                            /*Toast.makeText(getContext(), "PropID : " + propertyID, Toast.LENGTH_SHORT).show();
                            Toast.makeText(getContext(), "OwnID : " + ownerID, Toast.LENGTH_SHORT).show();
                            Toast.makeText(getContext(), "tentID : " + tenantID, Toast.LENGTH_SHORT).show();
                            Toast.makeText(getContext(), "witnessID : " + witnessID, Toast.LENGTH_SHORT).show();
                            Toast.makeText(getContext(), "rentID : " + rentID, Toast.LENGTH_SHORT).show();
                            Toast.makeText(getContext(), "rentTransID : " + rentTransID, Toast.LENGTH_SHORT).show();
                            Toast.makeText(getContext(), "tokenID : " + tokenID, Toast.LENGTH_SHORT).show();*/

                            //Toast.makeText(getContext(), "hmm : " + (tokenID.equalsIgnoreCase(FinalValues.NULL_VALUE)), Toast.LENGTH_SHORT).show();

                            //Toast.makeText(getContext(), "Condition : " + ((ownerID.equalsIgnoreCase(FinalValues.NULL_VALUE) || tenantID.equalsIgnoreCase(FinalValues.NULL_VALUE) ||  witnessID.equalsIgnoreCase(FinalValues.NULL_VALUE) ||  ( (rentID.equalsIgnoreCase(FinalValues.NULL_VALUE) ) || (rentTransID .equalsIgnoreCase(FinalValues.NULL_VALUE)) /*&& tokenID.equalsIgnoreCase(FinalValues.NULL_VALUE))*/ ) && (propertyID != null ) ) ), Toast.LENGTH_SHORT).show();

                            if((ownerID .equalsIgnoreCase(FinalValues.NULL_VALUE) || tenantID .equalsIgnoreCase(FinalValues.NULL_VALUE) ||  witnessID.equalsIgnoreCase(FinalValues.NULL_VALUE) ||  ( (rentID .equalsIgnoreCase(FinalValues.NULL_VALUE) ) || (rentTransID .equalsIgnoreCase(FinalValues.NULL_VALUE)) /*&& tokenID .equalsIgnoreCase(FinalValues.NULL_VALUE)*/) ) && !(propertyID.equalsIgnoreCase(FinalValues.NULL_VALUE) ) ){
                                mShimmerViewContainer.stopShimmer();
                                mShimmerViewContainer.setVisibility(View.GONE);
                                linearLayout.setVisibility(View.GONE);
                                layout.setVisibility(View.VISIBLE);
                            }else {
                                mShimmerViewContainer.stopShimmer();
                                mShimmerViewContainer.setVisibility(View.GONE);
                                linearLayout.setVisibility(View.VISIBLE);
                                layout.setVisibility(View.GONE);
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

                params.put(Keys.ID_USER_ID, userID);
                params.put(Keys.ID_PROPERTY_ID, propertyID);

                return params;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);

    }



}
