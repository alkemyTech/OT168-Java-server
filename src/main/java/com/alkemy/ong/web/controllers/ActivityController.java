package com.alkemy.ong.web.controllers;

import com.alkemy.ong.domain.activities.Activity;
import com.alkemy.ong.domain.activities.ActivityService;
import com.alkemy.ong.web.utils.WebUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

import static com.alkemy.ong.web.utils.WebUtils.*;

@RestController
@RequestMapping("/activities")
public class ActivityController {

    private final ActivityService activityService;

    public ActivityController(ActivityService activityService){
        this.activityService = activityService;
    }

    @PostMapping
    public ResponseEntity<ActivityDTO> saveActivity(@Valid @RequestBody ActivityDTO activityDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(activityService.saveActivity(toModel(activityDTO))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActivityDTO> updateActivity(@PathVariable Long id, @Valid @RequestBody ActivityDTO activityDTO){
        validateDtoIdWithBodyId(id, activityDTO.getId());
        return ResponseEntity.ok(toDTO(activityService.updateActivity(id, toModel(activityDTO))));
    }

    private Activity toModel(ActivityDTO activityDTO){
        return Activity.builder()
                .name(activityDTO.getName())
                .content(activityDTO.getContent())
                .image(activityDTO.getImage())
                .createdAt(activityDTO.getCreatedAt())
                .updatedAt(activityDTO.getUpdatedAt())
                .build();
    }

    private ActivityDTO toDTO(Activity activity){
        return ActivityDTO.builder()
                .id(activity.getId())
                .name(activity.getName())
                .image(activity.getImage())
                .content(activity.getContent())
                .createdAt(activity.getCreatedAt())
                .updatedAt(activity.getUpdatedAt())
                .build();
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    private static class ActivityDTO {
        private Long id;
        @NotEmpty(message = "Name can't be empty")
        private String name;
        @NotEmpty(message = "Content can't be empty")
        private String content;
        private String image;
        @JsonFormat(pattern="dd-MM-yyyy hh:mm")
        private LocalDateTime createdAt;
        @JsonFormat(pattern="dd-MM-yyyy hh:mm")
        private LocalDateTime updatedAt;
    }
}
