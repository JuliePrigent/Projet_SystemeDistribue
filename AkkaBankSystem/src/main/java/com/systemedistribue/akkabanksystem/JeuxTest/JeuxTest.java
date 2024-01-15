/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.systemedistribue.akkabanksystem.JeuxTest;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.systemedistribue.akkabanksystem.Operation;
import com.systemedistribue.akkabanksystem.acteurs.BanquierActeur;
import com.systemedistribue.akkabanksystem.acteurs.ClientActeur;
import com.systemedistribue.akkabanksystem.acteurs.JSONManagerActeur;
import com.systemedistribue.akkabanksystem.acteurs.SecretaireActeur;
import com.systemedistribue.akkabanksystem.messages.AnnonceClient;
import com.systemedistribue.akkabanksystem.messages.InstructionClient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author julie
 */
public class JeuxTest {
    public JeuxTest(){}
    
    public void cas1(){
        System.out.println("-------- CAS 1 : 1 banquier, 1 client, pour une opération d'ajout de 1000€ --------");
        // Créer le système d'acteur
        ActorSystem system = ActorSystem.create("BanqueSystem");
        
        ActorRef jsonManager = system.actorOf(Props.create(JSONManagerActeur.class), "jsonManager");

        // Créer un banquier avec une liste de clients
        List<ActorRef> clientsBanquier = new ArrayList<>();

        System.out.println("Création du client...");

        ActorRef client = system.actorOf(ClientActeur.props(100.00), "client1");
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
        try{
            Thread.sleep(1000);
        }
        catch(Exception e){
            
        }
        //le client demande une operation à son banquier 
        client.tell(new InstructionClient(Operation.DEPOSER, 10000.00), ActorRef.noSender());
        try{
        Thread.sleep(100);
            }catch(Exception e){
            
            }
        System.out.println("------------------------- FIN CAS 1 ---------------------------------------------");
   
    }
    
        public void cas2(){
        System.out.println("-------- CAS 2 : 1 banquier, 1 client, pour une opération de retrait de 1000€ (manque) --------");
        // Créer le système d'acteur
        ActorSystem system = ActorSystem.create("BanqueSystem");
        
        ActorRef jsonManager = system.actorOf(Props.create(JSONManagerActeur.class), "jsonManager");

        // Créer un banquier avec une liste de clients
        List<ActorRef> clientsBanquier = new ArrayList<>();

        System.out.println("Création du client...");

        ActorRef client = system.actorOf(ClientActeur.props(100.00), "client2");
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
        try{
            Thread.sleep(1000);
        }
        catch(Exception e){
            
        }
        //le client demande une operation à son banquier 
        client.tell(new InstructionClient(Operation.RETIRER, 10000.00), ActorRef.noSender());
        try{
        Thread.sleep(100);
            }catch(Exception e){
            
            }
        System.out.println("------------------------- FIN CAS 2 ---------------------------------------------");
   
    }
    
    public void cas3(){
        System.out.println("----- CAS 3 : création de 2 clients, pour 2 banquier, chaque client ajoute 100€ -----");
        // Créer le système d'acteur
        ActorSystem system = ActorSystem.create("BanqueSystem");
        
        ActorRef jsonManager = system.actorOf(Props.create(JSONManagerActeur.class), "jsonManager");

        // Créer un banquier avec une liste de clients
        List<ActorRef> clientsBanquier = new ArrayList<>();
        List<ActorRef> clientsBanquier2 = new ArrayList<>();

        System.out.println("Création du client1...");

        ActorRef client = system.actorOf(ClientActeur.props(100.00), "client3");
        clientsBanquier.add(client); // Ajouter le client à la liste
        
        System.out.println("Création du client2...");
        ActorRef client2 = system.actorOf(ClientActeur.props(2500.00), "client4");
        clientsBanquier2.add(client2); // Ajouter le client à la liste

        System.out.println("Création du banquier1...");
        ActorRef banquier = system.actorOf(Props.create(BanquierActeur.class, clientsBanquier), "banquier");
        
        System.out.println("Création du banquier2...");
        ActorRef banquier2 = system.actorOf(Props.create(BanquierActeur.class, clientsBanquier), "banquier2");
        
        // Créer la secrétaire avec la liste manuelle de clients-banquiers
        System.out.println("Création de la secrétaire...");
        Map<ActorRef, ActorRef> clientsBanquiers = new HashMap<>();
        clientsBanquiers.put(client, banquier);
        clientsBanquiers.put(client2, banquier2);

        ActorRef secretaire = system.actorOf(Props.create(SecretaireActeur.class, clientsBanquiers), "secretaire");
        
        //Le client s'annonce
        secretaire.tell(new AnnonceClient(), client);
        secretaire.tell(new AnnonceClient(), client2);

        try{
            Thread.sleep(1000);
        }
        catch(Exception e){
            
        }
        //le client demande une operation à son banquier 
        client.tell(new InstructionClient(Operation.DEPOSER, 100.00), ActorRef.noSender());
        client2.tell(new InstructionClient(Operation.DEPOSER, 100.00), ActorRef.noSender());

        try{
        Thread.sleep(100);
            }catch(Exception e){
            
            }
        System.out.println("------------------------- FIN CAS 3 ---------------------------------------------");
   
    }
            public void cas4(){
        System.out.println("-------- CAS 4 : 1 banquier, 1 client, opération d'ajout de 500€ et retrait de 400€ --------");
        // Créer le système d'acteur
        ActorSystem system = ActorSystem.create("BanqueSystem");
        
        ActorRef jsonManager = system.actorOf(Props.create(JSONManagerActeur.class), "jsonManager");

        // Créer un banquier avec une liste de clients
        List<ActorRef> clientsBanquier = new ArrayList<>();

        System.out.println("Création du client...");

        ActorRef client = system.actorOf(ClientActeur.props(0.00), "client5");
        clientsBanquier.add(client); // Ajouter le client à la liste

        System.out.println("Création du banquier...");
        ActorRef banquier = system.actorOf(Props.create(BanquierActeur.class, clientsBanquier), "banquier");
        
        // Créer la secrétaire avec la liste manuelle de clients-banquiers
        System.out.println("Création de la secrétaire...");
        Map<ActorRef, ActorRef> clientsBanquiers = new HashMap<>();
        clientsBanquiers.put(client, banquier);

        ActorRef secretaire = system.actorOf(Props.create(SecretaireActeur.class, clientsBanquiers), "secretaire");
        

        //Le client s'annonce
        secretaire.tell(new AnnonceClient(), client);
        try{
            Thread.sleep(1000);
        }
        catch(Exception e){
            
        }
        //le client demande une operation à son banquier 
        client.tell(new InstructionClient(Operation.DEPOSER, 500.00), ActorRef.noSender());
        try{
        Thread.sleep(100);
            }catch(Exception e){
            
            }
        client.tell(new InstructionClient(Operation.RETIRER, 400.00), ActorRef.noSender());
      try{
        Thread.sleep(100);
            }catch(Exception e){
            
            }
        System.out.println("------------------------- FIN CAS 4 ---------------------------------------------");
   
    }
}
