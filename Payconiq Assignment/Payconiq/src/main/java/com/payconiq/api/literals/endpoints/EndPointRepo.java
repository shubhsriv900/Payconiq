package com.payconiq.api.literals.endpoints;

public class EndPointRepo {
// We need different literals even if end point is same to accomodate future changes
    public static final String LIST_USER_GISTS = "/users/<user>/gists";
    public static final String LIST_ALL_GISTS = "/gists";
    public static final String LIST_STARRED_GISTS = "/users/gists/starred";
    public static final String GET_GIST_WITH_ID = "/gists";
    public static final String CREATE_GIST = "/gists";
    public static final String UPDATE_GIST = "/gists";
    public static final String STAR_GIST = "/gists/gistID/star";
    public static final String DELETE_GIST = "/gists";
    public static final String LIST_GIST_PUBLIC = "/gists/public";
}
