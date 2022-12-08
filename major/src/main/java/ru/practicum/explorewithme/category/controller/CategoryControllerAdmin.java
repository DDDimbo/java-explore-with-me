package ru.practicum.explorewithme.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.category.CategoryDto;
import ru.practicum.explorewithme.category.service.CategoryServiceAdmin;
import ru.practicum.explorewithme.markerinterface.Update;

import javax.validation.Valid;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/categories")
public class CategoryControllerAdmin {

    private final CategoryServiceAdmin categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public CategoryDto create(@Valid @RequestBody CategoryDto categoryDto) {
        log.info("Create new category with name - {} by admin", categoryDto.getName());
        return categoryService.create(categoryDto);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public CategoryDto update(@Validated(Update.class) @RequestBody CategoryDto categoryDto) {
        log.info("Update category with id {} for admin layer", categoryDto.getId());
        return categoryService.update(categoryDto);
    }

    @DeleteMapping("/{catId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable(value = "catId") Long id) {
        log.info("Delete category by id {} for admin layer", id);
        categoryService.deleteById(id);
    }
}
