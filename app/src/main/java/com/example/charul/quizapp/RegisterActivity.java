package com.example.charul.quizapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    EditText etCollege, etUserName, etCity, etPhone;
    Button btnReg;
    TextView textRole, textAlreadyReg, textLogin;
    private RadioGroup roleRadioGroup;

    TextView mresult;

    private static final String TAG = "RegisterActivity";
    private static final String URL_FOR_REGISTRATION = "http://quiztest1-env.wvxt8z5xwz.us-east-1.elasticbeanstalk.com/api/users";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        textAlreadyReg = (TextView) findViewById(R.id.textAlreadyReg);
        textLogin = (TextView) findViewById(R.id.textLogin);
        textRole = (TextView) findViewById(R.id.textRole);

        etCity = (EditText) findViewById(R.id.etCity);
        etUserName = (EditText) findViewById(R.id.etUserName);
        etCollege = (EditText) findViewById(R.id.etCollege);
        etPhone = (EditText) findViewById(R.id.etPhone);

        mresult = (TextView) findViewById(R.id.result);

        btnReg = (Button) findViewById(R.id.btnReg);
        roleRadioGroup = (RadioGroup) findViewById(R.id.radioRole);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   register();
             //   new postDataTask().execute(URL_FOR_REGISTRATION);
            }
        });
        textLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void register() {
        int selectedId = roleRadioGroup.getCheckedRadioButtonId();
        String role;
        if (selectedId == R.id.rbStudent)
            role = "Student";
        else
            role = "Admin";

        registerUser(etUserName.getText().toString(),
                etPhone.getText().toString(),
                etCity.getText().toString(),
                etCollege.getText().toString(),
                role);
    }

    private void registerUser(final String name, final String number, final String city,
                              final String college, final String role) {

        // Tag used to cancel the request
        String cancel_req_tag = "register";

        progressDialog.setMessage("Registering...");
        showDialog();
        Log.e("first","f1");

        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_FOR_REGISTRATION, new Response.Listener<String>()
        {



            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();


                try {
                    JSONObject jObj = new JSONObject(response);
                    /*boolean error = jObj.getBoolean("error");
                    Log.e("error",String.valueOf(error));
*/
                    Log.e("JSON",jObj.toString());

                        String user = jObj.get("name").toString();
                        Toast.makeText(getApplicationContext(), "Hi " + user + ", You are successfully Registered!", Toast.LENGTH_SHORT).show();

                        // Launch login activity
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        //finish();
                    /*} else {

                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }*/
                } catch (JSONException e) {
                    Log.e("here","error");
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                params.put("number", number);
                params.put("city", city);
                params.put("college", college);
                params.put("role", role);
                return params;
            }
        };
        Log.e("second","second");
        // Adding request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq, cancel_req_tag);
    }

    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();

    }
}
/*
    class postDataTask extends AsyncTask<String,Void,String>{
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog= new ProgressDialog(RegisterActivity.this);
            progressDialog.setMessage("Registering...");
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... strings) {

            try{
                return postData(strings[0]);
            }catch(IOException ex){
                return "Network Error !";
            }catch(JSONException ex){
                return "Data invalid !";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            mresult.setText(result);

            if(progressDialog!=null){
                progressDialog.dismiss();
            }

         //   startActivity(new Intent(RegisterActivity.this,LoginActivity.class));

        }


        private String postData(String urlPath) throws IOException,JSONException{


            StringBuilder result=new StringBuilder();
            BufferedWriter bufferedWriter=null;
            BufferedReader bufferedReader=null;

            int selectedId = roleRadioGroup.getCheckedRadioButtonId();
            String role;
            if(selectedId == R.id.rbStudent)
                role = "Student";
            else
                role = "Admin";
            try {
                JSONObject dataToSend = new JSONObject();
                dataToSend.put("name", etUserName.getText().toString());
                dataToSend.put("number", etPhone.getText());
                dataToSend.put("city", etCity.getText().toString());
                dataToSend.put("college", etCollege.getText().toString());
                dataToSend.put("role", role);


                URL url = new URL(urlPath);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(10000);
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoOutput(true);
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.connect();


                OutputStream outputStream = urlConnection.getOutputStream();
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
                bufferedWriter.write(dataToSend.toString());
                bufferedWriter.flush();

                InputStream inputStream = urlConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = bufferedReader.readLine()) != null) {

                    result.append(line).append("\n");
                }
            }

            finally{

                if(bufferedReader!=null){
                    bufferedReader.close();
                }
                if(bufferedWriter!=null){
                    bufferedWriter.close();
                }
        }
            return result.toString();
        }
    }

}*/



