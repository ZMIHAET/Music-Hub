package ru.kashigin.musichub.service.rabbit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DateMessage {
    private Long id;
    private LocalDate date;
}
