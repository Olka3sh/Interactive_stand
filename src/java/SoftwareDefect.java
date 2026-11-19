import java.time.LocalDate;

public class SoftwareDefect extends Defect {
    public SoftwareDefect(int idDefect, String description, String severity, LocalDate foundDate,
                          LocalDate fixedDate, String status, Employee employee, Operation operation) {
        super(idDefect, description, severity, foundDate, fixedDate, status, employee, operation);
    }

    @Override
    public String getDefectCategory() {
        return "software";
    }

    @Override
    public String getRoutingTarget() {
        return "Возврат программисту на переустановку и отладку ПО";
    }
}
