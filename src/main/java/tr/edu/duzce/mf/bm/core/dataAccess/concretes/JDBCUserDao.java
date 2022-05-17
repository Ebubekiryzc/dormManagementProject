package tr.edu.duzce.mf.bm.core.dataAccess.concretes;

import tr.edu.duzce.mf.bm.core.dataAccess.DatabaseConnection;
import tr.edu.duzce.mf.bm.core.dataAccess.abstracts.UserDao;
import tr.edu.duzce.mf.bm.core.dataAccess.constants.Queries;
import tr.edu.duzce.mf.bm.core.entities.concrete.OperationClaim;
import tr.edu.duzce.mf.bm.core.entities.concrete.User;
import tr.edu.duzce.mf.bm.core.entities.concrete.UserOperationClaim;
import tr.edu.duzce.mf.bm.core.utilities.annotations.Id;
import tr.edu.duzce.mf.bm.core.utilities.annotations.TableColumn;
import tr.edu.duzce.mf.bm.core.utilities.annotations.TableName;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JDBCUserDao extends BaseDaoJDBCRepository<User> implements UserDao {
    public JDBCUserDao() {
        super(User.class);
    }

    public User getByUsername(String username) {
        Statement statement = null;
        User user = null;
        try {
            statement = DatabaseConnection.getInstance().getConnection().createStatement();
            var tableName = User.class.getAnnotation(TableName.class).value();
            ResultSet resultSet = statement.executeQuery(Queries.getByUsername(tableName, username));
            while (resultSet.next()) {
                user = new User();
                super.loadResultSetIntoObject(resultSet, user);
            }
        } catch (SQLException | IllegalAccessException exception) {
            System.err.println(exception.getMessage() + "/37 JDBCUserDao");
            user = null;
        } finally {
            try {
                if (statement != null)
                    statement.close();
            } catch (SQLException exception) {
                System.err.println(exception.getMessage() + "/45 JDBCUserDao");
                user = null;
            }
        }
        return user;
    }
}
