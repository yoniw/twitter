package twitter.logic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import twitter.data.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	public User findByName(@Param("user_name") String name);
}