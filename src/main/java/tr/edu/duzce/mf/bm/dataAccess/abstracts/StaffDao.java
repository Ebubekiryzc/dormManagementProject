package tr.edu.duzce.mf.bm.dataAccess.abstracts;

import tr.edu.duzce.mf.bm.core.dataAccess.abstracts.BaseDao;
import tr.edu.duzce.mf.bm.entities.concretes.Staff;

import java.util.List;

public interface StaffDao extends BaseDao<Staff> {
    public List<Staff> getByFullName(String firstName, String lastName);

    public List<Staff> getByFirstName(String firstName);

    public List<Staff> getByLastName(String lastName);

}
