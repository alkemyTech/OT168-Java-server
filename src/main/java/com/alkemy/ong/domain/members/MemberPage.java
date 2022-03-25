package com.alkemy.ong.domain.members;

import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MemberPage {
    private List<Member> memberList;
    private String nextPage;
    private String previuosPage;
}
