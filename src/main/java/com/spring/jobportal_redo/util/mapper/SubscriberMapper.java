package com.spring.jobportal_redo.util.mapper;

import com.spring.jobportal_redo.domain.Subscriber;
import com.spring.jobportal_redo.domain.dto.subscriber.SubscriberResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubscriberMapper {
    SubscriberResponseDto toResponseDto(Subscriber subscriber);
    List<SubscriberResponseDto> toResponseDtoList(List<Subscriber> subscribers);

}
