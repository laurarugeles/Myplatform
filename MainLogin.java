package com.example.laura.myplatform;

import android.app.Activity;
import android.app.AlertDialog;
import android.support.v4.app.Fragment;
import android.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by laura on 27/09/2016.
 */

public class MainLogin extends AppCompatActivity {

    EditText user,psswd;
    Button login, register;

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setIcon(R.drawable.logo_solo);
        this.setTitle("UniGroups");

        TextView textViewInformation = (TextView) findViewById(R.id.textviewInstructionLogin);
        SpannableString string = new SpannableString("Información");
        string.setSpan(new UnderlineSpan(), 0, string.length(), 0);
        textViewInformation.setText(string);

        textViewInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainLogin.this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                alertDialog.setIcon(R.drawable.logo_solo);
                alertDialog.setTitle("Bienvenidos a UniGroups").setCancelable(false);
                alertDialog.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.setMessage("Para iniciar sesión, ingresa con tu usuario y contraseña de Uninorte").create();
                alertDialog.show();
            }
        });
    }


    public void loginMethod(View view) {
        user = (EditText) findViewById(R.id.usernameLG);
        psswd = (EditText) findViewById(R.id.passwordLG);
        login = (Button) findViewById(R.id.BLogin);

        String usr = user.getText().toString();
        String pss = psswd.getText().toString();

        if(usr.length()>0 && pss.length()>0){
            LogiTask logiTask = new LogiTask(this);
            logiTask.execute(usr,pss);
        }else{
            if((usr.length()>0 && pss.length()==0) || (usr.length()==0 && pss.length()>0) || (usr.length()==0 && pss.length()==0)){
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                alertDialog.setIcon(R.drawable.logo_solo);
                alertDialog.setTitle("Alerta").setCancelable(false);
                alertDialog.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.setMessage("No se ha ingresado la información requerida").create();
                alertDialog.show();
            }
        }
    }

    public void onLoginFinish(String resultDB){

        if(resultDB.matches(" ")){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            alertDialog.setIcon(R.drawable.logo_solo);
            alertDialog.setTitle("Alerta").setCancelable(false);
            alertDialog.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.setMessage("No se pudo realizar conexión con servidor").create();
            alertDialog.show();
        }else{
            if(resultDB.contains("login not succesful")){
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                alertDialog.setIcon(R.drawable.logo_solo);
                alertDialog.setTitle("Alerta").setCancelable(false);
                alertDialog.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.setMessage("Usuario o contraseña se encuentran errados ").create();
                alertDialog.show();
            }
            else{
                Intent intent = new Intent(MainLogin.this,MainActivity.class);

                try {
                    System.out.println("decoded: "+ URLDecoder.decode(resultDB,"UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                intent.putExtra("result",resultDB);
                startActivity(intent);
                finish();
            }
        }

    }

   /* public void registerMethod(View view) {
        if(view.getId() == R.id.buttonRegister){
            Intent intent = new Intent(MainLogin.this,MainLoginRegister.class);
            startActivity(intent);
        }
    }*/

    private class LogiTask extends AsyncTask<String, Void, String>
    {

        Context context;
        ProgressDialog pd;
        LogiTask(Context ctx)
        {
            context = ctx;
        }

        @Override
        protected String doInBackground(String... params) {

            String username = params[0];
            String password = params[1];
            String urlstring = "https://php-mrls94.c9users.io/login.php";
            String result = "";

            try {
                URL url = new URL(urlstring);
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                //httpsURLConnection.connect();
                httpsURLConnection.setRequestMethod("POST");
                httpsURLConnection.setDoInput(true);
                httpsURLConnection.setDoOutput(true);

                OutputStream outputStream = httpsURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));


                String post = URLEncoder.encode("username","UTF-8")+"="
                        +URLEncoder.encode(username,"UTF-8") +"&"
                        +URLEncoder.encode("password","UTF-8")+"="
                        +URLEncoder.encode(password,"UTF-8");


                bufferedWriter.write(post);
                bufferedWriter.flush();

                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpsURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));


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
                result = " ";
                return result;
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.hide();
            onLoginFinish(s);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(MainLogin.this);
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
