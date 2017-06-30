package com.droid.ray.buscatecnico.dbase;

import android.util.Log;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Robson on 19/06/2017.
 */

public class Usuario {
    private String id;
    private String telefone;
    private String nome;
    private String email;
    private String senha;
    private static String tipo;
    private String tela;
    private String cpfcnpj;
    private String tituloAnuncio;
    private String descricaoAnuncio;
    private String cep;



    public Usuario() {
    }

    public void Salvar() {
        FireBase.getFireBaseUsers().child(String.valueOf(getTelefone())).setValue(this.toMap());
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", getId());
        hashMap.put("telefone", getTelefone());
        hashMap.put("nome", getNome());
        hashMap.put("email", getEmail());
        hashMap.put("senha", getSenha());
        hashMap.put("tipo", getTipo());
        hashMap.put("tela", getTela());
        hashMap.put("cpfcnpj", getCpfcnpj());
        hashMap.put("tituloAnuncio", getTituloAnuncio());
        hashMap.put("descricaoAnuncio", getDescricaoAnuncio());
        hashMap.put("cep", getCep());
        return hashMap;
    }

    public static String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTela() {
        return tela;
    }

    public void setTela(String tela) {
        this.tela = tela;
    }

    public String getCpfcnpj() {
        return cpfcnpj;
    }

    public void setCpfcnpj(String cpfcnpj) {
        this.cpfcnpj = cpfcnpj;
    }

    public String getTituloAnuncio() {
        return tituloAnuncio;
    }

    public void setTituloAnuncio(String tituloAnuncio) {
        this.tituloAnuncio = tituloAnuncio;
    }

    public String getDescricaoAnuncio() {
        return descricaoAnuncio;
    }

    public void setDescricaoAnuncio(String descricaoAnuncio) {
        this.descricaoAnuncio = descricaoAnuncio;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }
}
