package tr.edu.duzce.mf.bm.business.abstracts;

import tr.edu.duzce.mf.bm.core.utilities.results.DataResult;
import tr.edu.duzce.mf.bm.core.utilities.results.Result;
import tr.edu.duzce.mf.bm.entities.concretes.Student;
import tr.edu.duzce.mf.bm.entities.dtos.StudentDetailDto;

import java.util.List;

public interface StudentService {
    public DataResult<List<Student>> getAll();

    public DataResult<List<StudentDetailDto>> getAllStudentDetails();

    public DataResult<Student> getById(int id);

    public Result add(Student student);

    public Result update(Student student);

    public Result delete(Student student);

    public DataResult<List<StudentDetailDto>> getByFullName(String firstName, String lastName);

    public DataResult<List<StudentDetailDto>> getByFirstName(String firstName);

    public DataResult<List<StudentDetailDto>> getByLastName(String lastName);
}
