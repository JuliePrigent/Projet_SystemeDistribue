/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.systemedistribue.akkabanksystem;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.systemedistribue.akkabanksystem.JeuxTest.JeuxTest;
import com.systemedistribue.akkabanksystem.SGBD.JSONHandler;
import com.systemedistribue.akkabanksystem.acteurs.BanquierActeur;
import com.systemedistribue.akkabanksystem.acteurs.ClientActeur;
import com.systemedistribue.akkabanksystem.acteurs.JSONManagerActeur;
import com.systemedistribue.akkabanksystem.acteurs.SecretaireActeur;
import com.systemedistribue.akkabanksystem.messages.AnnonceClient;
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
        
        JeuxTest test = new JeuxTest();
        test.cas1();
        test.cas2();
        test.cas3();
        test.cas4();
        
    }
}