package com.vitube.online_learning.mapper;

import com.vitube.online_learning.dto.response.LearnWhatResponse;
import com.vitube.online_learning.entity.LearnWhat;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LearnWhatMapper {

    LearnWhatResponse learnWhatTooLearnWhatResponse(LearnWhat learnWhat);
}
