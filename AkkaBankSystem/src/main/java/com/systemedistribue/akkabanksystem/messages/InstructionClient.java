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
public class InstructionClient {
    private final Operation operation;
    private final double montant;

    public InstructionClient(Operation operation, double montant) {
        this.operation = operation;
        this.montant = montant;
    }

    public Operation getOperation() {
        return operation;
    }

    public double getMontant() {
        return montant;
    }
    
}
