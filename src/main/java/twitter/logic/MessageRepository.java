package twitter.logic;

import org.springframework.data.jpa.repository.JpaRepository;

import twitter.data.Message;

public interface MessageRepository extends JpaRepository<Message, Integer>{

}
