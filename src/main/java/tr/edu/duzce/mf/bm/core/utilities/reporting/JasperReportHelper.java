package tr.edu.duzce.mf.bm.core.utilities.reporting;

import lombok.Getter;
import lombok.Setter;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import tr.edu.duzce.mf.bm.core.dataAccess.constants.Messages;

import java.util.List;

public class JasperReportHelper<TEntity> {
    private List<TEntity> entities;

    public void setEntities(List<TEntity> entities) {
        this.entities = entities;
    }

    public List<TEntity> getEntities(){
        return this.entities;
    }

    public void createReport(String reportPath, String pdfPath) throws JRException {
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(entities);
        JasperReport jasperReport = JasperCompileManager.compileReport(reportPath);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);
        JasperExportManager.exportReportToPdfFile(jasperPrint, pdfPath);
    }
}
