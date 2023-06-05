package io.bhimsur.librarian.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BaseResponse<T extends Serializable> implements Serializable {

    private static final long serialVersionUID = -9142972005288402682L;

    private T data;
}
