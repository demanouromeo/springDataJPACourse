import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class DbSchemaReader {
    public static void main(String[] args) {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("src/main/resources/application.properties")) {
            props.load(fis);
        } catch (IOException e) {
            System.err.println("Failed to load application.properties: " + e.getMessage());
            System.exit(1);
        }

        String url = props.getProperty("spring.datasource.url");
        String user = props.getProperty("spring.datasource.username");
        String password = props.getProperty("spring.datasource.password", "");
        if (url == null || user == null) {
            System.err.println("Missing required datasource properties.");
            System.exit(1);
        }

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            DatabaseMetaData meta = conn.getMetaData();
            System.out.println("Connected to: " + meta.getDatabaseProductName() + " " + meta.getDatabaseProductVersion());
            try (ResultSet tables = meta.getTables(conn.getCatalog(), null, "%", new String[] {"TABLE"})) {
                while (tables.next()) {
                    String tableName = tables.getString("TABLE_NAME");
                    String tableType = tables.getString("TABLE_TYPE");
                    System.out.println("TABLE: " + tableName + " (" + tableType + ")");
                    try (ResultSet cols = meta.getColumns(conn.getCatalog(), null, tableName, "%")) {
                        while (cols.next()) {
                            String colName = cols.getString("COLUMN_NAME");
                            String typeName = cols.getString("TYPE_NAME");
                            int size = cols.getInt("COLUMN_SIZE");
                            String nullable = cols.getInt("NULLABLE") == DatabaseMetaData.columnNullable ? "YES" : "NO";
                            System.out.println("  " + colName + " " + typeName + "(" + size + ") nullable=" + nullable);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("JDBC error: " + e.getMessage());
            e.printStackTrace(System.err);
            System.exit(2);
        }
    }
}
