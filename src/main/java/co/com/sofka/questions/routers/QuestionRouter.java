package co.com.sofka.questions.routers;

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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.Function;
import java.lang.invoke.MethodType;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class QuestionRouter {

    @Bean
    @RouterOperation(
            // Obtener todas las preguntas
            path = "/getAll",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            },
            method = RequestMethod.GET,
            beanClass = ListUseCase.class,
            beanMethod = "get",
            operation = @Operation(
                    operationId = "get",
                    responses = {
                            @ApiResponse(
                                    responseCode = "200",
                                    description = "Operación exitosa!",
                                    content = @Content(schema = @Schema(
                                            implementation = QuestionDTO.class
                                    ))
                            ),
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
            path = "/pagination/{page}",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            },
            method = RequestMethod.GET,
            beanClass = ListUseCase.class,
            beanMethod = "get",
            operation = @Operation(
                    operationId = "get",
                    responses = {
                            @ApiResponse(
                                    responseCode = "200",
                                    description = "Operación exitosa!",
                                    content = @Content(schema = @Schema(
                                            implementation = QuestionDTO.class
                                    ))
                            ),
                    },
                    parameters = {
                            @Parameter(in = ParameterIn.PATH,name = "page")
                    }
            )
    )
    public RouterFunction<ServerResponse> getQuestionPageable(ListUseCase listUseCase) {
        return route(GET("/pagination/{page}"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(listUseCase.getPage(Integer.parseInt(request.pathVariable("page"))), QuestionDTO.class))
        );
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
                                    description = "Operación exitosa!",
                                    content = @Content(schema = @Schema(
                                            implementation = QuestionRouter.class
                                    ))
                            ),
                    },
                    parameters = {
                            @Parameter(in = ParameterIn.PATH,name = "userId")
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
            // Crear pregunta
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
                                    description = "Operación exitosa!",
                                    content = @Content(schema = @Schema(
                                            implementation = QuestionDTO.class
                                    ))
                            )

                    },
                    requestBody = @RequestBody(
                            content = @Content(schema = @Schema(
                                    implementation = QuestionDTO.class
                            ))
                    )
            )
    )
    public RouterFunction<ServerResponse> create(CreateUseCase createUseCase) {
        Function<QuestionDTO, Mono<ServerResponse>> executor = questionDTO -> createUseCase.apply(questionDTO)
                .flatMap(result -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
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
            beanClass = GetUseCase.class,
            beanMethod = "get",
            operation = @Operation(
                    operationId = "get",
                    responses = {
                            @ApiResponse(
                                    responseCode = "200",
                                    description = "Operación exitosa!",
                                    content = @Content(schema = @Schema(
                                            implementation = QuestionDTO.class
                                    ))
                            ),
                    },
                    parameters = {
                            @Parameter(in = ParameterIn.PATH,name = "id")
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
            beanClass = AddAnswerUseCase.class,
            beanMethod = "post",
            operation = @Operation(
                    operationId = "post",
                    responses = {
                            @ApiResponse(
                                    responseCode = "200",
                                    description = "Operación exitosa!",
                                    content = @Content(schema = @Schema(
                                            implementation = AnswerDTO.class
                                    ))
                            ),
                    }
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
                                    responseCode = "202",
                                    description = "Operación exitosa!",
                                    content = @Content(schema = @Schema(
                                            implementation = QuestionRouter.class
                                    ))
                            ),
                    },
                    parameters = {
                            @Parameter(in = ParameterIn.PATH,name = "id")
                    }
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
            path = "/totalPages",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            },
            method = RequestMethod.GET,
            beanClass = ListUseCase.class,
            beanMethod = "get",
            operation = @Operation(
                    operationId = "get",
                    responses = {
                            @ApiResponse(
                                    responseCode = "200",
                                    description = "Operación exitosa!",
                                    content = @Content(schema = @Schema(
                                            implementation = Integer.class
                                    ))
                            ),
                    }
            )
    )
    public RouterFunction<ServerResponse> getTotalPage(ListUseCase listUseCase) {
        return route(GET("/totalPages"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(listUseCase.getTotalPages(), Integer.class)));
    }

    @Bean
    @RouterOperation(
            path = "/countQuestions",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            },
            method = RequestMethod.GET,
            beanClass = ListUseCase.class,
            beanMethod = "get",
            operation = @Operation(
                    operationId = "get",
                    responses = {
                            @ApiResponse(
                                    responseCode = "200",
                                    description = "Operación exitosa!",
                                    content = @Content(schema = @Schema(
                                            implementation = Long.class
                                    ))
                            ),
                    }
            )
    )
    public RouterFunction<ServerResponse> getTotalQuestions(ListUseCase listUseCase) {
        return route(GET("/countQuestions"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(listUseCase.getTotalQuestions(), Long.class)));
    }

    @Bean
    @RouterOperation(
            path = "/update",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            },
            method = RequestMethod.PUT,
            beanClass = QuestionRouter.class,
            beanMethod = "updateQuestion",
            operation = @Operation(
                    operationId = "updateQuestion",
                    responses = {
                            @ApiResponse(
                                    responseCode = "200",
                                    description = "Operación exitosa!",
                                    content = @Content(schema = @Schema(
                                            implementation = QuestionRouter.class
                                    ))
                            ),
                    },
                    requestBody = @RequestBody(
                            content = @Content(schema = @Schema(
                                    implementation = QuestionRouter.class
                            ))
                    )
            )
    )
    public RouterFunction<ServerResponse> updateQuestion(UpdateUseCase updateUseCase) {
        Function<QuestionDTO, Mono<ServerResponse>> executor = questionDTO ->  updateUseCase.apply(questionDTO)
                .flatMap(result -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(result));
        return route(
                PUT("/update").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(QuestionDTO.class).flatMap(executor)
        );
    }
}
