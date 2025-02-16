package com.goorm.friendchise.domain.headquarter.presentation;

import com.goorm.friendchise.domain.headquarter.Item.application.ItemService;
import com.goorm.friendchise.domain.headquarter.Item.dto.ItemReqDtoList;
import com.goorm.friendchise.domain.headquarter.Item.dto.ItemResDto;
import com.goorm.friendchise.domain.headquarter.application.HeadquarterService;
import com.goorm.friendchise.domain.headquarter.application.StoreRecommendationService;
import com.goorm.friendchise.domain.headquarter.dto.headquarter.HeadquarterReqDto;
import com.goorm.friendchise.domain.headquarter.dto.headquarter.HeadquarterResDto;
import com.goorm.friendchise.domain.headquarter.dto.headquarter.StoreRecommendReqDto;
import com.goorm.friendchise.domain.headquarter.dto.openai.ChatCompletionResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/headquarter")
public class HeadquarterController {
    private final HeadquarterService headquarterService;
    private final ItemService itemService;
    private final StoreRecommendationService storeRecommendationService;

    @PostMapping("/register")
    public ResponseEntity<HeadquarterResDto> createHeadquarter(@Valid @RequestBody HeadquarterReqDto headquarterReqDto) {
        return ResponseEntity.ok().body(headquarterService.createHeadquarter(headquarterReqDto));
    }

    // path variable의 id를 다 fracnhiseName으로 바꾸거나 없애야 할까..

    @GetMapping
    public ResponseEntity<HeadquarterResDto> getHeadquarter() {
        return ResponseEntity.ok().body(headquarterService.getHeadquarter());
    }

    @PutMapping("/update")
    public ResponseEntity<HeadquarterResDto> updateHeadquarter(@Valid @RequestBody HeadquarterReqDto headquarterReqDto) {
        return ResponseEntity.ok().body(headquarterService.updateHeadquarterName(headquarterReqDto));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteHeadquarter() {
        headquarterService.deleteHeadquarter();
        return ResponseEntity.ok().body(null);
    }

    @PageableAsQueryParam
    @GetMapping("/items")
    public ResponseEntity<Slice<ItemResDto>> getItems(Pageable pageable) {
        return ResponseEntity.ok().body(itemService.getItems(pageable));
    }

    @PostMapping("/items/register")
    public ResponseEntity<List<ItemResDto>> createItems(@Valid @RequestBody ItemReqDtoList itemReqDtoList) {
        return ResponseEntity.ok().body(itemService.createItems(itemReqDtoList));
    }

    @PostMapping("/store-recommendation")
    public ResponseEntity<ChatCompletionResponseDto> getRecommendationResult(@Valid @RequestBody StoreRecommendReqDto req) {
        return ResponseEntity.ok().body(storeRecommendationService.getRecommendation(req));
    }

}
