

Hi Insight team,

My program was built on Eclipse IDE on a CentOS 7 environment.
No external dependencies were necessary.
The program was tested with a large bulk of data without errors(2 GiB in roughly 10min). I used regular jdk libraries such as jav.util.Date, java.io.BufferedReader, java.io.BufferedWriter, java.util.ArrayList and java.swing.JOptionPane.

In case the run.sh file doesn't run. The programcan also be start through the command: bash java -jar ./src/run.jar

My project contains 4 unit tests. The first being the original provided as example., then I have a failed attempt, also an output that I was able to replicate and lastly, a 2 GiB file that I downloaded from EDGAR weblogs.

My approach in the project was to minimize coupling by having a class for each function (storing data, input, output and main), create methods with 1 function only, and try to come up with the most efficeint algorythm to skim through objects while reading and appending output. I truly enjoyed the challenge, unfortunately I couldn't have dedicated a day for workingon it until today due to my work schedule.

I look forward to upcoming projects and to verbalize  my passion  in softwaredevelopment.


Sincerely,


Nuno Correia
NCorreia1@my.devry.edu
       
# InsightCodingChallenge
