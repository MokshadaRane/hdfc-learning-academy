package com.hdfc.repository;

import com.hdfc.entity.Enrollment;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class EnrollmentRepository {
    private final Map<Integer, Enrollment> enrollmentMap = new ConcurrentHashMap<>();

    public Enrollment save(Enrollment enrollment) {
        enrollmentMap.put( enrollment.getEnrollmentId(), enrollment );
        return enrollment;
    }

    public Enrollment findById(Integer id) {
        return enrollmentMap.get(id);
    }

    public List<Enrollment> findAll() {
        return new ArrayList<>(enrollmentMap.values());
    }

    public void deleteById(Integer id) {
        enrollmentMap.remove(id);
    }
}
