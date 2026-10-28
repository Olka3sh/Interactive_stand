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