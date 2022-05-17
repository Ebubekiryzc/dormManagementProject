package tr.edu.duzce.mf.bm.business.abstracts;

import tr.edu.duzce.mf.bm.core.utilities.results.Result;

import java.io.InputStream;

public interface DatabaseHelper {
    public Result backupDatabase();

    public Result recoverDatabase();

    public Result importStaffsToDatabase(String filePath);

    public Result exportStaffsFromDatabase(String filePath);

    public Result importStudentsToDatabase(String filePath);

    public Result exportStudentsFromDatabase(String filePath);

    public Result importGendersToDatabase(String filePath);

    public Result exportGendersFromDatabase(String filePath);

    public Result uploadData(InputStream inputStream, String filePath);
}
