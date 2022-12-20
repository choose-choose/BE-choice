package com.example.moduhouse.board.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Category {

    //ㄱ
    Gangnam("강남구"), Gangdong("강동구"), Gangseo("강서구"), Gangbuk("강북구"), Gwanak("관악구"),
    Kwang("광진구"), Guro("구로구"), geumcheon("금천구"),

    //ㄴ
    Nowon("노원구"),

    //ㄷ
    Dongdaemun("동대문구"), Dobong("도봉구"), dongjak("동작구"),

    //ㅁ
    Mapo("마포구"),

    //ㅅ
    Seodaemun("서대문구"), Seongdong("성동구"), Seongbuk("성북구"), Seocho("서초구"), Songpa("송파구"),

    //ㅇ
    Yeongdeungpo("영등포구"), Yongsan("용산구"), Yangcheon("양천구"), Eunpyeong("은평구"),

    //ㅈ
    Jongno("종로구"), Jung("중구"), Jungnang("중랑구");


    private final String category;

    public static Category valueOfCategory(String category) {
        return Arrays.stream(values())
                .filter(value -> value.category.equals(category))
                .findAny()
                .orElse(null);
    }
}
