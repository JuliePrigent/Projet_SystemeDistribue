/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.systemedistribue.akkabanksystem.acteurs;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import com.systemedistribue.akkabanksystem.ClientData;
import com.systemedistribue.akkabanksystem.Operation;
import com.systemedistribue.akkabanksystem.messages.AnnonceClient;
import com.systemedistribue.akkabanksystem.messages.ClientAjout;
import com.systemedistribue.akkabanksystem.messages.DemandeOperation;
import com.systemedistribue.akkabanksystem.messages.InstructionClient;
import com.systemedistribue.akkabanksystem.messages.RefBanquier;
import com.systemedistribue.akkabanksystem.messages.ReponseOperation;

/**
 *
 * @author julie
 */

public class ClientActeur extends AbstractActor {
    ActorRef banquier;
    private double soldeInitial;

    // Constructeur pour créer l'acteur avec le solde initial
    public ClientActeur(double soldeInitial) {
        this.soldeInitial = soldeInitial;
    }

    // Méthode pour créer Props avec le solde initial
    public static Props props(double soldeInitial) {
        return Props.create(ClientActeur.class, soldeInitial);
    }
    
    @Override
    public void preStart() throws Exception {
        
        // Envoyer un message au JSONManagerActeur pour signaler l'ajout du client
        String clientId = self().path().name();
        ClientData clientData = new ClientData(clientId, soldeInitial);
        getContext().actorSelection("/user/jsonManager").tell(new ClientAjout(clientData), getSelf());

        super.preStart();
    }
    
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(RefBanquier.class, banquierRenvoye -> {
                    {
                        banquier = banquierRenvoye.banquier;
                    }
                    })
                .match(ReponseOperation.class, reponse -> {
                    System.out.println("");
                })
                .match(InstructionClient.class, instruction -> demandeOperation(instruction))
                .build();
    }
    public void demandeOperation(InstructionClient instruction){
            Operation operation = instruction.getOperation();
            double montant = instruction.getMontant();
            banquier.tell(new DemandeOperation(operation, montant), getSelf());
    }
}
