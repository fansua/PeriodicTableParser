*************** PERIODIC TABLE PARSER ****************************


1.0. SETUP 

 1. Ensure you have the latest JDK, JDK8 
 2. Ensure you have at least Maven 3.3.3
 3. Esure you have a local repository created at: ~/.m2/



2.0 HOW TO RUN (via commandline)

0. go to your home directory and type git clone https://github.com/fansua/PeriodicTableParser.git
1. type: cd periodictableparser ---- takes you in the periodcitabler parser directory
2. type: mvn clean install  ---   builds the entire project( Once this has finished executed, the .json and .xml files will be available under the src/main/resources directory. 
3. type: cd  karaf/target/assembly/bin  ---- takes you to the folder that contains the karaf executable
4. type: ./karaf - runs the karaf instance(wait a while or hit enter)
5. Once inside the karaf terminal,  type: log:tail | grep PeriodictableparserProvider 
6. type shutdown -f   to exit karaf terminal 

