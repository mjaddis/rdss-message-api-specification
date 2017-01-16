@ECHO OFF
SET JRE_HOME=
SET VALIDATOR_HOME=
SET RDSS_MESSAGE_API_DOCS=

SETLOCAL EnableDelayedExpansion
SET CLASSPATH="%VALIDATOR_HOME%\config\
FOR /R %VALIDATOR_HOME%/lib %%a IN (*.jar) DO (
	set CLASSPATH=!CLASSPATH!;%%a
)
SET CLASSPATH=!CLASSPATH!"

%JRE_HOME%\bin\java.exe -cp %CLASSPATH% -Dproject.base.path="%RDSS_MESSAGE_API_DOCS%" uk.ac.jisc.rdss.SchemaValidatorTestSuite
