package org.challenge.maze.infrastructure.db;

import org.challenge.maze.infrastructure.db.entities.MazeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface JpaDao extends JpaRepository<MazeEntity, Long> {

    @Query("Select m from MazeEntity m WHERE m.owner=:username")
    List<MazeEntity> findAllByUsername(@Param("username") String username);

}
