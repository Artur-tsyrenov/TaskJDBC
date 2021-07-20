package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {

    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Elon", "Musk", (byte)50);
        userService.saveUser("Bill", "Gates", (byte)65);
        userService.saveUser("Mark", "Zuckerberg", (byte)37);
        userService.saveUser("Steve", "Jobs", (byte)56);

        System.out.println(userService.getAllUsers());
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }


}
