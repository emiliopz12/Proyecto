package com.example.horse.proyecto;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModificarUsuario extends AppCompatActivity {

    String email, contraseña, contraseña2, nombre, apellido1, apellido2, telefono, fecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_usuario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        setCurrentDateOnView();

        addListenerOnButton();

        Button cancelar = (Button) findViewById(R.id.btnCancelar);

        cancelar.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View arg0) {
                Intent intento = new Intent(getApplicationContext(), Principal.class);
                finish();
                startActivity(intento);
                Toast.makeText(getApplicationContext(), "Cancelado", Toast.LENGTH_SHORT).show();
            }

        });

        Button registro = (Button) findViewById(R.id.btnRegistrarse);

        registro.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View arg0) {

                if(required()) {
                    Intent intento = new Intent(getApplicationContext(), Principal.class);
                    finish();
                    startActivity(intento);
                    Toast.makeText(getApplicationContext(), "Usuario modificado", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Debe llenar todas las opciones", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public boolean required(){
        boolean exito = true;

        EditText ed =(EditText) findViewById(R.id.editEmail);
        email = ed.getText().toString();
        if(isEmailValid(email)){
            email = ed.getText().toString();
        }
        else {
            //Email mal digitado
        }
        ed =(EditText) findViewById(R.id.editContraseña);
        contraseña = ed.getText().toString();
        ed =(EditText) findViewById(R.id.editContraseña2);
        contraseña2 = ed.getText().toString();
        if(contraseña.equals(contraseña2)){
            //Contraseñas iguales
        }
        else {
            //Alguna mal digitada
        }
        ed =(EditText) findViewById(R.id.editNombre);
        nombre = ed.getText().toString();
        ed =(EditText) findViewById(R.id.editApellido1);
        apellido1 = ed.getText().toString();
        ed =(EditText) findViewById(R.id.editApellido2);
        apellido2 = ed.getText().toString();
        ed =(EditText) findViewById(R.id.editTelefono);
        telefono = ed.getText().toString();

        fecha = "" + (new StringBuilder().append(day).append("-").append(month + 1).append("-").append(year)
                .append(" "));

        if(email.isEmpty() || contraseña.isEmpty() || contraseña2.isEmpty() || nombre.isEmpty() ||
                apellido1.isEmpty() || apellido2.isEmpty() || telefono.isEmpty() || fecha.isEmpty()){
            exito = false;
        }

        return exito;
    }

    private Pattern pattern = Pattern.compile("(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)");
    private Matcher matcher;
    public boolean validate(final String date){

        matcher = pattern.matcher(date);

        if(matcher.matches()){

            matcher.reset();

            if(matcher.find()){

                String day = matcher.group(1);
                String month = matcher.group(2);
                int year = Integer.parseInt(matcher.group(3));

                if (day.equals("31") &&
                        (month.equals("4") || month .equals("6") || month.equals("9") ||
                                month.equals("11") || month.equals("04") || month .equals("06") ||
                                month.equals("09"))) {
                    return false; // only 1,3,5,7,8,10,12 has 31 days
                } else if (month.equals("2") || month.equals("02")) {
                    //leap year
                    if(year % 4==0){
                        if(day.equals("30") || day.equals("31")){
                            return false;
                        }else{
                            return true;
                        }
                    }else{
                        if(day.equals("29")||day.equals("30")||day.equals("31")){
                            return false;
                        }else{
                            return true;
                        }
                    }
                }else{
                    return true;
                }
            }else{
                return false;
            }
        }else{
            return false;
        }
    }


    /////////////
    private TextView tvDisplayDate;
    //private DatePicker dpResult;
    private int year;
    private int month;
    private int day;
    private Button btnChangeDate;
    static final int DATE_DIALOG_ID = 999;


    // display current date
    public void setCurrentDateOnView() {

        tvDisplayDate = (TextView) findViewById(R.id.editNacimiento);
        //dpResult = (DatePicker) findViewById(R.id.nacimiento);

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        tvDisplayDate.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(day).append("-").append(month + 1).append("-")
                .append(year).append(" "));

    }

    public void addListenerOnButton() {

        btnChangeDate = (Button) findViewById(R.id.btnNacimiento);

        btnChangeDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                showDialog(DATE_DIALOG_ID);

            }

        });

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                // set date picker as current date
                return new DatePickerDialog(this, datePickerListener,
                        year, month,day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            tvDisplayDate.setText(new StringBuilder()
                    // Month is 0 based, just add 1
                    .append(day).append("-").append(month + 1).append("-")
                    .append(year).append(" "));

        }
    };

}
