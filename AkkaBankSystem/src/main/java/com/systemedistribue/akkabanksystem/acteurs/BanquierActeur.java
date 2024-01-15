/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.systemedistribue.akkabanksystem.acteurs;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import com.systemedistribue.akkabanksystem.Operation;
import com.systemedistribue.akkabanksystem.SGBD.JSONHandler;
import com.systemedistribue.akkabanksystem.messages.DemandeOperation;
import com.systemedistribue.akkabanksystem.messages.ReponseOperation;
import com.systemedistribue.akkabanksystem.messages.UpdateSoldeClient;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author julie
 */

public class BanquierActeur extends AbstractActor {
    private final List<ActorRef> clients;

    public BanquierActeur(List<ActorRef> clients) {
        this.clients = new ArrayList<>(clients);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(DemandeOperation.class, demande -> traiterOperation(demande))
                .build();
    }
     private void traiterOperation(DemandeOperation demande) throws IOException{
        
        // Vérifier si le client existe dans la liste du banquier
        if (!clients.contains(getSender())) {
            System.out.println("Je n'ai pas ce compte client dans ma liste");
        }
        // Traiter la demande du client
        double solde = JSONHandler.getSoldeFromJson(getSender().path().name());
        
        System.out.println("Client et solde avant l'opération : " + solde);

        // Mettre à jour le solde du client
        if (demande.operation == Operation.RETIRER && solde >= demande.montant) {
            solde -= demande.montant;
        } else if (demande.operation == Operation.RETIRER && demande.montant > solde) {
            double manque = demande.montant - solde;
            System.out.println("La somme demandée est trop élevée, il manque : " + manque);
        } else if (demande.operation == Operation.DEPOSER) {
            solde += demande.montant;
        }
        getContext().actorSelection("/user/jsonManager").tell(new UpdateSoldeClient(getSender().path().name(), solde), getSelf());

        // Répondre au client
        getSender().tell(new ReponseOperation(demande.operation, demande.montant, solde), getSelf());
        System.out.println("Solde après l'opération : " + solde);
    }
}
