/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.systemedistribue.akkabanksystem.messages;

import akka.actor.ActorRef;

/**
 *
 * @author julie
 */
public class RefBanquier {
    public final ActorRef banquier;

    public RefBanquier(ActorRef banquier) {
        this.banquier = banquier;
    }
}
