package com.vitube.online_learning.mapper;

import org.mapstruct.Mapper;

import com.vitube.online_learning.dto.response.LearnWhatResponse;
import com.vitube.online_learning.entity.LearnWhat;

@Mapper(componentModel = "spring")
public interface LearnWhatMapper {

    LearnWhatResponse learnWhatTooLearnWhatResponse(LearnWhat learnWhat);
}
