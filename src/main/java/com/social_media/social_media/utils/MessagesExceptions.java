package com.social_media.social_media.utils;

public final class MessagesExceptions {
    private MessagesExceptions() {
    }

    public static final String SELLER_ID_NOT_EXIST = "El vendedor no existe";
    public static final String BUYER_ID_NOT_EXIST = "El comprador no existe";
    public static final String FOLLOWED_USER_NOT_SELLER = "El usuario a seguir no es un vendedor";
    public static final String NO_FOLLOWERS_FOUND = "No se encontraron seguidores para el usuario con ID: ";
    public static final String NO_RECENT_POSTS_FOUND = "No se encontraron publicaciones recientes para los seguidores del usuario con ID: ";
}
