package tr.edu.duzce.mf.bm.dataAccess.concretes.JDBCDao;

import tr.edu.duzce.mf.bm.core.dataAccess.concretes.BaseDaoJDBCRepository;
import tr.edu.duzce.mf.bm.dataAccess.abstracts.StudentDao;
import tr.edu.duzce.mf.bm.entities.concretes.Student;

public class JDBCStudentDao extends BaseDaoJDBCRepository<Student> implements StudentDao {
    public JDBCStudentDao() {
        super(Student.class);
    }
}
