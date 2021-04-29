package dal.db;

import be.users.Admin;
import be.users.Client;
import dal.IClientRepository;
import error.ErrorHandler;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBClientRepository implements IClientRepository {
    private DatabaseConnection connection;
    private ErrorHandler errorHandler;

    public DBClientRepository() {
        connection = new DatabaseConnection();
        errorHandler = new ErrorHandler();
    }

    @Override
    public Client getClient(int clientID) {
        try (Connection con = connection.getConnection()) {
            String sql = "Select * From Client WHERE ID = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, clientID);
            if (statement.execute()) {
                ResultSet resultSet = statement.getResultSet();
                if (resultSet.next()) {
                    return new Client(resultSet.getInt("ID"), resultSet.getString("Username"));
                }
            }
        } catch (SQLException ex) {
            errorHandler.errorDevelopmentInfo("Issue getting a client", ex);
        }
        return null;
    }

    @Override
    public List<Client> getAllClients() {
        List<Client> clients = new ArrayList<>();
        try (Connection con = connection.getConnection()) {
            String sql = "Select * From Client";
            Statement statement = con.createStatement();

            if (statement.execute(sql)) {
                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    clients.add(new Client(
                            resultSet.getInt("ID"),
                            resultSet.getString("Username")));
                }
                return clients;
            }
        } catch (SQLException ex) {
            errorHandler.errorDevelopmentInfo("Issue getting all clients", ex);
        }
        return null;
    }

    @Override
    public List<Client> getAllClientsFromDepartment(int departmentID) {
        List<Client> clients = new ArrayList<>();
        try (Connection con = connection.getConnection()) {
            String sql = "Select * From Client WHERE DepartmentID = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, departmentID);

            if (statement.execute()) {
                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    clients.add(new Client(
                            resultSet.getInt("ID"),
                            resultSet.getString("Username")));
                }
                return clients;
            }
        } catch (SQLException ex) {
            errorHandler.errorDevelopmentInfo("Issue getting all clients from chosen department", ex);
        }
        return null;
    }

    @Override
    public void changeClientDepartment(int clientID, int departmentID) {
        try (Connection con = connection.getConnection()) {
            String sql = "UPDATE Client SET DepartmentID = ? WHERE ID = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, departmentID);
            statement.setInt(2, clientID);
            statement.execute();
        } catch (SQLException ex) {
            errorHandler.errorDevelopmentInfo("Issue changing client's department", ex);
        }
    }

    @Override
    public void createClient(String username, String password, int departmentID) {
        try (Connection con = connection.getConnection()) {
            String sql = "INSERT INTO Client Values(?,?,?)";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setInt(3, departmentID);
            statement.execute();
        } catch (SQLException ex) {
            errorHandler.errorDevelopmentInfo("Issue registering a client", ex);
        }
    }

    @Override
    public void deleteClient(int clientID) {
        try (Connection con = connection.getConnection()) {
            String sql = "DELETE FROM Client WHERE ID = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1,clientID);
            statement.execute();
        } catch (SQLException ex) {
            errorHandler.errorDevelopmentInfo("Issue deleting a client", ex);
        }
    }
}
