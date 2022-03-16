package com.alkemy.ong.data.mappers;

import com.alkemy.ong.data.entities.Activity;
import org.mapstruct.Mapper;

@Mapper
public interface ActivityMapper {
    Activity toActivity(Activity activity);
}
