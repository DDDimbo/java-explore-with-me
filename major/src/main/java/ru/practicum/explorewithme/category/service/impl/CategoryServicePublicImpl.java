package ru.practicum.explorewithme.category.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.category.Category;
import ru.practicum.explorewithme.category.CategoryDto;
import ru.practicum.explorewithme.category.CategoryMapper;
import ru.practicum.explorewithme.category.CategoryRepository;
import ru.practicum.explorewithme.category.service.CategoryServicePublic;
import ru.practicum.explorewithme.exceptions.CategoryNotFoundException;
import ru.practicum.explorewithme.utility.FromSizeRequest;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryServicePublicImpl implements CategoryServicePublic {

    private final CategoryRepository categoryRepository;


    @Override
    public List<CategoryDto> findAll(Integer from, Integer size) {
        Pageable pageable = FromSizeRequest.of(from, size);
        return categoryRepository.findAll(pageable).stream()
                .map(CategoryMapper::toCategoryDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto findById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Категории с таким с id = " + categoryId + "не найдено"));
        return CategoryMapper.toCategoryDto(category);
    }
}
