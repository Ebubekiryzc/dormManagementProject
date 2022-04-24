package tr.edu.duzce.mf.bm.dataAccess.concretes.JDBCDao;

import tr.edu.duzce.mf.bm.core.dataAccess.concretes.BaseDaoJDBCRepository;
import tr.edu.duzce.mf.bm.dataAccess.abstracts.DepartmentDao;
import tr.edu.duzce.mf.bm.entities.concretes.Department;

public class JDBCDepartmentDao extends BaseDaoJDBCRepository<Department> implements DepartmentDao {

    public JDBCDepartmentDao() {
        super(Department.class);
    }

}
