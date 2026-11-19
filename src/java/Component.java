public class Component {
    private final int idComponent;
    private String name;
    private String type;
    private String unit;
    private double pricePerUnit;
    private int stockQuantity;

    public Component(int idComponent, String name, String type, String unit, double pricePerUnit, int stockQuantity) {
        this.idComponent = idComponent;
        this.name = name;
        this.type = type;
        this.unit = unit;
        this.pricePerUnit = pricePerUnit;
        this.stockQuantity = stockQuantity;
    }

    public boolean isAvailable(int availableStock, int requiredQuantity) {
        return availableStock >= requiredQuantity;
    }

    public int getIdComponent() { return idComponent; }
    public String getName() { return name; }
    public String getType() { return type; }
    public String getUnit() { return unit; }
    public double getPricePerUnit() { return pricePerUnit; }
    public int getStockQuantity() { return stockQuantity; }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
}