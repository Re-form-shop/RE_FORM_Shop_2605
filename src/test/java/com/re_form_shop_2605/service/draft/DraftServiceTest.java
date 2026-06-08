package com.re_form_shop_2605.service.draft;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.re_form_shop_2605.dto.AI.RiskAnalysisResultDTO;
import com.re_form_shop_2605.dto.draft.PostDraftDTO;
import com.re_form_shop_2605.dto.draft.PostDraftStateDTO;
import com.re_form_shop_2605.entity.Enum.DeliveryType;
import com.re_form_shop_2605.entity.Enum.Grade;
import com.re_form_shop_2605.entity.Enum.Sport;
import com.re_form_shop_2605.entity.Enum.TargetType;
import com.re_form_shop_2605.service.AI.ModerationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DraftServiceTest {

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private ValueOperations<String, Object> valueOperations;

    @Mock
    private ModerationService moderationService;

    private DraftService draftService;
    private AtomicReference<Object> storedValue;

    @BeforeEach
    void setUp() {
        storedValue = new AtomicReference<>();
        draftService = new DraftService(redisTemplate, new ObjectMapper(), moderationService);

        lenient().when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        lenient().when(valueOperations.get(anyString())).thenAnswer(invocation -> storedValue.get());
        lenient().doAnswer(invocation -> {
            storedValue.set(invocation.getArgument(1));
            return null;
        }).when(valueOperations).set(anyString(), any(), any());
    }

    @Test
    void savePostDraft_mergesExistingFieldsWhenIncomingDraftIsPartial() {
        PostDraftDTO existingDraft = new PostDraftDTO(
                "기존 제목",
                "기존 내용",
                Sport.SOCCER,
                "토트넘",
                "7",
                Grade.A,
                "L",
                DeliveryType.DIRECT,
                120000,
                "서울",
                List.of("/uploads/post/1/a.png")
        );
        storedValue.set(new PostDraftStateDTO(existingDraft, null));

        PostDraftDTO incomingDraft = new PostDraftDTO(
                "새 제목",
                "새 내용",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );

        when(moderationService.checkDraft("새 제목 새 내용", TargetType.POST))
                .thenReturn(RiskAnalysisResultDTO.safe());

        PostDraftStateDTO result = draftService.savePostDraft(2L, incomingDraft);

        assertThat(result.draft().title()).isEqualTo("새 제목");
        assertThat(result.draft().content()).isEqualTo("새 내용");
        assertThat(result.draft().sport()).isEqualTo(Sport.SOCCER);
        assertThat(result.draft().team()).isEqualTo("토트넘");
        assertThat(result.draft().uniformNumber()).isEqualTo("7");
        assertThat(result.draft().condition()).isEqualTo(Grade.A);
        assertThat(result.draft().size()).isEqualTo("L");
        assertThat(result.draft().tradeType()).isEqualTo(DeliveryType.DIRECT);
        assertThat(result.draft().price()).isEqualTo(120000);
        assertThat(result.draft().directTradeLocation()).isEqualTo("서울");
        assertThat(result.draft().imageUrls()).isNull();
        assertThat(result.moderation()).isNull();
    }

    @Test
    void getPostDraft_supportsLegacyDraftShape() {
        storedValue.set(Map.of(
                "title", "legacy title",
                "content", "legacy content"
        ));

        PostDraftStateDTO result = draftService.getPostDraft(2L);

        assertThat(result).isNotNull();
        assertThat(result.draft().title()).isEqualTo("legacy title");
        assertThat(result.draft().content()).isEqualTo("legacy content");
        assertThat(result.draft().imageUrls()).isNull();
        assertThat(result.moderation()).isNull();
    }

    @Test
    void savePostDraft_doesNotPersistIncomingImageUrls() {
        PostDraftDTO incomingDraft = new PostDraftDTO(
                "이미지 포함 제목",
                "이미지 포함 내용",
                Sport.SOCCER,
                "토트넘",
                "7",
                Grade.A,
                "L",
                DeliveryType.DIRECT,
                120000,
                "서울",
                List.of("/uploads/post/temp/2/a.png")
        );

        when(moderationService.checkDraft("이미지 포함 제목 이미지 포함 내용", TargetType.POST))
                .thenReturn(RiskAnalysisResultDTO.safe());

        PostDraftStateDTO result = draftService.savePostDraft(2L, incomingDraft);

        assertThat(result.draft().imageUrls()).isNull();
        assertThat(((PostDraftStateDTO) storedValue.get()).draft().imageUrls()).isNull();
    }

    @Test
    void getPostDraft_removesImageUrlsFromStoredState() {
        PostDraftDTO storedDraft = new PostDraftDTO(
                "저장된 제목",
                "저장된 내용",
                Sport.SOCCER,
                "토트넘",
                "7",
                Grade.A,
                "L",
                DeliveryType.DIRECT,
                120000,
                "서울",
                List.of("/uploads/post/1/a.png")
        );
        storedValue.set(new PostDraftStateDTO(storedDraft, RiskAnalysisResultDTO.safe()));

        PostDraftStateDTO result = draftService.getPostDraft(2L);

        assertThat(result).isNotNull();
        assertThat(result.draft().imageUrls()).isNull();
    }
}
