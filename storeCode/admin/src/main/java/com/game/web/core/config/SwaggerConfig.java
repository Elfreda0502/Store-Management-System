package com.game.web.core.config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.game.common.config.RuoYiConfig;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Swagger2 interface configuration
 *
 * @author Yu Yue
 */
@Configuration
public class SwaggerConfig
{

    @Autowired
    private RuoYiConfig ruoyiConfig;


    @Value("${swagger.enabled}")
    private boolean enabled;


    @Value("${swagger.pathMapping}")
    private String pathMapping;

    /**
     * Create API
     */
    @Bean
    public Docket createRestApi()
    {
        return new Docket(DocumentationType.OAS_30)

                .enable(enabled)

                .apiInfo(apiInfo())

                .select()

                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))

                // .apis(RequestHandlerSelectors.basePackage("com.ruoyi.project.tool.swagger"))

                .paths(PathSelectors.any())
                .build()
                /* Set security mode, swagger can set access token */
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts())
                .pathMapping(pathMapping);
    }

    /**
     * Security mode, where the specified token is passed through the Authorization header request header
     */
    private List<SecurityScheme> securitySchemes()
    {
        List<SecurityScheme> apiKeyList = new ArrayList<SecurityScheme>();
        apiKeyList.add(new ApiKey("Authorization", "Authorization", In.HEADER.toValue()));
        return apiKeyList;
    }

    /**
     * security context
     */
    private List<SecurityContext> securityContexts()
    {
        List<SecurityContext> securityContexts = new ArrayList<>();
        securityContexts.add(
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .operationSelector(o -> o.requestMappingPattern().matches("/.*"))
                        .build());
        return securityContexts;
    }

    /**
     * Safe reference of default
     */
    private List<SecurityReference> defaultAuth()
    {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> securityReferences = new ArrayList<>();
        securityReferences.add(new SecurityReference("Authorization", authorizationScopes));
        return securityReferences;
    }

    /**
     * Add summary information
     */
    private ApiInfo apiInfo()
    {

        return new ApiInfoBuilder()

                .title("Title：Smart Store Management System_Interface Documentation")

                .description("Description: It is used to manage the personnel information of the companies under the group, including XXX, XXX modules...")

                .contact(new Contact(ruoyiConfig.getName(), null, null))

                .version("Version number:" + ruoyiConfig.getVersion())
                .build();
    }
}
