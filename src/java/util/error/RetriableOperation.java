/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util.error;

/**
 *
 * @author PC
 */
@FunctionalInterface
public interface RetriableOperation<T> {
    T execute() throws Exception;
}
