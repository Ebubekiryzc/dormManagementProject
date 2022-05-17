package tr.edu.duzce.mf.bm.business.concretes;

import tr.edu.duzce.mf.bm.business.abstracts.*;
import tr.edu.duzce.mf.bm.core.dataAccess.constants.Messages;
import tr.edu.duzce.mf.bm.core.entities.concrete.User;
import tr.edu.duzce.mf.bm.core.entities.dtos.AuthenticateUserDTO;
import tr.edu.duzce.mf.bm.core.utilities.business.BusinessRules;
import tr.edu.duzce.mf.bm.core.utilities.results.*;
import tr.edu.duzce.mf.bm.core.utilities.security.hashing.HashingHelper;
import tr.edu.duzce.mf.bm.core.utilities.security.hashing.SaltAndPepperModel;
import tr.edu.duzce.mf.bm.dataAccess.abstracts.StudentDao;
import tr.edu.duzce.mf.bm.entities.concretes.IndividualUser;
import tr.edu.duzce.mf.bm.entities.concretes.Student;
import tr.edu.duzce.mf.bm.entities.dtos.IndividualUserRegisterDto;
import tr.edu.duzce.mf.bm.entities.dtos.StudentDetailDto;
import tr.edu.duzce.mf.bm.entities.dtos.StudentRegisterDto;

import java.util.List;

public class StudentManager implements StudentService {

    private StudentDao studentDao;
    private IndividualUserService individualUserService;
    private UserService userService;
    private DepartmentService departmentService;
    private GenderService genderService;

    public StudentManager(StudentDao studentDao, IndividualUserService individualUserService, UserService userService, DepartmentService departmentService, GenderService genderService) {
        this.studentDao = studentDao;
        this.individualUserService = individualUserService;
        this.userService = userService;
        this.departmentService = departmentService;
        this.genderService = genderService;
    }

    @Override
    public DataResult<List<Student>> getAll() {
        return new SuccessDataResult<>(studentDao.getAll(), Messages.OperationSuccessful);
    }

    @Override
    public DataResult<List<StudentDetailDto>> getAllStudentDetails() {
        return new SuccessDataResult<>(studentDao.getAllStudentDetails(), Messages.OperationSuccessful);
    }

    @Override
    public DataResult<Student> getById(int id) {
        Student student = studentDao.getById(String.valueOf(id));
        var result = BusinessRules.check(isNull(student));
        if (!result.isSuccess()) {
            return (ErrorDataResult<Student>) result;
        }
        return new SuccessDataResult<>(student, Messages.OperationSuccessful);
    }

    @Override
    public Result add(Student student) {
        boolean isAdded = studentDao.add(student);
        if (isAdded) return new SuccessResult(Messages.OperationSuccessful);
        return new ErrorResult(Messages.OperationFailed);
    }

    @Override
    public Result update(StudentRegisterDto studentRegisterDto) {
        var totalResult = BusinessRules.check(userUpdate(studentRegisterDto.getIndividualUserRegisterDto().getAuthenticateUserDTO()), individualUserUpdate(studentRegisterDto.getIndividualUserRegisterDto()));
        if (!totalResult.isSuccess()) {
            return totalResult;
        }

        User user = userService.getByUsername(studentRegisterDto.getIndividualUserRegisterDto().getAuthenticateUserDTO().getUsername()).getEntity();
        Student student = new Student();
        student.setIndividualUserId(user.getId());
        student.setBlockCode(studentRegisterDto.getBlockCode());
        student.setDepartmentId(departmentService.getByFacultyIdAndName(studentRegisterDto.getFacultyId(), studentRegisterDto.getDepartmentName()).getEntity().getId());
        student.setDateOfEntry(studentRegisterDto.getDateOfEntry());
        student.setRoomNumber(studentRegisterDto.getRoomNumber());

        boolean isUpdated = studentDao.update(student);
        if (isUpdated) return new SuccessResult(Messages.OperationSuccessful);
        return new ErrorResult(Messages.OperationFailed);
    }

    @Override
    public Result delete(Student student) {
        boolean isDeleted = studentDao.delete(student);
        if (isDeleted) return new SuccessResult(Messages.OperationSuccessful);
        return new ErrorResult(Messages.OperationFailed);
    }

    @Override
    public DataResult<List<StudentDetailDto>> getByFullName(String firstName, String lastName) {
        List<StudentDetailDto> studentList = studentDao.getByFullName(firstName, lastName);
        if (studentList == null) return new ErrorDataResult<>(null, Messages.OperationFailed);
        return new SuccessDataResult<>(studentList, Messages.OperationSuccessful);
    }

    @Override
    public DataResult<List<StudentDetailDto>> getByFirstName(String firstName) {
        List<StudentDetailDto> studentList = studentDao.getByFirstName(firstName);
        if (studentList == null) return new ErrorDataResult<>(null, Messages.OperationFailed);
        return new SuccessDataResult<>(studentList, Messages.OperationSuccessful);
    }

    @Override
    public DataResult<List<StudentDetailDto>> getByLastName(String lastName) {
        List<StudentDetailDto> studentList = studentDao.getByLastName(lastName);
        if (studentList == null) return new ErrorDataResult<>(null, Messages.OperationFailed);
        return new SuccessDataResult<>(studentList, Messages.OperationSuccessful);
    }

    private DataResult<Student> isNull(Student student) {
        if (student == null) {
            return new ErrorDataResult<>(Messages.OperationFailed);
        }
        return new SuccessDataResult<>(Messages.OperationSuccessful);
    }

    private Result userUpdate(AuthenticateUserDTO authenticateUserDTO) {
        if (authenticateUserDTO.getPassword().equals("")) {
            return new SuccessResult();
        }

        SaltAndPepperModel saltAndPepperModel = new SaltAndPepperModel();
        User user = userService.getByUsername(authenticateUserDTO.getUsername()).getEntity();
        HashingHelper.generateHash(authenticateUserDTO.getPassword(), saltAndPepperModel);

        user.setUsername(authenticateUserDTO.getUsername());
        user.setPasswordSalt(saltAndPepperModel.getSalt());
        user.setPasswordHash(saltAndPepperModel.getHash());

        Result result = userService.update(user);
        return result;
    }

    private Result individualUserUpdate(IndividualUserRegisterDto individualUserRegisterDto) {
        String username = individualUserRegisterDto.getAuthenticateUserDTO().getUsername();
        User user = userService.getByUsername(username).getEntity();

        IndividualUser individualUser = new IndividualUser();
        individualUser.setUserId(user.getId());
        individualUser.setFirstName(individualUserRegisterDto.getFirstName());
        individualUser.setLastName(individualUserRegisterDto.getLastName());
        individualUser.setDayOffLimit(individualUserRegisterDto.getDayOffLimit());
        individualUser.setGenderId(genderService.getByName(individualUserRegisterDto.getGenderName()).getEntity().getId());
        Result result = individualUserService.update(individualUser);
        return result;
    }
}
