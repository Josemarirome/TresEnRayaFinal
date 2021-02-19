package com.example.tresenrayafinal;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Jugar extends Activity {

    Button mute;
    MediaPlayer media;
    int puntuacion_aspas;
    int puntuacion_circulos;
    String usuario;
    TextView user;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jugar);

        //Inicializamos el array con cada casilla del tablero
        CASILLAS= new int[9];
        CASILLAS[0]=R.id.a1;
        CASILLAS[1]=R.id.a2;
        CASILLAS[2]=R.id.a3;
        CASILLAS[3]=R.id.b1;
        CASILLAS[4]=R.id.b2;
        CASILLAS[5]=R.id.b3;
        CASILLAS[6]=R.id.c1;
        CASILLAS[7]=R.id.c2;
        CASILLAS[8]=R.id.c3;
        mute = findViewById(R.id.btMute);
        MediaPlayer media;
        user = findViewById(R.id.txUsuario);
        MainActivity main = new MainActivity();
        usuario = main.getnUsuario();
        user.setText(usuario);
    }

    public void mute (View view){
        AudioManager   mAudioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        int current_volume =mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        //If you want to player is mute ,then set_volume variable is zero.Otherwise you may supply some value.
        int set_volume=0;
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC,set_volume, 0);
    }

    public void Jugar(View v){

        //reseteamos el tablero
        ImageView imagen;

        for (int casilla:CASILLAS){
            imagen=(ImageView)findViewById(casilla);

            imagen.setImageResource(R.drawable.casilla);
        }

        //establecemos los jugadores que van a jugar (1 o 2 jugadores)
        jugadores=1;
        //el metodo Jugar será llamado tanto en el botón de un jugador como en el de dos
        //por eso comprobamos la vista que entra como parámetro
        if(v.getId()==R.id.dosjugadores){
            jugadores=2;
        }

        //evaluamos la dificultad
        RadioGroup configDificultad=(RadioGroup)findViewById(R.id.grupoDificultad);

        int id=configDificultad.getCheckedRadioButtonId();

        int dificultad=0;

        if(id==R.id.normal){
            dificultad=1;
        }else if(id==R.id.imposible){
            dificultad=2;
        }

        partida=new Partida(dificultad);

        //deshabilitamos los botones del tablero
        ((Button)findViewById(R.id.unjugador)).setEnabled(false);
        ((Button)findViewById(R.id.dosjugadores)).setEnabled(false);
        ((RadioGroup)findViewById(R.id.grupoDificultad)).setAlpha(0);

    }

    //creamos el método que se lanza al pulsar cada casilla
    public void toqueCasilla(View v){
        media = MediaPlayer.create(this, R.raw.dandy1);
        media.start();
        //hacemos que sólo se ejecute cuando la variable partida no sea null
        if(partida==null){
            return;
        }else{
            int casilla=0;
            //recorremos el array donde tenemos almacenada cada casilla
            for(int i=0;i<9;i++){
                if(CASILLAS[i]==v.getId()){
                    casilla=i;
                    break;
                }
            }

            //creamos un mensaje toast
           /* Toast mensaje= Toast.makeText(this,"has pulsado la casilla "+ casilla,Toast.LENGTH_LONG);
            mensaje.setGravity(Gravity.CENTER,0,0);
            mensaje.show();*/

            //si la casilla pulsada ya está ocupada salimos del método
            if(partida.casilla_libre(casilla)==false){
                return;
            }
            //llamamos al método para marcar la casilla que se ha tocado
            marcaCasilla(casilla);

            int resultado=partida.turno();

            if(resultado>0){
                terminar_partida(resultado);
                return;
            }

            //realizamos el marcado de la casilla que elige el programa
            casilla=partida.ia();

            while (partida.casilla_libre(casilla)!=true){
                casilla=partida.ia();
            }

            marcaCasilla(casilla);

            resultado=partida.turno();

            if(resultado>0){
                terminar_partida(resultado);
            }

        }
    }

    private void terminar_partida(int res){

        String mensaje;

        if(res==1){
            mensaje="Han ganado los círculos";
            MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.dandy);
            ContentValues values = new ContentValues();
            values.put("nombre", "Circulos");
            values.put("puntos", puntuacion_circulos=+1);

            mediaPlayer.start();
        } else if(res==2){
            mensaje="Han ganado las aspas";
            MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.dandy);
            ContentValues values = new ContentValues();
            values.put("nombre", "Aspas");
            values.put("puntos", puntuacion_aspas=+1);
            mediaPlayer.start();
        }else{
            mensaje="Empate";
        }





        Toast toast= Toast.makeText(this,mensaje,Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();

        //terminamos el juego
        partida=null;

        //habilitamos los botones del tablero
        ((Button)findViewById(R.id.unjugador)).setEnabled(true);
        ((Button)findViewById(R.id.dosjugadores)).setEnabled(true);
        ((RadioGroup)findViewById(R.id.grupoDificultad)).setAlpha(1);

    }

    //metodo para marcar las casillas
    private void marcaCasilla(int casilla){
        ImageView imagen;
        imagen=(ImageView)findViewById(CASILLAS[casilla]);

        if(partida.jugador==1){
            imagen.setImageResource(R.drawable.circulo);
        }else{
            imagen.setImageResource(R.drawable.aspa);
        }

    }

    //creamos un campo de clase para almacenar cuantos jugadores hay
    private int jugadores;
    //para guardar la casilla pulsada
    private int[] CASILLAS;

    private Partida partida;


}