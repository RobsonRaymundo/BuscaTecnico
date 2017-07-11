package com.droid.ray.buscatecnico.lists;

import java.util.HashMap;

public class HashMapGen extends HashMap<String, String> {

    public static final String ID = "id";
    public static final String NOME = "nome";
    public static final String TELEFONE = "telefone";
    public static final String FABRICANTE = "fabricante";
    public static final String DATA = "data";
    public static final String DEFEITO = "defeito";
    public static final String OBSERVACAO = "observacao";
    public static final String STATUS = "status";
    public static final String TECNICO_NOME = "tecnico_nome";
    public static final String TECNICO_TELEFONE = "tecnico_telefone";

    @Override
    public String toString() {
        return get(TELEFONE);
    }
}
