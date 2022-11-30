package com.example.caderno.controller.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotaResponse {
    private String titulo;
    private String nota;
}
