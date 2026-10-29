def check_materials_deficit(orders, stock):
    deficit_list = []
    total_cost = 0.0
    current_stock = stock.copy()
    i = 0
    while i < len(orders):
        order = orders[i]
        order_id = order.get("id_order")
        components = order.get("components", [])
        j = 0
        while j < len(components):
            item = components[j]
            comp_id = item.get("id_component")
            required_qty = item.get("quantity", 0)
            price = item.get("price_per_unit", 0.0)
            available_qty = current_stock.get(comp_id, 0)
            if available_qty < required_qty:
                deficit_item = {
                    "id_order": order_id,
                    "id_component": comp_id,
                    "component_name": item.get("name", "Неизвестно"),
                    "required": required_qty,
                    "available": available_qty,
                    "deficit": required_qty - available_qty,
                }
                deficit_list.append(deficit_item)
            else:
                current_stock[comp_id] = available_qty - required_qty
                total_cost += required_qty * price
            j += 1
        i += 1
    return deficit_list, round(total_cost, 2), current_stock

def analyze_defect_statistics(defects, total_inspections):
    if total_inspections <= 0:
        return {
            "defect_rate": 0.0,
            "hw_ratio": 0.0,
            "sw_ratio": 0.0,
            "total_defects": 0,
        }
    hw_count = 0
    sw_count = 0
    k = 0
    while k < len(defects):
        defect = defects[k]
        defect_type = defect.get("type", "").lower()
        if defect_type == "hardware":
            hw_count += 1
        else:
            sw_count += 1
        k += 1
    total_defects = len(defects)
    defect_rate = (total_defects / total_inspections) * 100.0
    if total_defects > 0:
        hw_ratio = hw_count / total_defects
        sw_ratio = sw_count / total_defects
    else:
        hw_ratio = 0.0
        sw_ratio = 0.0
    return {
        "defect_rate": round(defect_rate, 2),
        "hw_ratio": round(hw_ratio, 3),
        "sw_ratio": round(sw_ratio, 3),
        "hw_count": hw_count,
        "sw_count": sw_count,
        "total_defects": total_defects,
    }


def get_mock_data():
    stock_data = {
        101: 5,   # Сенсорная панель 55"
        102: 8,   # Вычислительный модуль Core i5
        103: 15,  # Блок питания 500W
        104: 30,  # Профиль алюминиевый (м)
        105: 3,   # Контроллер подсветки
    }
    orders_data = [
        {
            "id_order": 1,
            "order_number": "ORD-2026-001",
            "customer_name": "ООО ЭкспоДизайн",
            "components": [
                {"id_component": 101, "name": "Сенсорная панель 55\"", "quantity": 2, "price_per_unit": 45000.0},
                {"id_component": 102, "name": "Вычислительный модуль Core i5", "quantity": 2, "price_per_unit": 38000.0},
                {"id_component": 104, "name": "Профиль алюминиевый (м)", "quantity": 10, "price_per_unit": 1200.0},
            ],
        },
        {
            "id_order": 2,
            "order_number": "ORD-2026-002",
            "customer_name": "ПАО ТехноФорум",
            "components": [
                {"id_component": 101, "name": "Сенсорная панель 55\"", "quantity": 4, "price_per_unit": 45000.0},
                {"id_component": 103, "name": "Блок питания 500W", "quantity": 4, "price_per_unit": 4500.0},
                {"id_component": 105, "name": "Контроллер подсветки", "quantity": 2, "price_per_unit": 3100.0},
            ],
        },
    ]
    defects_data = [
        {"id_defect": 1, "type": "hardware", "severity": "Критический", "description": "Люфт крепления экрана"},
        {"id_defect": 2, "type": "software", "severity": "Значительный", "description": "Зависание цикла автозапуска"},
        {"id_defect": 3, "type": "hardware", "severity": "Незначительный", "description": "Повреждение изоляции шлейфа"},
        {"id_defect": 4, "type": "software", "severity": "Незначительный", "description": "Ошибка отрисовки шрифта в меню"},
    ]
    total_inspections = 10
    return stock_data, orders_data, defects_data, total_inspections


def main():
    stock, orders, defects, total_inspections = get_mock_data()
    deficits, batch_cost, _ = check_materials_deficit(orders, stock)
    print("Себестоимость:", batch_cost)
    print("Дефицит:", deficits)
    qc_stats = analyze_defect_statistics(defects, total_inspections)
    print("Статистика дефектов:", qc_stats)



if __name__ == "__main__":
    main()