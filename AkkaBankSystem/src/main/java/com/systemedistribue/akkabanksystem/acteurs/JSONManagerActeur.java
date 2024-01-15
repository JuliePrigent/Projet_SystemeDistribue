/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.systemedistribue.akkabanksystem.acteurs;

import akka.actor.AbstractActor;
import akka.actor.AbstractActor.Receive;
import com.systemedistribue.akkabanksystem.SGBD.JSONHandler;
import com.systemedistribue.akkabanksystem.messages.ClientAjout;
import com.systemedistribue.akkabanksystem.messages.UpdateSoldeClient;
import java.io.IOException;

/**
 *
 * @author julie
 */
public class JSONManagerActeur extends AbstractActor{
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(ClientAjout.class, client -> handleClientAjout(client))
                .match(UpdateSoldeClient.class, soldeClient -> handleUpdateSoldeClient(soldeClient))
                .build();
    }

    private void handleClientAjout(ClientAjout clientAjout)throws IOException {
        JSONHandler.writeClientDataToJson(clientAjout.getClientData());
    }

    private void handleUpdateSoldeClient(UpdateSoldeClient updateSoldeClient) throws IOException{
        JSONHandler.updateClientSoldeInJson(updateSoldeClient.getClientId(), updateSoldeClient.getNouveauSolde());
    }
}
