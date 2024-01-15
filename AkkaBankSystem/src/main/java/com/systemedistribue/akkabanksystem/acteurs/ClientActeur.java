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
import com.systemedistribue.akkabanksystem.SGBD.JSONHandler;
import com.systemedistribue.akkabanksystem.messages.AnnonceClient;
import com.systemedistribue.akkabanksystem.messages.ClientAjout;
import com.systemedistribue.akkabanksystem.messages.DemandeOperation;
import com.systemedistribue.akkabanksystem.messages.InstructionClient;
import com.systemedistribue.akkabanksystem.messages.RefBanquier;
import com.systemedistribue.akkabanksystem.messages.ReponseOperation;
import com.systemedistribue.akkabanksystem.messages.UpdateSoldeClient;

import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author julie
 */

public class ClientActeur extends AbstractActor {
    ActorRef banquier;
    private double soldeInitial;
    private boolean isOperationCompleted = true;

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
        //getContext().actorSelection("/user/secretaire").tell(new AnnonceClient(), getSelf());

        super.preStart();
    }
    
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(RefBanquier.class, banquierRenvoye -> {
                    {
                        banquier = banquierRenvoye.banquier;
                        System.out.println("Le client " + self().path().name() + "a reçu sa ref banquier et est en attente");
                    }
                    })
                .match(ReponseOperation.class, reponse -> {
                    System.out.println("");
                })
                .match(InstructionClient.class, instruction -> demandeOperation(instruction))
                .build();
    }
    public void demandeOperation(InstructionClient instruction){
        if(isOperationCompleted == true){
            isOperationCompleted = false;
            System.out.println("demandeOpe de clientActeur");
            Operation operation = instruction.getOperation();
            double montant = instruction.getMontant();
            banquier.tell(new DemandeOperation(operation, montant), getSelf());
        
        }
        else{
            demandeOperation(instruction);
        }
    }
    /*
    public void demandeOperationToUser(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Entrez l'opération pour le client :"  + self().path().name() + " un - pour un retrait et un + pour un dépot");
        String operationWritten = scanner.nextLine();
        if(operationWritten.contains("-")){
            this.operation = operation.RETIRER;
            System.out.println("- détecté");
        }
        else{
            this.operation = operation.DEPOSER;
            System.out.println("+ détecté");
        }
        System.out.println("Entrez le montant de l'operation");
        this.montantOperation = (double) scanner.nextInt();
        System.out.println("montant de : " +  montantOperation);
        
        banquier.tell(new DemandeOperation(operation, montantOperation), getSelf());
    }*/
}
