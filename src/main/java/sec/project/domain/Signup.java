package sec.project.domain;

import javax.persistence.Entity;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Signup extends AbstractPersistable<Long> {

    private String name;
    private String address;
    private String password;

    public Signup() {
        super();
    }
    
    public Signup(String name, String address, String password) {
        this();
        this.name = name;
        this.address = address;
        this.password = password;
        System.out.println("New user: " + name + " " + address + " " + password);
    }

    public Signup(String name, String address) {
        this();
        this.name = name;
        this.address = address;
        System.out.println("New user: " + name + " " + address);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
     public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}