package com.namung.cazinou.infrastructure.util.jda.message;

import java.awt.*;
import lombok.*;

/**
 * Embed 메시지를 생성하기 위한 모델
 * <p>
 *     Embed 메시지를 생성하기 위한 모델입니다.
 * </p>
 * <p>
 *     Embed 메시지는 다음과 같은 요소로 구성됩니다.
 *     <ul>
 *         <li>{@link  String} {@code title}: 타이틀</li>
 *         <li>{@link  String} {@code titleUrl}: 타이틀 URL</li>
 *         <li>{@link  String} {@code description}: 설명</li>
 *         <li>{@link  Color} {@code color}: 색상</li>
 *         <li>{@link  String} {@code fieldTitle}: 필드 타이틀</li>
 *         <li>{@link  String} {@code fieldDescription}: 필드 설명</li>
 *         <li>{@link  Boolean} {@code inline}: 인라인 여부</li>
 *         <li>{@link  String} {@code footerTitle}: 푸터 타이틀</li>
 *         <li>{@link  String} {@code footerUrl}: 푸터 URL</li>
 *         <li>{@link  String} {@code author}: 작성자</li>
 *         <li>{@link  String} {@code authorUrl}: 작성자 URL</li>
 *         <li>{@link  String} {@code authorImageUrl}: 작성자 이미지 URL</li>
 *         <li>{@link  String} {@code thumbnailUrl}: 썸네일 URL</li>
 *         <li>{@link  String} {@code imageUrl}: 이미지 URL</li>
 *     </ul>
 * </p>
 * @author namung08
 * @since 2024.09.12
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageModel {

  private String title;
  private String titleUrl;
  private String description;
  private Color color;
  private String fieldTitle;
  private String fieldDescription;
  private boolean inline;
  private String footerTitle;
  private String footerUrl;
  private String author;
  private String authorUrl;
  private String authorImageUrl;
  private String thumbnailUrl;
  private String imageUrl;
}
