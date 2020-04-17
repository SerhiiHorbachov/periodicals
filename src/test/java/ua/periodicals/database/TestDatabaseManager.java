package ua.periodicals.database;

import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;

public class TestDatabaseManager {
    public void populateDatabase(String scriptPath, Connection conn) {
        ScriptRunner runner = null;
        File reset = new File("src/test/resources/schema.sql");
        runner = new ScriptRunner(conn);
        try {
            runner.runScript(new FileReader(reset));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
