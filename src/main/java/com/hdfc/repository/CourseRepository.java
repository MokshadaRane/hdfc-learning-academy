package com.hdfc.repository;

import com.hdfc.entity.Course;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CourseRepository {
    private final Map<Integer, Course> courseMap = new ConcurrentHashMap<>();
    public Course save(Course course) {
        courseMap.put(course.getCourseId(), course);
        return course;
    }

    public Course findById(Integer id) {
        return courseMap.get(id);
    }

    public List<Course> findAll() {
        return new ArrayList<>(courseMap.values());
    }

    public boolean existsById(Integer id) {
        return courseMap.containsKey(id);
    }

    public void deleteById(Integer id) {
        courseMap.remove(id);
    }
}
