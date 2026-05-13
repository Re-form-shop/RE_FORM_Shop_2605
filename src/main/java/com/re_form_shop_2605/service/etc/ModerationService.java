package com.re_form_shop_2605.service.etc;

import com.re_form_shop_2605.repository.etc.RiskAnalysisResultRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.moderation.ModerationModel;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class ModerationService {

    private final ModerationModel moderationModel;
    private final ChatClient.Builder clientBuilder; // 개선 제안용 ChatGPT
    private final RiskAnalysisResultRepository riskAnalysisResultRepository;
}
