package ru.practicum.explorewithme.category.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.category.Category;
import ru.practicum.explorewithme.category.CategoryDto;
import ru.practicum.explorewithme.category.CategoryMapper;
import ru.practicum.explorewithme.category.CategoryRepository;
import ru.practicum.explorewithme.category.service.CategoryServiceAdmin;
import ru.practicum.explorewithme.event.EventRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceAdminImpl implements CategoryServiceAdmin {

    private final CategoryRepository categoryRepository;

    private final EventRepository eventRepository;


    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        Category result = categoryRepository.save(CategoryMapper.toCategory(categoryDto));
        return CategoryMapper.toCategoryDto(result);
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto) {
        categoryRepository.save(CategoryMapper.toCategory(categoryDto));
        return categoryDto;
    }

    @Override
    public void deleteById(Long id) {
        if (eventRepository.existsByCategory_Id(id))
            throw new IllegalArgumentException("Для удаления необходимо, чтобы категория не была связана с событием!");
        categoryRepository.deleteById(id);
    }
}
