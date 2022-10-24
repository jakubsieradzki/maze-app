package org.challenge.maze.infrastructure.http.maze

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.challenge.maze.application.MazeService
import org.challenge.maze.domain.MazeRepository
import org.challenge.maze.infrastructure.db.UserRepository
import org.challenge.maze.infrastructure.db.entities.UserEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import spock.lang.Specification

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
// drops the db before each test
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class MazeApiIntegrationTest extends Specification {

    @Autowired
    private MockMvc mvc
    @Autowired
    private MazeService mazeService
    @Autowired
    private MazeRepository mazeRepository
    @Autowired
    private ObjectMapper objectMapper
    @Autowired
    private UserRepository userRepository

    private MazeMockMvc mazeMockMvc
    private static final String testUserName = "user"

    void setup() {
        mazeMockMvc = new MazeMockMvc(mvc)
        def userEntity = new UserEntity()
        userEntity.setUsername(testUserName)
        userEntity.setPassword("pass")
        userRepository.save(userEntity)
    }

    @WithMockUser(testUserName)
    def "should return an existing maze for user"() {
        given: "maze exists for user"
        def existingMazeData = new MazeDataDto("4x6", "A1", ["B2", "C2", "C3", "C4", "A6", "B6", "C6"])
        def existingMaze = mazeService.addForUser(existingMazeData, testUserName)

        when:
        def resultActions = mazeMockMvc.allUserMazes()

        then:
        def response = isOKJsonResponse(resultActions)

        and:
        def userMazes = objectMapper.readValue(response.getContentAsString(), new TypeReference<List<MazeDto>>() {})
        userMazes.size() == 1
        with(userMazes.first()) {
            it.id == existingMaze.id
            with(it.data) { data ->
                data.gridSize == existingMazeData.gridSize
                data.entrance == existingMazeData.entrance
                data.walls == existingMazeData.walls
            }
        }
    }

    @WithMockUser(testUserName)
    def "should create a new maze for user"() {
        given:
        def requestData = new MazeDataDto("4x6", "A1", ["B2", "C2", "C3", "C4", "A6", "B6", "C6"])

        when:
        def resultActions = mazeMockMvc.addMaze(objectMapper.writeValueAsString(requestData))

        then:
        def response = isOKJsonResponse(resultActions)

        and:
        def responseMaze = objectMapper.readValue(response.getContentAsString(), MazeDto)

        with(responseMaze) {
            it.id == 1
            with(it.data) { data ->
                data.gridSize == requestData.gridSize
                data.entrance == requestData.entrance
                data.walls == requestData.walls
            }
        }
    }

    @WithMockUser(testUserName)
    def "should solve the maze"() {
        given: "maze exists for user"
        def existingMazeData = new MazeDataDto("4x6", "A1", ["B2", "C2", "C3", "C4", "A6", "B6", "C6"])
        def existingMaze = mazeService.addForUser(existingMazeData, testUserName)

        when: "getting min solution"
        def resultActionsMin = mazeMockMvc.getMazeSolution(existingMaze.id, "min")

        then:
        def responseMin = isOKJsonResponse(resultActionsMin)
        def minSolutionResponse = objectMapper.readValue(responseMin.getContentAsString(), MazeSolutionDto)
        minSolutionResponse.path == ["A1", "A2", "A3", "A4", "A5", "B5", "C5", "D5", "D6"]

        when: "getting max solution"
        def resultActionsMax = mazeMockMvc.getMazeSolution(existingMaze.id, "max")

        then:
        def responseMax = resultActionsMax
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andReturn()
                .getResponse()
        def maxSolutionResponse = objectMapper.readValue(responseMax.getContentAsString(), MazeSolutionDto)
        maxSolutionResponse.path == ["A1", "A2", "A3", "B3", "B4", "A4", "A5", "B5", "C5", "D5", "D6"]
    }

    private MockHttpServletResponse isOKJsonResponse(ResultActions resultActions) {
        resultActions
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andReturn()
                .getResponse()
    }
}
