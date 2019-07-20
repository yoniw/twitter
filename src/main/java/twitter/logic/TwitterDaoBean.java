package twitter.logic;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import twitter.data.Message;
import twitter.data.User;
import twitter.exceptions.TracelessException;

@Repository
@Transactional
public class TwitterDaoBean implements TwitterDao {

	private final UserRepository userRep;
	private final MessageRepository messageRep;
	
	@Autowired
	public TwitterDaoBean(UserRepository userRep, MessageRepository messageRep) {
		this.userRep = userRep;
		this.messageRep = messageRep;
	}
	
	@Override
	public User createUser(String userName) {
		if (userRep.findByName(userName) != null) {
			throw new TracelessException("User name '" + userName + "' already taken");
		} else {
			User user = new User();
			user.setName(userName);
			return userRep.save(user);
		}
	}

	@Override
	public Collection<User> getAllUsers() {
		return userRep.findAll();
	}

	@Override
	public Message addMessage(int userId, String message) {
		User user = getUser(userId);
		
		Message messageEntity = new Message();
		messageEntity.setText(message);
		messageEntity.setAuthor(user);
		
		return messageRep.save(messageEntity);
	}

	@Override
	public Collection<Message> getAllMessages() {
		return messageRep.findAll();
	}

	@Override
	public Collection<Message> getUserFeed(int userId) {
		User user = getUser(userId);
		
		Set<User> followedUsers = user.getFollowedUsers();
		return followedUsers.stream().flatMap(followedUser -> followedUser.getCreatedMessages().stream()).collect(Collectors.toList());
	}

	@Override
	public Collection<User> follow(int followingUserId, int userIdToFollow) {
		User followingUser = getUser(followingUserId);
		User userToFollow = getUser(userIdToFollow);
		
		Set<User> followedUsers = followingUser.getFollowedUsers();
		followedUsers.add(userToFollow);
		return followedUsers;
	}
	
	
	@Override
	public Collection<User> unfollow(int followingUserId, int userIdToUnfollow) {
		User followingUser = getUser(followingUserId);
		User userToUnfollow = getUser(userIdToUnfollow);
		
		Set<User> followedUsers = followingUser.getFollowedUsers();
		if (!followedUsers.contains(userToUnfollow)) {
			throw new TracelessException("User ID " + followingUserId + " does not follow User ID " + userIdToUnfollow);
		}
		
		followedUsers.remove(userToUnfollow);
		return followedUsers;
	}

	@Override
	public Collection<User> getAllFollowedUsers(int userId) {
		User user = getUser(userId);
		return user.getFollowedUsers();
	}

	@Override
	public void deleteUser(Integer userId) {
		User user = getUser(userId);
		
		userRep.delete(user);
	}

	@Override
	public void deleteMessage(Integer messageId) {
		Message message = messageRep.findById(messageId).orElse(null);
		if (message == null) {
			throw new TracelessException("Message ID " + messageId + " not found");
		}
		
		messageRep.delete(message);
	}
	
	private User getUser(int userId) {
		User user = userRep.findById(userId).orElse(null);
		if (user == null) {
			throw new TracelessException("User ID " + userId + " not found");
		}
		return user;
	}
}
