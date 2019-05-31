package com.lindj.boot.bean;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * @author lindj
 * @date 2019/5/27 0027
 * @description
 */
@Data
@NoArgsConstructor
@Builder
@Accessors(chain = true)
@ToString
@AllArgsConstructor
public class MessageEntity {
    private String code;
    private String title;
    private Object data;
}
