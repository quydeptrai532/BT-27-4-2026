package com.example.bt2.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "todos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", length = 255, nullable = false)
    @NotBlank(message = "Nội dung không được để trống")
    private String content;

    @Column(name = "due_date", nullable = false)
    @NotNull(message = "Ngày hết hạn không được để trống")
    @FutureOrPresent(message = "Ngày hết hạn phải từ hôm nay trở đi")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDate;

    @Column(name = "status", length = 50)
    private String status;

    @Column(name = "priority", length = 50)
    private String priority;
}