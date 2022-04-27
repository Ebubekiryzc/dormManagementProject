package tr.edu.duzce.mf.bm.dataAccess.concretes.JDBCDao;

import tr.edu.duzce.mf.bm.core.dataAccess.concretes.BaseDaoJDBCRepository;
import tr.edu.duzce.mf.bm.core.utilities.annotations.TableColumn;
import tr.edu.duzce.mf.bm.dataAccess.abstracts.StaffDao;
import tr.edu.duzce.mf.bm.entities.concretes.Staff;
import tr.edu.duzce.mf.bm.entities.dtos.StaffDetailDto;

import java.lang.reflect.Field;
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
    public List<StaffDetailDto> getAllStaffDetails() {
        String procedureName = "get_staffs_by_first_name";
        return getByFilters(procedureName, "%");
    }

    @Override
    public List<StaffDetailDto> getStaffDetailsByFullName(String firstName, String lastName) {
        List<StaffDetailDto> staffList = new ArrayList<>();
        try {
            CallableStatement callableStatement = super.getDatabaseConnection().getConnection().prepareCall("call get_staffs_by_full_name(?,?,?)");
            callableStatement.registerOutParameter(3, oracle.jdbc.OracleTypes.CURSOR);
            callableStatement.setString(1, firstName);
            callableStatement.setString(2, lastName);
            callableStatement.execute();
            Object outParameter = callableStatement.getObject(3);
            ResultSet resultSet = (ResultSet) outParameter;

            while (resultSet.next()) {
                StaffDetailDto staff = new StaffDetailDto();
                loadResultSetIntoObjectForDTO(resultSet, staff);
                staffList.add(staff);
            }
        } catch (SQLException | IllegalAccessException exception) {
            System.err.println(exception.getMessage() + "/45 JDBCStaffDao");
            return null;
        }
        return staffList;
    }

    public List<StaffDetailDto> getStaffDetailsByFirstName(String firstName) {
        String procedureName = "get_staffs_by_first_name";
        return getByFilters(procedureName, firstName);
    }

    @Override
    public List<StaffDetailDto> getStaffDetailsByLastName(String lastName) {
        String procedureName = "get_staffs_by_last_name";
        return getByFilters(procedureName, lastName);
    }

    private List<StaffDetailDto> getByFilters(String procedureName, String parameter) {
        List<StaffDetailDto> staffList = new ArrayList<>();
        try {
            CallableStatement callableStatement = super.getDatabaseConnection().getConnection().prepareCall(String.format("call %s(?,?)", procedureName));
            callableStatement.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);
            callableStatement.setString(1, parameter);
            callableStatement.execute();
            Object outParameter = callableStatement.getObject(2);
            ResultSet resultSet = (ResultSet) outParameter;

            while (resultSet.next()) {
                StaffDetailDto staff = new StaffDetailDto();
                loadResultSetIntoObjectForDTO(resultSet, staff);
                staffList.add(staff);
            }
        } catch (SQLException | IllegalAccessException exception) {
            System.err.println(exception.getMessage() + "/74 JDBCStaffDao");
            return null;
        }
        return staffList;
    }

    private void loadResultSetIntoObjectForDTO(ResultSet resultSet, StaffDetailDto staffDetailDto) throws SQLException, IllegalAccessException {
        for (Field field : StaffDetailDto.class.getDeclaredFields()) {
            field.setAccessible(true);

            String column = field.getName();
            if (field.getAnnotation(TableColumn.class) != null) {
                column = field.getAnnotation(TableColumn.class).name();
            }

            Object value = resultSet.getObject(column);

            if (value.getClass().getSimpleName().equals("Timestamp")) {
                value = value.toString();
            }

            field.set(staffDetailDto, value);
        }
    }

}
