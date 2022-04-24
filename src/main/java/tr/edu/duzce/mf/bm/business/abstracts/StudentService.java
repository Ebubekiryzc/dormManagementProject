package tr.edu.duzce.mf.bm.business.abstracts;

import tr.edu.duzce.mf.bm.core.utilities.results.DataResult;
import tr.edu.duzce.mf.bm.core.utilities.results.Result;
import tr.edu.duzce.mf.bm.entities.concretes.Student;

import java.util.List;

public interface StudentService {
    public DataResult<List<Student>> getAll();

    public DataResult<Student> getById(int id);

    public Result add(Student student);

    public Result update(Student student);

    public Result delete(Student student);
}
