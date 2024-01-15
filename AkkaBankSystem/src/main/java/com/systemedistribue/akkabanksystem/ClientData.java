/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.systemedistribue.akkabanksystem;

/**
 *
 * @author julie
 */
public class ClientData {
    private String clientId;
    private double solde;

    public ClientData(String clientId, double solde) {
        this.clientId = clientId;
        this.solde = solde;
    }

    public String getClientId() {
        return clientId;
    }

    public double getSolde() {
        return solde;
    }
}
