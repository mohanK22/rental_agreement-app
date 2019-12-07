package in.aartimkarande.rentalagreement.myrentalagreement.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.aartimkarande.rentalagreement.myrentalagreement.R;
import in.aartimkarande.rentalagreement.myrentalagreement.adapter.AgreementListsAdapter;
import in.aartimkarande.rentalagreement.myrentalagreement.config.APIURLLists;
import in.aartimkarande.rentalagreement.myrentalagreement.config.FinalValues;
import in.aartimkarande.rentalagreement.myrentalagreement.config.Keys;
import in.aartimkarande.rentalagreement.myrentalagreement.config.MyAppSharedPreferences;
import in.aartimkarande.rentalagreement.myrentalagreement.model.AgreementLists;
import in.aartimkarande.rentalagreement.myrentalagreement.model.IDs;


public class ViewAgreementFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<AgreementLists> agreementLists;
    private AgreementListsAdapter agreementListsAdapter;

    private ShimmerFrameLayout mShimmerViewContainer;

    private LinearLayout linearLayout;

    private String userID;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ViewAgreementFragment() {
    }

    public static ViewAgreementFragment newInstance(String param1, String param2) {
        ViewAgreementFragment fragment = new ViewAgreementFragment();
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

        return inflater.inflate(R.layout.fragment_view_agreement, container, false);
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        /*super.onViewCreated(view, savedInstanceState);*/

        initializingComponents(view);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        fetchIDs();

    }

    private void initializingComponents(View view) {

        recyclerView = view.findViewById(R.id.recycler_view);
        agreementLists = new ArrayList<>();
        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container);

        linearLayout = view.findViewById(R.id.ll);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(MyAppSharedPreferences.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        userID = sharedPreferences.getString(MyAppSharedPreferences.USR_ID,null);
    }

    private void fetchIDs(){

        StringRequest request = new StringRequest(Request.Method.POST, APIURLLists.GET_AGM_ID_BY_USR,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (!response.equalsIgnoreCase(FinalValues.ERROR_MSG)) {

                            try {
                                JSONArray jsonArray = new JSONArray(response);

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject data = jsonArray.getJSONObject(i);

                                    agreementLists.add(new AgreementLists(
                                            data.getString(Keys.GET_AGM_ID)
                                    ));
                                }

                                if(agreementLists.size() > 0){
                                    linearLayout.setVisibility(View.GONE);
                                    agreementListsAdapter = new AgreementListsAdapter(getContext(), agreementLists);
                                    recyclerView.setAdapter(agreementListsAdapter);
                                }else{
                                    linearLayout.setVisibility(View.VISIBLE);
                                    recyclerView.setVisibility(View.GONE);
                                }

                                mShimmerViewContainer.stopShimmer();
                                mShimmerViewContainer.setVisibility(View.GONE);

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getContext(),"catch : " + e.getMessage(),Toast.LENGTH_SHORT).show();

                                mShimmerViewContainer.stopShimmer();
                            }


                        }else {
                            Toast.makeText(getContext(), "Something went wrong\n " + response, Toast.LENGTH_SHORT).show();

                            mShimmerViewContainer.stopShimmer();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
                        mShimmerViewContainer.stopShimmer();
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                params.put(Keys.USER_ID, userID);

                return params;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }

    @Override
    public void onResume() {
        super.onResume();

        mShimmerViewContainer.startShimmer();
    }

    @Override
    public void onPause() {
        mShimmerViewContainer.stopShimmer();
        mShimmerViewContainer.setVisibility(View.GONE);
        super.onPause();
    }
}
