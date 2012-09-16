import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class TestDB2 {

	private static final String sql = "insert " +
            "into orbeon_form_data " +
            "( " +
            "    created, last_modified, username, app, form, document_id, deleted, " +
            "    xml " +
            ") " +
            "select " +
            "    case when last_deleted is null or last_deleted = 'Y' " +
            "        then  ?  " +
            "        else last_created end as lc, " +
            "         ? , " +
            "     ? , " +
            "     ? , " +
            "     ? , " +
            "     ? , " +
            "     'N', " +
            "     ?  " +
            "from " +
            "( " +
            "    select " +
            "        v1.*, " +
            "        ( " +
            "            select deleted " +
            "            from orbeon_form_data " +
            "            where " +
            "                app =  ?  " +
            "                and form =  ?  " +
            "                    and document_id =  ?  " +
            "                and last_modified = v1.last_last_modified " +
            "        ) last_deleted " +
            "    from " +
            "        ( " +
            "            select " +
            "                max(last_modified) last_last_modified , max(created) last_created " +
            "            from " +
            "            ( " +
            "                select max(last_modified) last_modified, max(created) created " +
            "                from orbeon_form_data " +
            "                where " +
            "                app =  ?  " +
            "                and form =  ?  " +
            "                    and document_id =  ?  " +
            "                union all select null last_modified, null created from sysibm.sysdummy1 " +
            "            ) v2 " +
            "        ) v1 " +
            ") v ";
			
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TestDB2 t = new TestDB2();
		t.run();

	}

	private void run() {
		try {

			String driverName = "oracle.jdbc.OracleDriver";
			Class.forName(driverName);

			// Create a connection to the database
			String url = "jdbc:oracle:thin:@localhost:1521:orcl";

			String username = "orbeondev";
			String password = "orbeondev";
			Connection con = DriverManager.getConnection(url, username, password);

			// Connection con = ds.getConnection();
			con.setAutoCommit(false);
			PreparedStatement pstmt = (PreparedStatement) con.prepareStatement(sql);
			pstmt.setString(1, "a");
			System.out.println(pstmt.executeUpdate());

			pstmt.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
