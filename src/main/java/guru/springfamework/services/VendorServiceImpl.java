package guru.springfamework.services;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.controllers.v1.VendorController;
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

    public VendorServiceImpl(VendorRepository vendorRepository, ModelMapper modelMapper) {

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

    @Override
    public VendorDTO createNewVendor(VendorDTO vendorDTO) {
        return saveAndReturnVendorDTO(this.modelMapper.map(vendorDTO, Vendor.class));
    }

    @Override
    public VendorDTO getVendorById(Long id) {
        return this.vendorRepository.findById(id)
                .map(vendor -> {
                    VendorDTO vendorDTO = this.modelMapper.map(vendor, VendorDTO.class);
                    vendorDTO.setVendorUrl(VendorController.BASE_URL +"/" + vendor.getId());
                    return vendorDTO;
                }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public VendorDTO updateVendorById(Long id, VendorDTO vendorDTO) {
        Vendor vendor = this.modelMapper.map(vendorDTO, Vendor.class);
        vendor.setId(id);
        return saveAndReturnVendorDTO(vendor);
    }

    @Override
    public void deleteVendor(Long id) {
        this.vendorRepository.deleteById(id);
    }

    private VendorDTO saveAndReturnVendorDTO(Vendor vendor) {
        Vendor saveVendor = this.vendorRepository.save(vendor);
        VendorDTO vendorDTO = this.modelMapper.map(saveVendor, VendorDTO.class);
        vendorDTO.setVendorUrl("/api/v1/vendor/" + saveVendor.getId());

        return vendorDTO;
    }
}
