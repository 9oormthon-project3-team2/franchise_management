package com.goorm.friendchise.domain.headquarter.dto.headquarter;

import com.goorm.friendchise.domain.headquarter.domain.Headquarter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record HeadquarterReqDto (
        @NotBlank(message = "프랜차이즈 이름은 필수입니다.")
        @Size(max = 50, message = "프랜차이즈 이름은 50자 이하로 입력해주세요.")
        String franchiseName
) {
    public static HeadquarterReqDto of(String franchiseName) {
        return new HeadquarterReqDto(franchiseName);
    }

    public static Headquarter toEntity(HeadquarterReqDto headquarterReqDto) {
        return Headquarter.of(headquarterReqDto.franchiseName());
    }
}
