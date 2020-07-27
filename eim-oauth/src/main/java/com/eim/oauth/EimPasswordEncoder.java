package com.eim.oauth;

import com.eim.utils.EncryptUtil;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.security.crypto.password.PasswordEncoder;

public class EimPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence charSequence) {
        String content = (String) charSequence;

        return encrypt(content);
    }

    @Override
    public boolean matches(CharSequence loginPassword , String encodedPassword) {
        String newPassword = encrypt((String) loginPassword);
        return encodedPassword.equals(newPassword);
    }

    private String encrypt(String content) {
        String newPwd = content;

        int index = content.indexOf("@");

        if (index > 0) {
            String username = content.substring(0, index);
            String password = content.substring(index + 1);

            try {
                //Base64 base64 = new Base64();
                //Base64.encodeBase64String()
                newPwd = Base64.encodeBase64String(EncryptUtil.encrypt(password, username));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return newPwd;
    }
}
