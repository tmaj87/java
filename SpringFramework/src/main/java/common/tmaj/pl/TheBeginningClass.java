package common.tmaj.pl;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan
public class TheBeginningClass {

    protected boolean __BOOL;
    protected int __INT;

    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(TheBeginningClass.class);
        SpringBeanClass printer = context.getBean(SpringBeanClass.class);

        printer.print();

    }

    @Bean
    SpringInterfaceWTF mockUnnamed() {
        return new SpringInterfaceWTF() {

            public String print() {
                return "yep..!";
            }
        };
    }

    class MeLikeCpp {

        public MeLikeCpp() {
            // super.__BOOL = true;

            // !!
            // maly wentylator do B
            // !!
        }
    }
}
