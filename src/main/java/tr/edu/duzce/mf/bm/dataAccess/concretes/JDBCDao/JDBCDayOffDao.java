package tr.edu.duzce.mf.bm.dataAccess.concretes.JDBCDao;

import tr.edu.duzce.mf.bm.core.dataAccess.concretes.BaseDaoJDBCRepository;
import tr.edu.duzce.mf.bm.core.utilities.annotations.TableColumn;
import tr.edu.duzce.mf.bm.core.utilities.annotations.TableName;
import tr.edu.duzce.mf.bm.dataAccess.abstracts.DayOffDao;
import tr.edu.duzce.mf.bm.entities.concretes.DayOff;
import tr.edu.duzce.mf.bm.entities.dtos.DayOffDetailDto;
import tr.edu.duzce.mf.bm.entities.dtos.StudentDetailDto;

import java.lang.reflect.Field;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JDBCDayOffDao extends BaseDaoJDBCRepository<DayOff> implements DayOffDao {

    public JDBCDayOffDao() {
        super(DayOff.class);
    }

    @Override
    public List<DayOffDetailDto> getDayOffsWithAccessLevel(String claim, String showOnlyActive) {
        CallableStatement callableStatement = null;
        List<DayOffDetailDto> dayOffsDayOffDetailDtos = new ArrayList<>();
        try {
            callableStatement = super.getDatabaseConnection().getConnection().prepareCall("call get_day_offs_by_access_level(?, ?, ?)".toString());
            callableStatement.setString(1, claim);
            callableStatement.setString(2, showOnlyActive);
            callableStatement.registerOutParameter(3, oracle.jdbc.OracleTypes.CURSOR);
            callableStatement.execute();
            Object outParameter = callableStatement.getObject(3);
            ResultSet resultSet = (ResultSet) outParameter;

            while (resultSet.next()) {
                DayOffDetailDto dayOffDetailDto = new DayOffDetailDto();
                loadResultSetIntoObjectForDTO(resultSet, dayOffDetailDto);
                dayOffsDayOffDetailDtos.add(dayOffDetailDto);
            }
        } catch (SQLException | IllegalAccessException exception) {
            System.err.println(exception.getMessage() + "/38 JDBCDayOffDao");
            dayOffsDayOffDetailDtos = null;
        } finally {
            try {
                if (callableStatement != null) callableStatement.close();

            } catch (SQLException exception) {
                System.err.println(exception.getMessage() + "/46 JDBCDayOffDao");
                dayOffsDayOffDetailDtos = null;
            }
        }
        return dayOffsDayOffDetailDtos;
    }

    @Override
    public DayOff getDayOffWithUserIdAtRange(String dateOfStart, String dateOfEnd, String userId) {
        Statement statement = null;
        DayOff dayOff = null;
        try {
            statement = super.getDatabaseConnection().getConnection().createStatement();
            String tableName = DayOff.class.getAnnotation(TableName.class).value();
            ResultSet resultSet = statement.executeQuery(String.format("SELECT * FROM %s WHERE user_id=%s AND NOT (TO_DATE(date_of_start) > TO_DATE(\'%s\') OR TO_DATE(date_of_end) < TO_DATE(\'%s\'))", tableName, userId, dateOfEnd, dateOfStart));

            while (resultSet.next()) {
                dayOff = new DayOff();
                loadResultSetIntoObject(resultSet, dayOff);
            }

        } catch (SQLException | IllegalAccessException exception) {
            System.err.println(exception.getMessage() + "/74 JDBCDayOffDao");
            dayOff = null;
        } finally {
            try {
                if (statement != null) statement.close();

            } catch (SQLException exception) {
                System.err.println(exception.getMessage() + "/82 JDBCDayOffDao");
                dayOff = null;
            }
        }
        return dayOff;
    }

    @Override
    public List<DayOff> getDayOffsByUserId(String userId) {
        Statement statement = null;
        List<DayOff> dayOffs = new ArrayList<>();
        try {
            statement = super.getDatabaseConnection().getConnection().createStatement();
            String tableName = DayOff.class.getAnnotation(TableName.class).value();
            ResultSet resultSet = statement.executeQuery(String.format("SELECT * FROM %s WHERE user_id=%s", tableName, userId));

            while (resultSet.next()) {
                DayOff dayOff = new DayOff();
                loadResultSetIntoObject(resultSet, dayOff);
                dayOffs.add(dayOff);
            }

        } catch (SQLException | IllegalAccessException exception) {
            System.err.println(exception.getMessage() + "/74 JDBCDayOffDao");
        } finally {
            try {
                if (statement != null) statement.close();

            } catch (SQLException exception) {
                System.err.println(exception.getMessage() + "/82 JDBCDayOffDao");
            }
        }
        return dayOffs;
    }

    @Override
    public List<DayOff> getActiveDayOffsByUserId(String userId) {
        Statement statement = null;
        List<DayOff> dayOffs = new ArrayList<>();
        try {
            statement = super.getDatabaseConnection().getConnection().createStatement();
            String tableName = DayOff.class.getAnnotation(TableName.class).value();
            ResultSet resultSet = statement.executeQuery(String.format("SELECT * FROM %s d WHERE user_id=%s AND (TO_DATE(d.date_of_end) > TO_DATE(SYSDATE))", tableName, userId));

            while (resultSet.next()) {
                DayOff dayOff = new DayOff();
                loadResultSetIntoObject(resultSet, dayOff);
                dayOffs.add(dayOff);
            }

        } catch (SQLException | IllegalAccessException exception) {
            System.err.println(exception.getMessage() + "/74 JDBCDayOffDao");
        } finally {
            try {
                if (statement != null) statement.close();

            } catch (SQLException exception) {
                System.err.println(exception.getMessage() + "/82 JDBCDayOffDao");
            }
        }
        return dayOffs;
    }

    private void loadResultSetIntoObjectForDTO(ResultSet resultSet, DayOffDetailDto dayOffDetailDto) throws SQLException, IllegalAccessException {
        for (Field field : DayOffDetailDto.class.getDeclaredFields()) {
            field.setAccessible(true);

            String column = field.getName();
            if (field.getAnnotation(TableColumn.class) != null) {
                column = field.getAnnotation(TableColumn.class).name();
            }

            Object value = resultSet.getObject(column);

            if (value.getClass().getSimpleName().equals("Timestamp")) {
                value = value.toString();
            }

            field.set(dayOffDetailDto, value);
        }
    }
}
