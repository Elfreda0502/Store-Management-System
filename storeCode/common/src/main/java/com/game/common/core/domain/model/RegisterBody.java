package com.game.common.core.domain.model;

/**
 * User registration object
 *
 * @author Yu Yue
 */

public class RegisterBody extends LoginBody
{

    private String email;

    private String code;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
