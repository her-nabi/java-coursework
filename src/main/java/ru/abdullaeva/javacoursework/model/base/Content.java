package ru.abdullaeva.javacoursework.model.base;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Content implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    private UUID id;

    private String data;

    @NotNull(message = "Поле не должно быть пустым")
    @Enumerated(EnumType.STRING)
    private State state;

    @JsonBackReference
    @ManyToMany(mappedBy = "contents")
    private Set<Page> pages = new HashSet<>();
}
