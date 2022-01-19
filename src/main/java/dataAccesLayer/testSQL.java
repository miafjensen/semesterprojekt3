/**

 * @author ${Mia}

 * @Date ${jan 2022}

 */
package dataAccesLayer;

import java.sql.SQLException;

public class testSQL {

    public static void main(String[] args) throws SQLException {
        SQL.getSqlOBJ().makeConnectionSQL();
        SQL.getSqlOBJ().getALLSessions();
    }
}
