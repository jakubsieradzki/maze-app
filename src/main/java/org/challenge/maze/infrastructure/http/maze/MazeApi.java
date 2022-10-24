package org.challenge.maze.infrastructure.http.maze;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.challenge.maze.application.MazeService;
import org.challenge.maze.domain.MazeEncoder;
import org.challenge.maze.domain.model.Maze;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/maze")
public class MazeApi {

    private final MazeService mazeService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public MazeDto createMaze(@Valid @RequestBody MazeDataDto data, Authentication principal) {
        Maze createdMaze = mazeService.addForUser(data, principal.getName());
        log.info("Successfully created maze for user {}: {}", principal.getName(), createdMaze);
        return MazeDto.from(createdMaze);
    }

    @GetMapping
    public List<MazeDto> getUserMazes(Principal principal) {
        String username = principal.getName();
        List<Maze> userMazes = mazeService.findUserMazes(username);
        log.info("Successfully fetched mazes for user {}, count: {}", username, userMazes.size());
        return userMazes.stream()
                .map(MazeDto::from)
                .toList();
    }

    @GetMapping("/{mazeId}/solution")
    public MazeSolutionDto getMazeSolution(@PathVariable long mazeId, @RequestParam("steps") SolverSteps steps) {
        List<String> path = mazeService.solveMaze(mazeId, steps)
                .stream()
                .map(MazeEncoder::encodeCell)
                .toList();
        log.info("Solved the maze with id: '{}' steps: {}, Path: {}", mazeId, steps, path);
        return new MazeSolutionDto(path);
    }

}
