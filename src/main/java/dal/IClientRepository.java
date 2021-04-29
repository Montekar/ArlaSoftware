package dal;

import be.users.Client;

import java.util.List;

public interface IClientRepository {
    Client getClient(int clientID);
    List<Client> getAllClients();
    List<Client> getAllClientsFromDepartment(int departmentID);

    void changeClientDepartment(int clientID, int departmentID);
    void createClient(String username, String password, int departmentID);
    void deleteClient(int clientID);
}
