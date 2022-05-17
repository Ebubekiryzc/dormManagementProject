package tr.edu.duzce.mf.bm.dataAccess.abstracts;

import tr.edu.duzce.mf.bm.core.dataAccess.abstracts.BaseDao;
import tr.edu.duzce.mf.bm.entities.concretes.Department;

public interface DepartmentDao extends BaseDao<Department> {
    public Department getByFacultyIdAndName(String facultyId, String name);
}
