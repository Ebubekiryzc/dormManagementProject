package tr.edu.duzce.mf.bm.business.abstracts;

import tr.edu.duzce.mf.bm.core.utilities.results.Result;

public interface DatabaseHelper {
    public Result backupDatabase();

    public Result recoverDatabase();

    public Result importStaffsToDatabase(String filePath);

    public Result exportStaffsFromDatabase(String filePath);

    public Result importStudentsToDatabase(String filePath);

    public Result exportStudentsFromDatabase(String filePath);
}
