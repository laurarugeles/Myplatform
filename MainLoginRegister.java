package com.example.laura.myplatform;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by laura on 10/11/2016.
 */

public class MainLoginRegister extends AppCompatActivity {

    EditText name,Lname,email,telephone,username,password,confUsername,confPassword;
    Spinner materias;
    Button selectMateria,registrarse;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_login_register);
        this.setTitle("Registrarse");
    }

    public void RegistrationMethod(View view){
        name = (EditText) findViewById(R.id.registerName);
        Lname = (EditText) findViewById(R.id.registerLName);
        email = (EditText) findViewById(R.id.registerEmail);
        telephone = (EditText) findViewById(R.id.registerTelephone);
        username = (EditText) findViewById(R.id.registerUsername);
        password = (EditText) findViewById(R.id.registerPassword);
        confUsername = (EditText) findViewById(R.id.registerConfirmUSername);
        confPassword = (EditText) findViewById(R.id.registerConfirmPassword);

        String Sname= name.getText().toString();
        String SLname = Lname.getText().toString();
        String Semail = email.getText().toString();
        String Stelephone = telephone.getText().toString();
        String Susername = username.getText().toString();
        String Spassword = password.getText().toString();
        String SconfUsername = confUsername.getText().toString();
        String SconfPassword = confPassword.getText().toString();

        if(name.length()>0 && Lname.length()>0 && email.length()>0 && telephone.length()>0 && username.length()>0 &&
                password.length()>0 && confUsername.length()>0 && confPassword.length()>0){
            if(username.getText() != confUsername.getText()){
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                alertDialog.setIcon(R.drawable.ic_info_outline);
                alertDialog.setTitle("Alerta").setCancelable(false);
                alertDialog.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.setMessage("El usuario y su confirmación no son iguales").create();
                alertDialog.show();
            }else{
                if(password.getText() != confPassword.getText()){
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                    alertDialog.setIcon(R.drawable.ic_info_outline);
                    alertDialog.setTitle("Alerta").setCancelable(false);
                    alertDialog.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alertDialog.setMessage("La contraseña y su confirmación no son iguales").create();
                    alertDialog.show();
                }else{
                    //spinner

                    ShowRegister showRegister = new ShowRegister(this);
                    showRegister.execute(Sname,SLname,Semail,Stelephone,Susername,Spassword);
                }
            }
        }

    }

    public void onShowRegister(String result){

    }

    private class ShowRegister extends AsyncTask<String, Void, String>
    {

        Context context;
        ProgressDialog pd;
        ShowRegister(Context ctx)
        {
            context = ctx;
        }

        @Override
        protected String doInBackground(String... params) {

            String StudentName = params[0];
            String StudentLName = params[1];
            String Email = params[2];
            String Telephone = params[3];
            String username = params[4];
            String password = params[5];
            String urlstring = "https://php-mrls94.c9users.io/CrearStudentDB.php";


            try {
                URL url = new URL(urlstring);
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                //httpsURLConnection.connect();
                httpsURLConnection.setRequestMethod("POST");
                httpsURLConnection.setDoInput(true);
                httpsURLConnection.setDoOutput(true);

                OutputStream outputStream = httpsURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));


                String post = URLEncoder.encode("StudentName","UTF-8")+"="
                        +URLEncoder.encode(StudentName,"UTF-8") +"&"
                        +URLEncoder.encode("StudentLName","UTF-8")+"="
                        +URLEncoder.encode(StudentLName,"UTF-8") +"&"
                        +URLEncoder.encode("Email","UTF-8")+"="
                        +URLEncoder.encode(Email,"UTF-8")+"&"
                        +URLEncoder.encode("Telephone","UTF-8")+"="
                        +URLEncoder.encode(Telephone,"UTF-8")+"&"
                        +URLEncoder.encode("username","UTF-8")+"="
                        +URLEncoder.encode(username,"UTF-8")+"&"
                        +URLEncoder.encode("password","UTF-8")+"="
                        +URLEncoder.encode(password,"UTF-8");

                bufferedWriter.write(post);
                bufferedWriter.flush();

                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpsURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));

                String result = "";
                String line = "";

                while((line = bufferedReader.readLine())!=null)
                {
                    result+=line;
                }


                bufferedReader.close();
                inputStream.close();

                httpsURLConnection.disconnect();

                return result;

            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.hide();
            onShowRegister(s);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(MainLoginRegister.this);
            pd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            pd.setTitle("Conexión");
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pd.setIndeterminate(true);
            pd.getProgress();
            pd.setMessage("Cargando...");
            pd.setCancelable(false);
            pd.getWindow().setGravity(Gravity.CENTER);
            pd.show();

        }
    }



}
