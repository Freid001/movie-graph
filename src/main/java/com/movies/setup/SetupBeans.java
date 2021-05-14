package com.movies.setup;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableNeo4jRepositories(basePackages="com.movies.repositories")
@EnableTransactionManagement
@EnableSwagger2
@Log4j2
public class SetupBeans {
    public static final String MOVIE = "Movie";
    public static final String PERSON = "Person";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .build()
                .tags(new Tag(MOVIE, "Create & retrieve movie resources."))
                .tags(new Tag(PERSON, "Create, retrieve & delete person resources."));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Movies Api")
                .description("")
                .termsOfServiceUrl(null)
                .version("1.0")
                .build();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public MeterRegistryCustomizer meterRegistryCustomizer(MeterRegistry meterRegistry) {
        return meterRegistry1 -> {
            meterRegistry.config()
                    .commonTags("application", "micrometer-com.movies-api");
        };
    }

}