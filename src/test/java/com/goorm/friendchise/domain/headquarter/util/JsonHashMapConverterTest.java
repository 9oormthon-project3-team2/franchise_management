package com.goorm.friendchise.domain.headquarter.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class JsonHashMapConverterTest {

    @Test
    void convertJsonToHashmap() {
        // given
        HashMap<String, HashMap<String, Integer>> map = JsonHashMapConverter.convertJsonToHashmap("rentalData.json");

        // when, then
        assertThat(map.get("강남구").size()).isEqualTo(19);
        assertThat(map.get("강남구").get("가로수길")).isEqualTo(115400);
    }
}