package ru.project.votingsystem.to;

import com.sun.istack.NotNull;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDate;

public class VoteTo extends BaseTo implements Serializable {

    @NotNull
    private LocalDate date;

    @NotNull
    private int userId;

    @NotBlank
    private String userName;

    @NotNull
    private int restaurantId;

    @NotBlank
    private String restaurantName;

    public VoteTo() {
    }

    public VoteTo(Integer id, LocalDate date, int userId, String userName, int restaurantId, String restaurantName) {
        super(id);
        this.date = date;
        this.userId = userId;
        this.userName = userName;
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    @Override
    public String toString() {
        return "VoteTo{" + " id=" + id +
                ", date=" + date +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", restaurantId=" + restaurantId +
                ", restaurantName='" + restaurantName + '\'' +
                "}";
    }
}
