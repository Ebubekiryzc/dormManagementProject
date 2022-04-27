package tr.edu.duzce.mf.bm.dataAccess.concretes.JDBCDao;

import tr.edu.duzce.mf.bm.core.dataAccess.concretes.BaseDaoJDBCRepository;
import tr.edu.duzce.mf.bm.dataAccess.abstracts.StudentDao;
import tr.edu.duzce.mf.bm.entities.concretes.Staff;
import tr.edu.duzce.mf.bm.entities.concretes.Student;

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
    public List<Student> getByFullName(String firstName, String lastName) {
        List<Student> studentList = new ArrayList<>();
        try {
            CallableStatement callableStatement = super.getDatabaseConnection().getConnection().prepareCall("call get_students_by_full_name(?,?,?)");
            callableStatement.registerOutParameter(3, oracle.jdbc.OracleTypes.CURSOR);
            callableStatement.setString(1, firstName);
            callableStatement.setString(2, lastName);
            callableStatement.execute();
            Object outParameter = callableStatement.getObject(3);
            ResultSet resultSet = (ResultSet) outParameter;

            while(resultSet.next()){
                Student student = new Student();
                loadResultSetIntoObject(resultSet,student);
                studentList.add(student);
            }
        } catch (SQLException | IllegalAccessException exception) {
            System.err.println(exception.getMessage()+ "/37 JDBCStudentDao");
            return null;
        }
        return studentList;
    }

    public List<Student> getByFirstName(String firstName){
        String procedureName = "get_students_by_first_name";
        return getByFilters(procedureName, firstName);
    }

    @Override
    public List<Student> getByLastName(String lastName) {
        String procedureName = "get_students_by_last_name";
        return getByFilters(procedureName, lastName);
    }

    private List<Student> getByFilters(String procedureName, String parameter){
        List<Student> studentList = new ArrayList<>();
        try {
            CallableStatement callableStatement = super.getDatabaseConnection().getConnection().prepareCall(String.format("call %s(?,?)",procedureName));
            callableStatement.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);
            callableStatement.setString(1, parameter);
            callableStatement.execute();
            Object outParameter = callableStatement.getObject(2);
            ResultSet resultSet = (ResultSet) outParameter;

            while(resultSet.next()){
                Student student = new Student();
                loadResultSetIntoObject(resultSet,student);
                studentList.add(student);
            }
        } catch (SQLException | IllegalAccessException exception) {
            System.err.println(exception.getMessage()+ "/70 JDBCStudentDao");
            return null;
        }
        return studentList;
    }
}
