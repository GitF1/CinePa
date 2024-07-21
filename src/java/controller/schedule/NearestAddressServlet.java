/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.schedule;

import DAO.schedule.ScheduleDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import model.Cinema;
import model.schedule.Address;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author PC
 */
@WebServlet(name = "NearestAddressServlet", urlPatterns = {"/cinema/nearest/address"})
public class NearestAddressServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String GEONAMES_USERNAME = "per03";
    ScheduleDAO dao;

    @Override
    public void init() throws ServletException {
        try {
            dao = new ScheduleDAO(getServletContext());
            super.init(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        } catch (Exception ex) {
            Logger.getLogger(NearestAddressServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

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
        try {
//            double userLat = Double.parseDouble(request.getParameter("latitude"));
//            double userLon = Double.parseDouble(request.getParameter("longitude"));

            double userLat = 15.5728;
            double userLon = 108.4709;

            List<Address> listAddress = getAddressCinema();
            List<Address> cinemaRs = new ArrayList<>();

            for (Address address : listAddress) {

                getCityCoordinates(address);

                double lat = address.getLatitude();
                double lon = address.getLongitude();

                if (lat == 0 && lon == 0) {
                    continue;
                }

                double distance = calculateDistance(userLat, userLon, lat, lon);

                address.setDistance(distance);

                cinemaRs.add(address);
            }
            Collections.sort(cinemaRs, new Comparator<Address>() {
                @Override
                public int compare(Address a1, Address a2) {
                    return Double.compare(a1.getDistance(), a2.getDistance());
                }
            });

            System.out.println("after calculate distance: " + cinemaRs);

        } catch (SQLException ex) {
            Logger.getLogger(NearestAddressServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        processRequest(request, response);
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
//            double userLat = Double.parseDouble(request.getParameter("latitude"));
//            double userLon = Double.parseDouble(request.getParameter("longitude"));

            double userLat = 15.5728;
            double userLon = 108.4709;

            List<Address> listAddress = getAddressCinema();
            List<Address> cinemaRs = new ArrayList<>();

            for (Address address : listAddress) {

                getCityCoordinates(address);

                double lat = address.getLatitude();
                double lon = address.getLongitude();

                if (lat == 0 && lon == 0) {
                    continue;
                }

                double distance = calculateDistance(userLat, userLon, lat, lon);

                address.setDistance(distance);

                cinemaRs.add(address);
            }
            Collections.sort(cinemaRs, new Comparator<Address>() {
                @Override
                public int compare(Address a1, Address a2) {
                    return Double.compare(a2.getDistance(), a1.getDistance());
                }
            });

            System.out.println("after calculate distance: " + cinemaRs);

        } catch (SQLException ex) {
            Logger.getLogger(NearestAddressServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private List<Address> getAddressCinema() throws SQLException {

        List<Cinema> cinemas = dao.getAllCinemas();

        List<Address> listAddress = new ArrayList<>();

        for (int i = 0; i < cinemas.size(); i++) {

            String address = cinemas.get(i).getCommune() + ", " + cinemas.get(i).getDistrict() + ", " + cinemas.get(i).getProvince();
            System.out.println("address" + address);
            listAddress.add(new Address(i, address, 0, 0));
        }

        return listAddress;
    }

    private String normalize(String text) {
        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalized).replaceAll("");
    }

    private void getCityCoordinates(Address address) throws IOException {

        String sanitizedName = sanitizeCityName(address.getName());

        String urlString = String.format("http://api.geonames.org/searchJSON?q=%s&maxRows=1&country=VN&username=%s", sanitizedName, GEONAMES_USERNAME);
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
        if (jsonArray.length() > 0) {
            JSONObject res = jsonArray.getJSONObject(0);

            double latitude = res.getDouble("lat");
            double longitude = res.getDouble("lng");

            address.setLatitude(latitude);
            address.setLongitude(longitude);
        }

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
