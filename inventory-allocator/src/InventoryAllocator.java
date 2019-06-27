import java.util.*;

public class InventoryAllocator {
	
	public Map<String, Map<String, Integer>> getCheapestShipment(HashMap<String, Integer> orderList, List<InventoryDistribution> warehouseList){
		
		Map<String, Map<String, Integer>> shipmentOrder = new HashMap<>();
		
		if(orderList.size() == 0 || warehouseList.size() == 0) // empty orders or empty warehouse lists, return empty shipment order. 
			return shipmentOrder;
		
		// for orders that can be completely satisfied from any warehouse without splitting 
		cheapestShipmentWithOutSplit(shipmentOrder, orderList, warehouseList);
		
		// for orders that require splitting or partial fulfillment(if order quantity greater than sum of all inventory of that item in warehouses) 
		cheapestShipmentWithSplit(shipmentOrder, orderList, warehouseList);
		
		return shipmentOrder;
	}


	private void cheapestShipmentWithOutSplit(Map<String, Map<String, Integer>> shipmentOrder, HashMap<String, Integer> orderList, List<InventoryDistribution> warehouseList) {

		for(InventoryDistribution warehouse: warehouseList) {
			Map<String, Integer> inventory = warehouse.getInventory();
			String name = warehouse.getName();
			
			for(String item: orderList.keySet()) {
				int requiredItemQuantity = orderList.get(item);
				
				if(requiredItemQuantity == 0)
					continue;
				
				if(inventory.containsKey(item)) {
					int availableItemQuantity = inventory.get(item);
					
					if(availableItemQuantity != 0) {
						if(availableItemQuantity >= requiredItemQuantity) {
							formatResultList(shipmentOrder, name, item, requiredItemQuantity);
							orderList.put(item, 0);// update order map
							inventory.put(item, availableItemQuantity - requiredItemQuantity); //update warehouseInventory
						}
					}
				}
			}
		}
		
	}
	
	
	private void cheapestShipmentWithSplit(Map<String, Map<String, Integer>> shipmentOrder, HashMap<String, Integer> orderList, List<InventoryDistribution> warehouseList) {
		
		for(InventoryDistribution warehouse: warehouseList) {
			Map<String, Integer> inventory = warehouse.getInventory();
			String name = warehouse.getName();
			
			for(String item: orderList.keySet()) {
				int requiredItemQuantity = orderList.get(item);
				
				if(requiredItemQuantity == 0)
					continue;
				
				if(inventory.containsKey(item)) {
					int availableItemQuantity = inventory.get(item);
					
					if(availableItemQuantity != 0) {
						if(availableItemQuantity >= requiredItemQuantity) {
							formatResultList(shipmentOrder, name, item, requiredItemQuantity);
							orderList.put(item, 0);// update order list
							inventory.put(item, availableItemQuantity - requiredItemQuantity); //update warehouse inventory
						}
						else {//split orders
							formatResultList(shipmentOrder, name, item, availableItemQuantity);
							orderList.put(item, requiredItemQuantity - availableItemQuantity);// update order list
							inventory.put(item, 0);//update warehouse inventory
						}
					}
				}
			}
		}
	}


	private void formatResultList(Map<String, Map<String, Integer>> shipmentOrder, String name, String item, int requiredItemQuantity) {
		
		if(shipmentOrder.containsKey(name)) { // if shipment list has an entry for the warehouse then update the shipment inventory of warehouse
			Map<String, Integer> tempInventory = shipmentOrder.get(name);
			tempInventory.put(item, requiredItemQuantity);
			
			shipmentOrder.put(name, tempInventory);
		}
		else { 
			Map<String, Integer> tempInventory = new HashMap<>();
			tempInventory.put(item, requiredItemQuantity);
			
			shipmentOrder.put(name, tempInventory);
		}
	} 
	
	
}
