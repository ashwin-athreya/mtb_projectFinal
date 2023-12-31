package com.cg.mts.service;

import com.cg.mts.entity.User;
import com.cg.mts.exception.UserCreationError;

public interface IUserService {

	public User addUser(User user) throws UserCreationError;

	public User removeUser(User user);
}
