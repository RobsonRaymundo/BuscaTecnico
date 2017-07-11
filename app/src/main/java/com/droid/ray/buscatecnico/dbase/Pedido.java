package com.droid.ray.buscatecnico.dbase;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Robson on 21/06/2017.
 */

public class Pedido {
    private String id;
    private String nome;
    private String telefone;
    private String fabricante;
    private String defeito;
    private String observacao;
    private String status;
    private String data;
    private Double latitude;
    private Double longitude;
    private String tecnico_nome;
    private String tecnico_telefone;

    public Pedido() {
    }

    public void Salvar() {
        //FireBase.getFireBasePedido().push().child(String.valueOf(getTelefone())).setValue(this.toMap());
        FireBase.getFireBasePedido().child(String.valueOf(getTelefone())).push().setValue(this.toMap());
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", getId());
        hashMap.put("nome", getNome());
        hashMap.put("telefone", getTelefone());
        hashMap.put("fabricante", getFabricante());
        hashMap.put("defeito", getDefeito());
        hashMap.put("observacao", getObservacao());
        hashMap.put("status", getStatus());
        hashMap.put("data", getData());
        hashMap.put("latitude", getLatitude());
        hashMap.put("longitude", getLongitude());
        hashMap.put("tecnico_nome", getTecnico_nome());
        hashMap.put("tecnico_telefone", getTecnico_telefone());
        return hashMap;
    }

    @Override
    public String toString() {
        return telefone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getDefeito() {
        return defeito;
    }

    public void setDefeito(String defeito) {
        this.defeito = defeito;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getTecnico_nome() {
        return tecnico_nome;
    }

    public void setTecnico_nome(String tecnico_nome) {
        this.tecnico_nome = tecnico_nome;
    }

    public String getTecnico_telefone() {
        return tecnico_telefone;
    }

    public void setTecnico_telefone(String tecnico_telefone) {
        this.tecnico_telefone = tecnico_telefone;
    }
}

