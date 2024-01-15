/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.systemedistribue.akkabanksystem.messages;

import com.systemedistribue.akkabanksystem.ClientData;

/**
 *
 * @author julie
 */
public class ClientAjout {
    private final ClientData clientData;

    public ClientAjout(ClientData clientData) {
        this.clientData = clientData;
    }

    public ClientData getClientData() {
        return clientData;
    }
}
