package sec.project.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.domain.Signup;
import sec.project.repository.SignupRepository;

@Controller
public class SignupController {

    @Autowired
    private SignupRepository signupRepository;
    
    @RequestMapping("*")
    public String defaultMapping() {
        return "redirect:/form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String loadForm() {
        return "form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String submitForm(@RequestParam String name, @RequestParam String address) {
        System.out.println(name + " " + address);
        signupRepository.saveAndFlush(new Signup(name, address));
        return "done";
    }
    
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loadLogin() {
        return "login";
    }
    
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String submitLogin(@RequestParam String username, @RequestParam String password) {
        System.out.println("REDIRECTING TICKET \n \n \n");
        return "ticket";
    }
    
    @RequestMapping(value = "/ticket", method = RequestMethod.GET)
    public String loadTicket() {
        return "ticket";
    }
    
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String loadAdmin() {
       List<Signup> allSignups = signupRepository.findAll();
       for(Signup client : allSignups) {
           System.out.println(client.getName());
           System.out.println(client.getAddress());
       }
        return "admin";
    }
    
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String loadSearch() {
        return "search";
    }
    
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String submitSearch(Model model, @RequestParam String name) throws SQLException {
        
       /** Connection connection = DriverManager.getConnection("jdbc:h2:file:./database", "sa", "");
        Statement st = connection.createStatement();
        String query = "SELECT * FROM  Signup where name=" + name;

        ResultSet res = st.executeQuery(query);
        String q="SELECT FROM Signup WHERE name=" + name;
        Query query=em.createQuery(q);
        List users=query.getResultList();
        
        connection.close();*/
                
                
         
        /* List<Signup> results = SignupRepositoryImpl.createNativeQuery(
            "SELECT FROM Signup WHERE name=" + name)
        .getResultList();
        */
        //This is the safe way: List<Signup> results = signupRepository.findByName(name, new Sort(Sort.Direction.ASC, "name"));
        List<Signup> results = signupRepository.findByName(name, JpaSort.unsafe("LENGTH(name)"));
                                                //findByName("lannister", new Sort("LENGTH(firstname)"))
        //List<Signup> results = signupRepository.findByName(name);
        System.out.println("FOUND: " + results);
        model.addAttribute("results", results);
        return "search";
    }
    
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteSignup(@RequestParam long signupId){
        System.out.println("Deleting " + signupId);
        //long id = Long.parseLong(signupId, 10);
        //System.out.println("Parsed: " + id);
        signupRepository.deleteById(signupId);
        return "admin";
    }
    
    @ModelAttribute("list")
    public List<Signup> allSignups() {
        return signupRepository.findAll();
    }
}
