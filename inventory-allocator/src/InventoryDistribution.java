import java.util.HashMap;

public class InventoryDistribution {
	private String name;
	private HashMap<String, Integer> inventory;
	
	public InventoryDistribution(String name, HashMap<String, Integer> inventory) {
		this.name = name;
		this.inventory = inventory;
	}
	
	public String getName() {
		return name;
	}
	
	public HashMap<String, Integer> getInventory() {
		return inventory;
	}
}
