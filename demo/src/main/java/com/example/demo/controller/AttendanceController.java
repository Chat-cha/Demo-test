package com.example.demo.controller;

import com.example.demo.model.dto.AttendanceRequest;
import com.example.demo.model.dto.AttendanceResponse;
import com.example.demo.model.Classroom;
import com.example.demo.repo.InMemoryClassroomRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api")
public class AttendanceController {

    private final InMemoryClassroomRepository classroomRepository;

    public AttendanceController(InMemoryClassroomRepository classroomRepository) {
        this.classroomRepository = classroomRepository;
    }


    @GetMapping("/classrooms")
    public Collection<Classroom> classrooms() {
        return classroomRepository.findAll();
    }


    @PostMapping("/attendance/check")
    public AttendanceResponse checkAttendance(@RequestBody AttendanceRequest request) {


        Classroom classroom = classroomRepository.findById(request.getClassId()).orElse(null);
        if (classroom == null) {
            return new AttendanceResponse(false, 0, 0,
                    "CLASS_NOT_FOUND", System.currentTimeMillis(), null, null);
        }



        double distanceMeters = calculateDistance(
                request.getLatitude(), request.getLongitude(),
                classroom.getLatitude(), classroom.getLongitude()
        );
        double allowedRadius = 50;
        boolean withinRadius = distanceMeters <= allowedRadius;

        return new AttendanceResponse(
                withinRadius,
                distanceMeters,
                allowedRadius,
                withinRadius ? "CHECKIN_OK" : "TOO_FAR",
                System.currentTimeMillis(),
                classroom.getId(),
                classroom.getName()
        );
    }


    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371000;
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

}
