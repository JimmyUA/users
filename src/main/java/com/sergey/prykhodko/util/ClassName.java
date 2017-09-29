package com.sergey.prykhodko.util;

/**
 * Class is used to get className of the class where
 * static method getCurrentClassName is invoked.
 *
 * It is using stackTrace to get Class instance and then it's name
 */
public class ClassName {
    public static String getCurrentClassName(){
        try {
            throw new RuntimeException();
        }catch (RuntimeException e){
            return e.getStackTrace()[1].getClassName();
        }
    }
}
