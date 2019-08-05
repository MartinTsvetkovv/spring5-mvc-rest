package guru.springfamework.services;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.api.v1.model.mapper.VendorMapper;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendorServiceImpl implements VendorService {
    private final VendorRepository vendorRepository;
    private final ModelMapper modelMapper;

    public VendorServiceImpl( VendorRepository vendorRepository, ModelMapper modelMapper) {

        this.vendorRepository = vendorRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<VendorDTO> getAllVendors() {

        return this.vendorRepository.findAll().stream()
                .map(vendor -> {
                    VendorDTO vendorDTO = this.modelMapper.map(vendor, VendorDTO.class);
                    vendorDTO.setVendorUrl("/api/v1/vendors/" + vendor.getId());
                    return vendorDTO;
                }).collect(Collectors.toList());



    }
}
