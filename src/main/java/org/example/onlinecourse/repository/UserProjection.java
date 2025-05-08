package org.example.onlinecourse.repository;

public interface UserProjection {
    Long getUserId();
    String getFirstName();
    String getLastName();
    String getEmail();
    String getTelephoneNumber();
    String getRole();
}
