package com.droid.ray.buscatecnico.lists;

import java.util.ArrayList;

/**
 * Created by Robson on 13/06/2017.
 */

public class TipoProblema {
    public static ArrayList<String> ListarTipoProblema(){
        ArrayList<String> tiposProblemas = new ArrayList<>();
        tiposProblemas.add("<Selecione o problema>");
        tiposProblemas.add("Não liga");
        tiposProblemas.add("3D não funciona");
        tiposProblemas.add("Imagem não aparece, só sai som");
        tiposProblemas.add("Som não funciona");
        tiposProblemas.add("Tela manchada");
        tiposProblemas.add("Tela quebrada");
        tiposProblemas.add("Wifi não funciona");
        tiposProblemas.add("Outros");
        return tiposProblemas;
    }
}
