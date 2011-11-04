package uk.ac.manchester.cs.irs.datastore;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import uk.ac.manchester.cs.irs.IRSException;

public class IRSDatabaseCreation {
    
    //TODO: Load data from file
    
    private static final String DB_URL = "jdbc:mysql://localhost:3306/irs";
    private static final String USER = "irs";
    private static final String PASS = "irs";
    
    private Connection conn;

    protected IRSDatabaseCreation() 
            throws IRSException {
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException ex) {
            Logger.getLogger(MySQLAccess.class.getName()).log(Level.SEVERE, null, ex);
            throw new IRSException("Problem connecting to database.", ex);
        }
    }
    
    private void createDatabase() {
        try {
            String sql = readFile("scripts/createMappingTable.sql");
            Statement st = conn.createStatement();
            st.execute("DROP TABLE mapping");
            st.execute(sql);
        } catch (SQLException ex) {
            Logger.getLogger(MySQLAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private String readFile(String file) {
        {
            String fileLocation = this.getClass().getClassLoader().getResource(file).getPath();
            System.out.println("File location:" + fileLocation);
            FileReader fileReader = null;
            StringBuilder sb = new StringBuilder();
            try {
                fileReader = new FileReader(fileLocation);
                BufferedReader in = new BufferedReader(fileReader);
                String str;
                while ((str = in.readLine()) != null) {
                    sb.append(str).append("\n");
                }
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(MySQLAccess.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    fileReader.close();
                } catch (IOException ex) {
                    Logger.getLogger(MySQLAccess.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return sb.toString();
        }
    }
    
    public static void main(String[] args) throws IRSException {
        IRSDatabaseCreation dbCreator = new IRSDatabaseCreation();
        dbCreator.createDatabase();
    }
    
}
