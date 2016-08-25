# Group 3
SOFTENG306 - Project 1 - Group 3

Overview:

This program is used to schedule tasks into processors to achieve an optimal schedule. To do this you must have an input file ready in the .dot format and the jar file for the program.

To run the program:
- Open a terminal and navigate to where the jar file and input file is located
- Into the terminal type: java -jar “your jar filename”.jar “your input filename”.dot P [OPTION] 

- replace “your jar filename” with the name of your jar file
- replace “your input filename” with the name of your .dot input file 
- replace P with an integer value for the number of processors you want to schedule the tasks in
- replace [OPTION] with the desired options you want to run the program on

- Input: a .dot file
- Options : −p N (use N cores for parallel execution, default is sequential) 
            −v visualize the search 
            −o OUPUT output file is named OUTPUT ( default is Input−output.dot )

When visualising the program a GUI will show up with one panel for statistics and information, a best solution panel which shows the current best solution as a table, a graph panel which shows the input file as a graph, and a panel which shows the number of threads running as rows and the task nodes being scheduled as columns. The node currently being examined by the thread is coloured in green.


Build instructions: 

- Make sure to have Maven installed on the system you are trying to build on.
- Open a terminal and navigate to where the project is located.
- Clean the project by typing the following command in the terminal: mvn clean
- Build the project by typing the following command in the terminal: mvn install
- The jar file will be located in the target file

Location for information:
https://github.com/taranpreetkohli/bronies/wiki

Location for plans:
Can be found in a folder called projectPlans in the main branch of the GIT repository
https://github.com/taranpreetkohli/bronies/tree/master/projectPlans





