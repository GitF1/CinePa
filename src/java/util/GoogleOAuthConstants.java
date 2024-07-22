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
  public static String GOOGLE_CLIENT_ID = "873935314523-2mdsfkjrj6asqdm2tdbmo1bve68n5um9.apps.googleusercontent.com";
  public static String GOOGLE_CLIENT_SECRET = "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";
  public static String GOOGLE_REDIRECT_URI = "https://cinepa.shop/movie/LoginGoogleServlet";//change to current project
  public static String GOOGLE_LINK_GET_TOKEN = "https://accounts.google.com/o/oauth2/token";
  public static String GOOGLE_LINK_GET_USER_INFO = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=";
  public static String GOOGLE_GRANT_TYPE = "authorization_code";
}