package dataAccesLayer;

import java.sql.Connection;
import java.sql.SQLException;

public class SQLtester {
    public static void main(String[] args) throws SQLException {

        Connection con = SQL.getConnection();

    }
}
