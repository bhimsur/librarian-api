package io.bhimsur.librarian.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class MemberDto implements Serializable {
    private static final long serialVersionUID = 6210802552583423348L;
    private String name;
    private String memberId;
    private String idCardNumber;
}
