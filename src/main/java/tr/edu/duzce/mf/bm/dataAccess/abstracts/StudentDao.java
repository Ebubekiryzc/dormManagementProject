package tr.edu.duzce.mf.bm.dataAccess.abstracts;

import tr.edu.duzce.mf.bm.core.dataAccess.abstracts.BaseDao;
import tr.edu.duzce.mf.bm.entities.concretes.Staff;
import tr.edu.duzce.mf.bm.entities.concretes.Student;

import java.util.List;

public interface StudentDao extends BaseDao<Student> {

    public List<Student> getByFullName(String firstName, String lastName);

    public List<Student> getByFirstName(String firstName);

    public List<Student> getByLastName(String lastName);
}
