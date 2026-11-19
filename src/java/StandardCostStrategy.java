public class StandardCostStrategy implements CostCalculationStrategy {
    @Override
    public double calculateItemCost(Component component, int quantity) {
        return component.getPricePerUnit() * quantity;
    }

    @Override
    public String getStrategyName() {
        return "Стандартный расчёт по прайс-листу";
    }
}
