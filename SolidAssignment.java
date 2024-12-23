import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Main {
    public static void main(String[] args) {
        Product product1 = new Product("Product1", 100, 10);
        Product product2 = new Product("Product2", 200, 20);
        Product product3 = new Product("Product3", 300, 30);
        Product product4 = new Product("Product4", 400, 40);
        Product product5 = new Product("Product5", 500, 50);
        Product product6 = new Product("Product6", 600, 60);
        Product product7 = new Product("Product7", 700, 70);
        Product product8 = new Product("Product8", 800, 80);
        Product product9 = new Product("Product9", 900, 90);
        Product product10 = new Product("Product10", 1000, 100);

        Customer customer1 = new Customer("Customer1", "customer1@gmail", "Address1");
        Customer customer2 = new Customer("Customer2", "customer2@gmail", "Address2");
        Customer customer3 = new Customer("Customer3", "customer3@gmail", "Address3");
        Set<Product> products = new HashSet<>();
        products.add(product1);
        products.add(product2);
        products.add(product3);
        products.add(product4);
        products.add(product5);
        products.add(product6);
        products.add(product7);
        products.add(product8);
        products.add(product9);
        products.add(product10);
        ProductManager productManager = new ProductManager(products);

        Order order1 = new Order(Set.of(product1, product2), customer1, productManager);
        Order order2 = new Order(Set.of(product3, product4), customer2, productManager);
        Order order3 = new Order(Set.of(product5, product6), customer3, productManager);
        order1.addProduct(new Product("Product1", 100, 5)); // Adding existing product with sufficient stock
        Product product11 = new Product("Product11", 1100, 10);
        productManager.add_product(product11);
        order1.addProduct(product11);
        OrderProcessor orderProcessor = new OrderProcessor();
        IPaymentProcessor creditCardProcessor = new CreditCardProcessor();
        IPaymentProcessor payPalProcessor = new PayPalProcessor();

        double totalAmount1 = orderProcessor.process_order(order1, creditCardProcessor);
        double totalAmount2 = orderProcessor.process_order(order2, payPalProcessor);
        double totalAmount3 = orderProcessor.process_order(order3, creditCardProcessor);

        InvoiceGenerator invoiceGenerator = new InvoiceGenerator();
        invoiceGenerator.generate_invoice(totalAmount1);
        invoiceGenerator.generate_invoice(totalAmount2);
        invoiceGenerator.generate_invoice(totalAmount3);
    }
}

class Product implements IStockManager {
    private String name;
    private double price;
    private int stock;

    public Product(String name, double price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public void get_details() {
        System.out.println("Name: " + name);
        System.out.println("Price: " + price);
        System.out.println("Stock: " + stock);
    }

    public void reduce_stock(int quantity) {
        if (quantity > stock) {
            System.out.println("Not enough stock available");
        } else {
            this.stock -= quantity;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        long temp;
        temp = Double.doubleToLongBits(price);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Product other = (Product) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
            return false;
        return true;
    }

    @Override
    public void check_stock(Product product) {
        if(product.getStock() == 0) {
            System.out.println("Product out of stock");
        }
    }

    @Override
    public void update_stock(int stock) {
        this.stock += stock;
    }
}

class ProductManager {
    private Set<Product> products;
    
    public ProductManager(Set<Product> products) {
        this.products = products;
    }

    

    public void add_product(Product product) {
        if(products.contains(product)){
            Product prodInProductManager = products.stream().filter(prod -> prod.equals(product)).toList().get(0);
            prodInProductManager.update_stock(product.getStock());
        }
        products.add(product);
    }

    public void remove_product(Product product) {
        if(!products.contains(product)){
            System.out.println("Product not found");
        } else {
            Product prodInProductManager = products.stream().filter(prod -> prod.equals(product)).toList().get(0);
            if(prodInProductManager.getStock() == product.getStock()) {
                products.remove(product);
            } else if (prodInProductManager.getStock() > product.getStock()) {
                prodInProductManager.reduce_stock(product.getStock());
            } else {
                System.out.println("Not enough stock available");
            }
        }
    }

    public boolean check_stock(Product product) {
        return product.getStock() < products.stream().filter(prod -> prod.equals(product)).toList().get(0).getStock();
           
    }

    public Set<Product> getProducts() {
        return products;
    }
}

class Customer {
    private String name;
    private String email;
    private String address;

    public Customer(String name, String email, String address) {
        this.name = name;
        this.email = email;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void get_contact_info() {
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
    }
}

class InvoiceGenerator {
    public void generate_invoice(double orderAmount) {
        System.out.println("Invoice generated for order: " + orderAmount);
    }
}

class Order {
    private Set<Product>  products;
    private Customer customer;
    private ProductManager productManager;
    
    public Order(Customer customer, ProductManager productManager) {
        this.customer = customer;
        this.productManager = productManager;
        this.products = new HashSet<>();
    }

    public Order(Set<Product> products, Customer customer, ProductManager productManager) {
        this.products = products;
        this.customer = customer;
        this.productManager = productManager;
    }

    public void addProduct(Product product) {
        List<Product> prodInProductManageer = productManager.getProducts().stream().filter(prod -> productManager.check_stock(prod)).toList();
        if(!prodInProductManageer.isEmpty()) {
            System.out.println("Not enough stock available");
            return;
        }   
        List<Product> prodInOrder = productManager.getProducts().stream().filter(prod -> prod.equals(product)).toList();
        if(prodInOrder.isEmpty()) {
            products.add(product);
        } else{
            prodInOrder.get(0).update_stock(product.getStock() + prodInOrder.get(0).getStock());
        }
        productManager.remove_product(product);
    }

    public Set<Product> getProducts() {
        return products;
    }

    public Customer getCustomer() {
        return customer;
    }
}

class OrderProcessor {
    public double process_order(Order order, IPaymentProcessor payment_type) {
        double totalCost = order.getProducts().stream().map(product -> product.getPrice()*product.getStock()).reduce((price, sum) -> (sum + price)).get();
        payment_type.process_payment(totalCost);
        return totalCost;
    }

}

interface IPaymentProcessor {
    void process_payment(double amount);
}

class CreditCardProcessor implements IPaymentProcessor {
    public void process_payment(double amount) {
        System.out.println("Credit card payment processed: " + amount);
    }
}

class PayPalProcessor  implements IPaymentProcessor {
    public void process_payment(double amount) {
        System.out.println("PayPal payment processed: " + amount);
    }
}

interface IStockManager{
    void check_stock(Product product);
    void update_stock(int stock);
}




