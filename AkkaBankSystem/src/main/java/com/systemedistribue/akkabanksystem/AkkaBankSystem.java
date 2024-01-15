/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.systemedistribue.akkabanksystem;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.systemedistribue.akkabanksystem.SGBD.JSONHandler;
import com.systemedistribue.akkabanksystem.acteurs.BanquierActeur;
import com.systemedistribue.akkabanksystem.acteurs.ClientActeur;
import com.systemedistribue.akkabanksystem.acteurs.JSONManagerActeur;
import com.systemedistribue.akkabanksystem.acteurs.SecretaireActeur;
import com.systemedistribue.akkabanksystem.messages.AnnonceClient;
import com.systemedistribue.akkabanksystem.messages.DemandeOperation;
import com.systemedistribue.akkabanksystem.messages.InstructionClient;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author julie
 */
public class AkkaBankSystem {

    public static void main(String[] args) {
                
        //Initialiser le fichier JSON qui assure la persistance des données clients
        try {
            JSONHandler.initializeJsonFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Créer le système d'acteur
        ActorSystem system = ActorSystem.create("BanqueSystem");
        
        ActorRef jsonManager = system.actorOf(Props.create(JSONManagerActeur.class), "jsonManager");

        // Créer un banquier avec une liste de clients
        List<ActorRef> clientsBanquier = new ArrayList<>();

        System.out.println("Création du client...");

        ActorRef client = system.actorOf(ClientActeur.props(100.00), "client");
        clientsBanquier.add(client); // Ajouter le client à la liste

        System.out.println("Création du banquier...");
        ActorRef banquier = system.actorOf(Props.create(BanquierActeur.class, clientsBanquier), "banquier");
        
        // Créer la secrétaire avec la liste manuelle de clients-banquiers
        System.out.println("Création de la secrétaire...");
        Map<ActorRef, ActorRef> clientsBanquiers = new HashMap<>();
        clientsBanquiers.put(client, banquier);

        ActorRef secretaire = system.actorOf(Props.create(SecretaireActeur.class, clientsBanquiers), "secretaire");

        // Simuler une demande de transaction depuis le client
        System.out.println("Simulation d'une demande de transaction depuis le client...");
        //Le client s'annonce
        secretaire.tell(new AnnonceClient(), client);

        //le client demande une operation à son banquier 
        client.tell(new InstructionClient(Operation.DEPOSER, 10000.00), ActorRef.noSender());
  
         
        //client.tell(new InstructionClient(Operation.DEPOSER, 10000.00), ActorRef.noSender());

    }
}