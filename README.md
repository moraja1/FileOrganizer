# FileOrganizer
This is a project proposal for automating and improving the efficiency of an administrative process for obtaining a Student Card of an important University in Latin America. The project is entirely in Java and does not use any external library for its development, except for the Java xml file reading library disclosed by w3c that is currently part of the JDK.

Executive Summary
The main objective of this project is to organize student and employee files by creating a folder for each with their name and ID number and placing inside the folder all the necessary files for the student ID application. This will reduce manual work, human errors and processing time. The project will also generate an excel file for each student as a necessary document for processing. The project will be designed to be scalable and deployable to a server in the future, if required by the client.

Project Background
The current process of obtaining a Student Card involves several steps that require manual intervention and verification. The students have to submit various documents, such as proof of identity, photografies, etc., in different formats (pdf, jpg, png, jpeg) to a designated Google Forms. The staff then have to download the attachments, check their validity and completeness, and store them in different folders according to the student's name and ID number. The staff also have to create an excel file for each student with their personal and academic information, which is used for further processing. This process is time-consuming, prone to errors and inefficient.

Proposed Solution
The purpose of this system is to organize files according to a list of applications and student information. The system has several requirements that must be met in order to function properly and efficiently. These requirements are:

- The user must indicate the location of the folders where the files to be organized are located.
- The user must indicate the location of the excel file where the list of applications and student information is located.
- The user must indicate the location where he/she wants the folders with the organized files to be generated.
- The user must receive clear messages regarding any errors that occur during the organization of the files.
- The system must create a log with the date and time it organizes files, the list of requests that are organized and the errors associated with each specific procedure, as well as general errors, if any.
- The system must be scalable.
- The system must have clear documentation.
- The system must not use third party libraries under any circumstances.
- The system must generate an excel file for each student as a necessary document for processing.
- The system should be designed to be as generic as possible, in order to be reusable in case of a future implementation in the cloud.
- The system should run on JDK 11.
- The system should organize pdf files, and image formats: jpg, png, jpeg.
- The system must rename the files if necessary so that each file has the student's ID number as its name.
- There are 2 similar processes, one process for students and one for staff. The system user must select which of the two corresponds to the organization to be performed.

Project Timeline
The project will be completed in 12 weeks, following these milestones:

- Week 1: Project planning and analysis
- Week 2: Designing the user interface and data structures
- Week 3-5: Implementing the excel file generation functionality
- Week 6: Implementing the file reading functionality
- Week 7: Implementing the file writing functionality
- Week 8: Implementing the folder creation and organization functionality
- Week 9: Testing and debugging
- Week 10: Writing documentation and comments
- Week 11: Delivering the first prototype and getting feedback from the client
- Week 12: Incorporating feedback and making improvements
- Week 13: Delivering the second prototype and getting feedback from the client
- Week 14: Incorporating feedback and making final adjustments
- Week 15: Delivering the final product and providing support

Conclusion
This project proposal presents a solution for automating and improving the efficiency of an administrative process for obtaining a Student Card of an important University in Latin America. The project will deliver a Java application that organizes student files by creating folders for each student with their name and ID number and placing inside them all the necessary files for processing. The project team has the skills and experience to deliver a high-quality product that meets or exceeds client expectations.

The entire logic of the proyect is being developed on Figma in the following url: https://www.figma.com/file/bVLHo0LxgvvDriddrMPbVV/Untitled?type=whiteboard&node-id=0%3A1&t=Avmig897usB7r3gG-1. You can go ahead and check it if you want. All the logic is being written in spanish.
