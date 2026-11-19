import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<Integer, Customer> customers = new LinkedHashMap<>();
        customers.put(1, new Customer(1, "ООО ЭкспоДизайн", "7701234567", "Иванов И.И.", "+7 (495) 111-22-33", "ivanov@expodesign.ru"));
        customers.put(2, new Customer(2, "ПАО ТехноФорум", "7709876543", "Петрова А.С.", "+7 (495) 444-55-66", "petrova@technoforum.ru"));

        Map<Integer, Component> components = new LinkedHashMap<>();
        components.put(101, new Component(101, "Сенсорная панель 55\"", "Электроника", "шт.", 45000.0, 5));
        components.put(102, new Component(102, "Вычислительный модуль Core i5", "Электроника", "шт.", 38000.0, 8));
        components.put(103, new Component(103, "Блок питания 500W", "Электроника", "шт.", 4500.0, 15));
        components.put(104, new Component(104, "Профиль алюминиевый", "Конструктив", "м", 1200.0, 30));
        components.put(105, new Component(105, "Контроллер подсветки", "Электроника", "шт.", 3100.0, 3));

        List<Order> orders = new ArrayList<>();
        Order order1 = new Order(1, LocalDate.parse("2026-10-10"), LocalDate.parse("2026-10-25"),
                "В производстве", "Информационный сенсорный киоск", customers.get(1));
        order1.addComponent(components.get(101), 2);
        order1.addComponent(components.get(102), 2);
        order1.addComponent(components.get(104), 10);
        orders.add(order1);

        Order order2 = customers.get(2).registerProductionOrder(
                orders,
                "Двусторонний выставочный стенд с интерактивной витриной",
                LocalDate.parse("2026-10-12"),
                16
        );
        order2.addComponent(components.get(101), 4);
        order2.addComponent(components.get(103), 4);
        order2.addComponent(components.get(105), 2);

        System.out.println("Новый заказ: " + order2);

        Map<Integer, Integer> stockMap = new HashMap<>();
        for (Map.Entry<Integer, Component> entry : components.entrySet()) {
            stockMap.put(entry.getKey(), entry.getValue().getStockQuantity());
        }

        CostCalculationStrategy costStrategy = new StandardCostStrategy();
        List<Map<String, Integer>> totalDeficits = new ArrayList<>();
        double totalBatchCost = 0.0;

        for (Order order : orders) {
            totalDeficits.addAll(order.checkMaterialsDeficit(stockMap, costStrategy));
            totalBatchCost += order.getTotalCost();
        }

        System.out.println("Себестоимость: " + (Math.round(totalBatchCost * 100.0) / 100.0));
        System.out.println("Дефицит: " + totalDeficits);

        Employee qcSpecialist = new Employee(2, "Смирнов Д.В.", "Специалист ОТК", "+7 (495) 777-88-99",
                "smirnov@standpro.ru", LocalDate.parse("2024-03-15"));
        Operation qcOperation = new Operation(1, "Приёмочные испытания стенда", LocalDate.parse("2026-10-20"),
                LocalDate.parse("2026-10-21"), "Завершена", 4.0, qcSpecialist, order1);

        qcOperation.addDefect(new HardwareDefect(1, "Люфт крепления экрана", "Критический",
                LocalDate.parse("2026-10-20"), LocalDate.parse("2026-10-21"), "Устранён", qcSpecialist, qcOperation));
        qcOperation.addDefect(new SoftwareDefect(2, "Зависание цикла автозапуска", "Значительный",
                LocalDate.parse("2026-10-20"), LocalDate.parse("2026-10-21"), "Устранён", qcSpecialist, qcOperation));
        qcOperation.addDefect(new HardwareDefect(3, "Повреждение изоляции шлейфа", "Незначительный",
                LocalDate.parse("2026-10-21"), LocalDate.parse("2026-10-21"), "Устранён", qcSpecialist, qcOperation));
        qcOperation.addDefect(new SoftwareDefect(4, "Ошибка отрисовки шрифта в меню", "Незначительный",
                LocalDate.parse("2026-10-21"), null, "Открыт", qcSpecialist, qcOperation));

        int totalInspections = 10;
        Map<String, Object> qcStats = qcOperation.analyzeDefectStatistics(totalInspections);
        System.out.println("Статистика дефектов: " + qcStats);
    }
}