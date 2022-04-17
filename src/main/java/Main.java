// 16/04/2022, Yuriy Lomtev
// ДЗ-9: Имеется следующая структура:
//class Student {
//    String getName() {
//                    ...
//    }
//    List<Course> getAllCourses() {
//                    ...
//    }
//}
//class Course {
//    String name ...
//}
//  1. Написать функцию, принимающую список Student и возвращающую список уникальных курсов,
//  на которые подписаны студенты.
//  2. Написать функцию, принимающую на вход список Student и возвращающую список из трех самых любознательных
//  (любознательность определяется количеством курсов).
//  3. Написать функцию, принимающую на вход список Student и экземпляр Course, возвращающую список студентов,
//  которые посещают этот курс.
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
// готовим список-БД студентов и их курсов, например, по 5:
        String k="Курс";
        String s="Студент";
        List<Student> students = Arrays.asList(
                new Student(s+"1",Arrays.asList(
                        new Course(k+"1"))),
                new Student(s+"2",Arrays.asList(
                        new Course(k+"1"),
                        new Course(k+"2"))),
                new Student(s+"3",Arrays.asList(
                        new Course(k+"1"),
                        new Course(k+"2"),
                        new Course(k+"3"))),
                new Student(s+"4",Arrays.asList(
                        new Course(k+"1"),
                        new Course(k+"2"),
                        new Course(k+"3"),
                        new Course(k+"4"))),
                new Student(s+"5",Arrays.asList(
                        new Course(k+"1"),
                        new Course(k+"2"),
                        new Course(k+"3"),
                        new Course(k+"4"),
                        new Course(k+"5"))));
// выбор всех уникальных неповторяющихся названий курсов:
        System.out.println("Уникальные курсы: "+uniqueCourses(students));
// Поиск самых любознательных
        System.out.println("Три самых любознательных студента (по числу изучаемых курсов): "+getTopThreeInquisitiveStudents(students));
// Поиск студентов по курсу - ввод произвольный:
        System.out.print("Введите курс для поиска посещающих его студентов: ");
        Scanner in = new Scanner(System.in);
        String courseInput = in.nextLine();
        System.out.println("Кто ходит на курс "+courseInput+": "+findStudentsByCourse(students, new Course(courseInput)));
    }

// п 1 ДЗ: для поиска всех уникальных курсов выбрана HashSet-коллекция, как содержащая уникальные значание:
    public static Set<String> uniqueCourses(List<Student> students) {
// из Студентов вынимаем все курсы:
        List<Course> courses = students.stream().flatMap(element -> element.getAllCourses().stream()).toList();
        Set<String> hash = new HashSet<>();
// Поток-List - в строку с уничтожением дублей:
        for (Course p : courses) { hash.add(""+p); }
        return hash;
    }

// п.2 ДЗ: тут разобрался с алгоритмом сравнения числа курсов у студентов,
// простой перебор/сортировка числа курсов, по-моему, проще и надежнее, но здесь красивее:
    public static List<Student> getTopThreeInquisitiveStudents(List<Student> from) {
        return from.stream()
                .sorted( new Comparator<Student>() { // Существуют не только анонимные методы, но и анонимные
                    @Override
                    // классы. В данном случае создан анонимный класс
                    public int compare(Student o1, Student o2) {        // наследник класса Comparator, в котором определено
                        if (o1.getAllCourses().size() > o2.getAllCourses().size()) {// поведение сравнения.
                            return -1;
                        } else if (o1.getAllCourses().size() < o2.getAllCourses().size()) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }
                })
                .limit(3) // ограничиваемся 3 топовыми студентами
                .collect(Collectors.toList());
    }

// п.3 ДЗ: тут все понятно, но вставил ввод иского курса в Main:
    public static List<Student> findStudentsByCourse(List<Student> from, Course course) {
        return from.stream()
                .filter(student -> student.getAllCourses().contains(course)) // Найти студента такого, что в его списке
                .collect(Collectors.toList());                               // курсов значится переданный в метод.
    }
}
