package guru.springfamework.services;

import guru.springfamework.api.v1.model.VendorDTO;

import java.util.List;

public interface VendorService {
    List<VendorDTO> getAllVendors();
    
    VendorDTO createNewVendor(VendorDTO vendorDTO);

    VendorDTO getVendorById(Long id);

    VendorDTO updateVendorById(Long id, VendorDTO vendorDTO);

    void deleteVendor(Long id);


}
