/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util.error;

/**
 *
 * @author PC
 */
public class Retry {

    public static <T> T retryOperation(RetriableOperation<T> operation, int maxRetries) throws Exception {
        int attempt = 0;
        while (attempt < maxRetries) {
            try {
                return operation.execute();
            } catch (Exception e) {
                attempt++;
                if (attempt >= maxRetries) {
                    throw e; // Re-throw the exception if max retries exceeded
                }
            }
        }
        return null; // This line should never be reached
    }
}
