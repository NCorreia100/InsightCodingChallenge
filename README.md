

Hi Insight team,



My program was built on Eclipse IDE on a CentOS 7 environment.
No external dependencies were necessary.I used regular jdk libraries such as jav.util.Date, java.io.BufferedReader, java.io.BufferedWriter, java.util.ArrayList and java.swing.JOptionPane.




In case the run.sh file doesn't run. The programcan also be start through the command: bash java -jar ./src/run.jar. Once the program runs, there will be no feedback until the program finished, note that it takes up to 10min to process cvs files of 2 GiB and above as this was tested without errors being reported.



My project contains 4 unit tests. The first being the original provided as example, then I have a failed attempt, also an output that I was able to replicate from the testsuite provided, and lastly, I had a 2 GiB file that I downloaded from EDGAR weblogs (which I wasn't able to upload through git bash.

I truly  enjoyed this challenge despite the short notice. I wasn't ablt to take days off from work to do it and I didn't get a day off until Tuesday. What took the longest wasn't coding related, the CentOS that I had on my PC wouldn't start so I had to install it again which didn't work until I used virtualbox.




My approach in the project was to minimize coupling by having a class for each function (storing data, input, output and main), create methods with a signle function, and try to come up with the most efficeint algorythm to skim through objects while reading and appending output. 



I look forward to upcoming projects and to verbalize  my passion  in softwaredevelopment.




Sincerely,





Nuno Correia

NCorreia1@my.devry.edu
      