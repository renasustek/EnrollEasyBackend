package com.enrollEasy.persistance.entites;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "members", schema = "enroll_easy")
public class MemberDao {
  @Id
  @Column(name = "id", columnDefinition = "CHAR(36)", nullable = false, unique = true, length = 36)
  @JdbcTypeCode(SqlTypes.CHAR)
  private UUID uuid;

  @Column(name = "username", columnDefinition = "VARCHAR(50)", nullable = false, length = 50)
  @JdbcTypeCode(SqlTypes.VARCHAR)
  private String userId;

  @Column(name = "paid", columnDefinition = "BOOLEAN", nullable = false)
  @JdbcTypeCode(SqlTypes.BOOLEAN)
  private boolean paidStatus;

  public UUID getUuid() {
    return uuid;
  }

  public void setUuid(UUID uuid) {
    this.uuid = uuid;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public boolean getPaidStatus() {
    return paidStatus;
  }

  public void setPaidStatus(boolean labelId) {
    this.paidStatus = labelId;
  }
}
