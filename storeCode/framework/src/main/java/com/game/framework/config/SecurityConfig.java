package com.game.framework.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.filter.CorsFilter;
import com.game.framework.security.filter.JwtAuthenticationTokenFilter;
import com.game.framework.manager.factory.handle.AuthenticationEntryPointImpl;
import com.game.framework.manager.factory.handle.LogoutSuccessHandlerImpl;

/**
 * spring security configuration
 *
 * @author Yu Yue
 */
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    /**
     * Custom User authentication logic
     */
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * Authentication failure handling class
     */
    @Autowired
    private AuthenticationEntryPointImpl unauthorizedHandler;

    /**
     * Exit handler class
     */
    @Autowired
    private LogoutSuccessHandlerImpl logoutSuccessHandler;

    /**
     * token authentication filter
     */
    @Autowired
    private JwtAuthenticationTokenFilter authenticationTokenFilter;

    /**
     * Cross domain filter
     */
    @Autowired
    private CorsFilter corsFilter;

    /**
     * Solved the inability to directly inject AuthenticationManager
     *
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception
    {
        return super.authenticationManagerBean();
    }

    /**
     * anyRequest | matches all request paths
     * access | Accessible when SpringEl expression evaluates to true
     * anonymous | Anonymous can access
     * denyAll | User cannot access
     * fullyAuthenticated | User is fully authenticated and can be accessed (automatic login under non-remember-me)
     * hasAnyAuthority | If there are parameters, the parameters represent permissions, any one of them can access
     * hasAnyRole | If there are parameters, the parameters represent roles, any one of them can access
     * hasAuthority | If there is a parameter, the parameter represents the authority, then its authority can access
     * hasIpAddress | If there is a parameter, the parameter indicates the IP address, if the UserIP and the parameter match, it can be accessed
     * hasRole | If there is a parameter, the parameter represents a role, then its role can access
     * permitAll | User can access arbitrarily
     * rememberMe | Allow access for users logged in via remember-me
     * authenticated | User can access after login
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception
    {
        httpSecurity
                .cors().and().cors().disable()
                // CSRF disabled because session is not used
                .csrf().disable()

                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()

                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                .authorizeRequests()

                .antMatchers("/login", "/register","/sendCode", "/captchaImage").anonymous()
                .antMatchers(
                        HttpMethod.GET,
                        "/",
                        "/*.html",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/profile/**"
                ).permitAll()
                .antMatchers("/store/**/**").permitAll()
                .antMatchers("/file/**").permitAll()
                .antMatchers("/email/**").permitAll()
                .antMatchers("/swagger-ui.html").anonymous()
                .antMatchers("/swagger-resources/**").anonymous()
                .antMatchers("/webjars/**").anonymous()
                .antMatchers("/*/api-docs").anonymous()
                .antMatchers("/druid/**").anonymous()


                .anyRequest().authenticated()
                .and()
                .headers().frameOptions().disable();
        httpSecurity.logout().logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler);

        httpSecurity.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        httpSecurity.addFilterBefore(corsFilter, JwtAuthenticationTokenFilter.class);
        httpSecurity.addFilterBefore(corsFilter, LogoutFilter.class);
    }

    /**
     * Strong hash hash encryption implementation
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    /**
     * Identity authentication interface
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers(HttpMethod.OPTIONS, "/**");
    }
}
