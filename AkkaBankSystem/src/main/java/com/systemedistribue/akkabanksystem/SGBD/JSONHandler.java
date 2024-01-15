package com.systemedistribue.akkabanksystem.SGBD;

import com.systemedistribue.akkabanksystem.ClientData;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JSONHandler {
    private static final Path jsonFilePath = Paths.get("src/main/java/com/systemedistribue/akkabanksystem/SGBD", "clients.json");
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void initializeJsonFile() throws IOException {
        // Créer une structure JSON avec une liste vide de clients
        ObjectNode rootNode = objectMapper.createObjectNode();
        rootNode.set("clients", objectMapper.createArrayNode());

        // Écrire le contenu dans le fichier avec l'option CREATE et TRUNCATE_EXISTING
        Files.write(jsonFilePath, objectMapper.writeValueAsBytes(rootNode), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        System.out.println("Fichier JSON initialisé avec succès.");
    }

    public static void writeClientDataToJson(ClientData clientData) throws IOException {
        ObjectNode rootNode = readJsonFile();

        // Construire un objet JSON à partir des données du client
        ObjectNode clientNode = objectMapper.createObjectNode();
        clientNode.put("clientId", clientData.getClientId());
        clientNode.put("solde", clientData.getSolde());

        // Ajouter le client à la liste des clients
        ArrayNode clientsArray = (ArrayNode) rootNode.get("clients");
        clientsArray.add(clientNode);
        
        // Écrire le contenu mis à jour dans le fichier
        Files.write(jsonFilePath, objectMapper.writeValueAsBytes(rootNode), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        System.out.println("Données JSON écrites avec succès dans le fichier existant.");
    }
    
    private static ObjectNode readJsonFile() throws IOException {
        // Lire le contenu du fichier JSON
        byte[] jsonData = Files.readAllBytes(jsonFilePath);
        return objectMapper.readTree(jsonData).deepCopy();
    }

    public static void updateClientBalanceInJson(String clientId, double newBalance) throws IOException {
        System.out.println("updateClientBalanceInJson (p2) de JSONHandler");

        ObjectNode rootNode = readJsonFile();

        // Mettre à jour le solde du client
        ArrayNode clientsArray = (ArrayNode) rootNode.get("clients");
        for (JsonNode clientNode : clientsArray) {
            if (clientNode.get("clientId").asText().equals(clientId)) {
                ((ObjectNode) clientNode).put("solde", newBalance);
                break;
            }
        }

        // Écrire le contenu mis à jour dans le fichier
        Files.write(jsonFilePath, objectMapper.writeValueAsBytes(rootNode), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        System.out.println("Solde du client mis à jour avec succès dans le fichier JSON.");
    }

    public static void updateClientSoldeInJson(String clientId, double newBalance) throws IOException {
        System.out.println("UpdateClientSolde de JSONHandler");
// Même logique que updateClientBalanceInJson
        updateClientBalanceInJson(clientId, newBalance);
    }

    public static double getSoldeFromJson(String clientId) throws IOException {
        JsonNode rootNode = readJsonFile();

        // Récupérer le solde du client à partir du fichier JSON
        ArrayNode clientsArray = (ArrayNode) rootNode.get("clients");
        for (JsonNode clientNode : clientsArray) {
            if (clientNode.get("clientId").asText().equals(clientId)) {
                return clientNode.get("solde").asDouble();
            }
        }

        return 0.0; // Retourner une valeur par défaut si le client n'est pas trouvé
    }
}
