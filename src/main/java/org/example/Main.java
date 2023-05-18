package model;

import java.util.Arrays;

public class University {
    private String name;
    private Faculty[] faculties;

    public University(String name, Faculty[] faculties) {
        this.name = name;
        this.faculties = faculties;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        University other = (University) obj;
        return name.equals(other.name) && Arrays.equals(faculties, other.faculties);
    }
}

class Faculty {
    private String name;
    private Department[] departments;

    public Faculty(String name, Department[] departments) {
        this.name = name;
        this.departments = departments;
    }

}

class Department {
    private String name;
    private Group[] groups;

    public Department(String name, Group[] groups) {
        this.name = name;
        this.groups = groups;
    }

}

class Group {
    private String name;
    private Student[] students;

    public Group(String name, Student[] students) {
        this.name = name;
        this.students = students;
    }

}

class Student {
    private String name;
    private Human human;

    public Student(String name, Human human) {
        this.name = name;
        this.human = human;
    }

}

class Human {
    private String firstName;
    private String lastName;
    private String parentage;
    private Sex gender;

    public Human(String firstName, String lastName, String parentage, Sex gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.parentage = parentage;
        this.gender = gender;
    }

}

enum Sex {
    MALE,
    FEMALE
}
package controller;

        import com.google.gson.Gson;
        import model.University;

        import java.io.IOException;
        import java.nio.file.Files;
        import java.nio.file.Paths;

public class JsonManager {
    private static final String FILE_PATH = "university.json";

    public static void writeUniversityToJsonFile(University university) {
        Gson gson = new Gson();
        String json = gson.toJson(university);
        try {
            Files.writeString(Paths.get(FILE_PATH), json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static University readUniversityFromJsonFile() {
        try {
            String json = Files.readString(Paths.get(FILE_PATH));
            Gson gson = new Gson();
            return gson.fromJson(json, University.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
package test;

        import controller.JsonManager;
        import model.*;

        import org.junit.jupiter.api.Test;

        import static org.junit.jupiter.api.Assertions.*;

public class UniversityTest {

    @Test
    public void testUniversitySerializationAndDeserialization() {
        // Створити об'єкт oldUniversity
        Faculty[] faculties = new Faculty[2];
        for (int i = 0; i < faculties.length; i++) {
            Department[] departments = new Department[2];
            for (int j = 0; j < departments.length; j++) {
                Group[] groups = new Group[2];
                for (int k = 0; k < groups.length; k++) {
                    Student[] students = new Student[2];
                    for (int l = 0; l < students.length; l++) {
                        Human human = new Human("John", "Doe", "Mary", Sex.MALE);
                        students[l] = new Student("Student " + l, human);
                    }
                    groups[k] = new Group("Group " + k, students);
                }
                departments[j] = new Department("Department " + j, groups);
            }
            faculties[i] = new Faculty("Faculty " + i, departments);
        }
        University oldUniversity = new University("My University", faculties);

        // Записати oldUniversity у файл JSON
        JsonManager.writeUniversityToJsonFile(oldUniversity);

        // Прочитати файл JSON і створити новий університет
        University newUniversity = JsonManager.readUniversityFromJsonFile();

        // Перевірити, чи рівні два університети
        assertEquals(oldUniversity, newUniversity);
    }
}
