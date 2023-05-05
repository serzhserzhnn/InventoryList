package org.example.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ThingsDTO {
    private String id;
    @NotBlank
    private String thingId;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotBlank
    private String location;
    @NotBlank
    private String category;
    @NotBlank
    private Integer quantity;
    @NotBlank
    private String dateEnd;
    @NotBlank
    private String user;
}
