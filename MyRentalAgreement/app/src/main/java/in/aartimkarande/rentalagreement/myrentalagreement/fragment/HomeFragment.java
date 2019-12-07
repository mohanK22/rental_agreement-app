package in.aartimkarande.rentalagreement.myrentalagreement.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.aartimkarande.rentalagreement.myrentalagreement.R;
import in.aartimkarande.rentalagreement.myrentalagreement.activity.DashboardActivity;
import in.aartimkarande.rentalagreement.myrentalagreement.adapter.SampleAdapter;
import in.aartimkarande.rentalagreement.myrentalagreement.config.APIURLLists;
import in.aartimkarande.rentalagreement.myrentalagreement.config.FinalValues;
import in.aartimkarande.rentalagreement.myrentalagreement.config.IDTracker;
import in.aartimkarande.rentalagreement.myrentalagreement.config.Keys;
import in.aartimkarande.rentalagreement.myrentalagreement.config.MyApp;
import in.aartimkarande.rentalagreement.myrentalagreement.config.MyAppSharedPreferences;
import in.aartimkarande.rentalagreement.myrentalagreement.model.IDs;
import in.aartimkarande.rentalagreement.myrentalagreement.model.SampleModel;
import android.support.v7.widget.DefaultItemAnimator;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    /*private String userID;
    private String serviceType;
    private String propertyID;
    private String ownerID;
    private String tenantID;
    private String witnessID;
    private String rentID;
    private String rentTransID;
    private String agmID;
    private String tokenID;*/

    /*private List<IDs> idLists;*/

    /*private static final String TAG = HomeFragment.class.getSimpleName();*/

    /*private RecyclerView recyclerView;
    private List<SampleModel> cartList;
    private SampleAdapter mAdapter;

    private ShimmerFrameLayout mShimmerViewContainer;*/

    // URL to fetch menu json
    // this endpoint takes 2 sec before giving the response to add
    // some delay to test the Shimmer effect
    /*private static final String URL = "https://api.androidhive.info/json/shimmer/menu.php";*/

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {

        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    /*private void getAllIDs() {

        StringRequest request = new StringRequest(Request.Method.POST, APIURLLists.GET_ALL_IDS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(!response.equalsIgnoreCase(FinalValues.ERROR_MSG)){

                            try {
                                //converting the string to json array object
                                JSONArray jsonArray = new JSONArray(response);

                                //traversing through all the object
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    //getting event object from json array
                                    JSONObject id = jsonArray.getJSONObject(i);


                                    //adding the product to event list
                                    idLists.add(new IDs(
                                            id.getString(Keys.GET_ID_PROP_ID),
                                            id.getString(Keys.GET_ID_SERVICE_TYPE),
                                            id.getString(Keys.GET_ID_OWN_ID),
                                            id.getString(Keys.GET_ID_TENT_ID),
                                            id.getString(Keys.GET_ID_WIT_ID),
                                            id.getString(Keys.GET_ID_RENT_ID),
                                            id.getString(Keys.GET_ID_RENT_TRANS_ID),
                                            id.getString(Keys.GET_ID_AGM_ID),
                                            id.getString(Keys.GET_ID_TOKEN_ID)

                                    ));
                                }

                                setAllIDs();

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getContext(),"catch : " + e.getMessage(),Toast.LENGTH_SHORT).show();
                            }

                        }else{


                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

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

        //Adding the StringRequest to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);

    }*/

    /*private void setAllIDs() {

        for (int i=0; i<idLists.size(); i++){
            IDs id = idLists.get(i);

            serviceType = id.getServiceType();
            propertyID = id.getPropertyID();
            ownerID = id.getOwnerID();
            tenantID = id.getTenantID();
            witnessID = id.getWitnessID();
            rentID = id.getRentID();
            rentTransID = id.getRentTransID();
            agmID = id.getAgreementID();
            tokenID = id.getTokenID();
        }


        //Creating a shared preference
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(MyAppSharedPreferences.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        //Creating editor to store
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //Adding values to the editor
        editor.putString(IDTracker.PROP_ID, propertyID);
        editor.putString(IDTracker.SERVICE_TYPE, serviceType);
        editor.putString(IDTracker.OWN_ID, ownerID);
        editor.putString(IDTracker.TENT_ID, tenantID);
        editor.putString(IDTracker.WIT_ID, witnessID);
        editor.putString(IDTracker.RENT_ID, rentID);
        editor.putString(IDTracker.RENT_TRANS_ID, rentTransID);
        editor.putString(IDTracker.AGREEMENT_ID, agmID);
        editor.putString(IDTracker.TOKEN_ID, tokenID);

        //Saving values to the editor
        editor.commit();

        *//*Toast.makeText(getContext(),"Service Type : " + serviceType,Toast.LENGTH_SHORT).show();
        Toast.makeText(getContext(),"Property ID : " + propertyID,Toast.LENGTH_SHORT).show();
        Toast.makeText(getContext(),"Owner ID : " + ownerID,Toast.LENGTH_SHORT).show();
        Toast.makeText(getContext(),"Tenant ID : " + tenantID,Toast.LENGTH_SHORT).show();
        Toast.makeText(getContext(),"Witness ID : " + witnessID,Toast.LENGTH_SHORT).show();
        Toast.makeText(getContext(),"Rent ID : " + rentID,Toast.LENGTH_SHORT).show();
        Toast.makeText(getContext(),"Rent Trans ID : " + rentTransID,Toast.LENGTH_SHORT).show();
        Toast.makeText(getContext(),"Agreement ID : " + agmID,Toast.LENGTH_SHORT).show();
        Toast.makeText(getContext(),"Token ID : " + tokenID,Toast.LENGTH_SHORT).show();*//*

    }*/

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        /*super.onViewCreated(view, savedInstanceState);*/

        /*idLists = new ArrayList<>();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(MyAppSharedPreferences.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        userID = sharedPreferences.getString(MyAppSharedPreferences.USR_ID,null);

        Toast.makeText(getContext(),"User ID : " + userID,Toast.LENGTH_SHORT).show();

        if(TextUtils.isEmpty(userID) || TextUtils.isEmpty(serviceType) || TextUtils.isEmpty(propertyID) || TextUtils.isEmpty(ownerID) || TextUtils.isEmpty(tenantID) || TextUtils.isEmpty(witnessID) || TextUtils.isEmpty(rentID) || TextUtils.isEmpty(rentTransID) || TextUtils.isEmpty(agmID) || TextUtils.isEmpty(tokenID)) {
            Toast.makeText(getContext(), "getAllIDs() called", Toast.LENGTH_LONG);
            getAllIDs();
        }

        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container);

        recyclerView = view.findViewById(R.id.recycler_view);

        cartList = new ArrayList<>();
        mAdapter = new SampleAdapter(getContext(), cartList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        *//*recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));*//*
        recyclerView.setAdapter(mAdapter);

        // making http call and fetching menu json
        fetchRecipes();*/

    }

    /*private void fetchRecipes() {

        JsonArrayRequest request = new JsonArrayRequest(URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response == null) {
                            Toast.makeText(getContext(), "Couldn't fetch the menu! Pleas try again.", Toast.LENGTH_LONG).show();
                            return;
                        }

                        List<SampleModel> recipes = new Gson().fromJson(response.toString(), new TypeToken<List<SampleModel>>() {
                        }.getType());

                        // adding recipes to cart list
                        cartList.clear();
                        cartList.addAll(recipes);

                        // refreshing recycler view
                        mAdapter.notifyDataSetChanged();

                        // stop animating Shimmer and hide the layout
                        mShimmerViewContainer.stopShimmer();
                        mShimmerViewContainer.setVisibility(View.GONE);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error in getting json
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(getContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        MyApp.getInstance().addToRequestQueue(request);

    }*/

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
        return inflater.inflate(R.layout.fragment_home, container, false);
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
    public void onResume() {
        super.onResume();

        /*mShimmerViewContainer.startShimmer();*/
    }

    @Override
    public void onPause() {
        /*mShimmerViewContainer.stopShimmer();*/
        super.onPause();
    }


}
