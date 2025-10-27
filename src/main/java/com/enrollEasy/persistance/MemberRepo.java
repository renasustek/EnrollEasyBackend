package com.enrollEasy.persistance;

import com.enrollEasy.persistance.entites.MemberDao;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepo extends JpaRepository<MemberDao, UUID> {}
