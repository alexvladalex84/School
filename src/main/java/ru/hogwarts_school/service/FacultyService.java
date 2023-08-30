package ru.hogwarts_school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts_school.exceptions.FacultyDoesNotExistException;
import ru.hogwarts_school.model.Faculty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    Map<Long, Faculty> facultyMap = new HashMap<>();
    private static long lastId = 0;

    public Faculty addFaculty(String name, String color) {

        Faculty faculty = new Faculty(++lastId, name, color);
        facultyMap.put(faculty.getId(), faculty);

        return faculty;
    }

    public Faculty findFaculty(long Id) {
        if (facultyMap.containsKey(Id)) {
            return facultyMap.get(Id);
        }
        throw new FacultyDoesNotExistException("Факультета с таким именем не существует");
    }

    public Faculty editFaculty(long id, String name, String color) {
        Faculty faculty = facultyMap.get(id);
        faculty.setName(name);
        faculty.setColor(color);
        return faculty;

    }

    public Faculty delete(long id) {
        Faculty faculty = facultyMap.get(id);
        if (facultyMap.containsKey(id)) {
            facultyMap.remove(id);
            return faculty;
        }
        throw new FacultyDoesNotExistException("Факультета с таким именем не существует");
    }

    public List<Faculty> getAllByColor(String color) {
        return facultyMap.values().stream()
                .filter(student -> student.getColor().equals(color))
                .collect(Collectors.toList());
    }

    public Map<Long, Faculty> getFacultyMap() {
        return facultyMap;
    }

}
