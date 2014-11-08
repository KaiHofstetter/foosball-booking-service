package net.softwareminds.foosballbooking.service.config;

import net.softwareminds.foosballbooking.service.oauth.UserApprovalHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
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
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
public class OAuth2ServerConfig {

  private static final String FOOSBALL_RESOURCE_ID = "foosballbooking";

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

  @Configuration
  @EnableAuthorizationServer
  protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private org.springframework.security.oauth2.provider.approval.UserApprovalHandler userApprovalHandler;

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
                           .redirectUris("http://localhost:8090/foosball-booking-client/authorization-code-callback");

        // @formatter:on
      }


      @Override
      public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore).userApprovalHandler(userApprovalHandler).authenticationManager(authenticationManager);
      }

      @Override
      public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.realm("sparklr2/client");
      }

      @Bean
      public TokenStore tokenStore() {
        return new InMemoryTokenStore();
      }
    }

    protected static class Stuff {

      @Autowired
      private ClientDetailsService clientDetailsService;

      @Autowired
      private TokenStore tokenStore;

      @Bean
      public ApprovalStore approvalStore() throws Exception {
        TokenApprovalStore store = new TokenApprovalStore();
        store.setTokenStore(tokenStore);
        return store;
      }

      @Bean
      @Lazy
      @Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
      public UserApprovalHandler userApprovalHandler() throws Exception {
        UserApprovalHandler handler = new UserApprovalHandler();
        handler.setApprovalStore(approvalStore());
        handler.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetailsService));
        handler.setClientDetailsService(clientDetailsService);
        handler.setUseApprovalStore(true);
        return handler;
      }
    }
  }
