package com.grad.ret;

import com.grad.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RegisterRet {
    private User user;
    private int statusCode;
    private String Msg;
}
