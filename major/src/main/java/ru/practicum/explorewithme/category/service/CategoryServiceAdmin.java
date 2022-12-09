package ru.practicum.explorewithme.category.service;

import ru.practicum.explorewithme.category.CategoryDto;

public interface CategoryServiceAdmin {

    // admin
    CategoryDto create(CategoryDto categoryDto);

    CategoryDto update(CategoryDto categoryDto);

    void deleteById(Long id);
}
