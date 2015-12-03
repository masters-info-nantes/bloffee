package fr.alma.mw1516.services;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author Arnaud Thimel (Code Lutin)
 */
public class TestH2 {

    public static void main(String[] args) throws Exception {
        Class.forName("org.h2.Driver");
        Connection conn = DriverManager.
                getConnection("jdbc:h2:~/test", "sa", "");

        // Appels vers la base ici

        conn.close();
    }

}
