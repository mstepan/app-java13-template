import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public final class DeleteData {

    //"jdbc:postgresql://localhost:5432/postgres", "postgres", "611191")) {

    public static void main(String[] args) throws Exception {

        if (args.length != 3) {
            System.err.println("You should provide 3 arguments:  jdbcUrl, username and passsword");
            System.exit(1);
        }

        final String jdbcUrl = args[0];
        final String username = args[1];
        final String password = args[2];

        try (Connection db = DriverManager.getConnection(jdbcUrl, username, password)) {
            try (PreparedStatement st = db.prepareStatement("DELETE FROM usertable")) {
                st.executeUpdate();
            }
        }

        System.out.println("All data from 'usertable' deleted.");
    }


}
