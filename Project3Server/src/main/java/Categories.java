import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Categories implements Serializable{
	
    private static final long serialVersionUID = 1L;


    private List<String> availableCategories;
    
    private String chosenCategory = "None";
    
    public Categories() {
        // Initialize with some example categories
        availableCategories = new ArrayList<>();
        availableCategories.add("Animals");   // 0
        availableCategories.add("Countries"); // 1
        availableCategories.add("Fruits"); 	  // 2
        // Add more categories as needed
        
    }
    
    public List<String> getAvailableCategories() {
        return availableCategories;
        
    }
    
    
    
    public void setCategory(String category) {
    	
    	chosenCategory = category;
    }
    
    public String getCategory() {
    	return chosenCategory;
    }
    
    public void removeCategory(String categoryToRemove) {
    	
    	availableCategories.removeIf(e -> e.contains(categoryToRemove));
    	
    }
    
    
    

}
