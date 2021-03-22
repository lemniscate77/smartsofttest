package com.example.SmartSoftTestv01;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkWithBD { // класс для работы с базой данных
    private String user = "postgres";
    private char[] password = {1, 2, 3, 4, 5};
    private String passwordStr = password.toString();

    public void TryConnect(List<Valute> valutes, String date) {  // попытка получить информацию из БД
        List<Valute> result = new ArrayList<>();
        String dateInBD = "no info";
        String SQL_SELECT = "Select * from public.\"MainTable\"";

        // auto close connection and preparedStatement
        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:postgresql://127.0.0.1:5432/SmartSoftDb", user, passwordStr);
                PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                dateInBD = resultSet.getString("Date");
                String id = resultSet.getString("ID");
                String code = resultSet.getString("CODE");
                String name = resultSet.getString("NAME");
                Double value = resultSet.getDouble("VALUE");

                Valute obj = new Valute();
                obj.setValuteID(id);
                obj.setCharCode(code);
                obj.setName(name);
                obj.setValue(value);

                result.add(obj);

            }
            result.forEach(x -> System.out.println(x));

        } catch (
                SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!dateInBD.equals(date)) {   // Если результат есть, но дата не совпадает - обновляем БД
            updateBD(valutes, date);
            System.out.println("Update");
            System.out.println(dateInBD);
        }
        if (dateInBD.equals("no info")) { // Если результата нет - заполняем БД
            insertBD(valutes, date);
            System.out.println("Insert");
        }

    }

    private void insertBD(List<Valute> valutes, String date) { // Вставляем в таблицу массив из валют + timestamp
        String id, code, name;
        String SQL_INSERT = "";
        double value;
        for (Valute valute : valutes) {
            id = valute.getValuteID();
            code = valute.getCharCode();
            name = valute.getName();
            value = valute.getValue();
            SQL_INSERT += "INSERT INTO public.\"MainTable\" (" + // += сделан чтобы кучу раз не вызывать метод отправки, а сделать все за один раз
                    "\"ID\"," +
                    "\"Code\"," +
                    "\"Name\"," +
                    "\"Value\"," +
                    "\"Date\")" +
                    " VALUES ('" + id + "','" + code + "','" + name + "','" + value + "" + "','" + date + "'); ";

            // auto close connection and preparedStatement

        }
        executeSQLstatement(SQL_INSERT); // отправка SQL запроса
    }

    private void updateBD(List<Valute> valutes, String date) { // Обновление информации в БД
        String SQL_UPDATE = "";
        String id;
        double value = 0;

        for (Valute valute : valutes) {
            id = valute.getValuteID();
            value = valute.getValue();
            SQL_UPDATE += "UPDATE public.\"MainTable\" SET \"Value\"=" +
                    value + "" + " WHERE \"ID\"='" +
                    id + "'; ";
            SQL_UPDATE += "UPDATE public.\"MainTable\" SET \"Date\"='" +
                    date + "' WHERE \"ID\"='" +
                    id + "'; ";
            // auto close connection and preparedStatement

        }
        executeSQLstatement(SQL_UPDATE);
    }

    private void executeSQLstatement(String SQL_STATE) { //Отправка SQL запроса
        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:postgresql://127.0.0.1:5432/SmartSoftDb", user, passwordStr);
                PreparedStatement preparedStatement = conn.prepareStatement(SQL_STATE)) {

            ResultSet resultSet = preparedStatement.executeQuery();

        } catch (
                SQLException e) {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public double actualCurs(String name) { // Получить из БД информации о курсе валюты по её Имени
        double value = 0;
        String SQL_SELECT = "SELECT * FROM public.\"MainTable\" WHERE \"Name\"='" + name + "';";
        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:postgresql://127.0.0.1:5432/SmartSoftDb", user, passwordStr);
                PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                value = resultSet.getDouble("VALUE");
            }

        } catch (
                SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public List<HistoryOperations> getHistory() { // Получить историю операций, возвращает коллекцию объектов
        String SQL_SELECT = "Select * from public.\"History\"";
        List<HistoryOperations> result = new ArrayList<>();
        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:postgresql://127.0.0.1:5432/SmartSoftDb", user, passwordStr);
                PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String from = resultSet.getString("From");
                String to = resultSet.getString("To");
                Double fromValue = resultSet.getDouble("FromValue");
                Double toValue = resultSet.getDouble("ToValue");
                String date = resultSet.getString("Date");

                HistoryOperations obj = new HistoryOperations();
                obj.setFrom(from);
                obj.setTo(to);
                obj.setFromValue(fromValue);
                obj.setToValue(toValue);
                obj.setDate(date);
                result.add(obj);

            }

        } catch (
                SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }

    public void insertHistory(HistoryOperations a) { // Добавление записи в БД о произошедшей операции
        String from, to, date;
        double fromValue, toValue;
        from = a.getFrom();
        to = a.getTo();
        fromValue = a.getFromValue();
        toValue = a.getToValue();
        date = a.getDate();
        String SQL_INSERT = "INSERT INTO public.\"History\" (" +
                "\"From\"," +
                "\"To\"," +
                "\"FromValue\"," +
                "\"ToValue\"," +
                "\"Date\")" +
                " VALUES ('" + from + "','" + to + "','" + fromValue + "','" + toValue + "" + "','" + date + "'); ";
        executeSQLstatement(SQL_INSERT);
    }

    public String authorization(String username) { // Добавление записи в БД о произошедшей операции
        String SQL_SELECT = "SELECT * FROM public.\"Users\" WHERE \"Username\"='" + username + "';";
        String password="User not found";
        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:postgresql://127.0.0.1:5432/SmartSoftDb", user, passwordStr);
                PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                password = resultSet.getString("Password");
            }

        } catch (
                SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return password;
    }
}
