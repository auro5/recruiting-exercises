import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

class CheapShipmentsTest {

	@Test
	// Exact inventory match with order list
	void testExactInventoryMatchWithOneItemOneWarehouse() {
		// orders
		HashMap<String, Integer> orderList = new HashMap<>();
		orderList.put("apple", 1);
		
		//Warehouse Inventory
		List<InventoryDistribution> warehouseList = new ArrayList<>();
		HashMap<String, Integer> inventory = new HashMap<>() ;
		inventory.put("apple", 1);
		InventoryDistribution warehouse = new InventoryDistribution("owd", inventory);
		warehouseList.add(warehouse);
		
		//Actual output
		InventoryAllocator inventoryAllocator = new InventoryAllocator();
		Map<String, Map<String, Integer>> cheapestShipment = inventoryAllocator.getCheapestShipment(orderList, warehouseList);
		
		//Expected output
		Map<String, Map<String, Integer>> expectedCheapestShipment = new HashMap<>();
		Map<String, Integer> expectedOrderAmount = new HashMap<>();
		expectedOrderAmount.put("apple", 1);
		expectedCheapestShipment.put("owd", expectedOrderAmount);
		
		assertEquals(expectedCheapestShipment, cheapestShipment);
	}
	
	@Test
	// Exact inventory match for multiple items and multiple warehouse orders
	void testExactInventoryMatchWithMultipleItemMultipleWarehouse() {
		// orders
		HashMap<String, Integer> orderList = new HashMap<>();
		orderList.put("apple", 5);
		orderList.put("orange", 5);
		
		//Warehouse Inventory
		List<InventoryDistribution> warehouseList = new ArrayList<>();
		
		HashMap<String, Integer> inventory1 = new HashMap<>() ;
		inventory1.put("apple", 5);
		InventoryDistribution warehouse1 = new InventoryDistribution("owd", inventory1);
		warehouseList.add(warehouse1);
		
		HashMap<String, Integer> inventory2 = new HashMap<>() ;
		inventory2.put("orange", 5);
		InventoryDistribution warehouse2 = new InventoryDistribution("dm", inventory2);
		warehouseList.add(warehouse2);
		
		//Actual output
		InventoryAllocator inventoryAllocator = new InventoryAllocator();
		Map<String, Map<String, Integer>> cheapestShipment = inventoryAllocator.getCheapestShipment(orderList, warehouseList);
		
		//Expected output
		Map<String, Map<String, Integer>> expectedCheapestShipment = new HashMap<>();
		Map<String, Integer> expectedOrderAmount1 = new HashMap<>();
		expectedOrderAmount1.put("apple", 5);
		expectedCheapestShipment.put("owd", expectedOrderAmount1);
		
		Map<String, Integer> expectedOrderAmount2 = new HashMap<>();
		expectedOrderAmount2.put("orange", 5);
		expectedCheapestShipment.put("dm", expectedOrderAmount2);
		
		assertEquals(expectedCheapestShipment, cheapestShipment);
	}
	
	
	@Test
	// If multiple warehouse can satisfy the order then choose the one that is least expensive
	void testShippingItemFromLeastExpensivetWarehouse() {
		// orders
		HashMap<String, Integer> orderList = new HashMap<>();
		orderList.put("apple", 5);
		
		//Warehouse Inventory
		List<InventoryDistribution> warehouseList = new ArrayList<>();
		
		HashMap<String, Integer> inventory1 = new HashMap<>() ;
		inventory1.put("apple", 10);
		InventoryDistribution warehouse1 = new InventoryDistribution("owd", inventory1);
		warehouseList.add(warehouse1);
		
		HashMap<String, Integer> inventory2 = new HashMap<>() ;
		inventory2.put("apple", 10);
		InventoryDistribution warehouse2 = new InventoryDistribution("dm", inventory2);
		warehouseList.add(warehouse2);
		
		HashMap<String, Integer> inventory3 = new HashMap<>() ;
		inventory3.put("apple", 10);
		inventory3.put("orange", 10);
		InventoryDistribution warehouse3 = new InventoryDistribution("pq", inventory3);
		warehouseList.add(warehouse3);
		
		//Actual output
		InventoryAllocator inventoryAllocator = new InventoryAllocator();
		Map<String, Map<String, Integer>> cheapestShipment = inventoryAllocator.getCheapestShipment(orderList, warehouseList);
		
		//Expected output
		Map<String, Map<String, Integer>> expectedCheapestShipment = new HashMap<>();
		Map<String, Integer> expectedOrderAmount = new HashMap<>();
		expectedOrderAmount.put("apple", 5);
		expectedCheapestShipment.put("owd", expectedOrderAmount);
		
		assertEquals(expectedCheapestShipment, cheapestShipment);
	}
	
	@Test
	// If the warehouse Inventory is empty then no allocations are made
	void testNoItemsInInventoryNoAllocations() {
		// orders
		HashMap<String, Integer> orderList = new HashMap<>();
		orderList.put("apple", 1);
		
		//Warehouse Inventory
		List<InventoryDistribution> warehouseList = new ArrayList<>();
		
		HashMap<String, Integer> inventory1 = new HashMap<>() ;
		inventory1.put("apple", 0);
		InventoryDistribution warehouse1 = new InventoryDistribution("owd", inventory1);
		warehouseList.add(warehouse1);
		
		
		//Actual output
		InventoryAllocator inventoryAllocator = new InventoryAllocator();
		Map<String, Map<String, Integer>> cheapestShipment = inventoryAllocator.getCheapestShipment(orderList, warehouseList);
		
		//Expected output
		Map<String, Map<String, Integer>> expectedCheapestShipment = new HashMap<>();
		
		assertEquals(expectedCheapestShipment, cheapestShipment);
	}
	
	@Test
	// If the order cannot be completely shipped then ship partially
	void testPartialShipmentWhenNotEnoughInventory() {
		// orders
		HashMap<String, Integer> orderList = new HashMap<>();
		orderList.put("apple", 5);
		
		//Warehouse Inventory
		List<InventoryDistribution> warehouseList = new ArrayList<>();
		
		HashMap<String, Integer> inventory1 = new HashMap<>() ;
		inventory1.put("apple", 3);
		InventoryDistribution warehouse1 = new InventoryDistribution("owd", inventory1);
		warehouseList.add(warehouse1);
		
		
		//Actual output
		InventoryAllocator inventoryAllocator = new InventoryAllocator();
		Map<String, Map<String, Integer>> cheapestShipment = inventoryAllocator.getCheapestShipment(orderList, warehouseList);
		
		//Expected output
		Map<String, Map<String, Integer>> expectedCheapestShipment = new HashMap<>();
		Map<String, Integer> expectedOrderAmount = new HashMap<>();
		expectedOrderAmount.put("apple", 3);
		expectedCheapestShipment.put("owd", expectedOrderAmount);
		
		assertEquals(expectedCheapestShipment, cheapestShipment);
	}
	
	
	@Test
	// If order list is empty then no allocations
	void testNoOderNoAllocations() {
		// orders
		HashMap<String, Integer> orderList = new HashMap<>();
		
		//Warehouse Inventory
		List<InventoryDistribution> warehouseList = new ArrayList<>();
		
		HashMap<String, Integer> inventory1 = new HashMap<>() ;
		inventory1.put("apple", 5);
		InventoryDistribution warehouse1 = new InventoryDistribution("owd", inventory1);
		warehouseList.add(warehouse1);
		
		
		//Actual output
		InventoryAllocator inventoryAllocator = new InventoryAllocator();
		Map<String, Map<String, Integer>> cheapestShipment = inventoryAllocator.getCheapestShipment(orderList, warehouseList);
		
		//Expected output
		Map<String, Map<String, Integer>> expectedCheapestShipment = new HashMap<>();
		
		assertEquals(expectedCheapestShipment, cheapestShipment);
	}
	
	@Test
	void testNoWarehouseNoAllocations() {
		// orders
		HashMap<String, Integer> orderList = new HashMap<>();
		orderList.put("apple", 5);
		
		//Warehouse Inventory
		List<InventoryDistribution> warehouseList = new ArrayList<>();
		
		//Actual output
		InventoryAllocator inventoryAllocator = new InventoryAllocator();
		Map<String, Map<String, Integer>> cheapestShipment = inventoryAllocator.getCheapestShipment(orderList, warehouseList);
		
		//Expected output
		Map<String, Map<String, Integer>> expectedCheapestShipment = new HashMap<>();
		
		assertEquals(expectedCheapestShipment, cheapestShipment);
	}
	
	@Test
	// prefer shipping items from expensive warehouse if splitting can be avoided
	void testShipFromExpensiveWarehouseIfSplittingCanBeAvoided() {
		// orders
		HashMap<String, Integer> orderList = new HashMap<>();
		orderList.put("apple", 10);
		orderList.put("orange", 20);
		
		//Warehouse Inventory
		List<InventoryDistribution> warehouseList = new ArrayList<>();
		
		HashMap<String, Integer> inventory1 = new HashMap<>() ;
		inventory1.put("apple", 5);
		inventory1.put("orange", 20);
		InventoryDistribution warehouse1 = new InventoryDistribution("owd", inventory1);
		warehouseList.add(warehouse1);
		
		HashMap<String, Integer> inventory2 = new HashMap<>() ;
		inventory2.put("apple", 10);
		inventory2.put("orange", 20);
		InventoryDistribution warehouse2 = new InventoryDistribution("dm", inventory2);
		warehouseList.add(warehouse2);
		
		//Actual output
		InventoryAllocator inventoryAllocator = new InventoryAllocator();
		Map<String, Map<String, Integer>> cheapestShipment = inventoryAllocator.getCheapestShipment(orderList, warehouseList);
		
		//Expected output
		Map<String, Map<String, Integer>> expectedCheapestShipment = new HashMap<>();
		Map<String, Integer> expectedOrderAmount1 = new HashMap<>();
		expectedOrderAmount1.put("orange", 20);
		expectedCheapestShipment.put("owd", expectedOrderAmount1);
		
		Map<String, Integer> expectedOrderAmount2 = new HashMap<>();
		expectedOrderAmount2.put("apple", 10);
		expectedCheapestShipment.put("dm", expectedOrderAmount2);
		
		assertEquals(expectedCheapestShipment, cheapestShipment);
	}
	
	@Test
	// If an oder cannot be completely shipped from one warehouse then split it across various warehouses 
	void testSplittingOrderAcrossWarehousesOnlyIfNecessary() {
		// orders
		HashMap<String, Integer> orderList = new HashMap<>();
		orderList.put("apple", 10);
		
		//Warehouse Inventory
		List<InventoryDistribution> warehouseList = new ArrayList<>();
		
		HashMap<String, Integer> inventory1 = new HashMap<>() ;
		inventory1.put("apple", 5);
		InventoryDistribution warehouse1 = new InventoryDistribution("owd", inventory1);
		warehouseList.add(warehouse1);
		
		HashMap<String, Integer> inventory2 = new HashMap<>() ;
		inventory2.put("apple", 5);
		InventoryDistribution warehouse2 = new InventoryDistribution("dm", inventory2);
		warehouseList.add(warehouse2);
		
		//Actual output
		InventoryAllocator inventoryAllocator = new InventoryAllocator();
		Map<String, Map<String, Integer>> cheapestShipment = inventoryAllocator.getCheapestShipment(orderList, warehouseList);
		
		//Expected output
		Map<String, Map<String, Integer>> expectedCheapestShipment = new HashMap<>();
		Map<String, Integer> expectedOrderAmount1 = new HashMap<>();
		expectedOrderAmount1.put("apple", 5);
		expectedCheapestShipment.put("owd", expectedOrderAmount1);
		
		Map<String, Integer> expectedOrderAmount2 = new HashMap<>();
		expectedOrderAmount2.put("apple", 5);
		expectedCheapestShipment.put("dm", expectedOrderAmount2);
		
		assertEquals(expectedCheapestShipment, cheapestShipment);
	}
	
	@Test
	// Some items in the order are split, but the choose more items from the less expensive warehouse
	void testSplittingOrderAcrossWarehousesButSelectMoreQuantityFromLessExpensiveWarehouse() {
		// orders
		HashMap<String, Integer> orderList = new HashMap<>();
		orderList.put("apple", 10);
		orderList.put("orange", 10);
		orderList.put("mango", 10);
		
		//Warehouse Inventory
		List<InventoryDistribution> warehouseList = new ArrayList<>();
		
		HashMap<String, Integer> inventory1 = new HashMap<>() ;
		inventory1.put("apple", 7);
		inventory1.put("orange", 10);
		InventoryDistribution warehouse1 = new InventoryDistribution("owd", inventory1);
		warehouseList.add(warehouse1);
		
		HashMap<String, Integer> inventory2 = new HashMap<>() ;
		inventory2.put("apple", 5);
		inventory2.put("mango", 10);
		InventoryDistribution warehouse2 = new InventoryDistribution("dm", inventory2);
		warehouseList.add(warehouse2);
		
		//Actual output
		InventoryAllocator inventoryAllocator = new InventoryAllocator();
		Map<String, Map<String, Integer>> cheapestShipment = inventoryAllocator.getCheapestShipment(orderList, warehouseList);
		
		//Expected output
		Map<String, Map<String, Integer>> expectedCheapestShipment = new HashMap<>();
		Map<String, Integer> expectedOrderAmount1 = new HashMap<>();
		expectedOrderAmount1.put("apple", 7);
		expectedOrderAmount1.put("orange", 10);
		expectedCheapestShipment.put("owd", expectedOrderAmount1);
		
		Map<String, Integer> expectedOrderAmount2 = new HashMap<>();
		expectedOrderAmount2.put("apple", 3);
		expectedOrderAmount2.put("mango", 10);
		expectedCheapestShipment.put("dm", expectedOrderAmount2);
		
		assertEquals(expectedCheapestShipment, cheapestShipment);
	}
	
	@Test
	// Some items in the order are split, some are partially shipped 
	void testSomeItemsAreSplitAndSomeItemsAreShippedPartially() {
		// orders
		HashMap<String, Integer> orderList = new HashMap<>();
		orderList.put("apple", 10);
		orderList.put("orange", 10);
		orderList.put("mango", 10);
		orderList.put("banana", 50);
		
		//Warehouse Inventory
		List<InventoryDistribution> warehouseList = new ArrayList<>();
		
		HashMap<String, Integer> inventory1 = new HashMap<>() ;
		inventory1.put("apple", 7);
		inventory1.put("orange", 10);
		inventory1.put("banana", 15);
		InventoryDistribution warehouse1 = new InventoryDistribution("owd", inventory1);
		warehouseList.add(warehouse1);
		
		HashMap<String, Integer> inventory2 = new HashMap<>() ;
		inventory2.put("apple", 5);
		inventory2.put("mango", 10);
		InventoryDistribution warehouse2 = new InventoryDistribution("dm", inventory2);
		warehouseList.add(warehouse2);
		
		//Actual output
		InventoryAllocator inventoryAllocator = new InventoryAllocator();
		Map<String, Map<String, Integer>> cheapestShipment = inventoryAllocator.getCheapestShipment(orderList, warehouseList);
		
		//Expected output
		Map<String, Map<String, Integer>> expectedCheapestShipment = new HashMap<>();
		Map<String, Integer> expectedOrderAmount1 = new HashMap<>();
		expectedOrderAmount1.put("apple", 7);
		expectedOrderAmount1.put("orange", 10);
		expectedOrderAmount1.put("banana", 15);
		expectedCheapestShipment.put("owd", expectedOrderAmount1);
		
		Map<String, Integer> expectedOrderAmount2 = new HashMap<>();
		expectedOrderAmount2.put("apple", 3);
		expectedOrderAmount2.put("mango", 10);
		expectedCheapestShipment.put("dm", expectedOrderAmount2);
		
		assertEquals(expectedCheapestShipment, cheapestShipment);
	}
	
	@Test
	// Some items in the order are split, some are partially shipped and for some item choose little more expensive warehouse to avoid splitting 
	void testSomeOrderSplitSomeOrderPartialShipmentAndChooseExpensiveIfNoSpliting() {
		// orders
		HashMap<String, Integer> orderList = new HashMap<>();
		orderList.put("apple", 10);
		orderList.put("orange", 10);
		orderList.put("mango", 10);
		orderList.put("banana", 50);
		orderList.put("blueberries", 40);
		
		//Warehouse Inventory
		List<InventoryDistribution> warehouseList = new ArrayList<>();
		
		HashMap<String, Integer> inventory1 = new HashMap<>() ;
		inventory1.put("apple", 7);
		inventory1.put("orange", 10);
		inventory1.put("banana", 15);
		inventory1.put("blueberries", 30);
		InventoryDistribution warehouse1 = new InventoryDistribution("owd", inventory1);
		warehouseList.add(warehouse1);
		
		HashMap<String, Integer> inventory2 = new HashMap<>() ;
		inventory2.put("apple", 5);
		inventory2.put("mango", 10);
		inventory2.put("blueberries", 40);
		InventoryDistribution warehouse2 = new InventoryDistribution("dm", inventory2);
		warehouseList.add(warehouse2);
		
		//Actual output
		InventoryAllocator inventoryAllocator = new InventoryAllocator();
		Map<String, Map<String, Integer>> cheapestShipment = inventoryAllocator.getCheapestShipment(orderList, warehouseList);
		
		//Expected output
		Map<String, Map<String, Integer>> expectedCheapestShipment = new HashMap<>();
		Map<String, Integer> expectedOrderAmount1 = new HashMap<>();
		expectedOrderAmount1.put("apple", 7);
		expectedOrderAmount1.put("orange", 10);
		expectedOrderAmount1.put("banana", 15);
		expectedCheapestShipment.put("owd", expectedOrderAmount1);
		
		Map<String, Integer> expectedOrderAmount2 = new HashMap<>();
		expectedOrderAmount2.put("apple", 3);
		expectedOrderAmount2.put("mango", 10);
		expectedOrderAmount2.put("blueberries", 40);
		expectedCheapestShipment.put("dm", expectedOrderAmount2);
		
		assertEquals(expectedCheapestShipment, cheapestShipment);
	}
	
	@Test
	// Even after splitting if complete order cannot be shipped then ship it partially
	void testPartialShipmentEvenAfterSplitting() {
		// orders
		HashMap<String, Integer> orderList = new HashMap<>();
		orderList.put("apple", 10);
		orderList.put("orange", 10);
		orderList.put("mango", 500);
		
		//Warehouse Inventory
		List<InventoryDistribution> warehouseList = new ArrayList<>();
		
		HashMap<String, Integer> inventory1 = new HashMap<>() ;
		inventory1.put("apple", 7);
		inventory1.put("orange", 10);
		inventory1.put("mango", 15);
		inventory1.put("blueberries", 30);
		InventoryDistribution warehouse1 = new InventoryDistribution("owd", inventory1);
		warehouseList.add(warehouse1);
		
		HashMap<String, Integer> inventory2 = new HashMap<>() ;
		inventory2.put("apple", 5);
		inventory2.put("mango", 10);
		InventoryDistribution warehouse2 = new InventoryDistribution("dm", inventory2);
		warehouseList.add(warehouse2);
		
		HashMap<String, Integer> inventory3 = new HashMap<>() ;
		inventory3.put("mango", 100);
		InventoryDistribution warehouse3 = new InventoryDistribution("xy", inventory3);
		warehouseList.add(warehouse3);
		
		//Actual output
		InventoryAllocator inventoryAllocator = new InventoryAllocator();
		Map<String, Map<String, Integer>> cheapestShipment = inventoryAllocator.getCheapestShipment(orderList, warehouseList);
		
		//Expected output
		Map<String, Map<String, Integer>> expectedCheapestShipment = new HashMap<>();
		Map<String, Integer> expectedOrderAmount1 = new HashMap<>();
		expectedOrderAmount1.put("apple", 7);
		expectedOrderAmount1.put("orange", 10);
		expectedOrderAmount1.put("mango", 15);
		expectedCheapestShipment.put("owd", expectedOrderAmount1);
		
		Map<String, Integer> expectedOrderAmount2 = new HashMap<>();
		expectedOrderAmount2.put("apple", 3);
		expectedOrderAmount2.put("mango", 10);
		expectedCheapestShipment.put("dm", expectedOrderAmount2);
		
		Map<String, Integer> expectedOrderAmount3 = new HashMap<>();
		expectedOrderAmount3.put("mango", 100);
		expectedCheapestShipment.put("xy", expectedOrderAmount3);
		
		assertEquals(expectedCheapestShipment, cheapestShipment);
	}
}
