/**

 * @author ${USER}

 * @Date ${DATE}

 */
package dataAccesLayer;

import java.sql.SQLException;

public class testSQL {

    public static void main(String[] args) throws SQLException {
        SQL.getSqlOBJ().makeConnectionSQL();
        SQL.getSqlOBJ().getALLSessions();
    }
}
