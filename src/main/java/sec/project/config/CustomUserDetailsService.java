package sec.project.config;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sec.project.domain.Signup;
import sec.project.repository.SignupRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private Map<String, String> accountDetails;
    @Autowired
    private SignupRepository signupRepository;

    @PostConstruct
    public void init() {
        // this data would typically be retrieved from a database
        // Replace the hash after "ted" with the hash that you got from SecurityConfiguration print.
        this.accountDetails = new TreeMap<>();
        this.accountDetails.put("ted", "hacker");//"$2a$10$RaHv8d2PZnF62ne2ZEQKgOxu5XvPFmOXAS8HssaMF63u5ePgOCChS");
        Signup signup = new Signup();
        signup.setName("Mr. Moneybags");
        signup.setAddress("Moneystreet");
        signup.setPassword("sweetmoney");
        signupRepository.save(signup);
        
        signup.setName("Hackerman");
        signup.setAddress("Errorstreet 404");
        signup.setPassword("invincible");
        signupRepository.save(signup);
    }

    public void createUser(String username, String hash) {
        this.accountDetails.put(username, hash);
    }
    
    public void checkUsers() {
        for(String account : this.accountDetails.values()){
            System.out.println(account);
        }
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!this.accountDetails.containsKey(username)) {
            throw new UsernameNotFoundException("No such user: " + username);
        }

        return new org.springframework.security.core.userdetails.User(
                username,
                this.accountDetails.get(username),
                true,
                true,
                true,
                true,
                Arrays.asList(new SimpleGrantedAuthority("USER")));
    }
}
