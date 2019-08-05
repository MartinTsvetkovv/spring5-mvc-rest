package guru.springfamework.services;

import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class VendorServiceImplTest {

    private VendorServiceImpl vendorService;
    private ModelMapper modelMapper;

    @Mock
    private VendorRepository vendorRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.modelMapper = mock(ModelMapper.class);
        this.vendorService = new VendorServiceImpl(this.vendorRepository, this.modelMapper);

    }

    @Test
    public void getAllVendors() {
        List<Vendor> vendors = Arrays.asList(new Vendor(), new Vendor());

        when(this.vendorRepository.findAll()).thenReturn(vendors);

        assertEquals(2, vendors.size());

    }
}