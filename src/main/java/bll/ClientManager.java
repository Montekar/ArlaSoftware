package bll;

import be.users.Client;
import dal.IClientRepository;
import dal.db.DBClientRepository;

import java.util.List;

public class ClientManager {
    private IClientRepository clientRepository;

    public ClientManager() {
        clientRepository = new DBClientRepository();
    }

    public Client getClient(int clientID) {
        return clientRepository.getClient(clientID);
    }

    public List<Client> getAllClients() {
        return clientRepository.getAllClients();
    }

    public List<Client> getAllClientsFromDepartment(int departmentID) {
        return clientRepository.getAllClientsFromDepartment(departmentID);
    }

    public void changeClientDepartment(int clientID, int departmentID) {
        clientRepository.changeClientDepartment(clientID, departmentID);
    }

    public void createClient(String username, String password, int departmentID) {
        clientRepository.createClient(username, password, departmentID);
    }

    public void deleteClient(int clientID) {
        clientRepository.deleteClient(clientID);
    }
}
