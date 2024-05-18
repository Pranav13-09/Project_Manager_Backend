package com.org.projectmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.org.projectmanager.model.Chat;
import com.org.projectmanager.model.User;

public interface ChatRepository extends JpaRepository<Chat,Long>{

}
