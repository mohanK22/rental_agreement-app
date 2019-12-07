package in.aartimkarande.rentalagreement.myrentalagreement.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.aartimkarande.rentalagreement.myrentalagreement.R;
import in.aartimkarande.rentalagreement.myrentalagreement.adapter.AgreementListsAdapter;
import in.aartimkarande.rentalagreement.myrentalagreement.adapter.UsersListAdapter;
import in.aartimkarande.rentalagreement.myrentalagreement.config.APIURLLists;
import in.aartimkarande.rentalagreement.myrentalagreement.config.FinalValues;
import in.aartimkarande.rentalagreement.myrentalagreement.config.Keys;
import in.aartimkarande.rentalagreement.myrentalagreement.model.AgreementLists;
import in.aartimkarande.rentalagreement.myrentalagreement.model.UserInfo;

public class UsersListsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<UserInfo> userInfoList;
    private UsersListAdapter usersListAdapter;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_lists);

        initializeComponents();

        getUsersList();

    }

    private void getUsersList() {
        StringRequest request = new StringRequest(Request.Method.POST, APIURLLists.GET_USERS_LISTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (!response.equalsIgnoreCase(FinalValues.ERROR_MSG)) {

                            try {
                                JSONArray jsonArray = new JSONArray(response);

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject data = jsonArray.getJSONObject(i);
                                    userInfoList.add(new UserInfo(
                                            data.getString(Keys.USERID),
                                            data.getString(Keys.USER_NAME),
                                            data.getString(Keys.USERNAME)));
                                }

                                //Toast.makeText(UsersListsActivity.this, "Size : " + userInfoList.size(), Toast.LENGTH_SHORT).show();

                                if(userInfoList.size() > 0){
                                    usersListAdapter = new UsersListAdapter(getApplicationContext(), userInfoList);
                                    recyclerView.setAdapter(usersListAdapter);
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(UsersListsActivity.this,"catch : " + e.getMessage(),Toast.LENGTH_SHORT).show();
                            }


                        }else {
                            Toast.makeText(UsersListsActivity.this, "Something went wrong\n " + response, Toast.LENGTH_SHORT).show();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UsersListsActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return null;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);
    }

    private void initializeComponents() {
        recyclerView = findViewById(R.id.recycler_view);
        userInfoList = new ArrayList<>();
        linearLayout = findViewById(R.id.ll);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onBackPressed() {
        /*super.onBackPressed();*/
        startActivity(new Intent(UsersListsActivity.this, AdminMainActivity.class));
    }
}
