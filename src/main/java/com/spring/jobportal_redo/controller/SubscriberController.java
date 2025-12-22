package com.spring.jobportal_redo.controller;

import com.spring.jobportal_redo.domain.dto.subscriber.SubscriberDto;
import com.spring.jobportal_redo.domain.dto.subscriber.SubscriberResponseDto;
import com.spring.jobportal_redo.service.SubscriberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subscribers")
@RequiredArgsConstructor
public class SubscriberController {
    private final SubscriberService subscriberService;

    @PostMapping
    public SubscriberResponseDto create(@RequestBody @Valid SubscriberDto dto) {
         return subscriberService.create(dto);
    }

    @GetMapping
    public List<SubscriberResponseDto> getAll() {
        return subscriberService.getAll();
    }

    @GetMapping("/by-user")
    public SubscriberResponseDto getByUser() {
        return subscriberService.getByUser();
    }

    @PutMapping
    public SubscriberResponseDto update(@RequestBody @Valid SubscriberDto dto) {
        return subscriberService.update(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        subscriberService.delete(id);
    }

}
