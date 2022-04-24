package tr.edu.duzce.mf.bm.dataAccess.concretes.JDBCDao;

import tr.edu.duzce.mf.bm.core.dataAccess.concretes.BaseDaoJDBCRepository;
import tr.edu.duzce.mf.bm.dataAccess.abstracts.FacultyDao;
import tr.edu.duzce.mf.bm.entities.concretes.Faculty;


public class JDBCFacultyDao extends BaseDaoJDBCRepository<Faculty> implements FacultyDao {

    public JDBCFacultyDao() {
        super(Faculty.class);
    }

}
