public interface CostCalculationStrategy {
    double calculateItemCost(Component component, int quantity);
    String getStrategyName();
}