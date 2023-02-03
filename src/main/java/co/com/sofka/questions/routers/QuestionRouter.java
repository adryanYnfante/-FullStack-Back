package co.com.sofka.questions.routers;

import co.com.sofka.questions.collections.Answer;
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
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.Function;


import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.resourceLookupFunction;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class QuestionRouter {


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
                                            description = "Succesful",
                                            content = @Content(schema = @Schema(
                                                    implementation = QuestionRouter.class
                                            ))
                                    )
                            }
                    )
            )
    public RouterFunction<ServerResponse> getAll(ListUseCase listUseCase) {
        return route(GET("/getAll"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(listUseCase.get(), QuestionDTO.class))
        );
    }
    @Bean
    @RouterOperation(
            path = "/pagination/{pageNumber}",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            },
            method = RequestMethod.GET,
            beanClass = QuestionRouter.class,
            beanMethod = "entitiesRoute",
            operation = @Operation(
                    operationId = "entitiesRoute",
                    responses = {
                            @ApiResponse(
                                    responseCode = "200",
                                    description = "Succesful",
                                    content = @Content(schema = @Schema(
                                            implementation = QuestionRouter.class
                                    ))
                            ),
                            @ApiResponse(
                                    responseCode  ="400", description = "Not found"
                            )
                    },
                    parameters = {
                            @Parameter(in = ParameterIn.PATH, name = "pageNumber")
                    }
            )
    )
    public RouterFunction<ServerResponse> entitiesRoute(ListUseCase listUseCase) {
        return route(GET("/pagination/{pageNumber}"),
                request -> ok().body(listUseCase.getPages(
                        Integer.valueOf(request.pathVariable("pageNumber"))
                ), QuestionDTO.class));
    }

    @Bean
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
                                    description = "Succesful",
                                    content = @Content(schema = @Schema(
                                            implementation = QuestionRouter.class
                                    ))
                            ),
                            @ApiResponse(
                                    responseCode  ="400", description = "Not found"
                            )
                    },
                    parameters = {
                            @Parameter(in = ParameterIn.PATH, name = "userId")
                    }
            )
    )
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

    @Bean
    @RouterOperation(
            path = "/create",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            },
            method = RequestMethod.POST,
            beanClass = QuestionRouter.class,
            beanMethod = "create",
            operation = @Operation(
                    operationId = "create",
                    responses = {
                            @ApiResponse(
                                    responseCode = "200",
                                    description = "Succesful",
                                    content = @Content(schema = @Schema(
                                            implementation = QuestionRouter.class
                                    ))
                            ),
                            @ApiResponse(
                                    responseCode  ="400", description = "Not found"
                            ),
                    },
                    requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = QuestionRouter.class)))

            )
    )
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
                                    description = "Succesful",
                                    content = @Content(schema = @Schema(
                                            implementation = QuestionRouter.class
                                    ))
                            ),
                            @ApiResponse(
                                    responseCode  ="400", description = "Not found"
                            )
                    },
                    parameters = {
                            @Parameter(in = ParameterIn.PATH, name = "id")
                    }
            )
    )
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
    @RouterOperation(
            path = "/add",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            },
            method = RequestMethod.POST,
            beanClass = QuestionRouter.class,
            beanMethod = "addAnswer",
            operation = @Operation(
                    operationId = "addAnswer",
                    responses = {
                            @ApiResponse(
                                    responseCode = "200",
                                    description = "Succesful",
                                    content = @Content(schema = @Schema(
                                            implementation = QuestionRouter.class
                                    ))
                            ),
                            @ApiResponse(
                                    responseCode  ="400", description = "Not found"
                            ),
                    },
                    requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = QuestionRouter.class)))

            )
    )
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
    @RouterOperation(
            path = "/update-answer",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            },
            method = RequestMethod.POST,
            beanClass = QuestionRouter.class,
            beanMethod = "updateAnswer",
            operation = @Operation(
                    operationId = "updateAnswer",
                    responses = {
                            @ApiResponse(
                                    responseCode = "200",
                                    description = "Succesful",
                                    content = @Content(schema = @Schema(
                                            implementation = Answer.class
                                    ))
                            ),
                            @ApiResponse(
                                    responseCode  ="400", description = "Not found"
                            ),
                    },
                    requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = Answer.class)))

            )
    )
    public RouterFunction<ServerResponse> updateAnswer(UpdateAnswerUseCase updateAnswerUseCase) {
        return route(POST("/update-answer").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(AnswerDTO.class)
                        .flatMap(addAnswerDTO -> updateAnswerUseCase.apply(addAnswerDTO)
                                .flatMap(result -> ServerResponse.ok()
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                        )
        );
    }

    @Bean
    @RouterOperation(
            path = "/update",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            },
            method = RequestMethod.POST,
            beanClass = QuestionRouter.class,
            beanMethod = "editQuestion",
            operation = @Operation(
                    operationId = "editQuestion",
                    responses = {
                            @ApiResponse(
                                    responseCode = "200",
                                    description = "Succesful",
                                    content = @Content(schema = @Schema(
                                            implementation = QuestionRouter.class
                                    ))
                            ),
                            @ApiResponse(
                                    responseCode  ="400", description = "Not found"
                            ),
                    },
                    requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = QuestionRouter.class)))

            )
    )
    public RouterFunction<ServerResponse> editQuestion(UpdateUseCase updateUseCaseAnswerUseCase) {
        return route(PUT("/update").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(QuestionDTO.class)
                        .flatMap(updateQuestionDTO -> updateUseCaseAnswerUseCase.apply(updateQuestionDTO)
                                .flatMap(result -> ServerResponse.ok()
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                        )
        );
    }

    @Bean
    @RouterOperation(
            path = "/delete/{id}",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            },
            method = RequestMethod.DELETE,
            beanClass = QuestionRouter.class,
            beanMethod = "delete",
            operation = @Operation(
                    operationId = "delete",
                    responses = {
                            @ApiResponse(
                                    responseCode = "200",
                                    description = "Succesful",
                                    content = @Content(schema = @Schema(
                                            implementation = QuestionRouter.class
                                    ))
                            ),
                            @ApiResponse(
                                    responseCode  ="400", description = "Not found"
                            ),
                    },
                    parameters = @Parameter(in = ParameterIn.PATH, name = "id")

            )
    )
    public RouterFunction<ServerResponse> delete(DeleteUseCase deleteUseCase) {
        return route(
                DELETE("/delete/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.accepted()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(deleteUseCase.apply(request.pathVariable("id")), Void.class))
        );
    }
    @Bean
    @RouterOperation(
            path = "/countQuestions",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            },
            method = RequestMethod.GET,
            beanClass = QuestionRouter.class,
            beanMethod = "getTotalQuestions",
            operation = @Operation(
                    operationId = "getTotalQuestions",
                    responses = {
                            @ApiResponse(
                                    responseCode = "200",
                                    description = "Succesful",
                                    content = @Content(schema = @Schema(
                                            implementation = QuestionRouter.class
                                    ))
                            )
                    }
            )
    )
    public RouterFunction<ServerResponse> getTotalQuestions(GetTotalQuestionsUseCase getTotalQuestionsUseCase) {
        return route(GET("/countQuestions"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getTotalQuestionsUseCase.getTotalQuestions(), Long.class))
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
            beanMethod = "getTotalQuestions",
            operation = @Operation(
                    operationId = "getTotalQuestions",
                    responses = {
                            @ApiResponse(
                                    responseCode = "200",
                                    description = "Succesful",
                                    content = @Content(schema = @Schema(
                                            implementation = QuestionRouter.class
                                    ))
                            )
                    }
            )
    )
    public RouterFunction<ServerResponse> getTotalPages(GetTotalPageUseCase getTotalPageUseCase) {
        return route(GET("/totalPages"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getTotalPageUseCase.getTotalPages(), Integer.class))
        );
    }
}
