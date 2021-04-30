package gui.model;

import be.users.Client;
import bll.ClientManager;

import java.util.List;

public class ClientModel {
    private final ClientManager clientManager;

    public ClientModel(){
        clientManager = new ClientManager();
    }

    public Client getClient(int clientID) {
        return clientManager.getClient(clientID);
    }

    public List<Client> getAllClients() {
        return clientManager.getAllClients();
    }

    public List<Client> getAllClientsFromDepartment(int departmentID) {
        return clientManager.getAllClientsFromDepartment(departmentID);
    }

    public void changeClientDepartment(int clientID, int departmentID) {
        clientManager.changeClientDepartment(clientID, departmentID);
    }

    public void createClient(String username, String password, int departmentID) {
        clientManager.createClient(username, password, departmentID);
    }

    public void deleteClient(int clientID) {
        clientManager.deleteClient(clientID);
    }
}
