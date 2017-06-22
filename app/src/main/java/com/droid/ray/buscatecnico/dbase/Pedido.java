package com.droid.ray.buscatecnico.dbase;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Robson on 21/06/2017.
 */

public class Pedido {
    private String id;
    private String telefone;
    private String fabricante;
    private String defeito;
    private String observacao;
    private String status;

    public Pedido() {
    }

    public void Salvar() {
        FireBase.getFireBasePedido().push().child(String.valueOf(getTelefone())).setValue(this.toMap());
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", getId());
        hashMap.put("telefone", getTelefone());
        hashMap.put("fabricante", getFabricante());
        hashMap.put("defeito", getDefeito());
        hashMap.put("observacao", getObservacao());
        hashMap.put("status", getStatus());
        return hashMap;
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
}

