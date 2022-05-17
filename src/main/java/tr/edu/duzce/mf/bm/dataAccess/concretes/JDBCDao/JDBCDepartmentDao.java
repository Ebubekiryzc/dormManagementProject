package tr.edu.duzce.mf.bm.dataAccess.concretes.JDBCDao;

import tr.edu.duzce.mf.bm.core.dataAccess.concretes.BaseDaoJDBCRepository;
import tr.edu.duzce.mf.bm.core.dataAccess.constants.Queries;
import tr.edu.duzce.mf.bm.core.utilities.annotations.TableColumn;
import tr.edu.duzce.mf.bm.core.utilities.annotations.TableName;
import tr.edu.duzce.mf.bm.dataAccess.abstracts.DepartmentDao;
import tr.edu.duzce.mf.bm.entities.concretes.Department;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JDBCDepartmentDao extends BaseDaoJDBCRepository<Department> implements DepartmentDao {

    public JDBCDepartmentDao() {
        super(Department.class);
    }

    public Department getByFacultyIdAndName(String facultyId, String name) {
        Statement statement = null;
        Department department = null;
        try {
            statement = super.getDatabaseConnection().getConnection().createStatement();
            String departmentTable = Department.class.getAnnotation(TableName.class).value();
            Department departmentToSearch = new Department();
            departmentToSearch.setFacultyId(BigDecimal.valueOf(Integer.valueOf(facultyId)));
            departmentToSearch.setName(name);

            String predicate = String.join(" and ", departmentToSearch.toString().split(","));

            ResultSet resultSet = statement.executeQuery(Queries.getObjectBy(departmentTable, predicate));
            while (resultSet.next()) {
                department = new Department();
                loadResultSetIntoObject(resultSet, department);
            }
        } catch (SQLException | IllegalAccessException exception) {
            System.err.println(exception.getMessage() + "/42 DepartmentJDBCDao");
            department = null;
        } finally {
            try {
                if (statement != null)
                    statement.close();
            } catch (SQLException exception) {
                System.err.println(exception.getMessage() + "/49 DepartmentJDBCDao");
                department = null;
            }
        }
        return department;
    }

}
