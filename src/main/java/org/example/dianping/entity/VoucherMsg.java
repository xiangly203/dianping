package org.example.dianping.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoucherMsg {
    Long userId;
    Long orderId;
    Long voucherId;
    LocalDateTime createTime;
}
