package guru.springfamework.api.v1.model.bootstrap;

import guru.springfamework.domain.Category;
import guru.springfamework.domain.Customer;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import guru.springfamework.repositories.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {
    private final CategoryRepository categoryRepository;
    private final CustomerRepository customerRepository;
    private final VendorRepository vendorRepository;

    @Autowired
    public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) {
        loadCategory();
        loadCustomer();
        loadVendors();

    }

    private void loadVendors() {
        Vendor vendor1 = new Vendor();
        vendor1.setName("Western Tasty Fruits Ltd.");
        this.vendorRepository.save(vendor1);

        Vendor vendor2 = new Vendor();
        vendor2.setName("Exotic Fruits Company");
        this.vendorRepository.save(vendor2);

        Vendor vendor3 = new Vendor();
        vendor3.setName("Home Fruits");
        this.vendorRepository.save(vendor3);

    }

    private void loadCustomer(){
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirstName("Mitko");
        customer1.setLastName("Georgiev");
        this.customerRepository.save(customer1);

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setFirstName("Martin");
        customer2.setLastName("Tsvetkov");
        this.customerRepository.save(customer2);
    }
    private void loadCategory() {
        Category fruits = new Category();
        fruits.setName("Fruits");

        Category dried = new Category();
        dried.setName("Dried");

        Category fresh = new Category();
        fresh.setName("Fresh");

        Category exotic = new Category();
        exotic.setName("Exotic");

        Category nuts = new Category();
        nuts.setName("Nuts");

        this.categoryRepository.save(fruits);
        this.categoryRepository.save(dried);
        this.categoryRepository.save(fresh);
        this.categoryRepository.save(exotic);
        this.categoryRepository.save(nuts);


        System.out.println("Data Loaded = " + this.categoryRepository.count() );
    }
}
