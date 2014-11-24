package net.softwareminds.foosballbooking.service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
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
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
public class OAuth2ServerConfig {

  private static final String FOOSBALL_RESOURCE_ID = "foosballbooking";

  @Configuration
  @EnableAuthorizationServer
  protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

      // @formatter:off
      clients.inMemory().withClient("Foosball Booking Read Client")
                           .resourceIds(FOOSBALL_RESOURCE_ID)
                           .authorizedGrantTypes("client_credentials")
                           .authorities("ROLE_CLIENT")
                           .scopes("Read_Booking_List")
                           .secret("secret")
                        .and()
		 	.withClient("Foosball Booking Read/Write Client")
			   .resourceIds(FOOSBALL_RESOURCE_ID)
			   .authorizedGrantTypes("authorization_code", "refresh_token")
			   .authorities("ROLE_CLIENT")
                           .scopes("Read_Booking_List", "Add_Booking")
                           .secret("secret")
                           .redirectUris("http://localhost:8090/foosball-booking-client/booking");
        // @formatter:on
    }


    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
      endpoints.tokenStore(new InMemoryTokenStore())
               .authenticationManager(authenticationManager);
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
