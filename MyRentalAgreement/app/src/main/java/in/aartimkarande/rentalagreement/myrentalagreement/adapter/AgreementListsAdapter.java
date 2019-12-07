package in.aartimkarande.rentalagreement.myrentalagreement.adapter;

import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
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

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.aartimkarande.rentalagreement.myrentalagreement.R;
import in.aartimkarande.rentalagreement.myrentalagreement.activity.CreateAgreementDashboardActivity;
import in.aartimkarande.rentalagreement.myrentalagreement.config.APIURLLists;
import in.aartimkarande.rentalagreement.myrentalagreement.config.FinalValues;
import in.aartimkarande.rentalagreement.myrentalagreement.config.Keys;
import in.aartimkarande.rentalagreement.myrentalagreement.config.MyAppSharedPreferences;
import in.aartimkarande.rentalagreement.myrentalagreement.handler.PDFFileDownloader;
import in.aartimkarande.rentalagreement.myrentalagreement.model.AgreementLists;

public class AgreementListsAdapter extends RecyclerView.Adapter<AgreementListsAdapter.MyViewHolder> {

    private Context context;
    private List<AgreementLists> agreementLists;

    private DownloadManager downloadManager;

    private String userID;

    private String fileUrl;
    private String fileName;

    public AgreementListsAdapter() {
    }

    public AgreementListsAdapter(Context context) {
        this.context = context;
    }

    public AgreementListsAdapter(Context context, List<AgreementLists> agreementLists) {
        this.context = context;
        this.agreementLists = agreementLists;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.agreement_list_layout, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        final AgreementLists agreement = agreementLists.get(i);

        myViewHolder.txtID.setText(agreement.getAgreementID());

        myViewHolder.btnPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, CreateAgreementDashboardActivity.class).putExtra("status", "view").putExtra("agm_id", agreement.getAgreementID()));
            }
        });


        myViewHolder.btnDownloadReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateReportRequest(agreement.getAgreementID());
            }
        });

    }

    private void generateReportRequest(final String agmId) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(MyAppSharedPreferences.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        userID = sharedPreferences.getString(MyAppSharedPreferences.USR_ID,null);

        StringRequest request = new StringRequest(Request.Method.POST, APIURLLists.GENERATE_REPORT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.equalsIgnoreCase(FinalValues.REPORT_GENERATED)) {
                            Toast.makeText(context, "Report is generated. Now downloading report", Toast.LENGTH_SHORT).show();
                            /*downloadReport(agmId);*/

                            fileUrl = APIURLLists.GET_REPORT + "" + agmId + "" + APIURLLists.PDF_EXT;
                            fileName = agmId + "" + APIURLLists.PDF_EXT;

                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(fileUrl));
                            context.startActivity(browserIntent);

                            /*new DownloadPDFFile().execute(fileUrl, fileName);*/


                        }else {
                            Toast.makeText(context, "Something went wrong\n " + response, Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Error: " + error, Toast.LENGTH_SHORT).show();
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put(Keys.USER_ID, userID);
                params.put(Keys.AGM_ID, agmId);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);

    }

    private void viewPDFFile(){

        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/Agm_APP_folder/" + fileName);  // -> filename = maven.pdf
        Uri path = Uri.fromFile(pdfFile);

        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        pdfIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        try{
            context.startActivity(pdfIntent);
        }catch(ActivityNotFoundException e){
            Toast.makeText(context, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
        }

    }

    private void downloadReport(String agmId) {

        downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

        String url = APIURLLists.GET_REPORT + "" + agmId + "" + APIURLLists.PDF_EXT;
        Log.e("report", url);

        Uri uri = Uri.parse(url);
        DownloadManager.Request request= new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        Long reference = downloadManager.enqueue(request);

    }

    private void deleteGeneratedReport(final String agmId){

        StringRequest request = new StringRequest(Request.Method.POST, APIURLLists.DELETE_REPORT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //If we are getting success msg from server
                        if (response.equalsIgnoreCase(FinalValues.SUCCESS_MSG)) {
                            Toast.makeText(context, "Report is deleted", Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(context, "Failed to delete report" + response, Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Error: " + error, Toast.LENGTH_SHORT).show();
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                //Adding Parameter to the request
                params.put(Keys.AGM_ID, agmId);

                //Returning parameter
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);

    }


    @Override
    public int getItemCount() {
        return agreementLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView txtID;
        Button btnPreview;
        Button btnDownloadReport;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtID = itemView.findViewById(R.id.txtViewAgmID);
            btnPreview = itemView.findViewById(R.id.btnPreview);
            btnDownloadReport = itemView.findViewById(R.id.btnDownloadReport);

        }
    }


    private class DownloadPDFFile extends AsyncTask<String, Void, Void>{

        @Override
        protected Void doInBackground(String... strings) {

            String fileUrl = strings[0];   // -> Report URL
            String fileName = strings[1];  // -> Report file name
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();

            File folder = new File(extStorageDirectory, "pdf_file");
            folder.mkdir();

            File pdfFile = new File(folder, fileName);

            try{
                pdfFile.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }
            PDFFileDownloader.downloadFile(fileUrl, pdfFile);

            viewPDFFile();

            return null;
        }
    }

}
