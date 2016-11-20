package com.example.laura.myplatform;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemClickListener{

    private ArrayList<String> subjectAct, Materias, Categories,
            CategoriesAct, Categoriesdetail, CategoriesDetailAct,
            SugesstionsAll,SuggestionsDetail, CategoryDetailEvent, CategoryDetailEventAct,
            SubjectEvent,SubjectEventId,SubjectEventAct,SubjectEventActID,
            ProfileOwnCreate,ProfileOwnCreateAt,ProfileSubSubscribe,ProfileSubSubscribeAt,
            ProfileOwnInteresCreate,ProfileOwnInteresCreateAt, EstudiantesInscritos,EstudiantesInscritosAt,
            ProfileIntSubscribe, ProfileIntSubscribeAct, SuggesstionInt, SuggesstionIntAct;
    String name, lastname,code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);

        //Para obtener la información de MainLogin
        Bundle extras = getIntent().getExtras();

        //Se asigna la información obtenida del MainLogin en el cual se hizo la consulta a la base de datos
        String result = extras.getString("result");

        //Se split lo recibido de MainLogin
        String[] values = result.split(";");

        Materias = new ArrayList<>();

        name= values[0];
        lastname= values[1];
        code = values[2];

        for(int i=3 ; i < values.length; i++ ){
           Materias.add(values[i]);
            i++;
            Communicator.NrcMaterias.add(values[i]);
        }

        TextView nametextView = (TextView) header.findViewById(R.id.nombre);
        nametextView.setText(name);

        onSuggestionBegin();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    //Tiene como entrada resultado de la consulta a la base de datos si el resultado es diferente de vacio
    //toma el string y lo convierte en una arraylist para poder tomar dato por dato
    public void onFinishCategory(String result)
    {
        Categories = new ArrayList<>();
        String[] catstring;

        if((result.split(";")).length == 1){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            alertDialog.setIcon(R.drawable.logo_solo);
            alertDialog.setTitle("Alerta").setCancelable(false);
            alertDialog.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.setMessage("Ocurrió un error").create();
            alertDialog.show();
        }else{

            catstring = result.split(";");

            for(int i=1; i<catstring.length; i++)
            {
                Categories.add(catstring[i]);
            }

            Categories categories = new Categories();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,categories).commit();
        }
    }

    //Tiene como entrada resultado de la consulta a la base de datos si el resultado es diferente de vacio
    //toma el string y lo convierte en una arraylist para poder tomar dato por dato
    public void onFinishSpecific(String result) {

        Categoriesdetail = new ArrayList<>();

        if ((result.split(";")).length == 1) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            alertDialog.setIcon(R.drawable.logo_solo);
            alertDialog.setTitle("Alerta").setCancelable(false);
            alertDialog.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.setMessage("Ocurrió un error").create();
            alertDialog.show();
        } else {

            String[] resultados = result.split(";");

            for(int i=1; i<resultados.length; i++)
            {
                Communicator.NrcInteres.add(resultados[i]);
                i++;
                Categoriesdetail.add(resultados[i]);
            }

            CategoriesDetail categoriesDetail = new CategoriesDetail();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, categoriesDetail).commit();
        }
    }

    public void onFinishCategoryDetailEvents(String result)
    {
        if((result.split("&")).length == 0){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            alertDialog.setIcon(R.drawable.logo_solo);
            alertDialog.setTitle("Alerta").setCancelable(false);
            alertDialog.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.setMessage("No hay eventos creados en relación al interés").create();
            alertDialog.show();
        }else{
            if(result.contains("Error")){
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                alertDialog.setIcon(R.drawable.logo_solo);
                alertDialog.setTitle("Alerta").setCancelable(false);
                alertDialog.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.setMessage("Ocurrió un error en mostrar los eventos relacionados").create();
                alertDialog.show();
            }else{
                CategoryDetailEvent = new ArrayList<>();
                String[] catStringGeneral;
                String[] catStringDetail;
                String sugerenciaresult, EvntID;
                catStringGeneral = result.split("&");

                for(int i=1; i<(catStringGeneral.length); i++)
                {
                    catStringDetail = catStringGeneral[i].split(";");
                    EvntID = catStringDetail[0];
                    sugerenciaresult =" Fecha: "+catStringDetail[1]+ "\n Hora de inicio : "+catStringDetail[2]+ "\n Lugar : "+catStringDetail[5];
                    CategoryDetailEvent.add(sugerenciaresult);
                    Communicator.IDeventsCategoriesSuggestion.add(EvntID);
                }

                CategoriesDetailEvents categoriesDetailEvents= new CategoriesDetailEvents();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,categoriesDetailEvents).commit();
            }
        }
    }

    public void onFinishShowSubjectEvents(String result)
    {
        if(result.contains("Error")){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            alertDialog.setIcon(R.drawable.logo_solo);
            alertDialog.setTitle("Alerta").setCancelable(false);
            alertDialog.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.setMessage("Ocurrió un error en mostrar los eventos relacionados").create();
            alertDialog.show();
        }else{

            SubjectEvent = new ArrayList<>();
            String[] catStringGeneral;
            String[] catStringDetail={};
            String sugerenciaresult="",ideventoresult="";
            catStringGeneral = result.split("&");

            for (int i = 1; i < (catStringGeneral.length); i++) {
                catStringDetail = catStringGeneral[i].split(";");
                ideventoresult = catStringDetail[0];
                sugerenciaresult ="Fecha: " + catStringDetail[1] +
                        "\n Hora inicio: " + catStringDetail[2] + "\n Lugar: "+catStringDetail[5];
                SubjectEvent.add(sugerenciaresult);
                Communicator.IDeventsDetail.add(ideventoresult);
            }
            MainListviewEvents mainListviewEvents = new MainListviewEvents();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,mainListviewEvents).commit();
        }
    }

    public void onFinishShowDetailStudentInformation(String result)
    {
        if(result.equals("Hubo un error")){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            alertDialog.setIcon(R.drawable.logo_solo);
            alertDialog.setTitle("Alerta").setCancelable(false);
            alertDialog.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.setMessage("Ocurrió un error").create();
            alertDialog.show();
        }else{

            EstudiantesInscritos = new ArrayList<>();
            String[] catStringGeneral;
            String[] catStringDetail={};
            String sugerenciaresult="",ideventoresult="",codigostud="";
            catStringGeneral = result.split("&");

            if(catStringGeneral.length ==1){
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                alertDialog.setIcon(R.drawable.logo_solo);
                alertDialog.setTitle("Alerta").setCancelable(false);
                alertDialog.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.setMessage("En el momento no hay estudiantes inscritos").create();
                alertDialog.show();
            }else{
                for (int i = 1; i < (catStringGeneral.length); i++) {
                    catStringDetail = catStringGeneral[i].split(";");
                    codigostud = catStringDetail[4];
                    sugerenciaresult ="Nombre: " + catStringDetail[0] + "\n Email: " + catStringDetail[2]+
                                        "\n Puntuación: "+catStringDetail[3];
                    EstudiantesInscritos.add(sugerenciaresult);
                    if(Communicator.codeStudentFromSubjectOrInteres == true){
                        Communicator.CodeStudentInscritos.add(codigostud);
                    }else{
                        Communicator.CodeStudentInscritosInteres.add(codigostud);
                    }
                }
            }
            StudentInscritos studentInscritos= new StudentInscritos();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,studentInscritos).commit();
        }
    }

    public void onFinishShowEventDetailSelected(String result)
    {
        System.out.println(result);
        if(result.equals("error")){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            alertDialog.setIcon(R.drawable.logo_solo);
            alertDialog.setTitle("Alerta").setCancelable(false);
            alertDialog.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.setMessage("Ocurrió un error en mostrar los detalles del evento").create();
            alertDialog.show();
        }else{
            SuggestionsDetail = new ArrayList<>();
            String[] catStringGeneral;

            for(int i=1; i<(result.length()-1); i++)
            {   System.out.println(result);
                catStringGeneral = result.split(";");
                Communicator.EventsDetailidgrupo = catStringGeneral[1];
                Communicator.EventsDetailfecha = catStringGeneral[2];
                Communicator.EventsDetailhorainicio = catStringGeneral[3];
                Communicator.EventsDetailminstud = catStringGeneral[4];
                Communicator.EventsDetailmaxstud = catStringGeneral[5];
                Communicator.EventsDetaillugar = catStringGeneral[6];
                Communicator.EventsDetaildescripcion = catStringGeneral[7];
                Communicator.EventsDetailhorafin = catStringGeneral[8];
                Communicator.EventsDetailstudname = catStringGeneral[9];
                Communicator.EventsDetailstudlname = catStringGeneral[10];
                Communicator.EventsDetailEmail = catStringGeneral[11];
                Communicator.EventsDetailTelephone = catStringGeneral[12];
                Communicator.EventsDetailinscritos = catStringGeneral[13];
                Communicator.subjectSelectionCreateEvent = catStringGeneral[14];
            }
            DetailEvent detailEvent= new DetailEvent();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,detailEvent).commit();
        }
    }

    public void onFinishShowEventsDetailProfileDelete(String result)
    {
        if(result.equals("error")){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            alertDialog.setIcon(R.drawable.logo_solo);
            alertDialog.setTitle("Alerta").setCancelable(false);
            alertDialog.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.setMessage("Ocurrió un error en mostrar los detalles del evento").create();
            alertDialog.show();
        }else{
            SuggestionsDetail = new ArrayList<>();
            String[] catStringGeneral;

            for(int i=1; i<(result.length()-1); i++)
            {
                catStringGeneral = result.split(";");
                Communicator.EventsDetailidgrupo = catStringGeneral[1];
                Communicator.EventsDetailfecha = catStringGeneral[2];
                Communicator.EventsDetailhorainicio = catStringGeneral[3];
                Communicator.EventsDetailminstud = catStringGeneral[4];
                Communicator.EventsDetailmaxstud = catStringGeneral[5];
                Communicator.EventsDetaillugar = catStringGeneral[6];
                Communicator.EventsDetaildescripcion = catStringGeneral[7];
                Communicator.EventsDetailhorafin = catStringGeneral[8];
                Communicator.EventsDetailstudname = catStringGeneral[9];
                Communicator.EventsDetailstudlname = catStringGeneral[10];
                Communicator.EventsDetailEmail = catStringGeneral[11];
                Communicator.EventsDetailTelephone = catStringGeneral[12];
                Communicator.EventsDetailinscritos = catStringGeneral[13];
                //   Communicator.EventsDetailmateria = catStringGeneral[14];
            }
            DetailEventProfile detailEventProfile = new DetailEventProfile();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,detailEventProfile).commit();
        }
    }

    public void onFinishShowEventsDetailProfileUnsubscribe(String result)
    {
        if(result.equals("error")){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            alertDialog.setIcon(R.drawable.logo_solo);
            alertDialog.setTitle("Alerta").setCancelable(false);
            alertDialog.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.setMessage("Ocurrió un error en mostrar los detalles del evento").create();
            alertDialog.show();
        }else{
            SuggestionsDetail = new ArrayList<>();
            String[] catStringGeneral;

            for(int i=1; i<(result.length()-1); i++)
            {
                catStringGeneral = result.split(";");
                Communicator.EventsDetailidgrupo = catStringGeneral[1];
                Communicator.EventsDetailfecha = catStringGeneral[2];
                Communicator.EventsDetailhorainicio = catStringGeneral[3];
                Communicator.EventsDetailminstud = catStringGeneral[4];
                Communicator.EventsDetailmaxstud = catStringGeneral[5];
                Communicator.EventsDetaillugar = catStringGeneral[6];
                Communicator.EventsDetaildescripcion = catStringGeneral[7];
                Communicator.EventsDetailhorafin = catStringGeneral[8];
                Communicator.EventsDetailstudname = catStringGeneral[9];
                Communicator.EventsDetailstudlname = catStringGeneral[10];
                Communicator.EventsDetailEmail = catStringGeneral[11];
                Communicator.EventsDetailTelephone = catStringGeneral[12];
                Communicator.EventsDetailinscritos = catStringGeneral[13];
                //   Communicator.EventsDetailmateria = catStringGeneral[14];
            }
            DetailEventProfileUnsubscribe detailEventProfileUnsubscribe = new DetailEventProfileUnsubscribe();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,detailEventProfileUnsubscribe).commit();
        }
    }

    public void onFinishShowEventsDetailProfileInteresSubscribe(String result)
    {
        if(result.equals("error")){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            alertDialog.setIcon(R.drawable.logo_solo);
            alertDialog.setTitle("Alerta").setCancelable(false);
            alertDialog.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.setMessage("Ocurrió un error en mostrar los detalles del evento").create();
            alertDialog.show();
        }else{
            SuggestionsDetail = new ArrayList<>();
            String[] catStringGeneral;

            for(int i=1; i<(result.length()-1); i++)
            {
                catStringGeneral = result.split(";");
                Communicator.EventsDetailidgrupo = catStringGeneral[1];
                Communicator.EventsDetailfecha = catStringGeneral[2];
                Communicator.EventsDetailhorainicio = catStringGeneral[3];
                Communicator.EventsDetailminstud = catStringGeneral[4];
                Communicator.EventsDetailmaxstud = catStringGeneral[5];
                Communicator.EventsDetaillugar = catStringGeneral[6];
                Communicator.EventsDetaildescripcion = catStringGeneral[7];
                Communicator.EventsDetailhorafin = catStringGeneral[8];
                Communicator.EventsDetailstudname = catStringGeneral[9];
                Communicator.EventsDetailstudlname = catStringGeneral[10];
                Communicator.EventsDetailEmail = catStringGeneral[11];
                Communicator.EventsDetailTelephone = catStringGeneral[12];
                Communicator.EventsDetailinscritos = catStringGeneral[13];
                //   Communicator.EventsDetailmateria = catStringGeneral[14];
            }
            DetailEventProfileUnsubscribe detailEventProfileUnsubscribe = new DetailEventProfileUnsubscribe();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,detailEventProfileUnsubscribe).commit();
        }
    }

    public void onFinishShowDestoyProfileDetailEvent(String result)
    {

        if(result.contains("Hubo un error")){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            alertDialog.setIcon(R.drawable.logo_solo);
            alertDialog.setTitle("Alerta").setCancelable(false);
            alertDialog.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.setMessage("Ocurrió un error en la eliminación del evento").create();
            alertDialog.show();
        }else{
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            alertDialog.setIcon(R.drawable.logo_solo);
            alertDialog.setTitle("Alerta").setCancelable(false);
            alertDialog.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.setMessage("La eliminación del evento fue exitosa").create();
            alertDialog.show();
            Suggestion  suggestion= new Suggestion();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,suggestion).commit();
        }
        Suggestion suggestion = new Suggestion();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,suggestion).commit();
    }

    public void onFinishShowAddInteresLikes(String result)
    {   System.out.println("Add interes : "+result);
        if(result.contains("hubo error")){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            alertDialog.setIcon(R.drawable.logo_solo);
            alertDialog.setTitle("Alerta").setCancelable(false);
            alertDialog.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.setMessage("Ocurrió un error, no se pudo realizar conexión al servidor").create();
            alertDialog.show();
        }else{
            if(result.contains("Ya estas inscrito")){
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                alertDialog.setIcon(R.drawable.logo_solo);
                alertDialog.setTitle("Alerta").setCancelable(false);
                alertDialog.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.setMessage("El interés ya se encuentra inscrito").create();
                alertDialog.show();
            }else{
                if(result.contains("Hubo error")){
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                    alertDialog.setIcon(R.drawable.logo_solo);
                    alertDialog.setTitle("Alerta").setCancelable(false);
                    alertDialog.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alertDialog.setMessage("Ocurrió un error, el interés no se pudo agregar").create();
                    alertDialog.show();
                }else{
                    if(result.contains("Agregado a Intereses")){
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                        alertDialog.setIcon(R.drawable.logo_solo);
                        alertDialog.setTitle("Alerta").setCancelable(false);
                        alertDialog.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alertDialog.setMessage("El interes ha sigo agregado a tus gustos").create();
                        alertDialog.show();
                    }
                }
                //Suggestion  suggestion= new Suggestion();
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,suggestion).commit();
            }
        }
      //  Suggestion suggestion = new Suggestion();
      //  getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,suggestion).commit();
    }

    public void onFinishShowDesinscribirseProfileEventsSubscribe(String result)
    {

        if(result.contains("Hubo un error")){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            alertDialog.setIcon(R.drawable.logo_solo);
            alertDialog.setTitle("Alerta").setCancelable(false);
            alertDialog.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.setMessage("Ocurrió un error en la desubscripción del evento").create();
            alertDialog.show();
        }else{
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            alertDialog.setIcon(R.drawable.logo_solo);
            alertDialog.setTitle("Alerta").setCancelable(false);
            alertDialog.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.setMessage("La desubscripción del evento fue exitosa").create();
            alertDialog.show();
            Suggestion  suggestion= new Suggestion();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,suggestion).commit();
        }
        onSuggestionBegin();
    }

    public void onFinishProfileOwnSubjectEventsCreate(String result)
    {
        if(result.equals("Hubo un Error")){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            alertDialog.setIcon(R.drawable.logo_solo);
            alertDialog.setTitle("Alerta").setCancelable(false);
            alertDialog.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.setMessage("Ocurrió un error en mostrar los eventos relacionados").create();
            alertDialog.show();
        }else{

            ProfileOwnCreate = new ArrayList<>();
            String[] catStringGeneral;
            String[] catStringDetail={};
            String sugerenciaresult="",ideventresult="",eventName="";
            catStringGeneral = result.split("&");

            if(catStringDetail.length == 1){
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                alertDialog.setIcon(R.drawable.logo_solo);
                alertDialog.setTitle("Alerta").setCancelable(false);
                alertDialog.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.setMessage("No hay grupos creados").create();
                alertDialog.show();
            }else {

                for (int i = 1; i < (catStringGeneral.length); i++) {
                    catStringDetail = catStringGeneral[i].split(";");
                    ideventresult = catStringDetail[8];
                    eventName = catStringDetail[0];
                    sugerenciaresult =" "+catStringDetail[0]+"\n Fecha: " + catStringDetail[1] +
                            "\n Hora inicio: " + catStringDetail[2] + "\n Lugar: "+catStringDetail[5];
                    ProfileOwnCreate.add(sugerenciaresult);
                    Communicator.IDeventProfileOwnSubjectEventCreate.add(ideventresult);
                    Communicator.NameProfSubEventCreate.add(eventName);
                }
            }
            ProfileOwnSubjectEventCreate profileOwnSubjectEventCreate = new ProfileOwnSubjectEventCreate();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,profileOwnSubjectEventCreate).commit();
        }
    }

    public void onFinishProfileOwnSubjectEventsSubscribe(String result)
    {
        if(result.equals("Hubo un error")){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            alertDialog.setIcon(R.drawable.logo_solo);
            alertDialog.setTitle("Alerta").setCancelable(false);
            alertDialog.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.setMessage("Ocurrió un error en mostrar los eventos relacionados").create();
            alertDialog.show();
        }else{

            ProfileSubSubscribe= new ArrayList<>();
            String[] catStringGeneral;
            String[] catStringDetail={};
            String sugerenciaresult="",resultID="",resultName="";
            catStringGeneral = result.split("&");

            if(catStringDetail.length == 1){
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                alertDialog.setIcon(R.drawable.logo_solo);
                alertDialog.setTitle("Alerta").setCancelable(false);
                alertDialog.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.setMessage("No hay grupos creados").create();
                alertDialog.show();
            }else {

                for (int i = 1; i < (catStringGeneral.length); i++) {
                    catStringDetail = catStringGeneral[i].split(";");
                    for (int j = 0; j<catStringDetail.length; j++)
                    {
                        System.out.println(j+" - "+catStringDetail[j]);
                    }
                    resultID = catStringDetail[8];
                    resultName = catStringDetail[0];
                    sugerenciaresult =" "+catStringDetail[0]+"\n Fecha: " + catStringDetail[1] +
                            "\n Hora inicio: " + catStringDetail[2] + "\n Lugar: "+catStringDetail[5];
                    ProfileSubSubscribe.add(sugerenciaresult);
                    Communicator.IDeventProfileSubjectEventSubscribe.add(resultID);
                    Communicator.NameProfSubEventSubscribe.add(resultName);
                }
            }
            ProfileSubjectEventSubscribe profileSubjectEventSubscribe= new ProfileSubjectEventSubscribe();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,profileSubjectEventSubscribe).commit();
        }
    }

    public void onFinishProfileInteresEventsSubscribe(String result)
    {
        if(result.equals("Hubo un error")){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            alertDialog.setIcon(R.drawable.logo_solo);
            alertDialog.setTitle("Alerta").setCancelable(false);
            alertDialog.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.setMessage("Ocurrió un error en mostrar los eventos relacionados").create();
            alertDialog.show();
        }else{

            ProfileIntSubscribe= new ArrayList<>();
            String[] catStringGeneral;
            String[] catStringDetail={};
            String sugerenciaresult="",resultID="",resultName;
            catStringGeneral = result.split("&");

            if(catStringDetail.length == 1){
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                alertDialog.setIcon(R.drawable.logo_solo);
                alertDialog.setTitle("Alerta").setCancelable(false);
                alertDialog.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.setMessage("No hay grupos creados").create();
                alertDialog.show();
            }else {

                for (int i = 1; i < (catStringGeneral.length); i++) {
                    catStringDetail = catStringGeneral[i].split(";");
                    for (int j = 0; j<catStringDetail.length; j++)
                    {
                        System.out.println(j+" - "+catStringDetail[j]);
                    }
                    resultID = catStringDetail[9];
                    resultName = catStringDetail[0];
                    sugerenciaresult =" "+catStringDetail[0]+"\n Fecha: " + catStringDetail[2] +
                            "\n Hora inicio: " + catStringDetail[3] + "\n Lugar: "+catStringDetail[6];
                    ProfileIntSubscribe.add(sugerenciaresult);
                    Communicator.IDeventProfileInteresEventSubscribe.add(resultID);
                    Communicator.NameProfIntEventSubscribe.add(resultName);
                }
            }
            ProfileInteresEventSubscribe profileInteresEventSubscribe= new ProfileInteresEventSubscribe();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,profileInteresEventSubscribe).commit();
        }
    }

    public void onFinishProfileOwnInteresEventsCreate(String result)
    {
        if(result.equals("Hubo un Error")){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            alertDialog.setIcon(R.drawable.logo_solo);
            alertDialog.setTitle("Alerta").setCancelable(false);
            alertDialog.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.setMessage("Ocurrió un error en mostrar los eventos relacionados").create();
            alertDialog.show();
        }else{

            ProfileOwnInteresCreate= new ArrayList<>();
            String[] catStringGeneral;
            String[] catStringDetail={};
            String sugerenciaresult="",resultID="",resultName="";
            catStringGeneral = result.split("&");

            if(catStringDetail.length == 1){
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                alertDialog.setIcon(R.drawable.logo_solo);
                alertDialog.setTitle("Alerta").setCancelable(false);
                alertDialog.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.setMessage("No hay grupos creados").create();
                alertDialog.show();
            }else {

                for (int i = 1; i < (catStringGeneral.length); i++) {
                    catStringDetail = catStringGeneral[i].split(";");
                    for (int j = 0; j<catStringDetail.length; j++)
                    {
                        System.out.println(j+" - "+catStringDetail[j]);
                    }
                    resultID = catStringDetail[9];
                    resultName = catStringDetail[1];
                    sugerenciaresult =" "+catStringDetail[1]+"\n Fecha: " + catStringDetail[2] +
                            "\n Hora inicio: " + catStringDetail[3] + "\n Lugar: "+catStringDetail[6];
                    ProfileOwnInteresCreate.add(sugerenciaresult);
                    Communicator.IDeventProfileOwnInteresEventCreate.add(resultID);
                    Communicator.NameProfIntEventCreate.add(resultName);
                }
            }
            ProfileOwnInteresEventCreate profileOwnInteresEventCreate = new ProfileOwnInteresEventCreate();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,profileOwnInteresEventCreate).commit();
        }
    }

    public void onFinishShowSuggestions(String result)
    {   SuggestionsDetail = new ArrayList<>();
        if((result.split("&")).length ==1){
            Suggestion suggestion = new Suggestion();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,suggestion).commit();
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            alertDialog.setIcon(R.drawable.logo_solo);
            alertDialog.setTitle("Alerta").setCancelable(false);
            alertDialog.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.setMessage("No hay grupos para mostrar").create();
            alertDialog.show();
        }else{
            String[] catStringGeneral;
            String[] catStringDetail;
            String sugerenciaresult,ideventoresult ="";
            catStringGeneral = result.split("&");

            for(int i=1; i<(catStringGeneral.length); i++)
            {
                catStringDetail = catStringGeneral[i].split(";");
                ideventoresult = catStringDetail[5];
                sugerenciaresult =" "+catStringDetail[0]+"\n Fecha: "+catStringDetail[1]+
                        "\n Hora de inicio: "+catStringDetail[2] + "\n Lugar: "+catStringDetail[4];
                SuggestionsDetail.add(sugerenciaresult);
                Communicator.IDeventsSuggestionDetail.add(ideventoresult);
            }
            Suggestion suggestion = new Suggestion();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,suggestion).commit();
        }
    }

    public void onFinishShowSuggestionsInteres(String result)
    {   SuggesstionInt = new ArrayList<>();
        if((result.split("&")).length ==1){
            Suggestion suggestion = new Suggestion();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,suggestion).commit();
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            alertDialog.setIcon(R.drawable.logo_solo);
            alertDialog.setTitle("Alerta").setCancelable(false);
            alertDialog.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.setMessage("No hay grupos para mostrar").create();
            alertDialog.show();
        }else{
            String[] catStringGeneral;
            String[] catStringDetail;
            String sugerenciaresult,ideventoresult ="";
            catStringGeneral = result.split("&");

            for(int i=1; i<(catStringGeneral.length); i++)
            {
                catStringDetail = catStringGeneral[i].split(";");
                ideventoresult = catStringDetail[6];
                sugerenciaresult =" "+catStringDetail[0]+"\n Fecha: "+catStringDetail[1]+
                        "\n Hora de inicio: "+catStringDetail[2] + "\n Lugar: "+catStringDetail[4];
                SuggesstionInt.add(sugerenciaresult);
                Communicator.IDeventsSuggestionDetailInteres.add(ideventoresult);
            }
            SuggestionInteres suggestionInteres = new SuggestionInteres();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,suggestionInteres).commit();
        }
    }

    public void onFinishEventsCreation(String result)
    {   System.out.println(result);
        if(result.matches("") || result.contains("Hubo un error")){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            alertDialog.setIcon(R.drawable.logo_solo);
            alertDialog.setTitle("Alerta").setCancelable(false);
            alertDialog.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.setMessage("Ocurrió un error en la creación del evento").create();
            alertDialog.show();
        }else{
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            alertDialog.setIcon(R.drawable.logo_solo);
            alertDialog.setTitle("Alerta").setCancelable(false);
            alertDialog.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.setMessage("La creación del evento fue exitosa").create();
            alertDialog.show();
            Profile profile= new Profile();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,profile).commit();
        }
    }

    public void onFinishEventsCreationCategory(String result)
    {
        if(result.matches("") || result.contains("Hubo un error")){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            alertDialog.setIcon(R.drawable.logo_solo);
            alertDialog.setTitle("Alerta").setCancelable(false);
            alertDialog.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.setMessage("Ocurrió un error en la creación del evento").create();
            alertDialog.show();
        }else{
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            alertDialog.setIcon(R.drawable.logo_solo);
            alertDialog.setTitle("Alerta").setCancelable(false);
            alertDialog.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.setMessage("La creación del evento fue exitosa").create();
            alertDialog.show();
           // Suggestion  suggestion= new Suggestion();
           // getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,suggestion).commit();
            Profile profile = new Profile();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,profile).commit();
        }
    }

    public void onFinishSubscribeDetailEventSubject(String result)
    {   System.out.println(result);
        if(result.contains("Hubo un error")){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            alertDialog.setIcon(R.drawable.logo_solo);
            alertDialog.setTitle("Alerta").setCancelable(false);
            alertDialog.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.setMessage("Ocurrió un error en la subscripción del evento").create();
            alertDialog.show();
        }else{
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            alertDialog.setIcon(R.drawable.logo_solo);
            alertDialog.setTitle("Alerta").setCancelable(false);
            alertDialog.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.setMessage("La Subscripción del evento fue exitosa").create();
            alertDialog.show();
            if(Communicator.suggestionInteresorSubject == true){
                onSuggestionBegin();
            }else{
                onStartSuggestionInteres();
            }

        }
    }

    public void onStartEventsCreation(String...params){
        String minStud = params[0];
        String maxStud = params[1];
        String fecha = params[2];
        String time = params[3];
        String lugar = params[4];
        String materiaEs = params[5];
        String codigo = code;
        String descripcion = params[6];
        String endtime = params[7];
        String idSub = Communicator.NrcMateriasEnviados;

        EventsCreation eventsCreation = new EventsCreation(this);
        eventsCreation.execute(minStud,maxStud,fecha,time,lugar,materiaEs,codigo,descripcion,endtime,idSub);
    }

    public void onStartEventsCreationCategory(String...params){
        String minStud = params[0];
        String maxStud = params[1];
        String fecha = params[2];
        String time = params[3];
        String lugar = params[4];
        String categoriaEs = params[5];
        String interesEs = params[6];
        String codigo = code;
        String descripcion = params[7];
        String endtime = params[8];

        EventsCreationCategory eventsCreationCategory = new EventsCreationCategory(this);
        eventsCreationCategory.execute(minStud,maxStud,fecha,time,lugar,categoriaEs,interesEs,codigo,descripcion,endtime);
    }

    //pasa información a Suggestion
    public ArrayList<String> getSuggestionInteres(){
        SuggesstionIntAct = SuggesstionInt;
                return SuggesstionIntAct;
    }

    // pasa información a categorías
    public ArrayList<String> getSubjectAct() {
        subjectAct = Materias;
        return subjectAct;
    }

    // pasa información a ProfileOwnSubjectEventCrete
    public ArrayList<String> getFinishProfileOwnSubjectEventsCreate() {
        ProfileOwnCreateAt = ProfileOwnCreate;
        return ProfileOwnCreateAt;
    }

    // pasa información a ProfileSubjectEventSubscribe
    public ArrayList<String> getFinishProfileSubjectEventsSubscribe() {
        ProfileSubSubscribeAt = ProfileSubSubscribe;
        return ProfileSubSubscribeAt;
    }

    // pasa información a ProfileSubjectEventSubscribe
    public ArrayList<String> getFinishProfileOwnInteresEventsCreate() {
        ProfileOwnInteresCreateAt = ProfileOwnInteresCreate;
        return ProfileOwnInteresCreateAt;
    }

    // pasa información a ProfileSubjectEventSubscribe
    public ArrayList<String> getFinishProfileInteresEventsSubscribe() {
        ProfileIntSubscribeAct = ProfileIntSubscribe;
        return ProfileIntSubscribeAct;
    }

    //Envia información ya split de la consulta a la base de datos
    //sobre suggestions esta se llama en el fragment
    //donde se requiere esta información
    public ArrayList<String> getSuggestionsDetail(){
        SugesstionsAll = SuggestionsDetail;
        return SugesstionsAll;
    }

    public ArrayList<String> getDetailStudentInformation(){
        EstudiantesInscritosAt= EstudiantesInscritos;
        return EstudiantesInscritosAt;
    }

    //Contiene el valor del listview item obtenido de Categories
    // y ejecuta una nueva consulta a la base de datos con la categoria seleccionada
    public void onCategoriaSelected(String nameCategoria){
        CategoriesConnection specificConnection = new CategoriesConnection(this);
        specificConnection.execute("Especifico",nameCategoria);
    }

    //inicializa asyntask para consultar los interes
    public void onCategoryDetailSelected(){
        ShowCategoriesSuggestions showCategoriesSuggestions= new ShowCategoriesSuggestions(this);
        showCategoriesSuggestions.execute(Communicator.NrcInteresEnviados,code);
    }

    //inicializa asyntask para consultar el detalle del evento de materia seleccionado
    public void onDetailStudentInformation(String a){
        Communicator.codeStudentFromSubjectOrInteres = true;
        Communicator.CodeStudentInscritos.clear();
        ShowDetailStudentInformation showDetailStudentInformation = new ShowDetailStudentInformation(this);
        showDetailStudentInformation.execute(a);
    }

    //inicializa asyntask para consultar el detalle del evento de interes seleccionado
    public void onDetailStudentInformationInteres(String a){
        Communicator.codeStudentFromSubjectOrInteres = false;
        Communicator.CodeStudentInscritosInteres.clear();
        ShowDetailStudentInformationInteres showDetailStudentInformationInteres = new ShowDetailStudentInformationInteres(this);
        showDetailStudentInformationInteres.execute(a);
    }

    //inicializa asyntask para consultar las materias
    public void onSubjectSelected(){
        ShowSubjectSuggestions showSubjectSuggestions= new ShowSubjectSuggestions(this);
        showSubjectSuggestions.execute(Communicator.subjectSelectionCreateEvent,Communicator.NrcMateriasEnviados,code);
    }

    //inicializa asyntask para consultar las sugerencias de interes
    public void onStartSuggestionInteres(){
        ShowSuggestionsInteres showSuggestionsInteres = new ShowSuggestionsInteres(this);
        showSuggestionsInteres.execute(code);
    }

    //inicializa asyntask para añadir interes a tabla de likes
    public void onAddInteresLikes(){
        ShowAddInteresLikes showAddInteresLikes = new ShowAddInteresLikes(this);
        showAddInteresLikes.execute(code,Communicator.NrcInteresEnviados);
    }

    //inicializa asyntask para consultar el detalle del evento de materia seleccionado
    public void onSubjectSelectedEventsDetail(){
        ShowEventsDetail showEventsDetail= new ShowEventsDetail(this);
        showEventsDetail.execute(Communicator.IDeventsDetailSelected);
    }

    //inicializa asyntask para consultar el detalle del evento de materia seleccionado
    public void onInteresSelectedEventsDetail(){
        ShowEventsDetailInteres showEventsDetailInteres= new ShowEventsDetailInteres(this);
        showEventsDetailInteres.execute(Communicator.IDeventsDetailSelectedInteres);
    }

    //inicializa asyntask para consultar el detalle del evento de interes seleccionado
    public void onProfileOwnInteresEventCreateDetailEvent(String idInteres){
        ShowEventsDetailProfileDeleteInteres showEventsDetailProfileDeleteInteres= new ShowEventsDetailProfileDeleteInteres(this);
        showEventsDetailProfileDeleteInteres.execute(idInteres);
    }

    //inicializa asyntask para consultar el detalle del evento de materia seleccionado
    public void onProfileOwnSubjectEventCreateDetailEvent(String idMateriasC){
        ShowEventsDetailProfileDelete showEventsDetailProfileDelete= new ShowEventsDetailProfileDelete(this);
        showEventsDetailProfileDelete.execute(idMateriasC);
    }

    //inicializa asyntask para consultar el detalle del evento de materia seleccionado
    public void onProfileSubjectEventSubscribeDetailEvent(String idMateriasS){
        ShowEventsDetailProfileUnsubscribe showEventsDetailProfileUnsubscribe= new ShowEventsDetailProfileUnsubscribe(this);
        showEventsDetailProfileUnsubscribe.execute(idMateriasS);
    }

    //inicializa asyntask para consultar el detalle del evento de materia seleccionado
    public void onProfileInteresEventSubscribeDetailEvent(String idInteresS){
        ShowEventsDetailProfileInteresSubscribe eventsDetailProfileInteresSubscribe= new ShowEventsDetailProfileInteresSubscribe(this);
        eventsDetailProfileInteresSubscribe.execute(idInteresS);
    }

    //inicializa asyntask para consultar el detalle del evento de sugerencia seleccionado
    public void onSuggestionSelectedEventsDetail(){
        ShowEventsDetail showEventsDetail= new ShowEventsDetail(this);
        showEventsDetail.execute(Communicator.itemSelectedSuggestion);
    }

    //inicializa asyntask para consultar las sugerencias
    public void onSuggestionBegin(){
        ShowSuggestions showSuggestions = new ShowSuggestions(this);
        showSuggestions.execute(code);
    }

    //inicializa asyntask para consultar los eventos de materias creados por la persona
    public void onProfileOwnSubjectEventsCreate(){
        ShowProfileOwnSubjectEventsCreate showProfileOwnSubjectEventsCreate = new ShowProfileOwnSubjectEventsCreate(this);
        showProfileOwnSubjectEventsCreate.execute(code);
    }

    //inicializa asyntask para consultar los eventos de materias inscritas por la persona
    public void onProfileSubjectEventsSubscribe(){
        ShowProfileSubjectEventsSubscribe showProfileSubjectEventsSubscribe = new ShowProfileSubjectEventsSubscribe(this);
        showProfileSubjectEventsSubscribe.execute(code);
    }

    //inicializa asyntask para consultar los eventos de materias inscritas por la persona
    public void onProfileOwnInteresEventCreate(){
        ShowProfileOwnInteresEventsCreate showProfileOwnInteresEventsCreate = new ShowProfileOwnInteresEventsCreate(this);
        showProfileOwnInteresEventsCreate.execute(code);
    }

    //inicializa asyntask para consultar los eventos de materias inscritas por la persona
    public void onProfileInteresEventSubscribe(){
        ShowProfileInteresEventsSubscribe showProfileInteresEventsSubscribe = new ShowProfileInteresEventsSubscribe(this);
        showProfileInteresEventsSubscribe.execute(code);
    }

    //inicializa asyntask para consultar la inscripción al evento de materia
    public void onSubscribeDetailEventSubject(String s){
        ShowSubscribeDetailEventSubject showSubscribeDetailEventSubject = new ShowSubscribeDetailEventSubject(this);
        showSubscribeDetailEventSubject.execute(code,s);
    }

    //inicializa asyntask para consultar la inscripción al evento de interes
    public void onSubscribeDetailEventInteres(String s){
        ShowSubscribeDetailEventInteres showSubscribeDetailEventInteres= new ShowSubscribeDetailEventInteres(this);
        showSubscribeDetailEventInteres.execute(code,s);
    }

    //inicializa asyntask para consultar el detalle del evento de sugerencia seleccionado
    public void onDestroyProfileEventsCreate(String a){
        ShowDestoyProfileDetailEvent showDestoyProfileDetailEvent = new ShowDestoyProfileDetailEvent(this);
        showDestoyProfileDetailEvent.execute(a);
    }

    //inicializa asyntask para eliminar elemento seleccionado en materias
    public void onDesinscribirseProfileEventsCreate(String a){
        ShowDesinscribirseProfileEventsSubscribe showDesinscribirseProfileEventsSubscribe = new ShowDesinscribirseProfileEventsSubscribe(this);
        showDesinscribirseProfileEventsSubscribe.execute(a,code);
    }

    //inicializa asyntask para eliminar elemento seleccionado en interes
    public void onDesinscribirseProfileEventsCreateInteres(String a){
        ShowDesinscribirseProfileEventsSubscribeInteres showDesinscribirseProfileEventsSubscribeInteres = new ShowDesinscribirseProfileEventsSubscribeInteres(this);
       showDesinscribirseProfileEventsSubscribeInteres.execute(a,code);
    }

    //Envia información ya split de la consulta a la base de datos
    //sobre categories general esta se llama en el fragment
    //donde se requiere esta información
    public ArrayList<String> getCategoryAct() {
        CategoriesAct = Categories;
        return CategoriesAct;
    }

    //Envia la información ya split de la consulta a la base de datos
    //sobre categories detail (categories specific) esta se llama en el fragment
    //donde se requiere esta información
    public ArrayList<String> getCategoryDetailAct() {
        CategoriesDetailAct = Categoriesdetail;
        return CategoriesDetailAct;
    }

    //Envia la información ya split de la consulta a la base de datos
    //sobre eventos realacionados a esa categoria esta se llama en el fragment
    //donde se requiere esta información
    public ArrayList<String> getCategoryDetailEventAct() {
        CategoryDetailEventAct = CategoryDetailEvent;
        return CategoryDetailEventAct;
    }

    //Envia la información ya split de la consulta a la base de datos
    //sobre eventos realacionados a esa materia esta se llama en el fragment
    //donde se requiere esta información
    public ArrayList<String> getSubjectEvents() {
        SubjectEventAct = SubjectEvent;
        return SubjectEventAct;
    }

    //Solicita información de la base de datos para mostrar grupos de sugerencias
    private class ShowCategoriesSuggestions extends AsyncTask<String,Void,String>{

        ProgressDialog pd;
        Context context;

        ShowCategoriesSuggestions(Context ctx){context = ctx;}

        @Override
        protected String doInBackground(String... params) {
            String idInteres = params[0];
            String codigo = params[1];
            String urlstring = "https://php-mrls94.c9users.io/getEventosporInteres.php";
            try {
                URL url = new URL(urlstring);
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                //httpsURLConnection.connect();
                httpsURLConnection.setRequestMethod("POST");
                httpsURLConnection.setDoInput(true);
                httpsURLConnection.setDoOutput(true);

                OutputStream outputStream = httpsURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post = URLEncoder.encode("idInteres","UTF-8")+"="+URLEncoder.encode(idInteres,"UTF-8") + "&"+
                        URLEncoder.encode("codigo","UTF-8")+"="+URLEncoder.encode(codigo,"UTF-8");

                bufferedWriter.write(post);
                bufferedWriter.flush();

                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpsURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));

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

            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.hide();
            onFinishCategoryDetailEvents(s);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(context);
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


    //Solicita información de la base de datos para mostrar grupos de materias creados
    private class ShowSubjectSuggestions extends AsyncTask<String,Void,String>{

        ProgressDialog pd;
        Context context;

        ShowSubjectSuggestions(Context ctx){context = ctx;}

        @Override
        protected String doInBackground(String... params) {
            String materia = params[0];
            String nrc = params[1];
            String codigo = params[2];
            String urlstring = "https://php-mrls94.c9users.io/EventoMateria.php";
            try {
                URL url = new URL(urlstring);
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                //httpsURLConnection.connect();
                httpsURLConnection.setRequestMethod("POST");
                httpsURLConnection.setDoInput(true);
                httpsURLConnection.setDoOutput(true);

                OutputStream outputStream = httpsURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post = URLEncoder.encode("materia","UTF-8")+"="+URLEncoder.encode(materia,"UTF-8") + "&" +
                        URLEncoder.encode("nrc","UTF-8") + "=" + URLEncoder.encode(nrc,"UTF-8") + "&" +
                        URLEncoder.encode("codigo","UTF-8") + "=" + URLEncoder.encode(codigo,"UTF-8");

                bufferedWriter.write(post);
                bufferedWriter.flush();

                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpsURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));

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

            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.hide();
            onFinishShowSubjectEvents(s);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(context);
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

    //Solicita información de la base de datos para mostrar grupos de sugerencias
    private class ShowSuggestions extends AsyncTask<String,Void,String>{

        ProgressDialog pd;
        Context context;

        ShowSuggestions(Context ctx){context = ctx;}

        @Override
        protected String doInBackground(String... params) {
            String Studentid = params[0];
            String urlstring = "https://php-mrls94.c9users.io/SugerenciasGrupos.php";
            try {
                URL url = new URL(urlstring);
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                //httpsURLConnection.connect();
                httpsURLConnection.setRequestMethod("POST");
                httpsURLConnection.setDoInput(true);
                httpsURLConnection.setDoOutput(true);

                OutputStream outputStream = httpsURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post = URLEncoder.encode("Studentid","UTF-8")+"="+URLEncoder.encode(Studentid,"UTF-8");

                bufferedWriter.write(post);
                bufferedWriter.flush();

                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpsURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));

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

            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.hide();
            onFinishShowSuggestions(s);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(context);
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

    //Solicita información de la base de datos para mostrar eventos de sugerencias
    private class ShowSuggestionsInteres extends AsyncTask<String,Void,String>{

        ProgressDialog pd;
        Context context;

        ShowSuggestionsInteres(Context ctx){context = ctx;}

        @Override
        protected String doInBackground(String... params) {
            String Studentid = params[0];
            String urlstring = "https://php-mrls94.c9users.io/SugerenciasEventos.php";
            try {
                URL url = new URL(urlstring);
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                //httpsURLConnection.connect();
                httpsURLConnection.setRequestMethod("POST");
                httpsURLConnection.setDoInput(true);
                httpsURLConnection.setDoOutput(true);

                OutputStream outputStream = httpsURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post = URLEncoder.encode("Studentid","UTF-8")+"="+URLEncoder.encode(Studentid,"UTF-8");

                bufferedWriter.write(post);
                bufferedWriter.flush();

                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpsURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));

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

            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.hide();
            onFinishShowSuggestionsInteres(s);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(context);
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

    //Solicita información de la base de datos para mostrar grupos de sugerencias
    private class ShowAddInteresLikes extends AsyncTask<String,Void,String>{

        ProgressDialog pd;
        Context context;

        ShowAddInteresLikes(Context ctx){context = ctx;}

        @Override
        protected String doInBackground(String... params) {
            String codigo = params[0];
            String idInteres = params[1];
            String urlstring = "https://php-mrls94.c9users.io/AddLikes.php";
            try {
                URL url = new URL(urlstring);
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                //httpsURLConnection.connect();
                httpsURLConnection.setRequestMethod("POST");
                httpsURLConnection.setDoInput(true);
                httpsURLConnection.setDoOutput(true);

                OutputStream outputStream = httpsURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post = URLEncoder.encode("codigo","UTF-8")+"="+URLEncoder.encode(codigo,"UTF-8")+"&"+
                        URLEncoder.encode("idInteres","UTF-8")+"="+ URLEncoder.encode(idInteres,"UTF-8");

                bufferedWriter.write(post);
                bufferedWriter.flush();

                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpsURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));

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

            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.hide();
            onFinishShowAddInteresLikes(s);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(context);
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

    //Solicitud para ingresar información a la base de datos para crear grupo de estudios
    private class EventsCreation extends AsyncTask<String,Void,String>{

        ProgressDialog pd;
        Context context;

        EventsCreation(Context ctx){context = ctx;}

        @Override
        protected String doInBackground(String... params) {
            String minStud = params[0];
            String maxStud = params[1];
            String fecha = params[2];
            String time = params[3];
            String lugar = params[4];
            String materiaEs = params[5];
            String codigo = params[6];
            String descripcion = params[7];
            String endtime = params[8];
            String idSub = params[9];
            String urlstring = "https://php-mrls94.c9users.io/CrearEvento.php";
            try {
                URL url = new URL(urlstring);
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                //httpsURLConnection.connect();
                httpsURLConnection.setRequestMethod("POST");
                httpsURLConnection.setDoInput(true);
                httpsURLConnection.setDoOutput(true);

                OutputStream outputStream = httpsURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post = URLEncoder.encode("minStud","UTF-8")+"="
                        +URLEncoder.encode(minStud,"UTF-8")+"&"+URLEncoder.encode("maxStud","UTF-8")+"="
                        +URLEncoder.encode(maxStud,"UTF-8")+"&"+URLEncoder.encode("fecha","UTF-8")+"="
                        +URLEncoder.encode(fecha,"UTF-8")+"&"+URLEncoder.encode("time","UTF-8")+"="
                        +URLEncoder.encode(time,"UTF-8")+"&"+URLEncoder.encode("lugar","UTF-8")+"="
                        +URLEncoder.encode(lugar,"UTF-8")+"&"+URLEncoder.encode("materiaEs","UTF-8")+"="
                        +URLEncoder.encode(materiaEs,"UTF-8")+"&"+URLEncoder.encode("codigo","UTF-8")+"="
                        +URLEncoder.encode(codigo,"UTF-8")+"&"+URLEncoder.encode("descripcion","UTF-8")+"="
                        +URLEncoder.encode(descripcion,"UTF-8")+"&"+URLEncoder.encode("endtime","UTF-8")+"="
                        +URLEncoder.encode(endtime,"UTF-8")+"&"+URLEncoder.encode("nrc","UTF-8")+"="
                        +URLEncoder.encode(idSub,"UTF-8");

                bufferedWriter.write(post);
                bufferedWriter.flush();

                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpsURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));

                String result = "";
                String line = "";

                while((line = bufferedReader.readLine())!=null)
                {
                    result+=line;
                }

                System.out.println(result);

                bufferedReader.close();
                inputStream.close();

                httpsURLConnection.disconnect();

                return result;

            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.hide();
            onFinishEventsCreation(s);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(context);
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

    //Crea el evento de interes
    private class EventsCreationCategory extends AsyncTask<String,Void,String>{

        ProgressDialog pd;
        Context context;

        EventsCreationCategory(Context ctx){context = ctx;}

        @Override
        protected String doInBackground(String... params) {
            String minStud = params[0];
            String maxStud = params[1];
            String fecha = params[2];
            String time = params[3];
            String lugar = params[4];
            String categoriaEs = params[5];
            String interesEs = params[6];
            String codigo = params[7];
            String descripcion = params[8];
            String endtime = params[9];
            String urlstring = "https://php-mrls94.c9users.io/CrearInteresEvento.php";
            try {
                URL url = new URL(urlstring);
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                //httpsURLConnection.connect();
                httpsURLConnection.setRequestMethod("POST");
                httpsURLConnection.setDoInput(true);
                httpsURLConnection.setDoOutput(true);

                OutputStream outputStream = httpsURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post = URLEncoder.encode("minStud","UTF-8")+"="
                        +URLEncoder.encode(minStud,"UTF-8")+"&"+URLEncoder.encode("maxStud","UTF-8")+"="
                        +URLEncoder.encode(maxStud,"UTF-8")+"&"+URLEncoder.encode("fecha","UTF-8")+"="
                        +URLEncoder.encode(fecha,"UTF-8")+"&"+URLEncoder.encode("time","UTF-8")+"="
                        +URLEncoder.encode(time,"UTF-8")+"&"+URLEncoder.encode("lugar","UTF-8")+"="
                        +URLEncoder.encode(lugar,"UTF-8")+"&"+URLEncoder.encode("categoriaEs","UTF-8")+"="
                        +URLEncoder.encode(categoriaEs,"UTF-8")+"&"+URLEncoder.encode("interesEs","UTF-8")+"="
                        +URLEncoder.encode(interesEs,"UTF-8")+"&"+URLEncoder.encode("codigo","UTF-8")+"="
                        +URLEncoder.encode(codigo,"UTF-8")+"&"+URLEncoder.encode("descripcion","UTF-8")+"="
                        +URLEncoder.encode(descripcion,"UTF-8")+"&"+URLEncoder.encode("endtime","UTF-8")+"="
                        +URLEncoder.encode(endtime,"UTF-8");

                bufferedWriter.write(post);
                bufferedWriter.flush();

                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpsURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));

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

            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.hide();
            onFinishEventsCreationCategory(s);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(context);
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

    //realiza la consulta a la base de datos sobre el detalle del evento seleccionado
    private class ShowEventsDetail extends AsyncTask<String,Void,String>{

        ProgressDialog pd;
        Context context;

        ShowEventsDetail(Context ctx){context = ctx;}

        @Override
        protected String doInBackground(String... params) {
            String idevento = params[0];
            String urlstring = "https://php-mrls94.c9users.io/DescribeEvent.php";
            try {
                URL url = new URL(urlstring);
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                //httpsURLConnection.connect();
                httpsURLConnection.setRequestMethod("POST");
                httpsURLConnection.setDoInput(true);
                httpsURLConnection.setDoOutput(true);

                OutputStream outputStream = httpsURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post = URLEncoder.encode("idevento","UTF-8")+"="+URLEncoder.encode(idevento,"UTF-8");

                System.out.println(post);

                bufferedWriter.write(post);
                bufferedWriter.flush();

                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpsURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));

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

            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.hide();
            onFinishShowEventDetailSelected(s);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(context);
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

    //realiza la consulta a la base de datos sobre el detalle del evento seleccionado
    private class ShowEventsDetailInteres extends AsyncTask<String,Void,String>{

        ProgressDialog pd;
        Context context;

        ShowEventsDetailInteres(Context ctx){context = ctx;}

        @Override
        protected String doInBackground(String... params) {
            String idevento = params[0];
            String urlstring = "https://php-mrls94.c9users.io/DescribeEventoInteres.php";
            try {
                URL url = new URL(urlstring);
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                //httpsURLConnection.connect();
                httpsURLConnection.setRequestMethod("POST");
                httpsURLConnection.setDoInput(true);
                httpsURLConnection.setDoOutput(true);

                OutputStream outputStream = httpsURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post = URLEncoder.encode("idevento","UTF-8")+"="+URLEncoder.encode(idevento,"UTF-8");

                System.out.println(post);

                bufferedWriter.write(post);
                bufferedWriter.flush();

                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpsURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));

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

            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.hide();
            onFinishShowEventDetailSelected(s);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(context);
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

    //realiza la consulta a la base de datos sobre el detalle del evento seleccionado
    private class ShowEventsDetailProfileDelete extends AsyncTask<String,Void,String>{

        ProgressDialog pd;
        Context context;

        ShowEventsDetailProfileDelete(Context ctx){context = ctx;}

        @Override
        protected String doInBackground(String... params) {
            String idevento = params[0];
            String urlstring = "https://php-mrls94.c9users.io/DescribeEvent.php";
            try {
                URL url = new URL(urlstring);
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                //httpsURLConnection.connect();
                httpsURLConnection.setRequestMethod("POST");
                httpsURLConnection.setDoInput(true);
                httpsURLConnection.setDoOutput(true);

                OutputStream outputStream = httpsURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post = URLEncoder.encode("idevento","UTF-8")+"="+URLEncoder.encode(idevento,"UTF-8");

                System.out.println(post);

                bufferedWriter.write(post);
                bufferedWriter.flush();

                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpsURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));

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

            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.hide();
            onFinishShowEventsDetailProfileDelete(s);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(context);
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

    //realiza la consulta a la base de datos sobre el detalle del evento seleccionado
    private class ShowEventsDetailProfileDeleteInteres extends AsyncTask<String,Void,String>{

        ProgressDialog pd;
        Context context;

        ShowEventsDetailProfileDeleteInteres(Context ctx){context = ctx;}

        @Override
        protected String doInBackground(String... params) {
            String idevento = params[0];
            String urlstring = "https://php-mrls94.c9users.io/DescribeEventoInteres.php";
            try {
                URL url = new URL(urlstring);
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                //httpsURLConnection.connect();
                httpsURLConnection.setRequestMethod("POST");
                httpsURLConnection.setDoInput(true);
                httpsURLConnection.setDoOutput(true);

                OutputStream outputStream = httpsURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post = URLEncoder.encode("idevento","UTF-8")+"="+URLEncoder.encode(idevento,"UTF-8");

                System.out.println(post);

                bufferedWriter.write(post);
                bufferedWriter.flush();

                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpsURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));

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

            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.hide();
            onFinishShowEventsDetailProfileDelete(s);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(context);
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

    //realiza la consulta a la base de datos sobre el detalle del evento seleccionado
    private class ShowEventsDetailProfileUnsubscribe extends AsyncTask<String,Void,String>{

        ProgressDialog pd;
        Context context;

        ShowEventsDetailProfileUnsubscribe(Context ctx){context = ctx;}

        @Override
        protected String doInBackground(String... params) {
            String idevento = params[0];
            String urlstring = "https://php-mrls94.c9users.io/DescribeEvent.php";
            try {
                URL url = new URL(urlstring);
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                //httpsURLConnection.connect();
                httpsURLConnection.setRequestMethod("POST");
                httpsURLConnection.setDoInput(true);
                httpsURLConnection.setDoOutput(true);

                OutputStream outputStream = httpsURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post = URLEncoder.encode("idevento","UTF-8")+"="+URLEncoder.encode(idevento,"UTF-8");

                System.out.println(post);

                bufferedWriter.write(post);
                bufferedWriter.flush();

                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpsURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));

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

            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.hide();
            onFinishShowEventsDetailProfileUnsubscribe(s);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(context);
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

    //realiza la consulta a la base de datos sobre el detalle del evento seleccionado
    private class ShowEventsDetailProfileInteresSubscribe extends AsyncTask<String,Void,String>{

        ProgressDialog pd;
        Context context;

        ShowEventsDetailProfileInteresSubscribe(Context ctx){context = ctx;}

        @Override
        protected String doInBackground(String... params) {
            String idevento = params[0];
            String urlstring = "https://php-mrls94.c9users.io/DescribeEventoInteres.php";
            try {
                URL url = new URL(urlstring);
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                //httpsURLConnection.connect();
                httpsURLConnection.setRequestMethod("POST");
                httpsURLConnection.setDoInput(true);
                httpsURLConnection.setDoOutput(true);

                OutputStream outputStream = httpsURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post = URLEncoder.encode("idevento","UTF-8")+"="+URLEncoder.encode(idevento,"UTF-8");

                System.out.println(post);

                bufferedWriter.write(post);
                bufferedWriter.flush();

                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpsURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));

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

            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.hide();
            onFinishShowEventsDetailProfileInteresSubscribe(s);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(context);
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

    //realiza la consulta a la base de datos sobre los materias creadas por el usuario
    private class ShowProfileOwnSubjectEventsCreate extends AsyncTask<String,Void,String>{

        ProgressDialog pd;
        Context context;

        ShowProfileOwnSubjectEventsCreate(Context ctx){context = ctx;}

        @Override
        protected String doInBackground(String... params) {
            String codigo = params[0];
            String urlstring = "https://php-mrls94.c9users.io/GrupoCreadoPorPersona.php";
            try {
                URL url = new URL(urlstring);
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                //httpsURLConnection.connect();
                httpsURLConnection.setRequestMethod("POST");
                httpsURLConnection.setDoInput(true);
                httpsURLConnection.setDoOutput(true);

                OutputStream outputStream = httpsURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post = URLEncoder.encode("codigo","UTF-8")+"="+URLEncoder.encode(codigo,"UTF-8");

                bufferedWriter.write(post);
                bufferedWriter.flush();

                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpsURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));

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

            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.hide();
            onFinishProfileOwnSubjectEventsCreate(s);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(context);
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

    //realiza la consulta a la base de datos sobre las intereses en las que esta subscrito el usuario
    private class ShowProfileSubjectEventsSubscribe extends AsyncTask<String,Void,String>{

        ProgressDialog pd;
        Context context;

        ShowProfileSubjectEventsSubscribe(Context ctx){context = ctx;}

        @Override
        protected String doInBackground(String... params) {
            String codigo = params[0];
            String urlstring = "https://php-mrls94.c9users.io/InscritosGrupos.php";
            try {
                URL url = new URL(urlstring);
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                //httpsURLConnection.connect();
                httpsURLConnection.setRequestMethod("POST");
                httpsURLConnection.setDoInput(true);
                httpsURLConnection.setDoOutput(true);

                OutputStream outputStream = httpsURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post = URLEncoder.encode("codigo","UTF-8")+"="+URLEncoder.encode(codigo,"UTF-8");

                bufferedWriter.write(post);
                bufferedWriter.flush();

                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpsURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));

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

            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.hide();
            onFinishProfileOwnSubjectEventsSubscribe(s);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(context);
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

    //realiza la consulta a la base de datos sobre las intereses en las que esta subscrito el usuario
    private class ShowProfileInteresEventsSubscribe extends AsyncTask<String,Void,String>{

        ProgressDialog pd;
        Context context;

        ShowProfileInteresEventsSubscribe(Context ctx){context = ctx;}

        @Override
        protected String doInBackground(String... params) {
            String codigo = params[0];
            String urlstring = "https://php-mrls94.c9users.io/InscritosEventos.php";
            try {
                URL url = new URL(urlstring);
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                //httpsURLConnection.connect();
                httpsURLConnection.setRequestMethod("POST");
                httpsURLConnection.setDoInput(true);
                httpsURLConnection.setDoOutput(true);

                OutputStream outputStream = httpsURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post = URLEncoder.encode("codigo","UTF-8")+"="+URLEncoder.encode(codigo,"UTF-8");

                bufferedWriter.write(post);
                bufferedWriter.flush();

                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpsURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));

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

            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.hide();
            onFinishProfileInteresEventsSubscribe(s);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(context);
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

    //realiza la consulta a la base de datos sobre las materias en las que esta subscrito el usuario
    private class ShowDetailStudentInformation extends AsyncTask<String,Void,String>{

        ProgressDialog pd;
        Context context;

        ShowDetailStudentInformation(Context ctx){context = ctx;}

        @Override
        protected String doInBackground(String... params) {
            String idGroup = params[0];
            String urlstring = "https://php-mrls94.c9users.io/StudentsInGrupo.php";
            try {
                URL url = new URL(urlstring);
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                //httpsURLConnection.connect();
                httpsURLConnection.setRequestMethod("POST");
                httpsURLConnection.setDoInput(true);
                httpsURLConnection.setDoOutput(true);

                OutputStream outputStream = httpsURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post = URLEncoder.encode("idGroup","UTF-8")+"="+URLEncoder.encode(idGroup,"UTF-8");

                bufferedWriter.write(post);
                bufferedWriter.flush();

                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpsURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));

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

            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.hide();
            onFinishShowDetailStudentInformation(s);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(context);
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

    //realiza la consulta a la base de datos sobre las materias en las que esta subscrito el usuario
    private class ShowDetailStudentInformationInteres extends AsyncTask<String,Void,String>{

        ProgressDialog pd;
        Context context;

        ShowDetailStudentInformationInteres(Context ctx){context = ctx;}

        @Override
        protected String doInBackground(String... params) {
            String idGroup = params[0];
            String urlstring = "https://php-mrls94.c9users.io/StudentsInEvento.php";
            try {
                URL url = new URL(urlstring);
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                //httpsURLConnection.connect();
                httpsURLConnection.setRequestMethod("POST");
                httpsURLConnection.setDoInput(true);
                httpsURLConnection.setDoOutput(true);

                OutputStream outputStream = httpsURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post = URLEncoder.encode("idGroup","UTF-8")+"="+URLEncoder.encode(idGroup,"UTF-8");

                bufferedWriter.write(post);
                bufferedWriter.flush();

                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpsURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));

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

            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.hide();
            onFinishShowDetailStudentInformation(s);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(context);
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

    //realiza la consulta a la base de datos sobre el detalle del evento seleccionado
    private class ShowProfileOwnInteresEventsCreate extends AsyncTask<String,Void,String>{

        ProgressDialog pd;
        Context context;

        ShowProfileOwnInteresEventsCreate(Context ctx){context = ctx;}

        @Override
        protected String doInBackground(String... params) {
            String codigo = params[0];
            String urlstring = "https://php-mrls94.c9users.io/InteresCreadosPorPersona.php";
            try {
                URL url = new URL(urlstring);
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                //httpsURLConnection.connect();
                httpsURLConnection.setRequestMethod("POST");
                httpsURLConnection.setDoInput(true);
                httpsURLConnection.setDoOutput(true);

                OutputStream outputStream = httpsURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post = URLEncoder.encode("codigo","UTF-8")+"="+URLEncoder.encode(codigo,"UTF-8");

                bufferedWriter.write(post);
                bufferedWriter.flush();

                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpsURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));

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

            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.hide();
            onFinishProfileOwnInteresEventsCreate(s);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(context);
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

    //realiza la consulta a la inscripción del evento de materia
    private class ShowSubscribeDetailEventSubject extends AsyncTask<String,Void,String>{

        ProgressDialog pd;
        Context context;

        ShowSubscribeDetailEventSubject (Context ctx){context = ctx;}

        @Override
        protected String doInBackground(String... params) {
            String codigo = params[0];
            String idevento = params[1];
            String urlstring = "https://php-mrls94.c9users.io/SubscribeEvent.php";
            try {
                URL url = new URL(urlstring);
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                //httpsURLConnection.connect();
                httpsURLConnection.setRequestMethod("POST");
                httpsURLConnection.setDoInput(true);
                httpsURLConnection.setDoOutput(true);

                OutputStream outputStream = httpsURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post = URLEncoder.encode("codigo","UTF-8")+"="+URLEncoder.encode(codigo,"UTF-8")+"&"+
                                URLEncoder.encode("idevento","UTF-8")+"="+URLEncoder.encode(idevento,"UTF-8");

                bufferedWriter.write(post);
                bufferedWriter.flush();

                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpsURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));

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

            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.hide();
            onFinishSubscribeDetailEventSubject(s);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(context);
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

    //realiza la consulta a la inscripción del evento de interes
    private class ShowSubscribeDetailEventInteres extends AsyncTask<String,Void,String>{

        ProgressDialog pd;
        Context context;

        ShowSubscribeDetailEventInteres (Context ctx){context = ctx;}

        @Override
        protected String doInBackground(String... params) {
            String codigo = params[0];
            String idevento = params[1];
            String urlstring = "https://php-mrls94.c9users.io/SubscribeInteresEvent.php";
            try {
                URL url = new URL(urlstring);
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                //httpsURLConnection.connect();
                httpsURLConnection.setRequestMethod("POST");
                httpsURLConnection.setDoInput(true);
                httpsURLConnection.setDoOutput(true);

                OutputStream outputStream = httpsURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post = URLEncoder.encode("codigo","UTF-8")+"="+URLEncoder.encode(codigo,"UTF-8")+"&"+
                        URLEncoder.encode("idevento","UTF-8")+"="+URLEncoder.encode(idevento,"UTF-8");

                bufferedWriter.write(post);
                bufferedWriter.flush();

                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpsURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));

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

            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.hide();
            onFinishSubscribeDetailEventSubject(s);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(context);
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

    //realiza eliminacion de evento creado
    private class ShowDestoyProfileDetailEvent extends AsyncTask<String,Void,String>{

        ProgressDialog pd;
        Context context;

        ShowDestoyProfileDetailEvent(Context ctx){context = ctx;}

        @Override
        protected String doInBackground(String... params) {
            String idGroup= params[0];
            String urlstring = "https://php-mrls94.c9users.io/DestruirGrupo.php";
            try {
                URL url = new URL(urlstring);
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                //httpsURLConnection.connect();
                httpsURLConnection.setRequestMethod("POST");
                httpsURLConnection.setDoInput(true);
                httpsURLConnection.setDoOutput(true);

                OutputStream outputStream = httpsURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post = URLEncoder.encode("idGroup","UTF-8")+"="+URLEncoder.encode(idGroup,"UTF-8");

                bufferedWriter.write(post);
                bufferedWriter.flush();

                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpsURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));

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

            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.hide();
            onFinishShowDestoyProfileDetailEvent(s);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(context);
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

    //realiza desuscripcion de evento inscrito de materias
    private class ShowDesinscribirseProfileEventsSubscribe extends AsyncTask<String,Void,String>{

        ProgressDialog pd;
        Context context;

        ShowDesinscribirseProfileEventsSubscribe(Context ctx){context = ctx;}

        @Override
        protected String doInBackground(String... params) {
            String idGroup= params[0];
            String codigo = params[1];
            String urlstring = "https://php-mrls94.c9users.io/DesinscribirseGrupo.php";
            try {
                URL url = new URL(urlstring);
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                //httpsURLConnection.connect();
                httpsURLConnection.setRequestMethod("POST");
                httpsURLConnection.setDoInput(true);
                httpsURLConnection.setDoOutput(true);

                OutputStream outputStream = httpsURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post =URLEncoder.encode("idGroup","UTF-8")+"="+URLEncoder.encode(idGroup,"UTF-8")+
                        "&" +URLEncoder.encode("codigo","UTF-8")+"="+URLEncoder.encode(codigo,"UTF-8");

                bufferedWriter.write(post);
                bufferedWriter.flush();

                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpsURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));

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

            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.hide();
            onFinishShowDesinscribirseProfileEventsSubscribe(s);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(context);
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

    //realiza desuscripcion de evento inscrito de interes
    private class ShowDesinscribirseProfileEventsSubscribeInteres extends AsyncTask<String,Void,String>{

        ProgressDialog pd;
        Context context;

        ShowDesinscribirseProfileEventsSubscribeInteres(Context ctx){context = ctx;}

        @Override
        protected String doInBackground(String... params) {
            String idGroup= params[0];
            String codigo = params[1];
            String urlstring = "https://php-mrls94.c9users.io/DesinscribirseEvento.php";
            try {
                URL url = new URL(urlstring);
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                //httpsURLConnection.connect();
                httpsURLConnection.setRequestMethod("POST");
                httpsURLConnection.setDoInput(true);
                httpsURLConnection.setDoOutput(true);

                OutputStream outputStream = httpsURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post =URLEncoder.encode("idGroup","UTF-8")+"="+URLEncoder.encode(idGroup,"UTF-8")+
                        "&" +URLEncoder.encode("codigo","UTF-8")+"="+URLEncoder.encode(codigo,"UTF-8");

                bufferedWriter.write(post);
                bufferedWriter.flush();

                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpsURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));

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

            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.hide();
            onFinishShowDesinscribirseProfileEventsSubscribe(s);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(context);
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

    //Consulta a la base de datos para obtener la información de categorias / intereses
    private class CategoriesConnection extends AsyncTask<String,Void,String>{

        String type;
        Context context;
        ProgressDialog pd;

        CategoriesConnection(Context ctx)
        {
            context = ctx;
        }

        @Override
        protected String doInBackground(String... params) {
            type = params[0];
            String urlstring = "https://php-mrls94.c9users.io/Categories.php";
            try {

                if(type.matches("General")){
                    URL url = new URL(urlstring);
                    HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                    //httpsURLConnection.connect();
                    httpsURLConnection.setRequestMethod("POST");
                    httpsURLConnection.setDoInput(true);
                    httpsURLConnection.setDoOutput(true);

                    OutputStream outputStream = httpsURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                    String post = URLEncoder.encode("type","UTF-8")+"="
                            +URLEncoder.encode(type,"UTF-8");

                    bufferedWriter.write(post);
                    bufferedWriter.flush();

                    bufferedWriter.close();
                    outputStream.close();

                    InputStream inputStream = httpsURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));

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
                }else{
                        String categoria = params[1];
                        URL url = new URL(urlstring);
                        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                        //httpsURLConnection.connect();
                        httpsURLConnection.setRequestMethod("POST");
                        httpsURLConnection.setDoInput(true);
                        httpsURLConnection.setDoOutput(true);

                        OutputStream outputStream = httpsURLConnection.getOutputStream();
                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                        String post = URLEncoder.encode("type","UTF-8")+"="
                                +URLEncoder.encode(type,"UTF-8")+"&"+URLEncoder.encode("category","UTF-8")+"="+
                                URLEncoder.encode(categoria,"UTF-8");

                        bufferedWriter.write(post);
                        bufferedWriter.flush();

                        bufferedWriter.close();
                        outputStream.close();

                        InputStream inputStream = httpsURLConnection.getInputStream();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));

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
                }

            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.hide();
            if(type.matches("General"))
            {
                onFinishCategory(s);
            }
            else
            {
                onFinishSpecific(s);
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(context);
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


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id == R.id.nav_profile){
            Profile profile = new Profile();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,profile).commit();
        }else if (id == R.id.nav_subject) {
            //Communicator.NrcMaterias.clear();
            MainListview mainListview = new MainListview();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,mainListview).commit();
        } else if (id == R.id.nav_interest) {
            Communicator.NrcInteres.clear();
            CategoriesConnection categoriesConnection = new CategoriesConnection(this);
            categoriesConnection.execute("General");
        } else if (id == R.id.nav_events) {
            Communicator.IDeventsSuggestionDetail.clear();
            onSuggestionBegin();
        }  else if (id == R.id.nav_coniguration) {
            Configuration configuration = new Configuration();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,configuration).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
