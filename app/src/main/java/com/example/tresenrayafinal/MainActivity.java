package com.example.tresenrayafinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btCambiarVentana;
    String nUsuario;

    public String getnUsuario() {
        return nUsuario;
    }

    EditText txtUsuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btCambiarVentana = findViewById(R.id.btVentanaJugar);
        txtUsuario = findViewById(R.id.etUsuario);

    }

    public void ventanaJugar(View view){
        nUsuario = String.valueOf(txtUsuario.getText());
        if(nUsuario.equals("")){
            Toast.makeText(getApplicationContext(), "Debe rellenar el campo", Toast.LENGTH_SHORT).show();
        }else{
            Intent i = new Intent(this, Jugar.class);
            startActivity(i);
        }

    }
}