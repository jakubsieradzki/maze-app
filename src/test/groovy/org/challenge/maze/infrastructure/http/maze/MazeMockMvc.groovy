package org.challenge.maze.infrastructure.http.maze

import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

class MazeMockMvc {
    private static final String MAZE_API_PATH = "/maze"
    private static final String MAZE_SOLUTION_API = MAZE_API_PATH + "/{mazeId}/solution"

    private final MockMvc mockMvc

    MazeMockMvc(MockMvc mockMvc) {
        this.mockMvc = mockMvc
    }

    ResultActions allUserMazes() {
        return mockMvc.perform(get(MAZE_API_PATH))
    }

    ResultActions addMaze(String payload) {
        return mockMvc.perform(
                post(MAZE_API_PATH)
                        .content(payload)
                        .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
        )
    }

    ResultActions getMazeSolution(long mazeId, String stepsArg) {
        return mockMvc.perform(
                get(MAZE_SOLUTION_API, mazeId)
                        .queryParam("steps", stepsArg)
        )
    }
}
