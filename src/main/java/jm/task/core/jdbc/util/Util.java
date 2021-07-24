package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/newDataBase";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "root";

    public Util() {
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
            System.out.println("Successful connection!");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.err.println("Connection ERROR!");
        }
        return connection;
    }

    // -----------------------------> Hibernate <--------------------------------

    private static SessionFactory sessionFactory;
    public static SessionFactory getSessionFactory () {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                //Hibernate settings equivalent to hibernate.cfg.xml's properties
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, DRIVER);                                 // можно не указывать
                settings.put(Environment.URL, "jdbc:mysql://localhost:3306/newDataBase?useSSL=false");
                settings.put(Environment.USER, LOGIN);
                settings.put(Environment.PASS, PASSWORD);
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect"); // можно не указывать

                settings.put(Environment.SHOW_SQL, "true");
//                settings.put(Environment.FORMAT_SQL, "true");     // Вывод в консоли в формате SQL
//                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");  // Пока не понял что делает
//                settings.put(Environment.HBM2DDL_AUTO, "create-drop");   // Удаляет и создает таблицу, если уже такая есть

                configuration.setProperties(settings);

                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}
