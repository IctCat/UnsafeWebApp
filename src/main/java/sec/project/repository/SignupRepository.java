package sec.project.repository;

import java.io.FileReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.List;
//import org.h2.tools.RunScript;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import sec.project.domain.Signup;

public interface SignupRepository extends JpaRepository<Signup, Long> {
    /**
    // Open connection
    public static void sql() throws Throwable {
    
        Connection connection = DriverManager.getConnection("jdbc:h2:file:./database", "sa", "");

        // Execute query and retrieve the query results
        try {
            //RunScript.execute(connection, new FileReader("data.sql"));
            
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM Signup");

            // Do something with the results -- here, we print the books
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");

                System.out.println(id + "\t" + name);
            }

        // Close the connection
        resultSet.close();
        connection.close();
        }
        catch (Throwable t) {
                System.err.println(t.getMessage());
            }
    }*/
    

    //List<Signup> findByName(String name);
    //@Query(value = "SELECT * FROM SIGNUP WHERE NAME = ?1",
    //countQuery = "SELECT count(*) FROM USERS WHERE NAME = ?1",
    //nativeQuery = true)
    List<Signup> findByName(String name);
    

    /**@Modifying
    @Query("DELETE FROM Signup WHERE id=?1")
    void deleteById(long signupId);
    */
    //String sql = "SELECT u FROM User u WHERE id=" + id;
    
    //@Query("DELETE FROM Signup WHERE id=" + signupId)
    //String expected = "DELETE FROM Signup WHERE id= :signupId";
    
    @Transactional
    Long deleteById(long signupId);
    
    
    //@Query("select s from Signup s where s.name = :name")
    @Query("select s from Signup s where LOWER(s.name) = LOWER(:name)")
    List<Signup> findByName(@Param("name") String name, Sort sort);
    
}
