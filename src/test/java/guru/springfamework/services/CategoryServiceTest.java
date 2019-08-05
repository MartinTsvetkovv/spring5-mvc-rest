package guru.springfamework.services;

import guru.springfamework.api.v1.model.mapper.CategoryMapper;
import guru.springfamework.domain.Category;
import guru.springfamework.repositories.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class CategoryServiceTest {

    private CategoryService categoryService;
    private Category category;

    @Mock
    private CategoryRepository categoryRepository;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        this.category = new Category();
        this.category.setName("Fruits");
        this.category.setId(1L);


        this.categoryService = new CategoryServiceImpl(CategoryMapper.INSTANCE, this.categoryRepository);

    }

    @Test
    public void findAllCategories() {
        List<Category> categories = Arrays.asList(new Category(), new Category());

        when(this.categoryRepository.findAll()).thenReturn(categories);

        assertEquals(2, categories.size());

    }

    @Test
    public void getCategoryByName() {

        when(this.categoryRepository.findByName(category.getName())).thenReturn(this.category);

        assertEquals("Fruits", this.category.getName());

    }
}