package tr.edu.duzce.mf.bm.core.dataAccess.concretes;

import tr.edu.duzce.mf.bm.core.dataAccess.DatabaseConnection;
import tr.edu.duzce.mf.bm.core.dataAccess.abstracts.OperationClaimDao;
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

public class JDBCOperationClaimDao extends BaseDaoJDBCRepository<OperationClaim> implements OperationClaimDao {

    public JDBCOperationClaimDao() {
        super(OperationClaim.class);
    }

    @Override
    public List<OperationClaim> getClaims(User user){
        List<OperationClaim> operationClaims = new ArrayList<>();
        try {
            Statement statement = DatabaseConnection.getInstance().getConnection().createStatement();
            String userOperationClaimTable = UserOperationClaim.class.getAnnotation(TableName.class).value();
            String operationClaimTable = OperationClaim.class.getAnnotation(TableName.class).value();
            String operationClaimTableIdColumn = null;

            for (Field field : OperationClaim.class.getDeclaredFields()){
                if (field.getAnnotation(Id.class) != null){
                    operationClaimTableIdColumn = field.getName();
                }
            }

            Field userOperationClaimTableOperationClaimIdField = UserOperationClaim.class.getDeclaredField("operationClaimId");
            userOperationClaimTableOperationClaimIdField.setAccessible(true);
            String userOperationClaimTableOperationIdColumn = userOperationClaimTableOperationClaimIdField.getAnnotation(TableColumn.class).name();

            Field userOperationClaimTableUserIdField = UserOperationClaim.class.getDeclaredField("userId");
            userOperationClaimTableUserIdField.setAccessible(true);
            String userOperationClaimTableUserIdColumn = userOperationClaimTableUserIdField.getAnnotation(TableColumn.class).name();

            ResultSet resultSet = statement.executeQuery(Queries.getUserClaims(userOperationClaimTable,operationClaimTable,operationClaimTableIdColumn,userOperationClaimTableOperationIdColumn,userOperationClaimTableUserIdColumn,user.getId().toString()));
            while(resultSet.next()){
                OperationClaim operationClaim = new OperationClaim();
                loadResultSetIntoObject(resultSet, operationClaim);
                operationClaims.add(operationClaim);
            }
        } catch (SQLException | NoSuchFieldException | IllegalAccessException exception) {
            System.err.println(exception.getMessage() + "49");
        }
        return operationClaims;
    }

    @Override
    public OperationClaim getClaimByName(String name) {
        try{
            Statement statement = super.getDatabaseConnection().getConnection().createStatement();
            String operationClaimTable = OperationClaim.class.getAnnotation(TableName.class).value();
            Field nameField = OperationClaim.class.getDeclaredField("name");
            String nameFieldColumnName = nameField.getAnnotation(TableColumn.class).name();

            ResultSet resultSet = statement.executeQuery(Queries.getClaimByName(operationClaimTable, nameFieldColumnName, name));
            while(resultSet.next()){
                OperationClaim operationClaim = new OperationClaim();
                loadResultSetIntoObject(resultSet, operationClaim);
                return operationClaim;
            }
        }catch (SQLException | NoSuchFieldException | IllegalAccessException exception) {
            System.err.println(exception.getMessage() + "66");
        }
        return null;
    }
}
