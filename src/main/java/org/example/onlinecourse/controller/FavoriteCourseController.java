package org.example.onlinecourse.controller;

import lombok.RequiredArgsConstructor;
import org.example.onlinecourse.dto.request.FavoriteCourseRequestDto;
import org.example.onlinecourse.dto.response.FavoriteCourseResponseDto;
import org.example.onlinecourse.service.FavoriteCourseService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/api/favorite-course")
public class FavoriteCourseController {
    private final FavoriteCourseService favoriteCourseService;

    @PostMapping("/save-favorite-course")
    public FavoriteCourseResponseDto addFavoriteCourse(@RequestBody FavoriteCourseRequestDto favoriteCourseRequestDto) {
        return favoriteCourseService.addFavoriteCourse(favoriteCourseRequestDto);
    }

    @GetMapping("/list-favorite-course")
    public Page<FavoriteCourseResponseDto> getFavoriteCourses(@RequestParam int page, @RequestParam int size) {
        return favoriteCourseService.getFavoriteCourses(page, size);
    }

    @DeleteMapping("/delete-favorite-course")
    public void deleteFavoriteCourse(@RequestParam long id) {
        favoriteCourseService.deleteFavoriteCourse(id);
    }

}
