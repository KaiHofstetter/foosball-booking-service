package net.softwareminds.foosballbooking.service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
public class OAuth2ServerConfig {

  private static final String FOOSBALL_RESOURCE_ID = "foosballbooking";

  @Configuration
  @EnableAuthorizationServer
  protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    public static final String CLIENT_CREDENTIALS_GRANT = "client_credentials";
    public static final String AUTHORIZATION_CODE_GRANT = "authorization_code";
    public static final String REFRESH_TOKEN = "refresh_token";
    public static final String IMPLICIT_GRANT = "implicit";
    public static final String RESOURCE_OWNER_PASSWORD_CREDENTIALS_GRANT = "password";

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

      // @formatter:off
      clients.inMemory().withClient("Foosball Booking Read Client")
                           .secret("secret")
                           .resourceIds(FOOSBALL_RESOURCE_ID)
                           .authorizedGrantTypes( CLIENT_CREDENTIALS_GRANT)
                           .scopes("Read_Booking_List")
                        .and()
		 	.withClient("Foosball Booking Read/Write Client")
                           .secret("secret")
			   .resourceIds(FOOSBALL_RESOURCE_ID)
			   .authorizedGrantTypes( AUTHORIZATION_CODE_GRANT,  REFRESH_TOKEN,  IMPLICIT_GRANT,  RESOURCE_OWNER_PASSWORD_CREDENTIALS_GRANT)
                           .scopes("Read_Booking_List", "Add_Booking")
                           .redirectUris("http://localhost:8090/foosball-booking-client/booking");
        // @formatter:on
    }


    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
      endpoints.authenticationManager(authenticationManager);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
      // The value will be returned in 'WWW-Authenticate' header, if the client credentials in the 'Authorization' header are wrong:
      // WWW-Authenticate: Basic realm="Foosball Booking Service API"
      oauthServer.realm("Foosball Booking Service API");
    }
  }


  @Configuration
  @EnableResourceServer
  protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
      resources.resourceId(FOOSBALL_RESOURCE_ID);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
      // @formatter:off
      http.requestMatchers()
          .antMatchers("/bookings").and()
          .authorizeRequests()
            .antMatchers(HttpMethod.GET, "/bookings").access("#oauth2.hasScope('Read_Booking_List')")
            .antMatchers(HttpMethod.POST, "/bookings").access("#oauth2.hasScope('Add_Booking')");
      // @formatter:on
    }
  }
}
