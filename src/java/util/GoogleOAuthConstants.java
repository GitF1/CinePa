/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

/**
 *
 * @author duyqu
 */
public class GoogleOAuthConstants {
  public static String GOOGLE_CLIENT_ID = "873935314523-gmuli7ipaik12iv4l4rpj2hr6o2jnoke.apps.googleusercontent.com";
  public static String GOOGLE_CLIENT_SECRET = "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";
  public static String GOOGLE_REDIRECT_URI = "http://localhost:8080/movie/LoginGoogleServlet";//change to current project
  public static String GOOGLE_LINK_GET_TOKEN = "https://accounts.google.com/o/oauth2/token";
  public static String GOOGLE_LINK_GET_USER_INFO = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=";
  public static String GOOGLE_GRANT_TYPE = "authorization_code";
}