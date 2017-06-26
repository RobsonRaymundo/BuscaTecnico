package com.droid.ray.buscatecnico.lists;

/**
 * Created by Robson on 13/06/2017.
 */

import java.util.ArrayList;

public class Fabricante {

    public static ArrayList<String> ListaFabricantes(){
        ArrayList<String> fabricantes = new ArrayList<>();
        fabricantes.add("<Selecione o fabricante>");
        fabricantes.add("AOC");
        fabricantes.add("CCE");
        fabricantes.add("Gradiente");
        fabricantes.add("H-Buster");
        fabricantes.add("LG");
        fabricantes.add("OAC");
        fabricantes.add("Panasonic");
        fabricantes.add("Philco");
        fabricantes.add("Philips");
        fabricantes.add("Samsung");
        fabricantes.add("Sharp");
        fabricantes.add("Sony");
        fabricantes.add("Toshiba");
        fabricantes.add("Outros");
        return fabricantes;
    }
}
