package dataAccesLayer;

public class StoreData {
}
 /*

 // Fra Semester projekt 2:

    private Connection connection;
    private Statement statement;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

 //Indsætning af større mængder data på en gang ved brug af batch
    public void InsertIntoMeasurementsArray(int value1, String[] value2) {
        String SQLMeasurementsArray = "INSERT INTO measurements (Cpr, maaling) VALUES (?,?)";
        try {
            preparedStatement = connection.prepareStatement(SQLMeasurementsArray);
            for (int i = 0; i < value2.length; i++) {
                preparedStatement.setInt(1, value1);
                preparedStatement.setString(2, value2[i]);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            System.out.println("batch sendt til database");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("fejl i indsætning af batch");
        }
    }

    // opretter automatisk table hvis den ikke allerede eksisterer i databasen
    public void createTable() {
        try {
            String SQLTable = "create table if not exists measurements " +
                    "( id int auto_increment primary key," +
                    "Cpr varchar(6) not null, maaling text not null, " +
                    "Dato timestamp default CURRENT_TIMESTAMP null);";
            Statement stmt = connection.createStatement();
            stmt.execute(SQLTable);
            System.out.println("table oprettet eller eksisterer allerede");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


       // Se id, måling og dato fra tabellen der matcher CPR, kun brugt til test
    public ArrayList<MeasurementObjects> FindAllMeasurementResults(int CPR) {

        ArrayList liste = new ArrayList();

        String SQLResults = "SELECT id, maaling, Dato FROM measurements WHERE Cpr = "
                + CPR + ";";
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQLResults);

            while (resultSet.next()) {
                System.out.println(
                        "id: " + resultSet.getInt("id") +
                                "   Måling: " + resultSet.getInt("maaling") +
                                "   Dato: " + resultSet.getTimestamp("Dato") + "\n"
                );
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return liste;
    }
  */