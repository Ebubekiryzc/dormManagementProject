package tr.edu.duzce.mf.bm.dataAccess.abstracts;

import tr.edu.duzce.mf.bm.core.dataAccess.abstracts.BaseDao;
import tr.edu.duzce.mf.bm.entities.concretes.Staff;
import tr.edu.duzce.mf.bm.entities.concretes.Student;
import tr.edu.duzce.mf.bm.entities.dtos.StudentDetailDto;

import java.util.List;

public interface StudentDao extends BaseDao<Student> {
    public List<StudentDetailDto> getAllStudentDetails();

    public List<StudentDetailDto> getByFullName(String firstName, String lastName);

    public List<StudentDetailDto> getByFirstName(String firstName);

    public List<StudentDetailDto> getByLastName(String lastName);
}
