package io.bhimsur.librarian.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "member")
@Entity
public class Member implements Serializable {
    private static final long serialVersionUID = 4636019856868375811L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Positive
    private Long id;

    @Column(name = "name", length = 150)
    @NotNull
    private String name;

    @Column(name = "member_id", length = 25)
    @NotNull
    private String memberId;
    @NotNull
    @Column(name = "id_card_number", length = 16)
    private String idCardNumber;
}
