package org.example;

import javax.swing.*;
import java.sql.*;
import java.util.Locale;
import java.util.Random;

public class Main {
    static Connection connection = BDConnect.getInstance().getConnection(BDSettings.URL, BDSettings.DB_USER, BDSettings.DB_PSWRD);
    public static void main(String[] args) throws SQLException {

        //реализуем CRUD

        //CREATE
        createRecord(getRandomStr(5), getRandomStr(10),getRandomStr(5),getRandomStr(8),new Date(122,10,5));

        //READ
        ResultSet set = allRecords();
        while (set.next()){
            System.out.println(set.getString(1)
                    + " " + set.getString(2)
                    + " "  + set.getString(3)
                    + " " + set.getString(4)
                    + " " + set.getString(5)
                    + " " + set.getString(6));
        }

        //UPDATE
        //Введем с клавиатуры логин пользователя для обновления
        String updLogin = JOptionPane.showInputDialog("Введите имя пользователя для обновления");
        updateRecord(updLogin);

        //DELETE
        // удаление записи по логину
        String deleteLogin = JOptionPane.showInputDialog("Введите имя пользователя для удаления");
        deleteRecord(deleteLogin);
    }

    // добавление записи CREATE
    private static void createRecord(String login, String pswrd, String name, String surname, Date date){
        String sql = "INSERT INTO users (login,pswrd,name,surname,birthday) " +
                "VALUES ('" + login + "','" + pswrd + "','" +
                name + "','" + surname + "','" + date + "')";
        try {
            connection.createStatement().executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //выбор всех данных READ
    private static ResultSet allRecords(){
        String sql = "SELECT * FROM users";
        ResultSet set;
        try {
            set = connection.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return set;
    }

    // обновление данных по логину UPDATE
    private static void updateRecord(String login){
        try {
            PreparedStatement set = connection.prepareStatement("UPDATE users SET pswrd=?, name=?, surname=?, birthday=? WHERE login = ?");
            set.setString(1, "updatePswrd");
            set.setString(2, "updateName");
            set.setString(3, "updateSurname");
            set.setDate(4, new Date(122,11,23));
            set.setString(5, login);
            set.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void deleteRecord(String deleteLogin) {
        try {
            PreparedStatement set = connection.prepareStatement("DELETE FROM users WHERE login = ?");
            set.setString(1, deleteLogin);
            set.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Генерация уникальной строки
    static String getRandomStr(int length){

        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lower = upper.toLowerCase(Locale.ROOT);

        Random random = new Random();
        //сделаем логин из n символов
        String login = "";
        for (int i=0; i < length / 2 + 1; i++){
            //случайная буква
            int b = random.nextInt(upper.length());
            login += upper.substring(b,b+1);
        }
        for (int i=0; i < length / 2; i++){
            // случайная цифра от 0 до 9
            int c = random.nextInt(10);
            login += c + "";
        }
        return login;
    }
    private static String getRandomDate() {
        //склеим дату в формате "YYYY-MM-DD"
        return new Random().nextInt(2000,2100) + "-" + new Random().nextInt(0,13) + "-" + new Random().nextInt(0,29);
    }



}