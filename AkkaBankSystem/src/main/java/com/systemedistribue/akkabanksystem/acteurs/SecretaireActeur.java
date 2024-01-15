/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.systemedistribue.akkabanksystem.acteurs;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import com.systemedistribue.akkabanksystem.messages.AnnonceClient;
import com.systemedistribue.akkabanksystem.messages.RefBanquier;
import java.util.Map;

/**
 *
 * @author julie
 */
public class SecretaireActeur extends AbstractActor {
    private final Map<ActorRef, ActorRef> clientsBanquiers;

    public SecretaireActeur(Map<ActorRef, ActorRef> clientsBanquiers) {
        this.clientsBanquiers = clientsBanquiers;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(AnnonceClient.class, client -> {
                    ActorRef banquier = clientsBanquiers.get(getSender());
                    getSender().tell(new RefBanquier(banquier), getSelf());
                })
                .build();
    }
}
