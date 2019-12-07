package in.aartimkarande.rentalagreement.myrentalagreement.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import in.aartimkarande.rentalagreement.myrentalagreement.activity.DashboardActivity;
import in.aartimkarande.rentalagreement.myrentalagreement.activity.LoginActivity;
import in.aartimkarande.rentalagreement.myrentalagreement.config.APIURLLists;
import in.aartimkarande.rentalagreement.myrentalagreement.config.FinalValues;
import in.aartimkarande.rentalagreement.myrentalagreement.config.Keys;
import in.aartimkarande.rentalagreement.myrentalagreement.config.MyAppSharedPreferences;
import in.aartimkarande.rentalagreement.myrentalagreement.model.Owner;
import in.aartimkarande.rentalagreement.myrentalagreement.model.UserInfo;
import in.aartimkarande.rentalagreement.myrentalagreement.user_owner.ViewOwnerActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyAccountFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyAccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyAccountFragment extends Fragment {

    private ShimmerFrameLayout mShimmerViewContainer;
    private ShimmerFrameLayout mShimmerViewContainerUsername;

    private LinearLayout linearLayout;

    private TextView textViewUsername;

    private TextView textViewFullName;
    private TextView textViewEmailID;
    private TextView textViewMobileNo;
    private TextView textViewDOB;
    private TextView textViewUserType;
    private TextView textViewPwd;

    private String userID;

    private List<UserInfo> userInfoList;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MyAccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyAccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyAccountFragment newInstance(String param1, String param2) {
        MyAccountFragment fragment = new MyAccountFragment();
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
        return inflater.inflate(R.layout.fragment_my_account, container, false);
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

        initializeComponents(view);

        /*refreshData();*/

        mShimmerViewContainer.startShimmer();
        mShimmerViewContainerUsername.startShimmer();

        getUserInfo();
    }

    @Override
    public void onResume() {
        super.onResume();

        /*refreshData();*/

        getUserInfo();
    }

    @Override
    public void onPause() {


        stopShimmerNVisibleComponents();

        super.onPause();
    }

    /*private void refreshData() {

        mShimmerViewContainer.startShimmer();
        mShimmerViewContainerUsername.startShimmer();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                mShimmerViewContainer.stopShimmer();
                mShimmerViewContainerUsername.stopShimmer();

                mShimmerViewContainer.setVisibility(View.GONE);
                mShimmerViewContainerUsername.setVisibility(View.GONE);

                linearLayout.setVisibility(View.VISIBLE);


                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(MyAppSharedPreferences.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                username = sharedPreferences.getString(MyAppSharedPreferences.USERNAME,null);

                textViewUsername.setText(username);
                textViewUsername.setVisibility(View.VISIBLE);

            }
        }, 2000);
    }*/

    private void initializeComponents(View view){

        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container);
        mShimmerViewContainerUsername = view.findViewById(R.id.shimmer_view_container_username);

        linearLayout = view.findViewById(R.id.linear_layout);
        textViewUsername = view.findViewById(R.id.txtUsername);

        textViewFullName = view.findViewById(R.id.text_view_full_name);
        textViewEmailID = view.findViewById(R.id.text_view_email_id);
        textViewMobileNo = view.findViewById(R.id.text_view_mobile_no);
        textViewDOB = view.findViewById(R.id.text_view_dob);
        textViewUserType = view.findViewById(R.id.text_view_user_type);
        textViewPwd = view.findViewById(R.id.text_view_pwd);

        userInfoList = new ArrayList<>();
    }


    private void getUserInfo(){

        StringRequest request = new StringRequest(Request.Method.POST, APIURLLists.GET_USER_INFO,
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
                                    userInfoList.add(new UserInfo(
                                            data.getString(Keys.GET_FULL_NAME),
                                            data.getString(Keys.GET_EMAIL_ID),
                                            data.getString(Keys.GET_MOBILE_NO),
                                            data.getString(Keys.GET_PROFESSION),
                                            data.getString(Keys.GET_DOB),
                                            data.getString(Keys.GET_USER_TYPE),
                                            data.getString(Keys.GET_USER_NAME)
                                    ));
                                }

                                setUserData();

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getContext(),"catch : " + e.getMessage(),Toast.LENGTH_SHORT).show();

                                stopShimmerNVisibleComponents();
                            }


                        }else {
                            Toast.makeText(getContext(), "Something went wrong\n " + response, Toast.LENGTH_SHORT).show();

                            stopShimmerNVisibleComponents();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
                        stopShimmerNVisibleComponents();
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


    private void setUserData(){

        for(int i=0; i<userInfoList.size(); i++){
            UserInfo userInfo = userInfoList.get(i);

            textViewUsername.setText(userInfo.getUserUsername());

            textViewFullName.setText(userInfo.getUserFullName());
            textViewEmailID.setText(userInfo.getUserEmailID());
            textViewMobileNo.setText(userInfo.getUserMobileNo());
            textViewDOB.setText(userInfo.getUserDOB());
            textViewUserType.setText(userInfo.getUserType());

        }

        textViewPwd.setText(FinalValues.PWD_STAR);

        stopShimmerNVisibleComponents();
    }


    private void stopShimmerNVisibleComponents(){
        mShimmerViewContainer.stopShimmer();
        mShimmerViewContainerUsername.stopShimmer();

        mShimmerViewContainer.setVisibility(View.GONE);
        mShimmerViewContainerUsername.setVisibility(View.GONE);

        linearLayout.setVisibility(View.VISIBLE);
        textViewUsername.setVisibility(View.VISIBLE);
    }


}
