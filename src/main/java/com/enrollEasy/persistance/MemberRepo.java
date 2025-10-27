package com.enrollEasy.persistance;

import com.enrollEasy.persistance.entites.MemberDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MemberRepo extends JpaRepository<MemberDao, UUID> {}
