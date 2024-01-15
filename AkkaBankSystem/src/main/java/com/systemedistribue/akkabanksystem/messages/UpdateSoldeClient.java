/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.systemedistribue.akkabanksystem.messages;

/**
 *
 * @author julie
 */
public class UpdateSoldeClient {
    private final String clientId;
    private final double nouveauSolde;

    public UpdateSoldeClient(String clientId, double nouveauSolde) {
        this.clientId = clientId;
        this.nouveauSolde = nouveauSolde;
    }

    public String getClientId() {
        return clientId;
    }

    public double getNouveauSolde() {
        return nouveauSolde;
    }
}
