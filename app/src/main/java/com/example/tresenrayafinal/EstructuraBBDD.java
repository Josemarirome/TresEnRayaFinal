package com.example.tresenrayafinal;

import android.provider.BaseColumns;

public class EstructuraBBDD {
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE IF NOT EXISTS "+ EstructuraGrupos.TABLE_NAME_DATOS +
                    "(" + EstructuraGrupos._ID + " integer PRIMARY KEY, "
                    + EstructuraGrupos.COLUMN_NAME_JUGADOR + " text, "
                    + EstructuraGrupos.COLUMN_NAME_PUNTOS + " integer, ";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS  " + EstructuraGrupos.TABLE_NAME_DATOS;

    private EstructuraBBDD() {}

    /* Clase interna que define la estructura de la tabla de cantantes */
    public static class EstructuraGrupos implements BaseColumns {
        public static final String TABLE_NAME_DATOS = "datos";
        public static final String COLUMN_NAME_JUGADOR = "jugador";
        public static final String COLUMN_NAME_PUNTOS = "puntos";
    }
}
