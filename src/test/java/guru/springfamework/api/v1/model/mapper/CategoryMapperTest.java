package guru.springfamework.api.v1.model.mapper;

import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.domain.Category;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryMapperTest {

    public static final String NAME = "Mitko";
    public static final long ID = 1L;
    private CategoryMapper categoryMapper;

    @Before
    public void setUp(){
        this.categoryMapper = CategoryMapper.INSTANCE;
    }

    @Test
    public void categoryToCategoryDTO() {

        Category category = new Category();
        category.setName(NAME);
        category.setId(ID);

        CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(category);

        assertEquals(Long.valueOf(ID), categoryDTO.getId());
        assertEquals(NAME, categoryDTO.getName());

    }

}