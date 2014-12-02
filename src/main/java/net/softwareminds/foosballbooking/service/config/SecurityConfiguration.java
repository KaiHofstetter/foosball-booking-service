package net.softwareminds.foosballbooking.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    // @formatter:off
    auth.inMemoryAuthentication()
        .withUser("Peter")
          .password("secret")
          .roles("USER")
        .and()
        .withUser("Bob")
          .password("secret")
          .roles("USER");
    // @formatter:on
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers("/webjars/**");
  }

  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // @formatter:off
    http.authorizeRequests()
          .antMatchers("/login.jsp").permitAll()
        .and()
        .authorizeRequests()
          .anyRequest().hasRole("USER")
        .and()
        .exceptionHandling()
          .accessDeniedPage("/login.jsp?authorization_error=true")
        .and()
        .formLogin()
          .loginPage("/login.jsp")
          .loginProcessingUrl("/login.do")
          .failureUrl("/login.jsp?authentication_error=true")
        .and()
        .csrf().disable();
    // @formatter:off
    }
}
