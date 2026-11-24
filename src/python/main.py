from datetime import date, timedelta

def check_materials_deficit(orders, order_components, components):
    deficit_list = []
    total_cost = 0.0
    stock_map = {
        comp_id: comp_data["stock_quantity"]
        for comp_id, comp_data in components.items()
    }
    i = 0
    while i < len(orders):
        order = orders[i]
        items = [oc for oc in order_components if oc["id_order"] == order["id_order"]]
        j = 0
        while j < len(items):
            item = items[j]
            comp = components[item["id_component"]]
            if stock_map[item["id_component"]] < item["quantity"]:
                deficit_item = {
                    "id_order": order["id_order"],
                    "id_component": item["id_component"],
                    "deficit": item["quantity"] - stock_map[item["id_component"]],
                }
                deficit_list.append(deficit_item)
            else:
                stock_map[item["id_component"]] -= item["quantity"]
                item["cost"] = item["quantity"] * comp["price_per_unit"]
                total_cost += item["cost"]
            j += 1
        i += 1
    return deficit_list, round(total_cost, 2)

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

# функция register_production_order() - только программная реализация
def register_production_order(orders, customers, id_customer, description, created_date=None, planned_days=14):
    if id_customer not in customers:
        raise ValueError(f"Заказчик с id_customer={id_customer} не найден")
    next_id = max((order["id_order"] for order in orders), default=0) + 1
    start_date = date.fromisoformat(created_date) if created_date else date.today()
    finish_date = start_date + timedelta(days=planned_days)
    new_order = {
        "id_order": next_id,
        "created_date": start_date.isoformat(),
        "finish_date": finish_date.isoformat(),
        "status": "Зарегистрирован",
        "total_cost": 0.0,
        "description": description,
        "id_customer": id_customer,
    }
    orders.append(new_order)
    return new_order

def get_mock_data():
    customers = {
        1: {"id_customer": 1, "name": "ООО ЭкспоДизайн", "inn": "7701234567", "contact_person": "Иванов И.И.", "phone": "+7 (495) 111-22-33", "email": "ivanov@expodesign.ru"},
        2: {"id_customer": 2, "name": "ПАО ТехноФорум", "inn": "7709876543", "contact_person": "Петрова А.С.", "phone": "+7 (495) 444-55-66", "email": "petrova@technoforum.ru"},
    }
    components = {
        101: {"id_component": 101, "name": "Сенсорная панель 55\"", "type": "Электроника", "unit": "шт.", "price_per_unit": 45000.0, "stock_quantity": 5},
        102: {"id_component": 102, "name": "Вычислительный модуль Core i5", "type": "Электроника", "unit": "шт.", "price_per_unit": 38000.0, "stock_quantity": 8},
        103: {"id_component": 103, "name": "Блок питания 500W", "type": "Электроника", "unit": "шт.", "price_per_unit": 4500.0, "stock_quantity": 15},
        104: {"id_component": 104, "name": "Профиль алюминиевый", "type": "Конструктив", "unit": "м", "price_per_unit": 1200.0, "stock_quantity": 30},
        105: {"id_component": 105, "name": "Контроллер подсветки", "type": "Электроника", "unit": "шт.", "price_per_unit": 3100.0, "stock_quantity": 3},
    }
    orders = [
        {"id_order": 1, "created_date": "2026-10-10", "finish_date": "2026-10-25", "status": "В производстве", "total_cost": 0.0, "description": "Информационный сенсорный киоск", "id_customer": 1},
    ]
    order_components = [
        {"id_order": 1, "id_component": 101, "quantity": 2, "cost": 0.0},
        {"id_order": 1, "id_component": 102, "quantity": 2, "cost": 0.0},
        {"id_order": 1, "id_component": 104, "quantity": 10, "cost": 0.0},
        {"id_order": 2, "id_component": 101, "quantity": 4, "cost": 0.0},
        {"id_order": 2, "id_component": 103, "quantity": 4, "cost": 0.0},
        {"id_order": 2, "id_component": 105, "quantity": 2, "cost": 0.0},
    ]
    defects = [
        {"id_defect": 1, "description": "Люфт крепления экрана", "type": "hardware", "severity": "Критический", "found_date": "2026-10-20", "fixed_date": "2026-10-21", "status": "Устранён", "id_employee": 2, "id_operation": 1},
        {"id_defect": 2, "description": "Зависание цикла автозапуска", "type": "software", "severity": "Значительный", "found_date": "2026-10-20", "fixed_date": "2026-10-21", "status": "Устранён", "id_employee": 2, "id_operation": 1},
        {"id_defect": 3, "description": "Повреждение изоляции шлейфа", "type": "hardware", "severity": "Незначительный", "found_date": "2026-10-21", "fixed_date": "2026-10-21", "status": "Устранён", "id_employee": 2, "id_operation": 1},
        {"id_defect": 4, "description": "Ошибка отрисовки шрифта в меню", "type": "software", "severity": "Незначительный", "found_date": "2026-10-21", "fixed_date": None, "status": "Открыт", "id_employee": 2, "id_operation": 1},
    ]
    total_inspections = 10
    return customers, components, orders, order_components, defects, total_inspections


def main():
    customers, components, orders, order_components, defects, total_inspections = get_mock_data()
    new_order = register_production_order(
        orders,
        customers,
        id_customer=2,
        description="Двусторонний выставочный стенд с интерактивной витриной",
        created_date="2026-10-12",
        planned_days=16,
    )
    print("Новый заказ:", new_order)
    deficits, batch_cost = check_materials_deficit(orders, order_components, components)
    print("Себестоимость:", batch_cost)
    print("Дефицит:", deficits)
    qc_stats = analyze_defect_statistics(defects, total_inspections)
    print("Статистика дефектов:", qc_stats)



if __name__ == "__main__":
    main()