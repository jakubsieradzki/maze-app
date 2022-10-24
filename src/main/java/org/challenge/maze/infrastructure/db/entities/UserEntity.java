package org.challenge.maze.infrastructure.db.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@Table(name = "users")
public class UserEntity {
    @Id
    String username;

    @NotBlank
    String password;
}
