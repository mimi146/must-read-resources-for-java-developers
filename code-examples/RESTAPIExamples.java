/**
 * Code examples for REST API Design section
 * Demonstrates RESTful API design principles, HTTP methods, status codes
 * Note: This is a conceptual example. In production, use Spring Boot or JAX-RS
 */

import java.util.*;

// Domain model
class Product {
    private String id;
    private String name;
    private double price;
    private String category;
    
    public Product(String id, String name, double price, String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
    }
    
    public String getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getCategory() { return category; }
    
    public void setName(String name) { this.name = name; }
    public void setPrice(double price) { this.price = price; }
    
    @Override
    public String toString() {
        return "Product{id='" + id + "', name='" + name + 
               "', price=" + price + ", category='" + category + "'}";
    }
}

// API Response wrapper
class ApiResponse<T> {
    private int statusCode;
    private String message;
    private T data;
    private List<String> errors;
    
    public ApiResponse(int statusCode, String message, T data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
        this.errors = new ArrayList<>();
    }
    
    public void addError(String error) {
        errors.add(error);
    }
    
    public int getStatusCode() { return statusCode; }
    public String getMessage() { return message; }
    public T getData() { return data; }
    public List<String> getErrors() { return errors; }
    
    @Override
    public String toString() {
        return "ApiResponse{statusCode=" + statusCode + 
               ", message='" + message + "', data=" + data + 
               ", errors=" + errors + "}";
    }
}

// HTTP Status Codes enum
enum HttpStatus {
    OK(200, "OK"),
    CREATED(201, "Created"),
    NO_CONTENT(204, "No Content"),
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    CONFLICT(409, "Conflict"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error");
    
    private final int code;
    private final String message;
    
    HttpStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }
    
    public int getCode() { return code; }
    public String getMessage() { return message; }
}

// RESTful Product API Controller (Conceptual)
class ProductApiController {
    private Map<String, Product> productStore = new HashMap<>();
    
    public ProductApiController() {
        // Initialize with sample data
        productStore.put("1", new Product("1", "Laptop", 999.99, "Electronics"));
        productStore.put("2", new Product("2", "Mouse", 29.99, "Electronics"));
        productStore.put("3", new Product("3", "Desk", 299.99, "Furniture"));
    }
    
    // GET /api/products - Get all products
    public ApiResponse<List<Product>> getAllProducts() {
        List<Product> products = new ArrayList<>(productStore.values());
        return new ApiResponse<>(
            HttpStatus.OK.getCode(),
            "Products retrieved successfully",
            products
        );
    }
    
    // GET /api/products/{id} - Get product by ID
    public ApiResponse<Product> getProductById(String id) {
        Product product = productStore.get(id);
        
        if (product == null) {
            ApiResponse<Product> response = new ApiResponse<>(
                HttpStatus.NOT_FOUND.getCode(),
                "Product not found",
                null
            );
            response.addError("Product with ID " + id + " does not exist");
            return response;
        }
        
        return new ApiResponse<>(
            HttpStatus.OK.getCode(),
            "Product retrieved successfully",
            product
        );
    }
    
    // GET /api/products?category={category} - Filter by category
    public ApiResponse<List<Product>> getProductsByCategory(String category) {
        List<Product> filtered = new ArrayList<>();
        for (Product p : productStore.values()) {
            if (p.getCategory().equalsIgnoreCase(category)) {
                filtered.add(p);
            }
        }
        
        return new ApiResponse<>(
            HttpStatus.OK.getCode(),
            "Filtered products retrieved",
            filtered
        );
    }
    
    // POST /api/products - Create new product
    public ApiResponse<Product> createProduct(Product product) {
        // Validation
        if (product.getName() == null || product.getName().isEmpty()) {
            ApiResponse<Product> response = new ApiResponse<>(
                HttpStatus.BAD_REQUEST.getCode(),
                "Validation failed",
                null
            );
            response.addError("Product name is required");
            return response;
        }
        
        if (productStore.containsKey(product.getId())) {
            ApiResponse<Product> response = new ApiResponse<>(
                HttpStatus.CONFLICT.getCode(),
                "Product already exists",
                null
            );
            response.addError("Product with ID " + product.getId() + " already exists");
            return response;
        }
        
        productStore.put(product.getId(), product);
        return new ApiResponse<>(
            HttpStatus.CREATED.getCode(),
            "Product created successfully",
            product
        );
    }
    
    // PUT /api/products/{id} - Update existing product
    public ApiResponse<Product> updateProduct(String id, Product updatedProduct) {
        Product existing = productStore.get(id);
        
        if (existing == null) {
            ApiResponse<Product> response = new ApiResponse<>(
                HttpStatus.NOT_FOUND.getCode(),
                "Product not found",
                null
            );
            response.addError("Cannot update non-existent product");
            return response;
        }
        
        existing.setName(updatedProduct.getName());
        existing.setPrice(updatedProduct.getPrice());
        
        return new ApiResponse<>(
            HttpStatus.OK.getCode(),
            "Product updated successfully",
            existing
        );
    }
    
    // PATCH /api/products/{id} - Partial update
    public ApiResponse<Product> partialUpdateProduct(String id, Map<String, Object> updates) {
        Product existing = productStore.get(id);
        
        if (existing == null) {
            ApiResponse<Product> response = new ApiResponse<>(
                HttpStatus.NOT_FOUND.getCode(),
                "Product not found",
                null
            );
            return response;
        }
        
        if (updates.containsKey("name")) {
            existing.setName((String) updates.get("name"));
        }
        if (updates.containsKey("price")) {
            existing.setPrice((Double) updates.get("price"));
        }
        
        return new ApiResponse<>(
            HttpStatus.OK.getCode(),
            "Product partially updated",
            existing
        );
    }
    
    // DELETE /api/products/{id} - Delete product
    public ApiResponse<Void> deleteProduct(String id) {
        Product removed = productStore.remove(id);
        
        if (removed == null) {
            ApiResponse<Void> response = new ApiResponse<>(
                HttpStatus.NOT_FOUND.getCode(),
                "Product not found",
                null
            );
            response.addError("Cannot delete non-existent product");
            return response;
        }
        
        return new ApiResponse<>(
            HttpStatus.NO_CONTENT.getCode(),
            "Product deleted successfully",
            null
        );
    }
}

// Error handling example
class ApiErrorHandler {
    public static ApiResponse<Void> handleError(Exception e) {
        ApiResponse<Void> response;
        
        if (e instanceof IllegalArgumentException) {
            response = new ApiResponse<>(
                HttpStatus.BAD_REQUEST.getCode(),
                "Bad Request",
                null
            );
        } else {
            response = new ApiResponse<>(
                HttpStatus.INTERNAL_SERVER_ERROR.getCode(),
                "Internal Server Error",
                null
            );
        }
        
        response.addError(e.getMessage());
        return response;
    }
}

public class RESTAPIExamples {
    
    public static void demonstrateRESTOperations() {
        System.out.println("=== RESTful API Design Examples ===\n");
        
        ProductApiController api = new ProductApiController();
        
        // GET all products
        System.out.println("1. GET /api/products");
        ApiResponse<List<Product>> allProducts = api.getAllProducts();
        System.out.println(allProducts);
        System.out.println("Products: " + allProducts.getData());
        
        // GET product by ID
        System.out.println("\n2. GET /api/products/1");
        ApiResponse<Product> product = api.getProductById("1");
        System.out.println(product);
        
        // GET with query parameter
        System.out.println("\n3. GET /api/products?category=Electronics");
        ApiResponse<List<Product>> filtered = api.getProductsByCategory("Electronics");
        System.out.println("Filtered products: " + filtered.getData());
        
        // POST - Create new product
        System.out.println("\n4. POST /api/products");
        Product newProduct = new Product("4", "Keyboard", 79.99, "Electronics");
        ApiResponse<Product> created = api.createProduct(newProduct);
        System.out.println(created);
        
        // PUT - Update product
        System.out.println("\n5. PUT /api/products/4");
        Product updated = new Product("4", "Mechanical Keyboard", 129.99, "Electronics");
        ApiResponse<Product> updateResponse = api.updateProduct("4", updated);
        System.out.println(updateResponse);
        
        // PATCH - Partial update
        System.out.println("\n6. PATCH /api/products/4");
        Map<String, Object> partialUpdate = new HashMap<>();
        partialUpdate.put("price", 119.99);
        ApiResponse<Product> patchResponse = api.partialUpdateProduct("4", partialUpdate);
        System.out.println(patchResponse);
        
        // DELETE product
        System.out.println("\n7. DELETE /api/products/4");
        ApiResponse<Void> deleteResponse = api.deleteProduct("4");
        System.out.println(deleteResponse);
        
        // Error cases
        System.out.println("\n8. Error Handling Examples:");
        
        // 404 Not Found
        System.out.println("\nGET /api/products/999 (Not Found)");
        ApiResponse<Product> notFound = api.getProductById("999");
        System.out.println(notFound);
        
        // 400 Bad Request
        System.out.println("\nPOST /api/products (Invalid data)");
        Product invalidProduct = new Product("5", "", 0, "");
        ApiResponse<Product> badRequest = api.createProduct(invalidProduct);
        System.out.println(badRequest);
        
        // 409 Conflict
        System.out.println("\nPOST /api/products (Duplicate)");
        Product duplicate = new Product("1", "Duplicate", 100, "Test");
        ApiResponse<Product> conflict = api.createProduct(duplicate);
        System.out.println(conflict);
    }
    
    public static void demonstrateBestPractices() {
        System.out.println("\n\n=== REST API Best Practices ===\n");
        
        System.out.println("1. Use nouns for resources: /api/products (not /api/getProducts)");
        System.out.println("2. Use HTTP methods correctly:");
        System.out.println("   - GET: Retrieve resources");
        System.out.println("   - POST: Create new resources");
        System.out.println("   - PUT: Update entire resource");
        System.out.println("   - PATCH: Partial update");
        System.out.println("   - DELETE: Remove resource");
        System.out.println("\n3. Use proper HTTP status codes:");
        System.out.println("   - 200 OK: Successful GET, PUT, PATCH");
        System.out.println("   - 201 Created: Successful POST");
        System.out.println("   - 204 No Content: Successful DELETE");
        System.out.println("   - 400 Bad Request: Invalid input");
        System.out.println("   - 404 Not Found: Resource doesn't exist");
        System.out.println("   - 500 Internal Server Error: Server error");
        System.out.println("\n4. Use query parameters for filtering: /api/products?category=Electronics");
        System.out.println("5. Version your API: /api/v1/products");
        System.out.println("6. Provide meaningful error messages");
        System.out.println("7. Use pagination for large collections: /api/products?page=1&limit=20");
    }
    
    public static void main(String[] args) {
        demonstrateRESTOperations();
        demonstrateBestPractices();
    }
}
