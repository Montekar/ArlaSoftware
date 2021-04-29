package dal;

import be.users.Client;

import java.util.List;

public interface IClientRepository {
    Client getClient(int clientID);
    List<Client> getAllClients();
    List<Client> getAllClientsFromDepartment(int departmentID);
}
