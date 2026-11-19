import java.time.LocalDate;

public class HardwareDefect extends Defect {
    public HardwareDefect(int idDefect, String description, String severity, LocalDate foundDate,
                          LocalDate fixedDate, String status, Employee employee, Operation operation) {
        super(idDefect, description, severity, foundDate, fixedDate, status, employee, operation);
    }

    @Override
    public String getDefectCategory() {
        return "hardware";
    }

    @Override
    public String getRoutingTarget() {
        return "Возврат сборщику (монтажнику) на переборку и коммутацию";
    }
}
