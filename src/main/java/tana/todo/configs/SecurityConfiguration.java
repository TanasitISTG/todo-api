package tana.todo.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.AntPathMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    private static final String[][] PUBLIC_ENDPOINTS = {
            { HttpMethod.POST.toString(), "/api/auth" },
            { HttpMethod.POST.toString(), "/api/user"},
    };

    public static boolean isPublicEndpoint(String method, String path) {
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        for (String[] endpoint : PUBLIC_ENDPOINTS) {
            String httpMethod = endpoint[0];
            String endpointPath = endpoint[1];

//            System.out.println("Request endpoint: " + path + " " + method);
//            System.out.println("Public endpoint: " + endpointPath + " " + httpMethod);
//            System.out.println("Endpoint match: " + antPathMatcher.match(endpointPath, path));
//            System.out.println("Method match: " + httpMethod.equals(method));
//            System.out.println("====================================");

            if (antPathMatcher.match(endpointPath, path) && httpMethod.equals(method)) {
                return true;
            }
        }
        return false;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> {
                            for (String[] endpoint : PUBLIC_ENDPOINTS) {
                                String httpMethod = endpoint[0];
                                String endpointPath = endpoint[1];
                                request.requestMatchers(HttpMethod.valueOf(httpMethod), endpointPath).permitAll();
                            }
                            request.anyRequest().authenticated();
                        }
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
