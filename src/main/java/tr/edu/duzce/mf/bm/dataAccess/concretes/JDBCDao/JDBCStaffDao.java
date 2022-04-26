package tr.edu.duzce.mf.bm.dataAccess.concretes.JDBCDao;

import tr.edu.duzce.mf.bm.core.dataAccess.concretes.BaseDaoJDBCRepository;
import tr.edu.duzce.mf.bm.dataAccess.abstracts.StaffDao;
import tr.edu.duzce.mf.bm.entities.concretes.IndividualUser;
import tr.edu.duzce.mf.bm.entities.concretes.Staff;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCStaffDao extends BaseDaoJDBCRepository<Staff> implements StaffDao {
    public JDBCStaffDao() {
        super(Staff.class);
    }

    @Override
    public List<Staff> getByFullName(String firstName, String lastName) {
        List<Staff> staffList = new ArrayList<>();
        try {
            CallableStatement callableStatement = super.getDatabaseConnection().getConnection().prepareCall("call get_staffs_by_full_name(?,?,?)");
            callableStatement.registerOutParameter(3, oracle.jdbc.OracleTypes.CURSOR);
            callableStatement.setString(1, firstName);
            callableStatement.setString(2, lastName);
            callableStatement.execute();
            Object outParameter = callableStatement.getObject(3);
            ResultSet resultSet = (ResultSet) outParameter;

            while(resultSet.next()){
                Staff staff = new Staff();
                loadResultSetIntoObject(resultSet,staff);
                staffList.add(staff);
            }
        } catch (SQLException | IllegalAccessException exception) {
            System.err.println(exception.getMessage()+ "/37 JDBCStaffDao");
            return null;
        }
        return staffList;
    }

    public List<Staff> getByFirstName(String firstName){
        String procedureName = "get_staffs_by_first_name";
        return getByFilters(procedureName, firstName);
    }

    @Override
    public List<Staff> getByLastName(String lastName) {
        String procedureName = "get_staffs_by_last_name";
        return getByFilters(procedureName, lastName);
    }

    private List<Staff> getByFilters(String procedureName, String parameter){
        List<Staff> staffList = new ArrayList<>();
        try {
            CallableStatement callableStatement = super.getDatabaseConnection().getConnection().prepareCall(String.format("call %s(?,?)",procedureName));
            callableStatement.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);
            callableStatement.setString(1, parameter);
            callableStatement.execute();
            Object outParameter = callableStatement.getObject(2);
            ResultSet resultSet = (ResultSet) outParameter;

            while(resultSet.next()){
                Staff staff = new Staff();
                loadResultSetIntoObject(resultSet,staff);
                staffList.add(staff);
            }
        } catch (SQLException | IllegalAccessException exception) {
            System.err.println(exception.getMessage()+ "/70 JDBCStaffDao");
            return null;
        }
        return staffList;
    }

}
