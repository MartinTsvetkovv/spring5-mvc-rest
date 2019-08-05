package guru.springfamework.services;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.api.v1.model.mapper.CustomerMapper;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerMapper customerMapper, CustomerRepository customerRepository) {
        this.customerMapper = customerMapper;
        this.customerRepository = customerRepository;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return this.customerRepository.findAll()
                .stream()
                .map(customer -> {
                    CustomerDTO customerDTO = this.customerMapper.customerToCustomerDTO(customer);
                    customerDTO.setCustomerUrl("/api/v1/customers/" + customer.getId());
                    return customerDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        return this.customerRepository.findById(id)
                .map(this.customerMapper::customerToCustomerDTO)
                .map(customerDTO -> {
                    customerDTO.setCustomerUrl("/api/v1/customer/" + id);
                    return customerDTO;
                }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
        return saveAndReturnDTO(this.customerMapper.customerDTOtoCustomer(customerDTO));
    }

    @Override
    public CustomerDTO saveCustomerByDTO(Long id, CustomerDTO customerDTO) {
        Customer customer = this.customerMapper.customerDTOtoCustomer(customerDTO);
        customer.setId(id);

        return saveAndReturnDTO(customer);
    }

    @Override
    public CustomerDTO patchCustomerByDTO(Long id, CustomerDTO customerDTO) {
        return this.customerRepository.findById(id)
                .map(customer -> {
                    if (customerDTO.getFirstName() != null) {
                        customer.setFirstName(customerDTO.getFirstName());
                    }
                    if (customerDTO.getFirstName() != null) {
                        customer.setLastName(customerDTO.getLastName());
                    }
                    CustomerDTO returnDTO = customerMapper.customerToCustomerDTO(customer);
                    returnDTO.setCustomerUrl("/api/v1/customer/" + id);

                    return returnDTO;
                }).orElseThrow(ResourceNotFoundException::new);

    }

    @Override
    public void deleteCustomerByID(Long id) {
        this.customerRepository.deleteById(id);
    }


    private CustomerDTO saveAndReturnDTO(Customer customer) {

        Customer savedCustomer = this.customerRepository.save(customer);

        CustomerDTO returnToDTO = this.customerMapper.customerToCustomerDTO(savedCustomer);
        returnToDTO.setCustomerUrl("/api/v1/customer/" + savedCustomer.getId());

        return returnToDTO;
    }
}
