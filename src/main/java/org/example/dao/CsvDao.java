package org.example.dao;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "csv_data")
public class CsvDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private String age;

    private LocalDate dob;

    private String gender;

    @Column(nullable = false)
    private String nic;

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ID: ").append(id).append("\n\n");
        stringBuilder.append("Age: ").append(age).append("\n");
        stringBuilder.append("Date of Birth: ").append(dob).append("\n");
        stringBuilder.append("Gender: ").append(gender).append("\n");
        stringBuilder.append("NIC: ").append(nic).append("\n\n");
        return stringBuilder.toString();
    }
}
