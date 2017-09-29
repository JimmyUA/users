package com.sergey.prykhodko.util;

import com.sergey.prykhodko.dao.factory.FactoryType;
import com.sergey.prykhodko.managers.UserManager;
import com.sergey.prykhodko.model.User;
import org.apache.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class UserValidator {
    private final static String EMAIL_PATTERN = "^([\\w-.]+){1,64}@([\\w&&[^_]]+){2,255}.[a-z]{2,}$";

    private final static Logger logger = Logger.getLogger(ClassName.getCurrentClassName());

    public static boolean validate(User user){
        if (notUniqueLogin(user.getLogin())) {
            logger.error("Login - \"" + user.getLogin() + "\" is not unique");
            return false;
        } else if (emailNotMatchPattern(user.getEmail())) {
            logger.error("Email - \"" + user.getEmail() + "\" is not valid");
            return false;
        }
        return true;
    }


    private static boolean notUniqueLogin(String userLogin) {
        return !isUnique(userLogin);
    }

    private static boolean emailNotMatchPattern(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);

        return !matcher.find();
    }

    private static boolean isUnique(String userLogin) {
        return new UserManager().isUnique(userLogin, FactoryType.SPRING);
    }
}
