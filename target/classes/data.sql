DROP TABLE IF EXISTS messages;
DROP TABLE IF EXISTS users_to_followed_users;
DROP TABLE IF EXISTS users;


CREATE TABLE users (
	id INT AUTO_INCREMENT PRIMARY KEY,
	
	user_name VARCHAR(15) NOT NULL,
	UNIQUE(user_name)
);
 

CREATE TABLE messages (
	id INT AUTO_INCREMENT PRIMARY KEY,
	
	message VARCHAR(280) NOT NULL,
	
	author_id INT NOT NULL,
	FOREIGN KEY(author_id) REFERENCES users(id) ON DELETE CASCADE,
	
	creation_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE users_to_followed_users (
	id INT AUTO_INCREMENT PRIMARY KEY,
	
	user_id INT NOT NULL,
	FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE,
	
	followed_user_id INT NOT NULL,
	FOREIGN KEY(followed_user_id) REFERENCES users(id) ON DELETE CASCADE
);