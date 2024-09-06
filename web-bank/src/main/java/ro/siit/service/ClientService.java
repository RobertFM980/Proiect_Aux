package ro.siit.service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import ro.siit.entity.Client;

public class ClientService {
    private Connection dbConnection = null;
    public ClientService(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }
    public void create(Client client) {
        String insertCommand = "insert into clients values (?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = this.dbConnection.prepareStatement(insertCommand);
            preparedStatement.setObject(1, client.getId());
            preparedStatement.setString(2, client.getName());
            preparedStatement.setString(3, client.getCity());
            preparedStatement.setDate(4, new Date(client.getBirthDate().getTime()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void update(UUID id, String newName, String newCity, java.util.Date newBirthDate) {
        String insertCommand = "update clients set name = ?, city = ?, birthDate = ?  where id = ?";
        System.out.println(insertCommand);
        try {
            PreparedStatement preparedStatement1 = this.dbConnection.prepareStatement(insertCommand);
            preparedStatement1.setString(1, newName);
            preparedStatement1.setString(2, newCity);
            preparedStatement1.setDate(3, new Date(newBirthDate.getTime()));
            preparedStatement1.setObject(4, id);
            preparedStatement1.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(UUID id) {
        String insertCommand = "delete from clients where id= ?";
        try {
            PreparedStatement preparedStatement = this.dbConnection.prepareStatement(insertCommand);
            preparedStatement.setObject(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Client findById(UUID id) {
        Client client=null;
        try {
            PreparedStatement statement = dbConnection.prepareStatement("select * from clients where id = ?");
            statement.setObject(1, id);
            ResultSet clientResult = statement.executeQuery();
            clientResult.next();
            client=new Client((UUID) clientResult.getObject(1),
                    clientResult.getString(2),
                    clientResult.getString(3),
                    new Date(clientResult.getDate(4).getTime()));

        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
        return client;
    }
    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();
        try {
            Statement statement = dbConnection.createStatement();
            ResultSet clientsResults = statement.executeQuery("select * from clients");
            while (clientsResults.next()) {
                clients.add(
                        new Client((UUID) clientsResults.getObject(1),
                                clientsResults.getString(2),
                                clientsResults.getString(3),
                                new Date(clientsResults.getDate(4).getTime())));
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
        return clients;
    }
}
