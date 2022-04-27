package tr.edu.duzce.mf.bm.dataAccess.abstracts;

import tr.edu.duzce.mf.bm.core.dataAccess.abstracts.BaseDao;
import tr.edu.duzce.mf.bm.entities.concretes.Staff;
import tr.edu.duzce.mf.bm.entities.dtos.StaffDetailDto;

import java.util.List;

public interface StaffDao extends BaseDao<Staff> {
    public List<StaffDetailDto> getAllStaffDetails();

    public List<StaffDetailDto> getStaffDetailsByFullName(String firstName, String lastName);

    public List<StaffDetailDto> getStaffDetailsByFirstName(String firstName);

    public List<StaffDetailDto> getStaffDetailsByLastName(String lastName);

}
