package util;

import DB.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CrudUtil {

    public static <X>X execute(String sql, Object... args) throws SQLException {
        PreparedStatement psTm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            psTm.setObject((i+1),args[i]);
        }
        if (sql.startsWith("SELECT")||sql.startsWith("select")){
            return (X) psTm.executeQuery();
        }
        return (X)  (Boolean) (psTm.executeUpdate()>0);
    }



    public static boolean executeUpdate(String sql, Object... params) throws SQLException {

        PreparedStatement stmt =  DBConnection.getInstance().getConnection().prepareStatement(sql);

        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }

        return stmt.executeUpdate() > 0; // Returns true if update is successful
    }



}
