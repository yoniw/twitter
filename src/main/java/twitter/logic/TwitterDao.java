package twitter.logic;

import java.util.Collection;

import twitter.data.Message;
import twitter.data.User;

public interface TwitterDao {

	public User createUser(String userName);
	
	public Collection<User> getAllUsers();

	public Message addMessage(int userId, String message);

	public Collection<Message> getAllMessages();

	public Collection<Message> getUserFeed(int userId);

	public Collection<User> follow(int followingUserId, int userIdToFollow);

	public Collection<User> unfollow(int followingUserId, int userIdToUnfollow);

	public Collection<User> getAllFollowedUsers(int userId);

	public void deleteUser(Integer userId);

	public void deleteMessage(Integer messageId);
}
