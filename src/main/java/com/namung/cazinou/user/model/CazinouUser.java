package com.namung.cazinou.user.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDate;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@Document("users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CazinouUser {

  @Id
  @Field("_id")
  private String id;

  // 디스코드의 사용자 고유 ID
  @Field("discord_id")
  private Long discordId;

  // 겜빗을 위한 돈
  @Field("balance")
  private Long balance;

  // 출석 체크 횟수
  @Field("attendance")
  private Integer attendance;

  @Field("last_attendance")
  private LocalDate lastAttendance;

  @Field("created_at")
  @CreatedDate
  private LocalDate createdAt;

  @Field("reg_date")
  @LastModifiedDate
  private LocalDate regDate;
}
