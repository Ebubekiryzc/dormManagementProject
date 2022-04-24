package tr.edu.duzce.mf.bm.dataAccess.concretes.JDBCDao;

import tr.edu.duzce.mf.bm.core.dataAccess.concretes.BaseDaoJDBCRepository;
import tr.edu.duzce.mf.bm.dataAccess.abstracts.StaffDao;
import tr.edu.duzce.mf.bm.entities.concretes.Staff;

public class JDBCStaffDao extends BaseDaoJDBCRepository<Staff> implements StaffDao {
    public JDBCStaffDao() {
        super(Staff.class);
    }
}
