package guru.springfamework.services;

import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.api.v1.model.mapper.CategoryMapper;
import guru.springfamework.domain.Category;
import guru.springfamework.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryMapper categoryMapper, CategoryRepository categoryRepository) {
        this.categoryMapper = categoryMapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryDTO> findAllCategories() {
        return this.categoryRepository.findAll()
                .stream()
                .map(this.categoryMapper::categoryToCategoryDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO getCategoryByName(String name) {
        Category categoryByName = this.categoryRepository.findByName(name);
        CategoryDTO categoryDTO = this.categoryMapper.categoryToCategoryDTO(categoryByName);
        categoryDTO.setProductUrl("/api/v1/category/" + categoryDTO.getId());
        return categoryDTO;


//        return this.categoryMapper.categoryToCategoryDTO(this.categoryRepository.findByName(name));
    }
}
