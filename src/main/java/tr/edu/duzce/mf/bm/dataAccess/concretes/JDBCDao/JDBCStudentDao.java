package tr.edu.duzce.mf.bm.dataAccess.concretes.JDBCDao;

import tr.edu.duzce.mf.bm.core.dataAccess.concretes.BaseDaoJDBCRepository;
import tr.edu.duzce.mf.bm.core.utilities.annotations.TableColumn;
import tr.edu.duzce.mf.bm.dataAccess.abstracts.StudentDao;
import tr.edu.duzce.mf.bm.entities.concretes.Student;
import tr.edu.duzce.mf.bm.entities.dtos.StudentDetailDto;

import java.lang.reflect.Field;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCStudentDao extends BaseDaoJDBCRepository<Student> implements StudentDao {
    public JDBCStudentDao() {
        super(Student.class);
    }

    @Override
    public List<StudentDetailDto> getAllStudentDetails() {
        String procedureName = "get_students_by_first_name";
        return getByFilters(procedureName, "%");
    }

    @Override
    public List<StudentDetailDto> getByFullName(String firstName, String lastName) {
        List<StudentDetailDto> studentList = new ArrayList<>();
        CallableStatement callableStatement = null;
        try {
            callableStatement = super.getDatabaseConnection().getConnection().prepareCall("call get_students_by_full_name(?,?,?)");
            callableStatement.registerOutParameter(3, oracle.jdbc.OracleTypes.CURSOR);
            callableStatement.setString(1, firstName);
            callableStatement.setString(2, lastName);
            callableStatement.execute();
            Object outParameter = callableStatement.getObject(3);
            ResultSet resultSet = (ResultSet) outParameter;

            while (resultSet.next()) {
                StudentDetailDto student = new StudentDetailDto();
                loadResultSetIntoObjectForDTO(resultSet, student);
                studentList.add(student);
            }
        } catch (SQLException | IllegalAccessException exception) {
            System.err.println(exception.getMessage() + "/46 JDBCStudentDao");
            studentList = null;
        } finally {
            try {
                if (callableStatement != null)
                    callableStatement.close();

            } catch (SQLException exception) {
                System.err.println(exception.getMessage() + "/54 JDBCStudentDao");
                studentList = null;
            }
        }
        return studentList;
    }

    public List<StudentDetailDto> getByFirstName(String firstName) {
        String procedureName = "get_students_by_first_name";
        return getByFilters(procedureName, firstName);
    }

    @Override
    public List<StudentDetailDto> getByLastName(String lastName) {
        String procedureName = "get_students_by_last_name";
        return getByFilters(procedureName, lastName);
    }

    private List<StudentDetailDto> getByFilters(String procedureName, String parameter) {
        List<StudentDetailDto> studentList = new ArrayList<>();
        CallableStatement callableStatement = null;
        try {
            callableStatement = super.getDatabaseConnection().getConnection().prepareCall(String.format("call %s(?,?)", procedureName));
            callableStatement.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);
            callableStatement.setString(1, parameter);
            callableStatement.execute();
            Object outParameter = callableStatement.getObject(2);
            ResultSet resultSet = (ResultSet) outParameter;

            while (resultSet.next()) {
                StudentDetailDto student = new StudentDetailDto();
                loadResultSetIntoObjectForDTO(resultSet, student);
                studentList.add(student);
            }
        } catch (SQLException | IllegalAccessException exception) {
            System.err.println(exception.getMessage() + "/89 JDBCStudentDao");
            studentList = null;
        } finally {
            try {
                if (callableStatement != null)
                    callableStatement.close();

            } catch (SQLException exception) {
                System.err.println(exception.getMessage() + "/97 JDBCStudentDao");
                studentList = null;
            }
        }
        return studentList;
    }

    private void loadResultSetIntoObjectForDTO(ResultSet resultSet, StudentDetailDto studentDetailDto) throws SQLException, IllegalAccessException {
        for (Field field : StudentDetailDto.class.getDeclaredFields()) {
            field.setAccessible(true);

            String column = field.getName();
            if (field.getAnnotation(TableColumn.class) != null) {
                column = field.getAnnotation(TableColumn.class).name();
            }

            Object value = resultSet.getObject(column);

            if (value.getClass().getSimpleName().equals("Timestamp")) {
                value = value.toString();
            }

            field.set(studentDetailDto, value);
        }
    }
}
