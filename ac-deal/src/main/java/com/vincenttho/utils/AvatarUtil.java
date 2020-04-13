package com.vincenttho.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @className:com.vincenttho.utils.TestFilePath
 * @description:
 * @version:v1.0.0
 * @author: VincentHo
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * -----------------------------------------------------------------
 * 2020/4/10     VincentHo       v1.0.0        create
 */
public class AvatarUtil {

    public static List<String> listAvatars() {
        File file = new File("/usr/share/html/ac");
        File[] fileList = file.listFiles();
        List<String> avatars = new ArrayList<>();
        for(File filea : fileList) {
            avatars.add(filea.getName());
        }
        return avatars;
    }

}