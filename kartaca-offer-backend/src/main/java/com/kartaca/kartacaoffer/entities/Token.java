package com.kartaca.kartacaoffer.entities;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Token implements Serializable {
    private String username;
    private String jwt;
}
