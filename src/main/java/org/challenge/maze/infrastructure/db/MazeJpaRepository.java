package org.challenge.maze.infrastructure.db;

import lombok.extern.slf4j.Slf4j;
import org.challenge.maze.domain.MazeRepository;
import org.challenge.maze.domain.model.MazeRoute;
import org.challenge.maze.domain.model.Maze;
import org.challenge.maze.infrastructure.db.entities.MazeEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
class MazeJpaRepository implements MazeRepository {

    private final JpaDao mazeDao;

    public MazeJpaRepository(JpaDao mazeDao) {
        this.mazeDao = mazeDao;
    }

    @Override
    public Maze saveForUser(MazeRoute mazeRoute, String username) {
        MazeEntity saved = mazeDao.save(MazeEntity.from(mazeRoute, username));
        return saved.toDomain();
    }

    @Override
    public List<Maze> findUserMazes(String username) {
        return mazeDao.findAllByUsername(username)
                .stream()
                .map(MazeEntity::toDomain)
                .toList();
    }

    @Override
    public Optional<Maze> find(long mazeId) {
        return mazeDao.findById(mazeId)
                .map(MazeEntity::toDomain);
    }
}
