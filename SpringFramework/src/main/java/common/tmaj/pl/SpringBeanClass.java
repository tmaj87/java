package common.tmaj.pl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SpringBeanClass {

    final private SpringInterfaceWTF service;

    @Autowired
    public SpringBeanClass(SpringInterfaceWTF service) {
        this.service = service;
    }

    public void print() {
        System.out.println(this.service.print());
    }
}
