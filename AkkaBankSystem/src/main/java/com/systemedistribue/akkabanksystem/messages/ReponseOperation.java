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

// Message de réponse une fois la demande d'opération traitée

public class ReponseOperation {
    final Operation operation;
    final double montant;
    final double nouveauSolde;

    public ReponseOperation(Operation operation, double montant, double nouveauSolde) {
        this.operation = operation;
        this.montant = montant;
        this.nouveauSolde = nouveauSolde;
    }
}
