/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.schedule;

import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import model.schedule.City;

/**
 *
 * @author PC
 */
@WebServlet(name = "NearestCityServlet", urlPatterns = {"/city/nearest"})
public class NearestCityServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String GEONAMES_USERNAME = "per03";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet NearestCityServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet NearestCityServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String city = request.getParameter("city");
        String username = "per03"; // Replace with your GeoNames username

        String urlStr = "http://api.geonames.org/searchJSON?q=" + city + "&country=VN&maxRows=1&username=" + username;
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();

        // Parse JSON response
        String[] coords = parseJSON(result.toString());
        if (coords != null && coords.length == 2) {
            response.getWriter().println("Latitude: " + coords[0] + "<br>");
            response.getWriter().println("Longitude: " + coords[1]);
        } else {
            response.getWriter().println("Coordinates not found for the city: " + city);
        }

    }

    private String[] parseJSON(String json) {
        String[] coords = new String[2];
        JSONObject jsonObject = new JSONObject(json);
        JSONArray geonames = jsonObject.getJSONArray("geonames");
        if (geonames.length() > 0) {
            JSONObject firstResult = geonames.getJSONObject(0);
            coords[0] = firstResult.getString("lat");
            coords[1] = firstResult.getString("lng");
            return coords;
        }
        return null;
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        double userLat = Double.parseDouble(request.getParameter("latitude"));
        double userLon = Double.parseDouble(request.getParameter("longitude"));

        List<City> cities = getListCities();

        City nearestCity = new City();

        double shortestDistance = Double.MAX_VALUE;

        for (City city : cities) {

            double cityLat = city.getLatitude();
            double cityLon = city.getLongitude();

            double distance = calculateDistance(userLat, userLon, cityLat, cityLon);
            city.setDistance(distance);

            if (distance < shortestDistance) {
                shortestDistance = distance;
                nearestCity = city;
            }
        }

        nearestCity.setDistance(shortestDistance);

        System.out.println("nearest city: " + nearestCity.getName() + "  ID:" + nearestCity.getId() + " distance: " + shortestDistance);

        Gson gson = new Gson();
        String json = gson.toJson(nearestCity);
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }

    private List<City> getListCities() throws IOException {
        List<City> cities = getCityNames();
        for (City city : cities) {

            getCityCoordinates(city);

        }
        return cities;
    }

    private List<City> getCityNames() {

        List<City> citiesProvinces = new ArrayList<>();

        citiesProvinces.add(new City(0, "Hà nội", 0, 0));
        citiesProvinces.add(new City(1, "TP. Hồ Chí Minh", 0, 0));
        citiesProvinces.add(new City(2, "Đà Nẵng", 0, 0));
        citiesProvinces.add(new City(3, "Hải Phòng", 0, 0));

        return citiesProvinces;
    }

    private String normalize(String text) {
        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalized).replaceAll("");
    }

    private void getCityCoordinates(City city) throws IOException {

        String sanitizedCityName = sanitizeCityName(city.getName());

        String urlString = String.format("http://api.geonames.org/searchJSON?q=%s&maxRows=1&country=VN&username=%s", sanitizedCityName, GEONAMES_USERNAME);
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        Scanner scanner = new Scanner(url.openStream());

        StringBuilder jsonResult = new StringBuilder();
        while (scanner.hasNext()) {
            jsonResult.append(scanner.nextLine());
        }
        scanner.close();

        JSONObject jsonObject = new JSONObject(jsonResult.toString());

        JSONArray jsonArray = jsonObject.getJSONArray("geonames");
        JSONObject cityData = jsonArray.getJSONObject(0);

        double latitude = cityData.getDouble("lat");
        double longitude = cityData.getDouble("lng");

        city.setLatitude(latitude);
        city.setLongitude(longitude);

    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {

        final int R = 6371; // Radius of the Earth in km

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double distance = R * c;

        return distance;
    }

    private String sanitizeCityName(String cityName) {

        // Normalize the string to remove accents and special characters
        String normalized = normalize(cityName);
        // Remove diacritical marks
        String sanitized = normalized.replaceAll("\\p{M}", "");
        // Remove any non-alphanumeric characters (excluding spaces)
        sanitized = sanitized.replaceAll("[^\\p{Alnum}\\s]", "");
        // Trim any leading or trailing whitespace
        // URL encode the sanitized string
        try {
            return URLEncoder.encode(normalized, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return sanitized; // Return the sanitized string if encoding fails
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
