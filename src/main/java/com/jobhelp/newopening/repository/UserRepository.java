package com.jobhelp.newopening.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.jobhelp.newopening.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, String> {
}
