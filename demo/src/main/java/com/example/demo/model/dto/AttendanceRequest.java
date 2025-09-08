package com.example.demo.model.dto;

public class AttendanceRequest {
    private String studentId;
    private double latitude;
    private double longitude;
    private Long classId;

    // getter & setter
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public Long getClassId() { return classId; }
    public void setClassId(Long classId) { this.classId = classId; }


}
