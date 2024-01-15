/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.systemedistribue.akkabanksystem.messages;

import com.systemedistribue.akkabanksystem.Operation;

/**
 *
 * @author julie
 */

// Message de demande de réalisartion d'une opération

public class DemandeOperation {
    public final Operation operation;
    public final double montant;

    public DemandeOperation(Operation operation, double montant) {
        this.operation = operation;
        this.montant = montant;
    }
    
}
