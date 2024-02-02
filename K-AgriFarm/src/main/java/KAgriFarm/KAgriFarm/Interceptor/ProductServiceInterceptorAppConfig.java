package KAgriFarm.KAgriFarm.Interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class ProductServiceInterceptorAppConfig implements WebMvcConfigurer {
@Autowired
BasicAuthInterceptor basicAuthInterceptor;
public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(basicAuthInterceptor);
 }
}
