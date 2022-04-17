import java.util.Objects;

public class Course {
    public String name;

    public Course(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

// Переопределение для поиска одинаковых курсов у Студентов:
    @Override
    public boolean equals(Object o) {
        Course course = (Course) o;
        return Objects.equals(name, course.name);
    }
}
