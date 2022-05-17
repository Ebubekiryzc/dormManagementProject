package tr.edu.duzce.mf.bm.dataAccess.concretes.JDBCDao;

import tr.edu.duzce.mf.bm.core.dataAccess.concretes.BaseDaoJDBCRepository;
import tr.edu.duzce.mf.bm.core.dataAccess.constants.Queries;
import tr.edu.duzce.mf.bm.core.entities.concrete.OperationClaim;
import tr.edu.duzce.mf.bm.core.utilities.annotations.TableColumn;
import tr.edu.duzce.mf.bm.core.utilities.annotations.TableName;
import tr.edu.duzce.mf.bm.dataAccess.abstracts.GenderDao;
import tr.edu.duzce.mf.bm.entities.concretes.Gender;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCGenderDao extends BaseDaoJDBCRepository<Gender> implements GenderDao {

    public JDBCGenderDao() {
        super(Gender.class);
    }

    @Override
    public Gender getByName(String name) {
        Statement statement = null;
        Gender gender = null;
        try {
            statement = super.getDatabaseConnection().getConnection().createStatement();
            String genderTable = Gender.class.getAnnotation(TableName.class).value();
            Field nameField = Gender.class.getDeclaredField("name");
            String nameFieldColumnName = nameField.getAnnotation(TableColumn.class).name();

            String predicate = String.format("%s=\'%s\'", nameFieldColumnName, name);

            ResultSet resultSet = statement.executeQuery(Queries.getObjectBy(genderTable, predicate));
            while (resultSet.next()) {
                gender = new Gender();
                loadResultSetIntoObject(resultSet, gender);
            }
        } catch (SQLException | NoSuchFieldException | IllegalAccessException exception) {
            System.err.println(exception.getMessage() + "/40 JDBCGenderDao");
            gender = null;
        } finally {
            try {
                if (statement != null)
                    statement.close();
            } catch (SQLException exception) {
                System.err.println(exception.getMessage() + "/47 JDBCGenderDao");
                gender = null;
            }
        }
        return gender;
    }
}
