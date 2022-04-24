package tr.edu.duzce.mf.bm.dataAccess.concretes.JDBCDao;

import tr.edu.duzce.mf.bm.core.dataAccess.concretes.BaseDaoJDBCRepository;
import tr.edu.duzce.mf.bm.dataAccess.abstracts.StaffTaskDao;
import tr.edu.duzce.mf.bm.entities.concretes.StaffTask;

public class JDBCStaffTaskDao extends BaseDaoJDBCRepository<StaffTask> implements StaffTaskDao {

    public JDBCStaffTaskDao() {
        super(StaffTask.class);
    }

}
