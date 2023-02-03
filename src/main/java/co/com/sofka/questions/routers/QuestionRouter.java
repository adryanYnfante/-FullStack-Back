package co.com.sofka.questions.routers;

<<<<<<< HEAD
import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.usecases.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
=======
import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.usecases.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
>>>>>>> 48dfb9c47282822683662b4a55e09805a5855c33
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
<<<<<<< HEAD
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
=======
>>>>>>> 48dfb9c47282822683662b4a55e09805a5855c33

import java.util.function.Function;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class QuestionRouter {

<<<<<<< HEAD

    @Bean

                    @RouterOperation(
                            path = "/getAll",
                            produces = {
                                    MediaType.APPLICATION_JSON_VALUE
                            },
                            method = RequestMethod.GET,
                            beanClass = QuestionRouter.class,
                            beanMethod = "getAll",
                            operation = @Operation(
                                    operationId = "getAll",
                                    responses = {
                                            @ApiResponse(
                                                    responseCode = "200",
                                                    description = "successful operation",
                                                    content = @Content(schema = @Schema(
                                                            implementation = Question.class
                                                    ))
                                            ), @ApiResponse(
                                            responseCode = "404",
                                            description = "question not found with given id"
                                    )
                                    }
                            )
                    )

=======
    @Bean
>>>>>>> 48dfb9c47282822683662b4a55e09805a5855c33
    public RouterFunction<ServerResponse> getAll(ListUseCase listUseCase) {
        return route(GET("/getAll"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(listUseCase.get(), QuestionDTO.class))
        );
    }

    @Bean
<<<<<<< HEAD
    @RouterOperation(
            path = "/getOwnerAll/{userId}",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            },
            method = RequestMethod.GET,
            beanClass = QuestionRouter.class,
            beanMethod = "getOwnerAll",
            operation = @Operation(
                    operationId = "getOwnerAll",
                    responses = {
                            @ApiResponse(
                                    responseCode = "200",
                                    description = "successful operation",
                                    content = @Content(schema = @Schema(
                                            implementation = Question.class
                                    ))
                            ), @ApiResponse(
                            responseCode = "404",
                            description = "question not found with given id"
                    )},
                    parameters = {@Parameter(
                            in = ParameterIn.PATH,
                            name = "userId"
                    )
                    }
            )
    )
=======
>>>>>>> 48dfb9c47282822683662b4a55e09805a5855c33
    public RouterFunction<ServerResponse> getOwnerAll(OwnerListUseCase ownerListUseCase) {
        return route(
                GET("/getOwnerAll/{userId}"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(
                                ownerListUseCase.apply(request.pathVariable("userId")),
                                QuestionDTO.class
                         ))
        );
    }

<<<<<<< HEAD
    @RouterOperation(
            path = "/pagination/{pageNumber}",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            },
            method = RequestMethod.GET,
            beanClass = QuestionRouter.class,
            beanMethod = "pagination",
            operation = @Operation(
                    operationId = "pagination",
                    responses = {
                            @ApiResponse(
                                    responseCode = "200",
                                    description = "successful operation",
                                    content = @Content(schema = @Schema(
                                            implementation = Question.class
                                    ))
                            ), @ApiResponse(
                            responseCode = "404",
                            description = "question not found with given id"
                    )},
                    parameters = {@Parameter(
                            in = ParameterIn.PATH,
                            name = "pageNumber"
                    )
                    }
            )
    )
    @Bean
    public RouterFunction<ServerResponse> pagination(ListUseCase listUseCase) {
        return route(GET("/pagination/{pageNumber}"),
                request -> ok().body(listUseCase.getPage(
                        Integer.valueOf(request.pathVariable("pageNumber"))
                ), QuestionDTO.class));
    }


    @Bean
    @RouterOperation(
            path = "/create",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = {RequestMethod.POST},
            beanClass = QuestionRouter.class,
            beanMethod = "create",
            operation = @Operation(
                    operationId = "create",
                    responses = {@ApiResponse(
                            responseCode = "200",
                            description = "Successful operation",
                            content = {@Content(
                                    schema = @Schema(
                                            implementation = Question.class
                                    )
                            )}
                    )},
                    requestBody = @RequestBody(
                            content = {@Content(
                                    schema = @Schema(
                                            implementation = Question.class
                                    )
                            )}
                    )
            )
    )
=======
    @Bean
>>>>>>> 48dfb9c47282822683662b4a55e09805a5855c33
    public RouterFunction<ServerResponse> create(CreateUseCase createUseCase) {
        Function<QuestionDTO, Mono<ServerResponse>> executor = questionDTO ->  createUseCase.apply(questionDTO)
                .flatMap(result -> ServerResponse.ok()
                        .contentType(MediaType.TEXT_PLAIN)
                        .bodyValue(result));

        return route(
                POST("/create").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(QuestionDTO.class).flatMap(executor)
        );
    }

    @Bean
<<<<<<< HEAD
    @RouterOperation(
            path = "/get/{id}",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            },
            method = RequestMethod.GET,
            beanClass = QuestionRouter.class,
            beanMethod = "get",
            operation = @Operation(
                    operationId = "get",
                    responses = {
                            @ApiResponse(
                                    responseCode = "200",
                                    description = "successful operation",
                                    content = @Content(schema = @Schema(
                                            implementation = Question.class
                                    ))
                            ), @ApiResponse(
                            responseCode = "404",
                            description = "question not found with given id"
                    )},
                    parameters = {@Parameter(
                            in = ParameterIn.PATH,
                            name = "id"
                    )
                    }
            )
    )
=======
>>>>>>> 48dfb9c47282822683662b4a55e09805a5855c33
    public RouterFunction<ServerResponse> get(GetUseCase getUseCase) {
        return route(
                GET("/get/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getUseCase.apply(
                                request.pathVariable("id")),
                                QuestionDTO.class
                        ))
        );
    }

    @Bean
<<<<<<< HEAD
    @RouterOperation(
            path = "/add",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = {RequestMethod.POST},
            beanClass = QuestionRouter.class,
            beanMethod = "addAnswer",
            operation = @Operation(
                    operationId = "addAnswer",
                    responses = {@ApiResponse(
                            responseCode = "200",
                            description = "Successful operation",
                            content = {@Content(
                                    schema = @Schema(
                                            implementation = Answer.class
                                    )
                            )}
                    )},
                    requestBody = @RequestBody(
                            content = {@Content(
                                    schema = @Schema(
                                            implementation = Answer.class
                                    )
                            )}
                    )
            )
    )

=======
>>>>>>> 48dfb9c47282822683662b4a55e09805a5855c33
    public RouterFunction<ServerResponse> addAnswer(AddAnswerUseCase addAnswerUseCase) {
        return route(POST("/add").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(AnswerDTO.class)
                        .flatMap(addAnswerDTO -> addAnswerUseCase.apply(addAnswerDTO)
                                .flatMap(result -> ServerResponse.ok()
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                        )
        );
    }

    @Bean
<<<<<<< HEAD
    @RouterOperation(
            path = "/update",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = {RequestMethod.POST},
            beanClass = QuestionRouter.class,
            beanMethod = "editQuestion",
            operation = @Operation(
                    operationId = "editQuestion",
                    responses = {@ApiResponse(
                            responseCode = "200",
                            description = "Successful operation",
                            content = {@Content(
                                    schema = @Schema(
                                            implementation = Question.class
                                    )
                            )}
                    )},
                    requestBody = @RequestBody(
                            content = {@Content(
                                    schema = @Schema(
                                            implementation = Question.class
                                    )
                            )}
                    )
            )
    )
    public RouterFunction<ServerResponse> editQuestion(UpdateUseCase updateUseCase) {
        return route(POST("/update").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(QuestionDTO.class)
                        .flatMap(updateUseCaseDTO -> updateUseCase.apply(updateUseCaseDTO)
                                .flatMap(result -> ServerResponse.ok()
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                        )

        );
    }

    @Bean
    @RouterOperation(
            path = "/updateAnswer",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = {RequestMethod.POST},
            beanClass = QuestionRouter.class,
            beanMethod = "updateAnswer",
            operation = @Operation(
                    operationId = "updateAnswer",
                    responses = {@ApiResponse(
                            responseCode = "200",
                            description = "Successful operation",
                            content = {@Content(
                                    schema = @Schema(
                                            implementation = Answer.class
                                    )
                            )}
                    )},
                    requestBody = @RequestBody(
                            content = {@Content(
                                    schema = @Schema(
                                            implementation = Answer.class
                                    )
                            )}
                    )
            )
    )
    public RouterFunction<ServerResponse> updateAnswer(UpdateAnswerUseCase updateAnswerUseCase) {
        return route(POST("/updateAnswer").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(AnswerDTO.class)
                        .flatMap(updateAnswerUseCaseDTO -> updateAnswerUseCase.apply(updateAnswerUseCaseDTO)
                                .flatMap(result -> ServerResponse.ok()
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                        )

        );
    }

    @Bean
    @RouterOperation(
            path = "/delete/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = {RequestMethod.DELETE},
            beanClass = QuestionRouter.class,
            beanMethod = "delete",
            operation = @Operation(
                    operationId = "delete",
                    responses = {@ApiResponse(
                            responseCode = "200",
                            description = "Successful operation",
                            content = {@Content(
                                    schema = @Schema(
                                            implementation = Question.class
                                    )
                            )}
                    )},
                    parameters = {@Parameter(
                            in = ParameterIn.PATH,
                            name = "id"
                    )}
            )
    )
=======
>>>>>>> 48dfb9c47282822683662b4a55e09805a5855c33
    public RouterFunction<ServerResponse> delete(DeleteUseCase deleteUseCase) {
        return route(
                DELETE("/delete/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.accepted()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(deleteUseCase.apply(request.pathVariable("id")), Void.class))
        );
    }
<<<<<<< HEAD

    @Bean

    @RouterOperation(
            path = "/countQuestions",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            },
            method = RequestMethod.GET,
            beanClass = QuestionRouter.class,
            beanMethod = "countQuestions",
            operation = @Operation(
                    operationId = "countQuestions",
                    responses = {
                            @ApiResponse(
                                    responseCode = "200",
                                    description = "successful operation",
                                    content = @Content(schema = @Schema(
                                            implementation = Question.class
                                    ))
                            ), @ApiResponse(
                            responseCode = "404",
                            description = "question not found"
                    )
                    }
            )
    )

    public RouterFunction<ServerResponse> countQuestions(GetTotalQuestionsUseCase totalQuestionsUseCase) {
        return route(GET("/countQuestions"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(totalQuestionsUseCase.getTotalQuestions(), long.class))
        );
    }

    @Bean

    @RouterOperation(
            path = "/totalPages",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            },
            method = RequestMethod.GET,
            beanClass = QuestionRouter.class,
            beanMethod = "totalPages",
            operation = @Operation(
                    operationId = "totalPages",
                    responses = {
                            @ApiResponse(
                                    responseCode = "200",
                                    description = "successful operation",
                                    content = @Content(schema = @Schema(
                                            implementation = Question.class
                                    ))
                            ), @ApiResponse(
                            responseCode = "404",
                            description = "not found"
                    )
                    }
            )
    )
    public RouterFunction<ServerResponse> totalPages(GetTotalPagesUseCase getTotalPagesUseCase) {
        return route(GET("/totalPages"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getTotalPagesUseCase.getTotalPages(), Integer.class))
        );
    }
=======
>>>>>>> 48dfb9c47282822683662b4a55e09805a5855c33
}
