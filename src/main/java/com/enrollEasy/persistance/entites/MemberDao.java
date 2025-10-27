package com.enrollEasy.persistance.entites;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.sql.Date;
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

  @Column(name = "membername", columnDefinition = "VARCHAR(50)", nullable = false, length = 50)
  @JdbcTypeCode(SqlTypes.VARCHAR)
  private String memberName;

  @Column(name = "membershipvalidtill", columnDefinition = "DATE", nullable = true)
  @JdbcTypeCode(SqlTypes.DATE)
  private Date membershipValidTill;

  public UUID getUuid() {
    return uuid;
  }

  public void setUuid(UUID uuid) {
    this.uuid = uuid;
  }

  public String getMemberName() {
    return memberName;
  }

  public void setMemberName(String userId) {
    this.memberName = userId;
  }

  public Date getMembershipValidTill() {
    return membershipValidTill;
  }

  public void setMembershipValidTill(Date date) {
    this.membershipValidTill = date;
  }
}
