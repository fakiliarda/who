package com.yaf.who.model;

import lombok.Getter;

/**
 * @author ardafakili
 * @date 1.05.2020
 */
public class Action {

    @Getter
    private final int dbid;

    @Getter
    private final UserProfile userProfile;

    @Getter
    private final String image;

    @Getter
    private final String message;

    public Action(int dbid, UserProfile userProfile, String image, String message) {
        this.dbid = dbid;
        this.userProfile = userProfile;
        this.image = image;
        this.message = message;
    }

}
