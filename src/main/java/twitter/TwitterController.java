package twitter;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import twitter.data.Message;
import twitter.data.User;
import twitter.exceptions.TracelessException;
import twitter.logic.TwitterServiceLocal;

@RestController
public class TwitterController {
	private final TwitterServiceLocal twitterService;
		
	@Autowired
	public TwitterController(TwitterServiceLocal twitterService) {
		this.twitterService = twitterService;
	}
	
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    @ApiOperation("Get all users")
    public Collection<User> getAllUsers() {
        return twitterService.getAllUsers();
    }
	
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    @ApiOperation("Create a user")
    public User createUser(
    		@ApiParam(value="User. Specify its name", required=true) @RequestBody User user
    		) {
    	String userName = user.getName();
    	if (userName == null || userName.length() < 1 || userName.length() > 15) {
    		throw new TracelessException("User name must have 1-15 characters");
    	}
    	
        return twitterService.createUser(userName);
    }
    
    @RequestMapping(value = "/users/{userId}", method = RequestMethod.DELETE)
    @ApiOperation("Delete a user")
    public void deleteUser(
    		@ApiParam(value="User ID", required=true) @PathVariable("userId") int userId
    		) {
        twitterService.deleteUser(userId);
    }
    
    @RequestMapping(value = "/users/{userId}/followedUsers", method = RequestMethod.GET)
    @ApiOperation("Get followed users")
    public Collection<User> getAllFollowedUsers(
    		@ApiParam(value="User ID", required=true) @PathVariable("userId") int userId
    		) {
        return twitterService.getAllFollowedUsers(userId);
    }
    
    @RequestMapping(value = "/users/{userId}/followedUsers", method = RequestMethod.POST)
    @ApiOperation("Follow a user")
    public Collection<User> follow(
    		@ApiParam(value="User ID", required=true) @PathVariable("userId") int userId,
    		@ApiParam(value="User to follow. Specify its ID", required=true) @RequestBody User userToFollow
    		) {
        return twitterService.follow(userId, userToFollow.getId());
    }
    
    @RequestMapping(value = "/users/{userId}/followedUsers/{followedUserId}", method = RequestMethod.DELETE)
    @ApiOperation("Unfollow a user")
    public Collection<User> unfollow(
    		@ApiParam(value="User ID", required=true) @PathVariable("userId") int userId,
    		@ApiParam(value="User to unfollow ID", required=true) @PathVariable("followedUserId") int followedUserId
    		) {
        return twitterService.unfollow(userId, followedUserId);
    }
    
    @RequestMapping(value = "/users/{userId}/feed", method = RequestMethod.GET)
    @ApiOperation("Get user feed")
    public Collection<Message> getUserFeed(
    		@ApiParam(value="User ID", required=true) @PathVariable("userId") int userId
    		) {
    	return twitterService.getUserFeedSortedByNewestFirst(userId);
    }
    
    @RequestMapping(value = "/messages", method = RequestMethod.GET)
    @ApiOperation("Get global feed")
    public Collection<Message> getAllMessages() {
        return twitterService.getAllMessagesSortedByNewestFirst();
    }
    
    @RequestMapping(value = "/messages", method = RequestMethod.POST)
    @ApiOperation("Post a message")
    public Message addMessage(
    		@ApiParam(value="Message. Specify its author ID and text", required=true) @RequestBody Message message
    		) {
    	String text = message.getText();
    	if (text == null || text.length() < 1 || text.length() > 280) {
    		throw new TracelessException("Message text must have 1-280 characters");
    	}
    	User author = message.getAuthor();
    	if (author == null) {
    		throw new TracelessException("Must specify message's author");
    	}
    	
        return twitterService.addMessage(author.getId(), text);
    }
    
    @RequestMapping(value = "/messages/{messageId}", method = RequestMethod.DELETE)
    @ApiOperation("Delete a message")
    public void deleteMessage(
    		@ApiParam(value="Message ID", required=true) @PathVariable("messageId") int messageId
    		) {
        twitterService.deleteMessage(messageId);
    }
}
