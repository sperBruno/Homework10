
import java.util.List;
import java.util.HashSet;
import java.util.Set;

/*
 * SD2x Homework #10
 * Modify the method below so that it uses defensive programming.
 * Please be sure not to change the method signature!
 */

public class FriendFinder {

	protected ClassesDataSource classesDataSource;
	protected StudentsDataSource studentsDataSource;

	public FriendFinder(ClassesDataSource cds, StudentsDataSource sds) {
		classesDataSource = cds;
		studentsDataSource = sds;
	}

	public Set<String> findClassmates(Student theStudent) {
		List<String> myClasses;
		Set<String> classmates = new HashSet<String>();
		//assert this.classesDataSource != null ;
		//assert this.studentsDataSource != null ;
		if (theStudent == null || theStudent.getName() == null) {
			throw new IllegalArgumentException("the student is null");
		}
		
		if (this.classesDataSource == null || this.studentsDataSource == null) {
			//throw new IllegalArgumentException("the student is null");
			//return classmates;
			
			throw new IllegalStateException("the student is null");
		}
		try {
			String name = theStudent.getName();
			if (classesDataSource == null || classesDataSource.getClasses(name) == null) {
				return null;
			}
			// find the classes that this student is taking
			myClasses = classesDataSource.getClasses(name);

			// use the classes to find the names of the students
			for (String myClass : myClasses) {
				// list all the students in the class
				if (myClass == null) {
					continue;
				}
				
				List<Student> students = studentsDataSource.getStudents(myClass);
				if (students == null || students.isEmpty()) {
					return null;

				}
				for (Student student : students) {
					if (student == null || student.getName() == null) {
						continue;
					}
					// find the other classes that they're taking
					List<String> theirClasses = classesDataSource.getClasses(student.getName());

					// see if all of the classes that they're taking are the
					// same as the ones this student is taking
					boolean same = true;
					for (String c : myClasses) {
						if (c == null) {
							continue;
						}
						if (theirClasses.contains(c) == false) {
							same = false;
							break;
						}
					}
					if (same) {
						if (student.getName().equals(name) == false && classmates.contains(student.getName()) == false)
							classmates.add(student.getName());
					}
				}

			}
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			// System.out.println("null pointer exception");
			return null;
		}

		if (classmates.isEmpty()) {
			return null;
		} else
			return classmates;
	}

}
