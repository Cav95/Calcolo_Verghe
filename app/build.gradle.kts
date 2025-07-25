plugins {
    // Apply the java plugin to add support for Java
    java

    // Apply the application plugin to add support for building a CLI application
    // You can run your app via task "run": ./gradlew run
    application

    /*
     * Adds tasks to export a runnable jar.
     * In order to create it, launch the "shadowJar" task.
     * The runnable jar will be found in build/libs/projectname-all.jar
     */
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("org.danilopianini.gradle-java-qa") version "1.75.0"
}

repositories { // Where to search for dependencies
    mavenCentral()
}

dependencies {
    // Suppressions for SpotBugs
    compileOnly("com.github.spotbugs:spotbugs-annotations:4.8.6")

    // Maven dependencies are composed by a group name, a name and a version, separated by colons
    implementation("com.omertron:API-OMDB:1.5")
    implementation("org.jooq:jool:0.9.15")

    implementation("com.google.guava:guava:24.0-jre") 

    implementation ("org.apache.poi:poi:5.2.3")        // Supporto per .xls
    implementation ("org.apache.poi:poi-ooxml:5.2.3")   // Supporto per .xlsx
    implementation ("org.apache.commons:commons-collections4:4.4")
    implementation ("org.apache.xmlbeans:xmlbeans:5.1.1")
    implementation ("org.apache.poi:poi-ooxml-lite:5.2.3")


    /*
     * Simple Logging Facade for Java (SLF4J) with Apache Log4j
     * See: http://www.slf4j.org/
     */
    val slf4jVersion = "2.0.16"
    implementation("org.slf4j:slf4j-api:$slf4jVersion")
    // Logback backend for SLF4J
    runtimeOnly("ch.qos.logback:logback-classic:1.5.12")

    // JUnit API and testing engine
    val jUnitVersion = "5.11.3"
    // when dependencies share the same version, grouping in a val helps to keep them in sync
    testImplementation("org.junit.jupiter:junit-jupiter-api:$jUnitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$jUnitVersion")

        // https://mvnrepository.com/artifact/org.javatuples/javatuples
    implementation("org.javatuples:javatuples:1.2")

    // https://mvnrepository.com/artifact/com.itextpdf/itext7-core
    implementation("com.itextpdf:itext7-core:7.2.5")
}

application {
    // Define the main class for the application.
    mainClass.set("CalcoloTubolare.MainCalcoloTubolare")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events(*org.gradle.api.tasks.testing.logging.TestLogEvent.values())
        showStandardStreams = true
    }
}