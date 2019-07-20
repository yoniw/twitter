package twitter.logic;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import twitter.data.Message;
import twitter.data.User;

@Service
public class TwitterServiceBean implements TwitterServiceLocal {

	private final TwitterDao dao;

	@Autowired
	public TwitterServiceBean(TwitterDao dao) {
		this.dao = dao;
	}

	@Override
	public User createUser(String userName) {
		return dao.createUser(userName);
	}

	@Override
	public Collection<User> getAllUsers() {
		return dao.getAllUsers();
	}

	@Override
	public Message addMessage(int userId, String message) {
		return dao.addMessage(userId, message);
	}

	@Override
	public Collection<Message> getAllMessagesSortedByNewestFirst() {
		return dao.getAllMessages().stream().sorted(Comparator.comparing(Message::getCreationTime).reversed()).collect(Collectors.toList());
	}

	@Override
	public Collection<Message> getUserFeedSortedByNewestFirst(int userId) {
		return dao.getUserFeed(userId).stream().sorted(Comparator.comparing(Message::getCreationTime).reversed()).collect(Collectors.toList());
	}

	@Override
	public Collection<User> follow(int followingUserId, int userIdToFollow) {
		return dao.follow(followingUserId, userIdToFollow);
	}

	@Override
	public Collection<User> unfollow(int followingUserId, int userIdToUnfollow) {
		return dao.unfollow(followingUserId, userIdToUnfollow);
	}

	@Override
	public Collection<User> getAllFollowedUsers(int userId) {
		return dao.getAllFollowedUsers(userId);
	}

	@Override
	public void deleteUser(Integer userId) {
		dao.deleteUser(userId);
	}

	@Override
	public void deleteMessage(Integer messageId) {
		dao.deleteMessage(messageId);
	}
}
