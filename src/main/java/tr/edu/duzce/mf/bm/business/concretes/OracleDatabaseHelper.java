package tr.edu.duzce.mf.bm.business.concretes;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import tr.edu.duzce.mf.bm.business.abstracts.*;
import tr.edu.duzce.mf.bm.core.dataAccess.DatabaseConnection;
import tr.edu.duzce.mf.bm.core.dataAccess.constants.Messages;
import tr.edu.duzce.mf.bm.core.entities.concrete.OperationClaim;
import tr.edu.duzce.mf.bm.core.entities.concrete.User;
import tr.edu.duzce.mf.bm.core.utilities.annotations.Id;
import tr.edu.duzce.mf.bm.core.utilities.annotations.InheritedId;
import tr.edu.duzce.mf.bm.core.utilities.results.ErrorResult;
import tr.edu.duzce.mf.bm.core.utilities.results.Result;
import tr.edu.duzce.mf.bm.core.utilities.results.SuccessResult;
import tr.edu.duzce.mf.bm.entities.concretes.IndividualUser;
import tr.edu.duzce.mf.bm.entities.concretes.Student;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

//TODO: Sabah buradan devam edip recovery işlemi halledilecek. Bunun dışında yalnızca tabloların çekilmesi için kod yazılabilir. Yalnızca user'lar ve claim'ler
public class OracleDatabaseHelper implements DatabaseHelper {

    private UserService userService;
    private IndividualUserService individualUserService;
    private StaffService staffService;
    private StudentService studentService;
    private OperationClaimService operationClaimService;

    public OracleDatabaseHelper(UserService userService, IndividualUserService individualUserService, StaffService staffService, StudentService studentService, OperationClaimService operationClaimService) {
        this.userService = userService;
        this.individualUserService = individualUserService;
        this.staffService = staffService;
        this.studentService = studentService;
        this.operationClaimService = operationClaimService;
    }

    @Override
    public Result backupDatabase() {
        try {
            String username = DatabaseConnection.getInstance().getUsername();
            String password = DatabaseConnection.getInstance().getPassword();
            String command = String.format("cmd /c expdp %s/%s directory=oracle_full dumpfile=oracle_full_backup.dmp logfile=oracle_full_backup.log full=y reuse_dumpfiles=y", username, password);
            Runtime.getRuntime().exec(command);
        } catch (IOException exception) {
            System.err.println(exception.getMessage() + "/52 OracleDatabaseHelper");
            return new ErrorResult(Messages.BackupFailed);
        }
        return new SuccessResult(Messages.OperationSuccessful);
    }

    @Override
    public Result recoverDatabase() {
        try {
            String username = DatabaseConnection.getInstance().getUsername();
            String password = DatabaseConnection.getInstance().getPassword();
            String command = String.format("cmd /c impdp %s/%s directory=oracle_full dumpfile=oracle_full_backup.dmp logfile=oracle_full_backup.log full=y", username, password);
            Runtime.getRuntime().exec(command);
        } catch (IOException exception) {
            System.err.println(exception.getMessage() + "/66 OracleDatabaseHelper");
            return new ErrorResult(Messages.BackupFailed);
        }
        return new SuccessResult(Messages.OperationSuccessful);
    }

    @Override
    public Result importStaffsToDatabase(String filePath) {
        return null;
    }

    @Override
    public Result exportStaffsFromDatabase(String filePath) {
        return null;
    }

    @Override
    public Result importStudentsToDatabase(String filePath) {
        return null;
    }

    @Override
    public Result exportStudentsFromDatabase(String filePath) {
        List<Student> students = studentService.getAll().getEntity();
        List<IndividualUser> individualUsers = new ArrayList<>();
        List<User> users = new ArrayList<>();
        List<OperationClaim> operationClaims;

        for (Student student : students) {
            IndividualUser individualUser = individualUserService.getById(student.getIndividualUserId().intValue()).getEntity();
            individualUsers.add(individualUser);

            User user = userService.getById(individualUser.getUserId().intValue()).getEntity();
            users.add(user);

            operationClaims = operationClaimService.getUserClaims(user).getEntity();
        }
        var result = exportToCSV(filePath, students, Student.class);
        if (!result.isSuccess()) System.err.println("Hata oluştu /88 OracleDatabaseHelper");
        return result;
    }


    private <TEntity> Result exportToCSV(String filePath, List<TEntity> entityList, Class<TEntity> entityClass) {
        try {
            Writer writer = new FileWriter(filePath);

            ColumnPositionMappingStrategy mappingStrategy = new ColumnPositionMappingStrategy();
            mappingStrategy.setType(entityClass);

            List<String> columnList = getColumnsForEntity(entityClass);
            String[] columns = new String[columnList.size()];
            columnList.toArray(columns);

            mappingStrategy.setColumnMapping(columns);

            StatefulBeanToCsvBuilder<TEntity> builder = new StatefulBeanToCsvBuilder<>(writer);
            StatefulBeanToCsv beanWriter = builder.withMappingStrategy(mappingStrategy).build();
            beanWriter.write(entityList);

            writer.close();
        } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException exception) {
            System.err.println(exception.getMessage() + "/96 OracleDatabaseHelper");
            return new ErrorResult(Messages.OperationFailed);
        }
        return new SuccessResult(Messages.OperationSuccessful);
    }

    private <TEntity> List<String> getColumnsForEntity(Class<TEntity> entityClass) {
        List<String> fields = new ArrayList<>();
        try {
            for (var field : entityClass.getDeclaredFields()) {
                if (field.getAnnotation(Id.class) != null || field.getAnnotation(InheritedId.class) != null) continue;

                String fieldName = field.getName();
                fields.add(fieldName);
            }
        } catch (Exception exception) {
            System.err.println(exception.getMessage() + "/123 OracleDatabaseHelper");
        }
        return fields;
    }
}
