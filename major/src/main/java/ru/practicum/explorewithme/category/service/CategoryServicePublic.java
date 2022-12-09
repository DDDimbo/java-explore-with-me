package ru.practicum.explorewithme.category.service;

import ru.practicum.explorewithme.category.CategoryDto;

import java.util.List;

public interface CategoryServicePublic {

    // public
    List<CategoryDto> findAll(Integer from, Integer size);

    CategoryDto findById(Long categoryId);
}
