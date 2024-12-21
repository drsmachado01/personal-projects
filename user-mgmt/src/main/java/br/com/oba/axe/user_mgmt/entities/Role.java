package br.com.oba.axe.user_mgmt.entities;

import br.com.oba.axe.user_mgmt.util.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Document(collection = "roles")
public class Role {
    @Id
    private String id;
    private UserRole role;
    private String description;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
