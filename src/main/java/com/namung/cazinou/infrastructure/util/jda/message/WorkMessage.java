package com.namung.cazinou.infrastructure.util.jda.message;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class WorkMessage {

  public static List<MessageModel> generateWorkRewards() {
    List<MessageModel> rewards = new ArrayList<>();
    // 7개의 10,000원 이상의 보상
    rewards.add(
      MessageModel.builder()
        .title("대규모 계약 성사!")
        .description(
          "당신은 회사와 대규모 계약을 성사시켜 " + 80000 + "원을 벌었습니다!"
        )
        .color(Color.GREEN)
        .build()
    );

    rewards.add(
      MessageModel.builder()
        .title("복권 당첨!")
        .description("당신은 복권에 당첨되어 " + 90000 + "원을 벌었습니다!")
        .color(Color.GREEN)
        .build()
    );

    rewards.add(
      MessageModel.builder()
        .title("투자 성공!")
        .description(
          "당신의 현명한 투자로 " + 75000 + "원의 수익을 얻었습니다!"
        )
        .color(Color.GREEN)
        .build()
    );

    rewards.add(
      MessageModel.builder()
        .title("프로젝트 완성!")
        .description(
          "당신은 어려운 프로젝트를 성공적으로 완료해 " +
          100000 +
          "원을 받았습니다!"
        )
        .color(Color.GREEN)
        .build()
    );

    rewards.add(
      MessageModel.builder()
        .title("회사의 특별 보너스!")
        .description("회사의 특별 보너스로 " + 50000 + "원을 받았습니다!")
        .color(Color.CYAN)
        .build()
    );

    rewards.add(
      MessageModel.builder()
        .title("성공적인 마케팅 캠페인!")
        .description(
          "당신의 마케팅 캠페인이 대박을 터뜨려 " + 60000 + "원을 벌었습니다!"
        )
        .color(Color.CYAN)
        .build()
    );

    rewards.add(
      MessageModel.builder()
        .title("판매왕 선정!")
        .description(
          "당신은 이번 달의 판매왕으로 선정되어 " +
          70000 +
          "원의 보너스를 받았습니다!"
        )
        .color(Color.CYAN)
        .build()
    );

    // 3개의 10,000원 미만의 보상
    rewards.add(
      MessageModel.builder()
        .title("작은 프로젝트 성공")
        .description(
          "당신은 작은 프로젝트를 성공적으로 마쳐 " + 5000 + "원을 벌었습니다!"
        )
        .color(Color.ORANGE)
        .build()
    );

    rewards.add(
      MessageModel.builder()
        .title("길거리에서 돈을 주웠습니다!")
        .description("길을 걷다 우연히 " + 3000 + "원을 발견했습니다!")
        .color(Color.ORANGE)
        .build()
    );

    rewards.add(
      MessageModel.builder()
        .title("소규모 계약 성사!")
        .description(
          "당신은 소규모 계약을 성사시켜 " + 8000 + "원을 벌었습니다!"
        )
        .color(Color.ORANGE)
        .build()
    );

    return rewards;
  }

  public static List<Long> getMoneyList() {
    List<Long> moneyList = new ArrayList<>();
    moneyList.add(80000L);
    moneyList.add(90000L);
    moneyList.add(75000L);
    moneyList.add(100000L);
    moneyList.add(50000L);
    moneyList.add(60000L);
    moneyList.add(70000L);
    moneyList.add(5000L);
    moneyList.add(3000L);
    moneyList.add(8000L);
    return moneyList;
  }
}
