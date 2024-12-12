**ONLY** if you're using a Gradle in your Java environment to build (as I did), add the following to the "build.gradle.kts":

*// Add these configurations to enable preview features*

*tasks.withType<JavaCompile> {*
    *options.compilerArgs.add("--enable-preview")*
    *options.release.set(21)*
*}*

*tasks.withType<Test> {*
    *jvmArgs("--enable-preview")*
*}*

*tasks.withType<JavaExec> {*
    *jvmArgs("--enable-preview")
}*

MUST-DO
---------------------------------------------------------------
 Must construct a .java with the file name being "InMemoryDB.java" as to run InMemoryDB class since it is public. Other than that, you are fine then to run in Java environment

 How to Modify to be Official Assignment
 ----------------------------------------
 Be clear about the return values and data types for methods; said to return "null" for keys that did not exist when you cannot return null except in an Integer wrapper class.
 Specificity needs to be there, with actual provided expectation of what the desired output should look like or resemble. In terms of transactions, it would be more realistic if there
 were more operations, such as a delete().
