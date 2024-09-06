package ro.siit.service;

import ro.siit.entity.Bank;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BankService {
    private Connection dbConnection = null;
    public BankService(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }
    public void create(Bank bank) {
        String insertCommand = "insert into banks values (?, ?)";
        try {
            PreparedStatement preparedStatement = this.dbConnection.prepareStatement(insertCommand);
            preparedStatement.setObject(1, bank.getId());
            preparedStatement.setString(2, bank.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void update(UUID id, String newName) {
        String insertCommand = "update banks set name = ? where id = ?";
        try {
            PreparedStatement preparedStatement1 = this.dbConnection.prepareStatement(insertCommand);
            preparedStatement1.setObject(1, newName);
            preparedStatement1.setObject(2, id);
            preparedStatement1.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(UUID id) {
        String insertCommand = "delete from banks where id= ?";
        try {
            PreparedStatement preparedStatement = this.dbConnection.prepareStatement(insertCommand);
            preparedStatement.setObject(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Bank findById(UUID id) {
        Bank bank = null;
        try {
            PreparedStatement statement = dbConnection.prepareStatement("select * from banks where id = ?");
            statement.setObject(1, id);
            ResultSet bankResult = statement.executeQuery();
            bankResult.next();
            bank = new Bank((UUID) bankResult.getObject(1),
                    bankResult.getString(2));
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
        return bank;
    }
    public List<Bank> findAll() {
        List<Bank> banks = new ArrayList<>();
        try {
            Statement statement = dbConnection.createStatement();
            ResultSet banksResults = statement.executeQuery("select * from banks");
            while (banksResults.next()) {
                banks.add(
                        new Bank((UUID) banksResults.getObject(1),
                                banksResults.getString(2)));
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
        return banks;
    }
}
