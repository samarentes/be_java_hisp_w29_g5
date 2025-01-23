package com.social_media.social_media.utils;

public final class MessagesExceptions {
    private MessagesExceptions() {
    }

    public static final String SELLER_ID_NOT_EXIST = "El vendedor no existe";
    public static final String BUYER_ID_NOT_EXIST = "El comprador no existe";
    public static final String FOLLOWED_USER_NOT_SELLER = "El usuario a seguir no es un vendedor";
    public static final String THE_USER_CANNOT_FOLLOW_THEMSELVES = "El usuario no se puede seguir a si mismo";
    public static final String FOLLOW_ALREADY_EXISTS = "Este usuario ya está siguiendo al usuario especificado.";
    public static final String USER_NOT_FOUND = "El usuario no fue encontrado";
    public static final String NO_FOLLOWERS_FOUND = "No se encontraron seguidores para el usuario con ID: ";
}
