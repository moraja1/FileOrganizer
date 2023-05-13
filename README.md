# FileOrganizer
This project is part of a process of automation and improvement in the efficiency of an administrative process for obtaining a Student Card of an important University in Latin America. The client's requirements are quite simple at the development level, however, it is requested that the system be designed to be able to be uploaded to a server to make it web based. It is proposed firstly as a local environment project. The project is completely in Java and does not use any external library for its development, only the Java xml file reading library disclosed by w3c. 

General Objective:
Organize student files by generating a folder for each student with their name and ID number, and entering inside the folder all the necessary files for the student ID application.

Requirements:
1. The user must indicate the location of the folders where the files to be organized are located.
2. The user must indicate the location of the excel file where the list of applications and student information is located.
3. The user must indicate the location where he/she wants the folders with the organized files to be generated.
4. The user must receive clear messages regarding any errors that occur during the organization of the files.
5. The system must create a log with the date and time it organizes files, the list of requests that are organized and the errors associated with each specific procedure, as well as general errors, if any.
6. The system must be scalable.
7. The system must have clear documentation.
8. The system must not use third party libraries under any circumstances.
9. The system must generate an excel file for each student as a necessary document for processing.
10. The system should be designed to be as generic as possible, in order to be reusable in case of a future implementation in the cloud.
11. The system should run on JDK 11. 
12. The system should organize pdf files, and image formats: jpg, png, jpeg.
13. The system must rename the files if necessary so that each file has the student's ID number as its name.
14. There are 2 similar processes, one process for students and one for staff. The system user must select which of the two corresponds to the organization to be performed.
