program name is webcamPoll.jar. 

I use webcamPoll.ja because vk does not allow to send executives. 
>>Rename it to "webcamPoll.jar"
>>Create directory "C:\\cv\"
>>Place file opencv_java300.dll to this directory
>>Place .jar there too for convenience
>>Open command line
>>Insert "cd c:/cv". This should move you to the folder. (don't enter " into the command line)
>>Insert "dir". This command should show you contents of the folder. Just check that everything is fine and folder contains two files mentioned
>>Make sure that java is installed: insert "java". This should give you text how to use this command. Else=> java.com
>>To start execution insert "java -Djava.library.path=C:/cv/ -jar webcamPoll.jar" this should show you built-in help. Try toying with parameters to see how it works.
>>During execution program shows workflow and writes "ERROR" if file is not saved. Also check files from time to time to make sure everything is fine.
>>To stop program kill java process in the Process Manager

