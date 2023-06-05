package io.bhimsur.librarian.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorDto implements Serializable {
    private static final long serialVersionUID = 6055910259702067287L;
    private Integer errorCode;
    private String errorMessage;
    private String traceId;
}
