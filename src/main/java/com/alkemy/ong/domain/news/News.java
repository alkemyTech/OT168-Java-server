package com.alkemy.ong.domain.news;

import com.alkemy.ong.data.entities.CommentEntity;
import com.alkemy.ong.domain.comments.Comment;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class News {

    private Long newsId;
    private String name;
    private String content;
    private String image;
    private String type;
    private List<Comment> comments = new ArrayList<>();
}