@echo off
set JYTHON_HOME=C:\Apps\jython-2.0
java -Dpython.home=%JYTHON_HOME% -cp lib\alhambra.jar;lib\core.jar;lib\ga.jar;lib\tiles.jar;%JYTHON_HOME%\jython.jar org.tiling.alhambra.app.AlhambraApplication lib\tiles.jar
