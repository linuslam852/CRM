package com.linus.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BaseQuery {
    private  String token;

    private String filterSQL;//數據權限的SQL過濾條件
}
