package ru.practicum.explorewithme.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.category.CategoryDto;
import ru.practicum.explorewithme.category.service.CategoryServicePublic;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryControllerPublic {

    private final CategoryServicePublic categoryServicePublic;


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryDto> findAll(@RequestParam(value = "from", defaultValue = "0") @PositiveOrZero Integer from,
                                     @RequestParam(value = "size", defaultValue = "10") @PositiveOrZero Integer size) {
        log.info("Find all categories for public layer");
        return categoryServicePublic.findAll(from, size);
    }

    @GetMapping("/{catId}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDto findById(@PathVariable(value = "catId") @Positive Long categoryId) {
        log.info("Find all by category with id={} for public layer", categoryId);
        return categoryServicePublic.findById(categoryId);
    }
}
