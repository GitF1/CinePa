/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package model.schedule;

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
import java.net.URLEncoder;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

        PrintWriter out = response.getWriter();

        try {
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
        } catch (Exception e) {
            out.println("Error: " + e.getMessage());
        } finally {
            out.close();
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
        System.out.println("cities:" + cities);

        City nearestCity = new City();

        double shortestDistance = Double.MAX_VALUE;

        for (City city : cities) {
            double cityLat = city.getLatitude();
            double cityLon = city.getLongitude();
            double distance = calculateDistance(userLat, userLon, cityLat, cityLon);

            if (distance < shortestDistance) {
                shortestDistance = distance;
                nearestCity = city;
            }
        }

        nearestCity.setDistance(shortestDistance);

        System.out.println("nearest city: " + nearestCity.getName());

        Gson gson = new Gson();
        String json = gson.toJson(nearestCity);
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }

    private List<City> getListCities() throws IOException {
        List<City> cities = new ArrayList<>();
        List<String> cityNames = getCityNames();

        for (String cityName : cityNames) {
            String sanitizedCityName = sanitizeCityName(cityName);
            City city = getCityCoordinates(sanitizedCityName);
            cities.add(city);
        }

        return cities;
    }

    private List<String> getCityNames() {
        List<String> citiesProvinces = new ArrayList<>();
        citiesProvinces.add("Hà Nội");
        citiesProvinces.add("TP. Hồ Chí Minh");
        citiesProvinces.add("Đà Nẵng");
        citiesProvinces.add("Hải Phòng");
        return citiesProvinces;
    }

    private City getCityCoordinates(String cityName) throws IOException {
        String urlString = String.format("http://api.geonames.org/searchJSON?q=%s&maxRows=1&country=VN&username=%s", cityName, GEONAMES_USERNAME);
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

        return new City(cityName, latitude, longitude);
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
        String normalized = Normalizer.normalize(cityName, Normalizer.Form.NFD);
        // Remove diacritical marks
        String sanitized = normalized.replaceAll("\\p{M}", "");
        // Remove any non-alphanumeric characters (excluding spaces)
        sanitized = sanitized.replaceAll("[^\\p{Alnum}\\s]", "");
        // Trim any leading or trailing whitespace
        // URL encode the sanitized string
        try {
            return URLEncoder.encode(sanitized, "UTF-8");
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
