import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Operation {
    private final int idOperation;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private double hours;
    private Employee employee;
    private Order order;
    private final List<Defect> defects;

    public Operation(int idOperation, String name, LocalDate startDate, LocalDate endDate,
                     String status, double hours, Employee employee, Order order) {
        this.idOperation = idOperation;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.hours = hours;
        this.employee = employee;
        this.order = order;
        this.defects = new ArrayList<>();
    }

    public void addDefect(Defect defect) {
        defects.add(defect);
    }

    public Map<String, Object> analyzeDefectStatistics(int totalInspections) {
        Map<String, Object> stats = new LinkedHashMap<>();
        if (totalInspections <= 0) {
            stats.put("defect_rate", 0.0);
            stats.put("hw_ratio", 0.0);
            stats.put("sw_ratio", 0.0);
            stats.put("total_defects", 0);
            return stats;
        }

        int hwCount = 0;
        int swCount = 0;
        for (Defect defect : defects) {
            if ("hardware".equalsIgnoreCase(defect.getDefectCategory())) {
                hwCount++;
            } else {
                swCount++;
            }
        }

        int totalDefects = defects.size();
        double defectRate = Math.round(((double) totalDefects / totalInspections) * 10000.0) / 100.0;
        double hwRatio = (totalDefects > 0) ? Math.round(((double) hwCount / totalDefects) * 1000.0) / 1000.0 : 0.0;
        double swRatio = (totalDefects > 0) ? Math.round(((double) swCount / totalDefects) * 1000.0) / 1000.0 : 0.0;

        stats.put("defect_rate", defectRate);
        stats.put("hw_ratio", hwRatio);
        stats.put("sw_ratio", swRatio);
        stats.put("hw_count", hwCount);
        stats.put("sw_count", swCount);
        stats.put("total_defects", totalDefects);
        return stats;
    }

    public List<Defect> getDefects() {
        return Collections.unmodifiableList(defects);
    }

    public int getIdOperation() { return idOperation; }
    public String getName() { return name; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public String getStatus() { return status; }
    public double getHours() { return hours; }
    public Employee getEmployee() { return employee; }
    public Order getOrder() { return order; }
}