package pl.edu.agh.eaiib.odis.odis.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.filter.CommonsRequestLoggingFilter
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2
import javax.servlet.Filter

@Configuration
@EnableSwagger2
class RestConfig {

    @Bean
    fun configureCORS() = object : WebMvcConfigurer {
        override fun addCorsMappings(registry: CorsRegistry) {
            registry.addMapping("/**")
        }
    }

    @Bean
    fun logFilter(): Filter {
        val filter = CommonsRequestLoggingFilter()
        filter.setIncludeClientInfo(true)
        filter.isIncludeHeaders = true
        filter.setIncludeQueryString(true)
        filter.setIncludePayload(true)
        filter.setMaxPayloadLength(5120)
        return filter
    }

    @Bean
    fun apiDocket(): Docket =
            Docket(DocumentationType.SWAGGER_2)
                    .apiInfo(apiInfo())
                    .select()
                    .apis(RequestHandlerSelectors.any())
                    .paths(PathSelectors.regex("/api/.*"))
                    .build()

    private fun apiInfo() = ApiInfoBuilder()
            .title("ODiS REST API")
            .license("EAIIB AGH License")
            .licenseUrl("http://eaiib.agh.edu.pl")
            .termsOfServiceUrl("http://eaiib.agh.edu.pl")
            .version("1.0")
            .description("REST API for ODiS project - EAIIB AGH")
            .build()
}