package com.spring.jobportal_redo.service;

import com.spring.jobportal_redo.domain.Skill;
import com.spring.jobportal_redo.domain.Subscriber;
import com.spring.jobportal_redo.domain.User;
import com.spring.jobportal_redo.domain.dto.subscriber.SubscriberDto;
import com.spring.jobportal_redo.domain.dto.subscriber.SubscriberResponseDto;
import com.spring.jobportal_redo.repository.SubscriberRepository;
import com.spring.jobportal_redo.util.SecurityUtil;
import com.spring.jobportal_redo.util.mapper.SubscriberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubscriberService {
    private final SubscriberRepository subscriberRepository;
    private final UserService userService;
    private final SkillService skillService;
    private final SubscriberMapper subscriberMapper;

    public SubscriberResponseDto create(SubscriberDto dto) {
        User user = userService.getUserLogin();
        Subscriber subscriber = new Subscriber();
        subscriber.setEmail(user.getEmail());
        Set<Skill> skills = dto.getSkillIds().stream()
                .map(skillService::getByIdOrThrow)
                .collect(Collectors.toSet());
        subscriber.setSkills(skills);
        Subscriber saved = subscriberRepository.save(subscriber);
        return subscriberMapper.toResponseDto(saved);
    }

    public List<SubscriberResponseDto> getAll() {
        List<Subscriber> subscribers = subscriberRepository.findAll();
        return subscriberMapper.toResponseDtoList(subscribers);
    }

    public SubscriberResponseDto getByUser() {
        String email = SecurityUtil.getPrincipalCurrentUserLogin().orElse("");
        Subscriber subscriber = subscriberRepository.findByEmail(email).orElse(null);
        return subscriberMapper.toResponseDto(subscriber);
    }

    public SubscriberResponseDto update(SubscriberDto dto) {
        String email = SecurityUtil.getPrincipalCurrentUserLogin().orElse("");
        Subscriber subscriber = subscriberRepository.findByEmail(email)
                .orElseThrow(
                        () -> new IllegalArgumentException("You haven't subscribed")
                );
        subscriber.clearSkills();
        Set<Skill> skills = dto.getSkillIds().stream()
                .map(skillService::getByIdOrThrow)
                .collect(Collectors.toSet());
        subscriber.setSkills(skills);
        Subscriber updatedSub = subscriberRepository.save(subscriber);
        return subscriberMapper.toResponseDto(updatedSub);
    }


    public void delete(Long id) {
        subscriberRepository.deleteById(id);
    }
}
