package common.tmaj.pl;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan
public class TheBeginningClass {

    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(TheBeginningClass.class);
        SpringBeanClass printer = context.getBean(SpringBeanClass.class);

        printer.print();

    }

    @Bean
    SpringInterface mockUnnamed() {
        return new SpringInterfaceWTF() {

            public String print() {
                return "yep..!";
            }
        };
    }
}
