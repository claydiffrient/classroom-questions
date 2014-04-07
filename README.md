classroom-questions
===================

A system by which questions are sent from a teacher laptop to student laptops 
via multicast after which the students can respond to the teacher with an answer 
all implemented in Java.

The build.xml file included works well for use in the Linux Lab on the
BYU-Idaho campus.

Using `ant compile` will download dependencies and build the files.

Using `ant run` will perform the same as `ant compile` however it will
also run the file.  It conditionally checks for the presence of
`/mnt/local/jdk1.8.0/bin/java` and will run using Java 8 if that file is
found.  Otherwise it will run using Java 7.
Please note that the classpath dependencies for Java 7 in the build file is
a bit off therefore, I suggest using Java 8 (which will happen by default in
the Linux Lab).