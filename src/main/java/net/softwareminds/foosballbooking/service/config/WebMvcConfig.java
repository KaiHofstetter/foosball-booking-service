package net.softwareminds.foosballbooking.service.config;

import net.softwareminds.foosballbooking.service.controller.AccessConfirmationController;
import net.softwareminds.foosballbooking.service.controller.FoosballBookingController;
import net.softwareminds.foosballbooking.service.repository.MemoryBookingStorage;
import net.softwareminds.foosballbooking.service.resources.BookingResourceAssembler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.web.accept.ContentNegotiationManagerFactoryBean;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Arrays;

@Configuration
@EnableHypermediaSupport(type = {EnableHypermediaSupport.HypermediaType.HAL})
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter {

  @Bean
  public ContentNegotiatingViewResolver contentViewResolver() throws Exception {
    ContentNegotiationManagerFactoryBean contentNegotiationManager = new ContentNegotiationManagerFactoryBean();
    contentNegotiationManager.addMediaType("json", MediaType.APPLICATION_JSON);

    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setPrefix("/WEB-INF/jsp/");
    viewResolver.setSuffix(".jsp");

    MappingJackson2JsonView defaultView = new MappingJackson2JsonView();
    defaultView.setExtractValueFromSingleKeyModel(true);

    ContentNegotiatingViewResolver contentViewResolver = new ContentNegotiatingViewResolver();
    contentViewResolver.setContentNegotiationManager(contentNegotiationManager.getObject());
    contentViewResolver.setViewResolvers(Arrays.<ViewResolver>asList(viewResolver));
    contentViewResolver.setDefaultViews(Arrays.<View>asList(defaultView));
    return contentViewResolver;
  }

  @Bean
  public AccessConfirmationController accessConfirmationController(ClientDetailsService clientDetailsService) {
    AccessConfirmationController accessConfirmationController = new AccessConfirmationController();
    accessConfirmationController.setClientDetailsService(clientDetailsService);
    return accessConfirmationController;
  }

  @Override
  public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
    configurer.enable();
  }

  @Bean
  public BookingResourceAssembler bookingResourceAssembler() {
    return new BookingResourceAssembler();
  }

  @Bean
  public FoosballBookingController foosballServiceController() {
    return new FoosballBookingController(new MemoryBookingStorage());
  }

}
